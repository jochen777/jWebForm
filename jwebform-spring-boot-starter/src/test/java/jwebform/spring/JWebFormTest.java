package jwebform.spring;


import static org.junit.Assert.assertTrue;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Size;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import jwebform.integration.DefaultBean2Form;
import jwebform.integration.Bean2Form;
import jwebform.integration.beanvalidation.BeanValidationRuleDeliverer;
import jwebform.integration.beanvalidation.BeanValidationValidator;
import jwebform.integration.beanvalidation.ExternalValidation;
import jwebform.integration.beanvalidation.ExternalValidationDescription;
import org.junit.Test;
import jwebform.FormResult;
import jwebform.themes.FormRenderer;
import jwebform.themes.sourcecode.ThemeJavaRenderer;
import jwebform.themes.sourcecode.mapper.StandardMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JWebFormTest {

  // test if bean-validation works in JWebForm
  @Test
  public void testRun_beanValidation() {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Bean2Form bean2FromContract = getBean2Form(factory.getValidator());
    JWebFormProperties properties = new JWebFormProperties();
    FormRenderer formRenderer = new ThemeJavaRenderer(
        new StandardMapper(jwebform.themes.sourcecode.BootstrapTheme.instance(msg -> msg)));

    JWebForm jwebform = new JWebForm(ExampleRequests.exampleSubmittedRequest("name", "test"),
        ExampleRequests.emptySessionGet(), ExampleRequests.emptySessionPut(),
        ExampleRequests.stupidModel(), bean2FromContract, properties, formRenderer);

    FormResult fr = jwebform.run(new MyForm10());
    assertTrue(
        "The form should be not okay, beause the validation should fail ('test' is smaller than 10 chars)",
        !fr.isOk());

    FormResult fr2 = jwebform.run(new MyForm2());
    assertTrue("The form should be okay, 'test' is bigger than 2 chars", fr2.isOk());

  }




  public class MyForm10 {
    @Size(min = 10, max = 200, message = "The name must be between 10 and 200 chars")
    public String name = "";
  }

  public class MyForm2 {
    @Size(min = 2, max = 200, message = "The name must be between 10 and 200 chars")
    public String name = "";
  }

  private Bean2Form getBean2Form(Validator validator) {
    return new DefaultBean2Form(getBeanValidator(validator), getRuleDeliverer(validator));
  }

  private BeanValidationRuleDeliverer getRuleDeliverer(Validator validator) {
    return (bean, name) -> {
      Set<ExternalValidationDescription> criteraSet = new HashSet<>();
      BeanDescriptor i = validator.getConstraintsForClass(bean.getClass());
      PropertyDescriptor b = i.getConstraintsForProperty(name);
      if (b != null) {
        Set<ConstraintDescriptor<?>> z = b.getConstraintDescriptors();
        z.forEach(constraintDesc -> {
          criteraSet.add(new ExternalValidationDescription(
            constraintDesc.getAnnotation().annotationType().getSimpleName(),
            constraintDesc.getAttributes()));

        });
      }
      return criteraSet;
    };
  }

  private BeanValidationValidator getBeanValidator(Validator validator) {

    return (b) -> {
      Set<ConstraintViolation<Object>> vals = validator.validate(b);
      List<ExternalValidation> externalVals = new ArrayList<>();
      vals.forEach(constr -> {
        ExternalValidation e =
          new ExternalValidation(constr.getPropertyPath().toString(), constr.getMessage());
        externalVals.add(e);
      });

      return externalVals;
    };
  }
}

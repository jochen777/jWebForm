package jwebform.integration;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;
import org.junit.Test;
import jwebform.Form;
import jwebform.integration.beanvalidation.BeanValidationRuleDeliverer;
import jwebform.integration.beanvalidation.BeanValidationValidator;
import jwebform.integration.beanvalidation.ExternalValidation;
import jwebform.validation.Criterion;
import jwebform.validation.criteria.Required;

public class TestBeanValidationTransfer {

  @Test
  public void test_beanValidationTransferToCritera() {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Form f = new Bean2From(getBeanValidator(validator), getRuleDeliverer(validator))
        .getFormFromBean(new MyFormRequired());
    Criterion[] criteria = f.getFields().get(0).criteria;
    assertTrue("There must be exact one critera (required) ", criteria.length == 1);
    assertTrue("The found critera must be 'required' ", (criteria[0] instanceof Required));
  }


  public class MyFormRequired {
    @NotEmpty
    public String name;
  }


  private BeanValidationRuleDeliverer getRuleDeliverer(Validator validator) {
    return (bean, name) -> {
      Set<String> criteraSet = new HashSet<>();
      BeanDescriptor i = validator.getConstraintsForClass(bean.getClass());
      PropertyDescriptor b = i.getConstraintsForProperty(name);
      Set<ConstraintDescriptor<?>> z = b.getConstraintDescriptors();
      z.forEach(constraintDesc -> {
        criteraSet.add(constraintDesc.getAnnotation().annotationType().getName());

      });
      return criteraSet;
    };
  }


  private BeanValidationValidator getBeanValidator(Validator validator) {

    return (b) -> {
      Set<ConstraintViolation<Object>> vals = validator.validate(b);
      List<ExternalValidation> externalVals = new ArrayList<>();
      vals.forEach(constr -> {
        ExternalValidation e = new ExternalValidation();
        e.fieldName = constr.getPropertyPath().toString();
        e.validationMessage = constr.getMessage();
        externalVals.add(e);
      });

      return externalVals;
    };
  }
}

package jwebform.integration;

import jwebform.Form;
import jwebform.integration.bean2form.DefaultBean2Form;
import jwebform.integration.beanvalidation.BeanValidationRuleDeliverer;
import jwebform.integration.beanvalidation.BeanValidationValidator;
import jwebform.integration.beanvalidation.ExternalValidation;
import jwebform.integration.beanvalidation.ExternalValidationDescription;
import jwebform.validation.Criterion;
import jwebform.validation.criteria.MaxLength;
import jwebform.validation.criteria.Required;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class TestBeanValidationTransfer {

  static final int MAX = 40;

  @Test
  public void test_beanValidationTransferToCriteraEmpty() {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Form f = new DefaultBean2Form(getBeanValidator(validator), getRuleDeliverer(validator))
        .getFormFromBean(new MyFormRequired());
    Criterion[] criteria = f.getFields().get(0).criteria;
    assertTrue("There must be exact one critera (required) ", criteria.length == 1);
    assertTrue("The found critera must be 'required' ", (criteria[0] instanceof Required));
  }

  public class MyFormRequired {
    @NotEmpty
    public String name;
  }


  @Test
  public void test_beanValidationTransferToCriteraMaxLen() {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Form f = new DefaultBean2Form(getBeanValidator(validator), getRuleDeliverer(validator))
        .getFormFromBean(new MyFormSize());
    Criterion[] criteria = f.getFields().get(0).criteria;
    assertTrue("There must be exact one critera (MaxLenghth) ", criteria.length == 1);
    assertTrue("The found critera must be 'required' ", (criteria[0] instanceof MaxLength));
    MaxLength ml = (MaxLength) criteria[0];
    assertTrue("The maxlen must be: " + MAX, ml.getMaxLength() == MAX);

  }

  public class MyFormSize {
    @Size(max = MAX)
    public String street;
  }


  private BeanValidationRuleDeliverer getRuleDeliverer(Validator validator) {
    return (bean, name) -> {
      Set<ExternalValidationDescription> criteraSet = new HashSet<>();
      BeanDescriptor i = validator.getConstraintsForClass(bean.getClass());
      PropertyDescriptor b = i.getConstraintsForProperty(name);
      Set<ConstraintDescriptor<?>> z = b.getConstraintDescriptors();
      z.forEach(constraintDesc -> {
        criteraSet.add(new ExternalValidationDescription(
            constraintDesc.getAnnotation().annotationType().getSimpleName(),
            constraintDesc.getAttributes()));

      });
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

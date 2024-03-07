package jwebform.integration;

import jakarta.validation.*;
import jwebform.Form;
import jwebform.integration.bean2form.Bean2Form;
import jwebform.integration.bean2form.DefaultBean2Form;
import jwebform.integration.beanvalidation.BeanValidationRuleDeliverer;
import jwebform.integration.beanvalidation.BeanValidationValidator;
import jwebform.integration.beanvalidation.ExternalValidation;
import jwebform.integration.beanvalidation.ExternalValidationDescription;
import jwebform.validation.Criterion;
import jwebform.validation.criteria.MaxLength;
import jwebform.validation.criteria.Required;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.Test;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.metadata.BeanDescriptor;
import jakarta.validation.metadata.ConstraintDescriptor;
import jakarta.validation.metadata.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestBeanValidationTransfer {

  static final int MAX = 40;

  @Test
  public void test_beanValidationTransferToCriteraEmpty() {

    Configuration<?> i = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator());
    Validator validator;
    try (ValidatorFactory vf = i.buildValidatorFactory()) {
      validator = vf.getValidator();
    }

      Form f = new DefaultBean2Form(getBeanValidator(validator), getRuleDeliverer(validator))
        .getFormFromBean(new MyFormRequired());
    Criterion[] criteria = f.getFields().get(0).criteria;
      assertEquals("There must be exact one critera (required) ", 1, criteria.length);
    assertTrue("The found critera must be 'required' ", (criteria[0] instanceof Required));
  }

  public class MyFormRequired {
    @NotEmpty
    public String name;
  }


  @Test
  public void test_beanValidationTransferToCriteraMaxLen() {

    Configuration<?> i = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator());
    Validator validator;
    try (ValidatorFactory vf = i.buildValidatorFactory()) {
      validator = vf.getValidator();
    }

      Form f = new DefaultBean2Form(getBeanValidator(validator), getRuleDeliverer(validator))
        .getFormFromBean(new MyFormSize());
    Criterion[] criteria = f.getFields().get(0).criteria;
      assertEquals("There must be exact one critera (MaxLenghth) ", 1, criteria.length);
    assertTrue("The found critera must be 'required' ", (criteria[0] instanceof MaxLength));
    MaxLength ml = (MaxLength) criteria[0];
      assertEquals("The maxlen must be: " + MAX, MAX, ml.getMaxLength());

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

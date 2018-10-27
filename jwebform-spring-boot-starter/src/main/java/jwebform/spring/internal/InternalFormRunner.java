package jwebform.spring.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;
import jwebform.Form;
import jwebform.FormResult;
import jwebform.env.Env;
import jwebform.integration.Bean2From;
import jwebform.integration.beanvalidation.BeanValidationRuleDeliverer;
import jwebform.integration.beanvalidation.BeanValidationValidator;
import jwebform.integration.beanvalidation.ExternalValidation;
import jwebform.integration.beanvalidation.ExternalValidationDescription;
import jwebform.processor.FormGenerator;

public class InternalFormRunner {



  public FormResult run(Object input, Env env, BiConsumer<String, Object> model,
      Validator validator) {
    Form form = null;
    if (input instanceof FormGenerator) {
      form = ((FormGenerator) input).generateForm();
    } else {
      form = new Bean2From(getBeanValidator(validator), getRuleDeliverer(validator))
          .getFormFromBean(input);
    }
    FormResult fr = form.run(env);

    // RFE: What can we do, if we have more than one Form on the page?
    // RFE: Should be configurable!
    model.accept("form", fr.getView());

    return fr;
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
        ExternalValidation e = new ExternalValidation();
        e.fieldName = constr.getPropertyPath().toString();
        e.validationMessage = constr.getMessage();
        externalVals.add(e);
      });

      return externalVals;
    };
  }
}

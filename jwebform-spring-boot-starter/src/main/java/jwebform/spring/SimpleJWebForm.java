package jwebform.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.BeanUtils;
import jwebform.Form;
import jwebform.FormResult;
import jwebform.env.Env;
import jwebform.env.EnvBuilder;
import jwebform.env.Request;
import jwebform.env.SessionGet;
import jwebform.env.SessionSet;
import jwebform.integration.Bean2From;
import jwebform.integration.beanvalidation.BeanValidationValidator;
import jwebform.integration.beanvalidation.ExternalValidation;
import jwebform.processor.FormGenerator;

// Container, that holds a jWebForm Form (or a normal bean) and provides a facade to jwebform
// objects
// typically this will be filled by the framework
public class SimpleJWebForm<T> {
  private final Env env;
  private final BiConsumer<String, Object> model;
  private final FormResult formResult;
  private final T bean;
  private final Validator validator;

  public SimpleJWebForm(Class<T> typeOfBean, Request request, SessionGet sessionGet,
      SessionSet sessionSet, BiConsumer<String, Object> model, Validator validator) {
    this.env = new EnvBuilder().of(request, sessionGet, sessionSet);
    this.model = model;
    this.bean = BeanUtils.instantiateClass(typeOfBean);
    this.validator = validator;
    this.formResult = run(bean);
  }



  private FormResult run(T input) {
    Form form = null;
    if (input instanceof FormGenerator) {
      form = ((FormGenerator) input).generateForm();
    } else {
      form = new Bean2From(getValidator()).getFormFromBean(input);
    }
    FormResult fr = form.run(env);

    // RFE: What can we do, if we have more than one Form on the page?
    // RFE: Should be configurable!
    model.accept("form", fr.getView());

    return fr;
  }

  private BeanValidationValidator getValidator() {

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



  public boolean isOk() {
    return formResult.isOk();
  }

  public String getStringValue(String name) {
    return formResult.getStringValue(name);
  }

  public T getBean() {
    return bean;
  }

}

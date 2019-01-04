package jwebform.integration;

import jwebform.FormResult;

public class FormResultAndBean {
  private final FormResult formResult;
  private final Object bean;

  public FormResultAndBean(FormResult formResult, Object bean) {
    this.formResult = formResult;
    this.bean = bean;
  }

  // convenience methods
  public final boolean isValid() {
    return formResult.isValid();
  }
  public final boolean isSubmitted() {
    return formResult.isSubmitted();
  }

  public FormResult getFormResult() {
    return formResult;
  }

  public Object getBean() {
    return bean;
  }
}

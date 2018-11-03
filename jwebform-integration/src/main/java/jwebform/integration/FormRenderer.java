package jwebform.integration;

import jwebform.FormModel.Method;
import jwebform.FormResult;

public interface FormRenderer {
  public String render(FormResult result, Method method, boolean html5Validation,
      MessageSource messageSource);
}

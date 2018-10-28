package jwebform.themes;

import jwebform.FormResult;
import jwebform.View.Method;
import jwebform.themes.common.MessageSource;

public interface FormRenderer {
  public String render(FormResult result, Method method, boolean html5Validation,
      MessageSource messageSource);
}

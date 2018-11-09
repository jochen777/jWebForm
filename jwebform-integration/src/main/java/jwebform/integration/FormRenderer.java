package jwebform.integration;

import jwebform.FormModel.Method;
import jwebform.FormResult;

/**
 *  Renders a from REsult to HTML. Find implementations for this in jwebform-themes project
  */
public interface FormRenderer {
  public String render(FormResult result, Method method, boolean html5Validation);
}

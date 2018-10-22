package jwebform.view;

import jwebform.View;
import jwebform.View.Html5Validation;
import jwebform.View.Method;
import jwebform.processor.FieldResults;

@FunctionalInterface
public interface ViewGenerator {
  View build(String formId, FieldResults fieldResults, Method method,
      Html5Validation html5Validation);
}

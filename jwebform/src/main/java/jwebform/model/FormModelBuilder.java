package jwebform.model;

import jwebform.FormModel;
import jwebform.FormModel.Html5Validation;
import jwebform.FormModel.Method;
import jwebform.FormResult;

@Deprecated
@FunctionalInterface
public interface FormModelBuilder {
  FormModel build(
    FormResult formResult, Method method,
      Html5Validation html5Validation);
}

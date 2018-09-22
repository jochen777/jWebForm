package jwebform.processor;

import jwebform.Form;

@FunctionalInterface
public interface FormGenerator {
  Form generateForm();
}

package jwebform.integration.bean2form;

import jwebform.Form;

/**
 * Converts a bean to a Form.
 *
 */
@FunctionalInterface
public interface Bean2Form {
  Form getFormFromBean(Object bean);
}

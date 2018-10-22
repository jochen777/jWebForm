package jwebform.integration.beanvalidation;

import java.util.List;

@FunctionalInterface
public interface BeanValidationValidator {
  List<ExternalValidation> getValidationResults(Object bean);
}

package jwebform.integration.beanvalidation;

import java.util.Set;

@FunctionalInterface
public interface BeanValidationRuleDeliverer {

  Set<ExternalValidationDescription> getCriteriaForField(Object bean, String fieldname);
}

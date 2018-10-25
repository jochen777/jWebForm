package jwebform.integration.beanvalidation;

import java.util.Set;

@FunctionalInterface
public interface BeanValidationRuleDeliverer {

  Set<String> getCriteriaForField(Object bean, String fieldname);
}

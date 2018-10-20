package jwebform.integration;

import java.beans.PropertyDescriptor;

import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.processor.FieldResults;
import org.apache.commons.beanutils.PropertyUtils;
import jwebform.FormResult;

public class FormResultWithBean extends FormResult {


  public FormResultWithBean(String formId, FieldResults fieldResults, boolean formIsValid, Object bean) {
    super(formId, fieldResults, formIsValid);
    fillBean(bean);
  }

  // RFE: this is kind of ugly, because it modifies the bean. Better: Make a new object of this and copy all elements in it.
  // not it can not immutable!
  private void fillBean(Object bean) {
    FieldResults fieldResults = this.getFieldResults();

    try {
      for (Field field : fieldResults.getContainers()) {
        FieldResult result = fieldResults.get(field);
        String key = result.getStaticFieldInfo().getName();
        if (PropertyUtils.isWriteable(bean, key)) {
          PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(bean, key);

          Object className = pd.getPropertyType().getName();
          if (className.equals("java.lang.String")) {
            PropertyUtils.setSimpleProperty(bean, key, result.getValue());
          } else if (className.equals("java.lang.Boolean") || className.equals("boolean")) {
            if ("true".equals(result.getValue())) {
              PropertyUtils.setSimpleProperty(bean, key, true);
            } else {
              PropertyUtils.setSimpleProperty(bean, key, false);
            }
          } else if (className.equals("java.lang.Integer") || className.equals("int")) {
            try {
              if (result.getValue() != null && !"".equals(result.getValue())) {
                PropertyUtils.setSimpleProperty(bean, key, Integer.parseInt(result.getValue()));
              }
            } catch (NumberFormatException e) {
              // logger.info("unable to set int property because can not parse to int", e);
            }
          } else if (className.equals("java.lang.Long") || className.equals("long")) {
            try {
              PropertyUtils.setSimpleProperty(bean, key, Long.parseLong(result.getValue()));
            } catch (NumberFormatException e) {
              // logger.info("unable to set long property because can not parse to int", e);
            }
          } else if (className.equals("java.time.LocalDate")) {
            // if (elem instanceof DateInputCompound) {
            // Date dateVal = ((DateInputCompound) elem).getDateValue();
            // LocalDate dateValLocalDate = new java.sql.Date(dateVal.getTime()).toLocalDate();
            // PropertyUtils.setSimpleProperty(bean, key, dateValLocalDate);
            // }
          }
        } else {
          // logger.info("unable to fill property coming from form: " + key);
        }
      }
    } catch (Exception e) {
      throw new FillBeanExecption("Problem with filling the bean from from.", e);
    }
    // // Run hook if bean wants it to get additionalThings out of the form
    // if (bean instanceof FormCheckerBean) {
    // ((FormCheckerBean) bean).postRun(form);
    // }
  }

  public class FillBeanExecption extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FillBeanExecption(String msg, Exception e) {
      super(msg, e);
    }

  }

}

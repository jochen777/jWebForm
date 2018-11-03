package jwebform.integration.bean2form;

import java.util.Optional;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.processor.FieldResults;

public class FillBeanWithFieldResults {

  public void fill(Object bean, FieldResults fieldResults) {
    for (Field fieldofForm : fieldResults.getContainers()) {

      FieldResult result = fieldResults.get(fieldofForm);
      String fieldname = result.getStaticFieldInfo().getName();


      try {
        java.lang.reflect.Field fieldOfBean = bean.getClass().getField(fieldname);
        Object toSetComingFromForm = result.getValueObject();
        if (toSetComingFromForm instanceof Optional) {
          toSetComingFromForm = ((Optional) toSetComingFromForm).orElseGet(() -> null);
        }
        fieldOfBean.set(bean, toSetComingFromForm);
      } catch (NoSuchFieldException | SecurityException | IllegalArgumentException
          | IllegalAccessException e) {
        // TODO Auto-generated catch block
        // e.printStackTrace();
        // Fail silently RFE: log this!
      }

      /*
       * if (PropertyUtils.isWriteable(bean, key)) { PropertyDescriptor pd =
       * PropertyUtils.getPropertyDescriptor(bean, key);
       * 
       * Object className = pd.getPropertyType().getName(); if
       * (className.equals("java.lang.String")) { PropertyUtils.setSimpleProperty(bean, key,
       * result.getValue()); } else if (className.equals("java.lang.Boolean") ||
       * className.equals("boolean")) { if ("true".equals(result.getValue())) {
       * PropertyUtils.setSimpleProperty(bean, key, true); } else {
       * PropertyUtils.setSimpleProperty(bean, key, false); } } else if
       * (className.equals("java.lang.Integer") || className.equals("int")) { try { if
       * (result.getValue() != null && !"".equals(result.getValue())) {
       * PropertyUtils.setSimpleProperty(bean, key, Integer.parseInt(result.getValue())); } } catch
       * (NumberFormatException e) { //
       * logger.info("unable to set int property because can not parse to int", e); } } else if
       * (className.equals("java.lang.Long") || className.equals("long")) { try {
       * PropertyUtils.setSimpleProperty(bean, key, Long.parseLong(result.getValue())); } catch
       * (NumberFormatException e) { //
       * logger.info("unable to set long property because can not parse to int", e); } } else if
       * (className.equals("java.time.LocalDate")) { // if (elem instanceof DateInputCompound) { //
       * Date dateVal = ((DateInputCompound) elem).getDateValue(); // LocalDate dateValLocalDate =
       * new java.sql.Date(dateVal.getTime()).toLocalDate(); //
       * PropertyUtils.setSimpleProperty(bean, key, dateValLocalDate); // } } } else { //
       * logger.info("unable to fill property coming from form: " + key); }
       */
    }
    // // Run hook if bean wants it to get additionalThings out of the form
    // if (bean instanceof FormCheckerBean) {
    // ((FormCheckerBean) bean).postRun(form);
    // }
  }

}

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
        // Fail silently RFE: log this!
      }

    }
  }

}

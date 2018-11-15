package jwebform.integration.bean2form;

import java.util.Optional;
import jwebform.FormResult;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.processor.FieldResults;
import jwebform.model.FormModelBuilder;

public class FormResultWithBean extends FormResult {

  private final Object bean;

  public FormResultWithBean(String formId, FieldResults fieldResults, boolean formIsValid, boolean isFirstRun,
      FormModelBuilder viewBuilder, Object bean) {
    super(formId, fieldResults, formIsValid, isFirstRun, viewBuilder);
    this.bean = bean;
    /*
     * if (bean instanceof JWebFormBean) { fillBean(bean, ((JWebFormBean) bean).postRun(this)); }
     * else { fillBean(bean, this); }
     *
     */
  }

  public Object getBean() {
    return bean;
  }
}

package jwebform.themes;

import jwebform.FormModel;
import jwebform.FormResult;
import jwebform.integration.FormRenderer;

// a FormModel with the ability to render html via Theming
public class FormModelWithFormRenderer extends FormModel {

  private final FormRenderer formRenderer;

  public FormModelWithFormRenderer(FormResult formResult, Method method,
      Html5Validation html5Validation, FormRenderer formRenderer) {
    super(formResult, method, html5Validation);
    this.formRenderer = formRenderer;
  }



  public String getHtml() {
    return formRenderer.render(getFormResult(), getMethodObject(),
        getHtml5Validaiton().asBoolean());
  }

}

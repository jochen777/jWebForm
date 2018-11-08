package jwebform.themes;

import jwebform.FormModel;
import jwebform.FormResult;
import jwebform.integration.FormRenderer;
import jwebform.integration.MessageSource;
import jwebform.processor.FieldResults;

// a FormModel with the ability to render html via Theming
public class FormModelWithFormRenderer extends FormModel {

  private final FormRenderer formRenderer;
  private final MessageSource messgMessageSource;

  public FormModelWithFormRenderer(
    FormResult formResult, Method method, Html5Validation html5Validation, FormRenderer formRenderer, MessageSource messgMessageSource) {
    super(formResult, method, html5Validation);
    this.formRenderer = formRenderer;
    this.messgMessageSource = messgMessageSource;
  }



  public String getHtml() {
    return formRenderer.render(getFormResult(), getMethodObject(), getHtml5Validaiton().asBoolean());
  }

}

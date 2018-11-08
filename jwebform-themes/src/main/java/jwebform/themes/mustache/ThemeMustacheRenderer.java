package jwebform.themes.mustache;

import java.util.HashMap;
import java.util.Map;
import jwebform.FormResult;
import jwebform.FormModel;
import jwebform.FormModel.Html5Validation;
import jwebform.FormModel.Method;
import jwebform.integration.FormRenderer;
import jwebform.integration.MessageSource;
import jwebform.themes.common.StartEndRenderer;

// renders the form with help of mustache
public class ThemeMustacheRenderer implements FormRenderer {


  private final MustacheRenderer renderer;
  private final MessageSource messageSource;

  public ThemeMustacheRenderer(MustacheRenderer renderer, MessageSource messageSource) {
    this.renderer = renderer;
    this.messageSource = messageSource;
  }



  @Override
  public String render(FormResult result, Method method, boolean html5Validation
      ) {
    FormModel view = result.getFormModel(Html5Validation.ON, Method.GET);
    StartEndRenderer startEndRenderer = new StartEndRenderer(result, "POST", true);

    String tmpl = "{{>jwebform/common/start}}\n" + "\n" + "{{ #model.drawableElements }}\n"
        + "{{>jwebform/common/handle_type}}\n" + "{{ /model.drawableElements }}\n" + "\n"
        + "{{>jwebform/common/end}}";
    Map<String, Object> elements = new HashMap<>();
    elements.put("model", view);
    elements.put("startEnd", startEndRenderer);
    return renderer.render(tmpl, elements);
  }


}

package jwebform.themes.mustache;

import java.util.HashMap;
import java.util.Map;
import jwebform.FormResult;
import jwebform.View;
import jwebform.View.Html5Validation;
import jwebform.View.Method;
import jwebform.themes.FormRenderer;
import jwebform.themes.common.MessageSource;
import jwebform.themes.common.StartEndRenderer;

// renders the form with help of mustache
public class ThemeMustacheRenderer implements FormRenderer {


  private final MustacheRenderer renderer;

  public ThemeMustacheRenderer(MustacheRenderer renderer) {
    this.renderer = renderer;
  }



  @Override
  public String render(FormResult result, Method method, boolean html5Validation,
      MessageSource messageSource) {
    View view = result.getView(Html5Validation.ON, Method.GET);
    StartEndRenderer startEndRenderer = new StartEndRenderer(result, "POST", true);

    String tmpl = "{{>jwebform/common/start}}\n" + "\n" + "{{ #view.drawableElements }}\n"
        + "{{>jwebform/common/handle_type}}\n" + "{{ /view.drawableElements }}\n" + "\n"
        + "{{>jwebform/common/end}}";
    Map<String, Object> elements = new HashMap<>();
    elements.put("view", view);
    elements.put("startEnd", startEndRenderer);
    return renderer.render(tmpl, elements);
  }


}

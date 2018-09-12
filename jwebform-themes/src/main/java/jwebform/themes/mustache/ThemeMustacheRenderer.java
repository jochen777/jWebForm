package jwebform.themes.mustache;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import com.samskivert.mustache.Mustache;
import jwebform.FormResult;
import jwebform.View;
import jwebform.View.Html5Validation;
import jwebform.View.Method;
import jwebform.themes.common.MessageSource;
import jwebform.themes.common.StartEndRenderer;

// renders the form with help of mustache
public class ThemeMustacheRenderer {

  public static final String BOOTSTRAP = "bootstrap";

  public ThemeMustacheRenderer() {}


  public String render(FormResult result, MessageSource messageSource) {
    View view = result.getView(Html5Validation.ON, Method.GET);
    StartEndRenderer renderer = new StartEndRenderer(result, "POST", true);
    Mustache.Compiler c = Mustache.compiler().withLoader(new Mustache.TemplateLoader() {
      public Reader getTemplate(String name) throws FileNotFoundException {
        String templateName = "templates/" + BOOTSTRAP + "/" + name + ".html";
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(templateName);
        return new InputStreamReader(in);
      }
    });
    String tmpl = "{{>jwebform/common/start}}\n" + "\n" + "{{ #view.drawableElements }}\n"
        + "{{>jwebform/common/handle_type}}\n" + "{{ /view.drawableElements }}\n" + "\n"
        + "{{>jwebform/common/end}}";
    Map<String, Object> elements = new HashMap<>();
    elements.put("view", view);
    elements.put("startEnd", renderer);
    return c.compile(tmpl).execute(elements);
  }


}

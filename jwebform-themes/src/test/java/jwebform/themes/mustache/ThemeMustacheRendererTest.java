package jwebform.themes.mustache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import com.samskivert.mustache.Mustache;
import jwebform.Form;
import jwebform.FormResult;
import jwebform.View.Method;
import jwebform.env.Env;
import jwebform.env.EnvBuilder;
import jwebform.themes.FormRenderer;
import jwebform.themes.MyFormBuilder;
import jwebform.themes.SimpleTemplate;

public class ThemeMustacheRendererTest {
  // TODO: Test if complete form is valid!!
  String formId = "fid";

  SimpleTemplate template = new SimpleTemplate();

  // @Test
  public void testnormalUsageFirstRun() {

    Env env = new EnvBuilder().of(it -> null, // this simulates the first run (all values null)
        t -> t, (k, v) -> {
        });
    boolean result = testFormAgainstRequest(env, "test/expectedHTMLExampleForm_firstrun.html");
    assertTrue("The form should be false, because it is the firstrun", !result);
  }


  // @Test
  public void testnormalUsageSubmitSuccess() {
    Env env = new EnvBuilder().of(it -> {
      if (it.equals("WF_SUBMITTED")) {
        return "WF-fid";
      }
      return "1";

    }, // this simulates the input of the names
        t -> t, (k, v) -> {
        });
    boolean result = testFormAgainstRequest(env, "test/expectedHTMLExampleForm_submitted.html");
    assertTrue("The form should be true, because input-fields should be okay", result);
  }

  // @Test
  public void testnormalUsageSubmitError() {
    Env env = new EnvBuilder().of(it -> {
      if (it.equals("WF_SUBMITTED")) {
        return "WF-fid";
      }
      return "";

    }, // this simulates empty inputs
        t -> t, (k, v) -> {
        });
    boolean result = testFormAgainstRequest(env, "test/expectedHTMLExampleForm_error.html");
    assertTrue("The form should be false, because some fields are required or reqire a number",
        !result);
  }


  // @Test
  public void testnormalUsageSubmitVarious() {
    Env env = new EnvBuilder().of(it -> {
      if (it.equals("WF_SUBMITTED")) {
        return "WF-fid";
      }
      if (("textInput").equals(it)) {
        return "1";
      } else
        return "";

    }, t -> t, (k, v) -> {
    });
    boolean result = testFormAgainstRequest(env, "test/expectedHTMLExampleForm_various.html");
    assertTrue("The form should be false, because some fields are required or reqire a number",
        !result);
  }


  private boolean testFormAgainstRequest(Env env, String templateName) {
    MyFormBuilder formBuilder = new MyFormBuilder(formId);
    Form f = formBuilder.buildForm();
    FormResult result = f.run(env);



    FormRenderer renderer = new ThemeMustacheRenderer(new MustacheRendererImpl());
    String content = renderer.render(result, Method.POST, true, msg -> msg).trim();
    String filecontent;
    try {
      filecontent = this.template.loadAndProcessTempalte(templateName);
      assertEquals(filecontent.trim(), content);
      return result.isOk();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return !result.isOk();
  }


  public class MustacheRendererImpl implements MustacheRenderer {
    public static final String BOOTSTRAP = "bootstrap";

    private final Mustache.Compiler c;

    public MustacheRendererImpl() {
      c = Mustache.compiler().withLoader(new Mustache.TemplateLoader() {
        public Reader getTemplate(String name) throws FileNotFoundException {
          String templateName = "templates/" + BOOTSTRAP + "/" + name + ".html";
          InputStream in = this.getClass().getClassLoader().getResourceAsStream(templateName);
          return new InputStreamReader(in);
        }
      });
    }

    @Override
    public String render(String templ, Map<String, Object> model) {
      return c.compile(templ).execute(model);
    }


  }

}

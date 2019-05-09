package jwebform.themes.sourcecode;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import jwebform.Form;
import jwebform.FormModel;
import jwebform.env.EnvBuilder;
import jwebform.env.Request;
import jwebform.integration.MessageSource;
import jwebform.integration.bean2form.DefaultBean2Form;
import jwebform.integration.bean2form.annotations.UseDecoration;
import jwebform.themes.ExampleRequests;
import jwebform.themes.sourcecode.mapper.StandardMapper;

public class TestDecorationAnnotation {

  public class TestForm_Not_TranslatedAnnotation {
    @UseDecoration(label = "NameNotTranslated", helpText = "helpTextNotTranslated",
        placeholder = "PlaceholderNotTranslated", isTranslated = false)
    public String name = "";
  }

  @Test
  public void testTranslatedKey() {
    TestForm_Not_TranslatedAnnotation testBean = new TestForm_Not_TranslatedAnnotation();
    String renderedForm = renderBean(testBean);
    assertTrue(renderedForm.contains("#namenottranslated#"));
    assertTrue(renderedForm.contains("#helptextnottranslated#"));
    assertTrue(renderedForm.contains("#placeholdernottranslated#"));
  }

  public class TestFormTranslatedAnnotation {
    @UseDecoration(label = "NameNotTranslated", helpText = "helpTextNotTranslated",
        placeholder = "PlaceholderNotTranslated", isTranslated = true)
    public String name = "";
  }

  @Test
  public void testNotTranslatedKey() {
    TestFormTranslatedAnnotation testBean = new TestFormTranslatedAnnotation();
    String renderedForm = renderBean(testBean);
    assertTrue(renderedForm.contains("NameNotTranslated"));
    assertTrue(renderedForm.contains("helpTextNotTranslated"));
    assertTrue(renderedForm.contains("PlaceholderNotTranslated"));
  }



  private String renderBean(Object testBean) {
    Form formFromBean = new DefaultBean2Form().getFormFromBean(testBean);
    Request request = new ExampleRequests("id").notSubmittedRequest();
    MessageSource msgSource = msg -> "#" + msg.toLowerCase() + "#";
    ThemeJavaRenderer renderer =
        new ThemeJavaRenderer(new StandardMapper(new BootstrapTheme(msgSource)));
    String renderedForm = renderer
        .render(formFromBean.run(new EnvBuilder().of(request)), FormModel.Method.POST, true).trim();
    return renderedForm;
  }



}

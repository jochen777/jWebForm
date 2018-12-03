package jwebform.themes.sourcecode;

import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.FormModel;
import jwebform.FormResult;
import jwebform.env.EnvBuilder;
import jwebform.env.Request;
import jwebform.field.builder.BuildInType;
import jwebform.processor.FormGenerator;
import jwebform.themes.ExampleRequests;
import jwebform.themes.FileReader;
import jwebform.themes.SimpleTemplate;
import jwebform.themes.sourcecode.mapper.StandardMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestCheckbox {

  FileReader fr = new FileReader();
  SimpleTemplate template = new SimpleTemplate();
  static String formId = "fid";



  @Test
  public void test_renderInitialCommit() throws IOException {
    FormResult result = getFormResult(true, false, false);
    assertEquals(processTemplate("test/checkbox_not_submitted.html"), renderForm(result));
  }

  @Test
  public void test_renderInitialCommitFalse() throws IOException {
    FormResult result = getFormResult(false, false, false);
    assertEquals(processTemplate("test/checkbox_not_submitted_false.html"), renderForm(result));
  }

  @Test
  public void test_renderSubmitClicked() throws IOException {
    FormResult result = getFormResult(false, true, true);
    assertEquals(processTemplate("test/checkbox_submitted_ok.html"), renderForm(result));
  }

  @Test
  public void test_renderSubmitNotClicked() throws IOException {
    FormResult result = getFormResult(false, true, false);
    assertEquals(processTemplate("test/checkbox_submitted_not_clicked_ok.html"), renderForm(result));
  }


  private String renderForm(FormResult result) {
    ThemeJavaRenderer renderer =
      new ThemeJavaRenderer(new StandardMapper(BootstrapTheme.instance(msg -> msg)));
    return renderer.render(result, FormModel.Method.POST, true).trim();
  }

  private String processTemplate(String s) throws IOException {
    return template.loadAndProcessTempalte(s);
  }


  private FormResult getFormResult(boolean initialValue, boolean submitted, boolean clicked) {
    ExampleRequests exampleRequests = new ExampleRequests(formId);
    Request request = exampleRequests.notSubmittedRequest();
    if (submitted) {
      request = exampleRequests.exampleSubmittedRequest("ch", clicked?"ch":"");
    }
    return new CheckBoxForm("ch", "lbl", "help", initialValue).generateForm().
      run(new EnvBuilder().of(request));
  }


  public static class CheckBoxForm implements FormGenerator {

    String name;
    String label;
    String helptext;
    boolean initial;

    public CheckBoxForm(String name, String label, String helptext, boolean initial) {
      this.name = name;
      this.label = label;
      this.helptext = helptext;
      this.initial = initial;
    }

    @Override public Form generateForm() {
      return FormBuilder.withId(formId).typeBuilder(BuildInType.array(BuildInType.checkbox(name, initial).
        label(label).
        helpText(helptext))).build();
    }
  }
}

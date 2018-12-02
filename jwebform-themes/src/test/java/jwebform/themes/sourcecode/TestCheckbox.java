package jwebform.themes.sourcecode;

import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.FormModel;
import jwebform.FormResult;
import jwebform.env.Env;
import jwebform.env.EnvBuilder;
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


  @Test
  public void testRenderInitialCommit() throws IOException {
    FormResult result = new CheckBoxForm("ch","lbl", "help", true).generateForm().
      run(new EnvBuilder().of(ExampleRequests.notSubmittedRequest())
      );

    ThemeJavaRenderer renderer = new ThemeJavaRenderer(
      new StandardMapper(jwebform.themes.sourcecode.BootstrapTheme.instance(msg -> msg)));
    String content = renderer.render(result, FormModel.Method.POST, true).trim();

    FileReader fr = new FileReader();
    SimpleTemplate template = new SimpleTemplate();
    String filecontent = template.loadAndProcessTempalte("test/checkbox_not_submitted.html");
    assertEquals(filecontent, content);
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
      return FormBuilder.withId("fid").typeBuilder(BuildInType.array(BuildInType.checkbox(name, initial).
        label(label).
        helpText(helptext))).build();
    }
  }
}

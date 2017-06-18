package jwebform.usage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jwebform.Env;
import jwebform.Form;
import jwebform.FormResult;
import jwebform.View;
import jwebform.element.SimpleElement;
import jwebform.element.SubmitButton;
import jwebform.element.TextInput;

public class SampleUsage {

	@Test
	public void normalUsage() {
		Form f = new Form();
		f.addElement(new SimpleElement());
		f.addElement(new SimpleElement());
		f.addElement(new TextInput("textInput", "SampleTextInput", "Peter\"Paul", ""));
		f.addElement(new SubmitButton("Submit"));
		Env env = new Env();
		FormResult result = f.run(env);
		if (result.isOk()) {
			View view = result.getView();
			assertEquals("<form name=\"FORMCHECKER_id\" method=\"POST\" "
					+ "id=\"id\" novalidate>simplesimple"
					+ "<label for=\"form-id-textInput\">SampleTextInput:</label>"
					+ "<input tabindex=\"0\" type=\"text\" name=\"textInput\" value=\"Peter&quot;Paul\">"
					+ "<input tabindex=\"0\" type=\"submit\" value=\"Submit\"></form>", view.getHtml());
		}
	}
}

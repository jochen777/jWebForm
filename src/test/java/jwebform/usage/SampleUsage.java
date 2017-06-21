package jwebform.usage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.View;
import jwebform.element.SimpleElement;
import jwebform.element.SubmitButton;
import jwebform.element.TextInput;
import jwebform.env.Env;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;

public class SampleUsage {

	@Test
	public void normalUsage() {
		Form f = createForm();
		Env env = new Env(it -> null);
		FormResult result = f.run(env);
		if (result.isOk()) {
			View view = result.getView();
			assertEquals("<form name=\"FORMCHECKER_id\" method=\"POST\" "
					+ "id=\"id\" novalidate>\nsimple\n"
					+ "simple\n"
					+ "<div class=\"form-group\"><label for=\"form-id-textInput\">SampleTextInput:</label>"
					+ "<input tabindex=\"0\" type=\"text\" name=\"textInput\" value=\"Peter&quot;Paul\"></div>\n"
					+ "<input tabindex=\"0\" type=\"submit\" value=\"Submit\"></form>", view.getHtml());
		}
	}

	private Form createForm() {
		Form f = new Form();
		f.addElement(new SimpleElement());
		f.addElement(new SimpleElement());
		f.addElement(new TextInput("textInput", "SampleTextInput", "Peter\"Paul", "", new Validator(Criteria.required())));
		f.addElement(new SubmitButton("Submit"));
		return f;
	}
}

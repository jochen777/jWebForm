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

	String expectedFormStart = "<form name=\"FORMCHECKER_id\" method=\"POST\" "
			+ "id=\"id\" novalidate>\n";
	String exectedFormEnd = "</form>";
	String exectedSimpleElement = "simple\n";
	String exectedSubmitButton = "<input tabindex=\"0\" type=\"submit\" value=\"Submit\">";
	
	@Test
	public void normalUsageFirstRun() {
		Form f = createForm();
		Env env = new Env(it -> null);
		FormResult result = f.run(env);
		if (result.isOk()) {
			View view = result.getView();
			assertEquals(expectedFormStart + exectedSimpleElement
					+ exectedSimpleElement
					+ "<div class=\"form-group\"><label for=\"form-id-textInput\">SampleTextInput:</label>"
					+ "<input tabindex=\"0\" type=\"text\" name=\"textInput\" value=\"Peter&quot;Paul\"></div>\n"
					+ exectedSubmitButton + exectedFormEnd, view.getHtml());
		}
	}

	@Test
	public void normalUsageSubmitSuccess() {
		Form f = createForm();
		Env env = new Env(it -> it);
		FormResult result = f.run(env);
		if (result.isOk()) {
			View view = result.getView();
			assertEquals(expectedFormStart + exectedSimpleElement
					+ exectedSimpleElement
					+ "<div class=\"form-group has-success\"><label for=\"form-id-textInput\">SampleTextInput:</label>"
					+ "<input tabindex=\"0\" type=\"text\" name=\"textInput\" value=\"textInput\"></div>\n"
					+ exectedSubmitButton + exectedFormEnd, view.getHtml());
		}
	}
	
	@Test
	public void normalUsageSubmitError() {
		Form f = createForm();
		Env env = new Env(it -> "");
		FormResult result = f.run(env);
		if (result.isOk()) {
			View view = result.getView();
			assertEquals(expectedFormStart + exectedSimpleElement
					+ exectedSimpleElement
					+ "<div class=\"form-group has-error\">Problem: jformchecker.required<br><label for=\"form-id-textInput\">SampleTextInput:</label>"
					+ "<input tabindex=\"0\" type=\"text\" name=\"textInput\" value></div>\n"
					+ exectedSubmitButton + exectedFormEnd, view.getHtml());
		}
	}
	
	
	private Form createForm() {
		Form f = new Form();
		f.addElement(new SimpleElement());
		f.addElement(new SimpleElement());
		TextInput textInput = new TextInput("textInput", "SampleTextInput", "Peter\"Paul", "", new Validator(Criteria.required()));
		f.addElement(textInput);
		f.addElement(new SubmitButton("Submit"));
		return f;
	}
}

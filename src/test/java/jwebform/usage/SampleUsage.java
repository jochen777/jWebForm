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
	String exectedSubmitButton = "<input tabindex=\"2\" type=\"submit\" value=\"Submit\">";
	
	
	@Test
	public void normalUsageFirstRun() {
		Env env = new Env(it -> null);
		Form f = createForm(env);
		FormResult result = f.run();
		if (result.isOk()) {
			View view = result.getView();
			assertEquals(expectedFormStart + exectedSimpleElement
					+ exectedSimpleElement
					+ "<div class=\"form-group\"><label for=\"form-id-textInput\">SampleTextInput:</label>"
					+ "<input tabindex=\"0\" type=\"text\" name=\"textInput\" value=\"Peter&quot;Paul\"></div>\n"
					+ "<div class=\"form-group\"><label for=\"form-id-textInput2\">SampleTextInput:</label>"
					+ "<input tabindex=\"1\" type=\"text\" name=\"textInput2\" value=\"Peter&quot;Paul\"></div>\n"
					+ exectedSubmitButton + exectedFormEnd, view.getHtml());
		}
	}

	@Test
	public void normalUsageSubmitSuccess() {
		Env env = new Env(it -> it);
		Form f = createForm(env);
		FormResult result = f.run();
		if (result.isOk()) {
			View view = result.getView();
			assertEquals(expectedFormStart + exectedSimpleElement
					+ exectedSimpleElement
					+ "<div class=\"form-group has-success\"><label for=\"form-id-textInput\">SampleTextInput:</label>"
					+ "<input tabindex=\"0\" type=\"text\" name=\"textInput\" value=\"textInput\"></div>\n"
					+ "<div class=\"form-group has-success\"><label for=\"form-id-textInput2\">SampleTextInput:</label>"
					+ "<input tabindex=\"1\" type=\"text\" name=\"textInput2\" value=\"textInput2\"></div>\n"
					+ exectedSubmitButton + exectedFormEnd, view.getHtml());
		}
	}
	
	@Test
	public void normalUsageSubmitError() {
		Env env = new Env(it -> "");
		Form f = createForm(env);
		FormResult result = f.run();
		if (result.isOk()) {
			View view = result.getView();
			assertEquals(expectedFormStart + exectedSimpleElement
					+ exectedSimpleElement
					+ "<div class=\"form-group has-error\">Problem: jformchecker.required<br><label for=\"form-id-textInput\">SampleTextInput:</label>"
					+ "<input tabindex=\"0\" type=\"text\" name=\"textInput\" value></div>\n"
					+ "<div class=\"form-group has-error\">Problem: jformchecker.required<br><label for=\"form-id-textInput2\">SampleTextInput:</label>"
					+ "<input tabindex=\"1\" type=\"text\" name=\"textInput2\" value></div>\n"
					+ exectedSubmitButton + exectedFormEnd, view.getHtml());
		}
	}
	
	
	private Form createForm(Env env) {
		Form f = new Form();
		f.addElement(new SimpleElement());
		f.addElement(new SimpleElement());
		TextInput textInput = new TextInput("textInput", env.getRequest(), "SampleTextInput", "Peter\"Paul", "", new Validator(Criteria.required()));
		f.addElement(textInput);
		TextInput textInput2 = new TextInput("textInput2", env.getRequest(), "SampleTextInput", "Peter\"Paul", "", new Validator(Criteria.required()));
		f.addElement(textInput2);
		f.addElement(new SubmitButton("Submit"));
		return f;
	}
}

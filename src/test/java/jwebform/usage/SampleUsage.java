package jwebform.usage;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.View;
import jwebform.element.SimpleElement;
import jwebform.element.SubmitButton;
import jwebform.element.TextInput;
import jwebform.element.structure.Element;
import jwebform.env.Env;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;

public class SampleUsage {

	String expectedFormStart = "<form name=\"fid-FORMCHECKER\" method=\"POST\" "
			+ "id=\"fid\" novalidate>\n";
	String exectedFormEnd = "</form>";
	String exectedSimpleElement = "simple\n";
	String exectedSubmitButton = "<input tabindex=\"2\" type=\"submit\" value=\"Submit\">";
	String expectedHelpForTextInput = "<span id=\"helpBlock-textInput2\" class=\"help-block\">Help-Text</span>";
	String placeholder = "thisplaceholder";
	
	
	@Test
	public void normalUsageFirstRun() {
		Env env = new Env(it -> null);
		Form f = createForm(env);
		FormResult result = f.run();
		if (result.isOk()) {
			View view = result.getView();
			assertEquals(expectedFormStart + exectedSimpleElement
					+ exectedSimpleElement
					+ "<div class=\"form-group\"><label for=\"fid-textInput\">SampleTextInput:</label>"
					+ "<input tabindex=\"0\" type=\"text\" name=\"fid-textInput\" value=\"Peter&quot;Paul\"></div>\n"
					+ "<div class=\"form-group\"><label for=\"fid-textInput2\">SampleTextInput:</label>"
					+ "<input tabindex=\"1\" type=\"text\" name=\"fid-textInput2\" value=\"Peter&quot;Paul\" placeholder=\""+placeholder+"\" aria-describedby=\"helpBlock-textInput2\">"
					+ expectedHelpForTextInput
					+ "</div>\n"
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
					+ "<div class=\"form-group has-success\"><label for=\"fid-textInput\">SampleTextInput:</label>"
					+ "<input tabindex=\"0\" type=\"text\" name=\"fid-textInput\" value=\"fid-textInput\"></div>\n"
					+ "<div class=\"form-group has-success\"><label for=\"fid-textInput2\">SampleTextInput:</label>"
					+ "<input tabindex=\"1\" type=\"text\" name=\"fid-textInput2\" value=\"fid-textInput2\" placeholder=\""+placeholder+"\" aria-describedby=\"helpBlock-textInput2\">"
					+ expectedHelpForTextInput
					+ "</div>\n"
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
					+ "<div class=\"form-group has-error\">Problem: jformchecker.required<br><label for=\"fid-textInput\">SampleTextInput:</label>"
					+ "<input tabindex=\"0\" type=\"text\" name=\"fid-textInput\" value></div>\n"
					+ "<div class=\"form-group has-error\">Problem: jformchecker.required<br><label for=\"fid-textInput2\">SampleTextInput:</label>"
					+ "<input tabindex=\"1\" type=\"text\" name=\"fid-textInput2\" value placeholder=\""+placeholder+"\" aria-describedby=\"helpBlock-textInput2\">"
					+ expectedHelpForTextInput
					+ "</div>\n"
					+ exectedSubmitButton + exectedFormEnd, view.getHtml());
		}
	}
	
	
	private Form createForm(Env env) {
		String formId = "fid";
		List<Element> elements = new ArrayList<>();
		elements.add(new SimpleElement());
		elements.add(new SimpleElement());
		TextInput textInput = new TextInput(formId, "textInput", env.getRequest(), "SampleTextInput", "Peter\"Paul", "", "", new Validator(Criteria.required()));
		elements.add(textInput);
		TextInput textInput2 = new TextInput(formId, "textInput2", env.getRequest(), "SampleTextInput", "Peter\"Paul", "Help-Text", placeholder,  new Validator(Criteria.required()));
		elements.add(textInput2);
		elements.add(new SubmitButton("Submit"));
		
		Form f = new Form(formId, elements, new ArrayList<>());
		
		return f;
	}
}

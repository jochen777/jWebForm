package jwebform.usage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.element.OneFieldDecoration;
import jwebform.element.SimpleElement;
import jwebform.element.SubmitButton;
import jwebform.element.TextDateInput;
import jwebform.element.TextInput;
import jwebform.element.XSRFProtection;
import jwebform.element.renderer.bootstrap.BootstrapTheme;
import jwebform.element.structure.Element;
import jwebform.env.Env;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;

public class SampleUsage {

	// TODO: Test if complete form is valid!!
	
	@Test
	public void testnormalUsageFirstRun() {
		Env env = new Env(it -> null, // this simulates the first run (all values null)
				t -> t,
				(k,v) -> {}
				); 
		boolean result = testFormAgainstRequest(env, "test/expectedHTMLExampleForm_firstrun.html");
		assertTrue("The form should be false, because it is the firstrun", !result);
	}


	@Test
	public void testnormalUsageSubmitSuccess() {
		Env env = new Env(it -> "1", // this simulates the input of the names
				t -> t,
				(k,v) -> {}
				); 
		boolean result = testFormAgainstRequest(env, "test/expectedHTMLExampleForm_submitted.html");
		assertTrue("The form should be true, because input-fields should be okay", result);
	}

	@Test
	public void testnormalUsageSubmitError() {
		Env env = new Env(it -> "", // this simulates empty inputs
				t -> t,
				(k,v) -> {}
				); 
		boolean result = testFormAgainstRequest(env, "test/expectedHTMLExampleForm_error.html");
		assertTrue("The form should be false, because some fields are required or reqire a number", !result);
	}

	private Form createForm() {
		// TODO: Add Custom FormValidator
		String formId = "fid";
		XSRFProtection protection = new XSRFProtection(true);	// no random values, so we can expect constant html

		TextInput textInput = new TextInput("textInput", new OneFieldDecoration("TextInputLabel"), "Peter\"Paul", new Validator(Criteria.required()));

		TextDateInput date = new TextDateInput("dateInput", new OneFieldDecoration("Please insert date", "datehelptext", ""), LocalDate.of(2017, 7, 4), new Validator());
		TextInput textInput2 = new TextInput("textInput2", new OneFieldDecoration("TextInputLabel2", "Help-Text", "Placeholder"), "Peter\"Paul",
				new Validator(Criteria.required()));

		List<FormValidator> formValidators = new ArrayList<>();
		formValidators.add(it -> {
			final Map<Element, ValidationResult> overridenValidationResults = new HashMap<>();
			String valueOfTextInput = it.get(textInput).getValue();
			if (valueOfTextInput.length() > 3) {
				overridenValidationResults.put(textInput, ValidationResult.fail("not_ok"));
			}
			return overridenValidationResults;
			});

		Form f = new Form(formId, formValidators, 
		    protection, 
		    new SimpleElement(), 
		    new SimpleElement(), 
		    textInput, 
		    date, 
		    textInput2, 
		    new SubmitButton("Submit")
		    );

		return f;
	}

	private boolean testFormAgainstRequest(Env env, String templateName) {
		Form f = createForm();
		FormResult result = f.run(env, new BootstrapTheme());

		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream(templateName);
		assertEquals(convertStreamToString(in).trim(), result.getView(new BootstrapTheme()).getHtml().trim());
		return result.isOk();
	}

	
	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

}

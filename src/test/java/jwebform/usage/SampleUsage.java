package jwebform.usage;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.element.SimpleElement;
import jwebform.element.SubmitButton;
import jwebform.element.TextDateInput;
import jwebform.element.TextInput;
import jwebform.element.XSRFProtection;
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
		testFormAgainstRequest(env, "test/expectedHTMLExampleForm_firstrun.html");

	}


	@Test
	public void testnormalUsageSubmitSuccess() {
		Env env = new Env(it -> it, // this simulates the input of the names
				t -> t,
				(k,v) -> {}
				); 
		testFormAgainstRequest(env, "test/expectedHTMLExampleForm_submitted.html");
	}

	@Test
	public void testnormalUsageSubmitError() {
		Env env = new Env(it -> "", // this simulates empty inputs
				t -> t,
				(k,v) -> {}
				); 
		testFormAgainstRequest(env, "test/expectedHTMLExampleForm_error.html");

	}

	private Form createForm() {
		// TODO: Add Custom FormValidator
		String formId = "fid";
		List<Element> elements = new ArrayList<>();
		XSRFProtection protection = new XSRFProtection(true);	// no random values, so we can expect constant html
		elements.add(protection);
		elements.add(new SimpleElement());
		elements.add(new SimpleElement());

		TextInput textInput = new TextInput("textInput", "TextInputLabel", "Peter\"Paul", "",
				"", new Validator(Criteria.required()));
		
		elements.add(textInput);

		TextDateInput date = new TextDateInput("dateInput", "Please insert date", LocalDate.of(2017, 7, 4),"datehelptext", new Validator());
		elements.add(date);
		TextInput textInput2 = new TextInput("textInput2", "TextInputLabel2", "Peter\"Paul",
				"Help-Text", "Placeholder", new Validator(Criteria.required()));
		elements.add(textInput2);
		elements.add(new SubmitButton("Submit"));

		List<FormValidator> formValidators = new ArrayList<>();
		formValidators.add(it -> {
			final Map<Element, ValidationResult> overridenValidationResults = new HashMap<>();
			String valueOfTextInput = it.get(textInput).getValue();
			if (valueOfTextInput.length() > 3) {
				overridenValidationResults.put(textInput, ValidationResult.fail("not_ok"));
			}
			return overridenValidationResults;
			});
		
		Form f = new Form(formId, elements, formValidators);

		return f;
	}

	private void testFormAgainstRequest(Env env, String templateName) {
		Form f = createForm();
		FormResult result = f.run(env);

		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream(templateName);
		assertEquals(convertStreamToString(in).trim(), result.getView().getHtml().trim());
	}

	
	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

}

package jwebform.usage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jwebform.Env;
import jwebform.Form;
import jwebform.FormResult;
import jwebform.View;
import jwebform.element.SimpleElement;

public class SampleUsage {

	@Test
	public void normalUsage() {
		Form f = new Form();
		f.addElement(new SimpleElement());
		f.addElement(new SimpleElement());
		Env env = new Env();
		FormResult result = f.run(env);
		if (result.isOk()) {
			View view = result.getView();
			assertEquals("<form name=\"FORMCHECKER_id\" method=\"POST\" "
					+ "id=\"id\" novalidate >simplesimple</form>", view.getHtml());
		}
	}
}

/*
 *
 * todo: 
 * - form-start und ende
 * - submit button
 * - simple input-element
 * - validate elements
 * - preset elements
 * - elements should expose a model
 * - form-id (mehrere forms pro Seite!)
 * - tabindex
 * - firstRun?
 * - xsrf
 * - maxLen (via request??)
 * - different themes: via: Each element has a has of renderers. Hash has always "default", but can have more (bootstrap, mobile...)
 *   - you can add a set of renderers to a form to style differently
 * 
 * 
 * Start:
 * <form name="TESTFORMID" id="form-TESTFORMID" novalidate  action="#" method="POST" >
<input type="hidden" name="submitted" value="FORMCHECKER-TESTFORMID">

*/
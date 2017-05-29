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

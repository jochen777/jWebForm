package jwebform;

import java.util.Map;

import jwebform.element.structure.Element;
import jwebform.element.structure.TabIndexAwareElement;
import jwebform.validation.ValidationResult;
import jwebform.view.StartEndRenderer;

public class View {

	Form form;
	Map<Element, ValidationResult> overridenValidationResults;
	
	public View(Form form, Map<Element, ValidationResult> overridenValidationResults) {
		super();
		this.form = form;
		this.overridenValidationResults = overridenValidationResults;
	}

	public String getHtml() {
		StartEndRenderer startEndRenderer = new StartEndRenderer(form);	// RFE: Remove new
		StringBuilder html = new StringBuilder();
		html.append(startEndRenderer.getStart());
		int tabIndex = 0;
		for (Element element : form.getElements()) {
			ValidationResult overridenValidationResult = overridenValidationResults.get(element);
			html.append(element.getHtml(tabIndex, overridenValidationResult));
			if (element instanceof TabIndexAwareElement) {
				tabIndex += ((TabIndexAwareElement) element).getTabIndexIncrement();
			}
		}
		html.append(startEndRenderer.getEnd());
		return html.toString();
	}
	
}

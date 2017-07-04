package jwebform;

import java.util.Map;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.RenderInfos;
import jwebform.element.structure.TabIndexAwareElement;
import jwebform.validation.ValidationResult;
import jwebform.view.StartEndRenderer;

public class View {

	private final Form form;
	private final Map<Element, ValidationResult> overridenValidationResults;
	
	public View(Form form, Map<Element, ValidationResult> overridenValidationResults) {
		this.form = form;
		this.overridenValidationResults = overridenValidationResults;
	}

	public String getHtml() {
		StartEndRenderer startEndRenderer = new StartEndRenderer(form);	// RFE: Remove new
		StringBuilder html = new StringBuilder();
		html.append(startEndRenderer.getStart());
		int tabIndex = 1;
		for (Element element : form.getElements()) {
			ValidationResult overridenValidationResult = overridenValidationResults.get(element);
			RenderInfos renderInfos = new RenderInfos(form.id, tabIndex, null, ValidationResult.undefined());
			ElementResult result = element.getHtml(renderInfos);
			html.append(result.getHtml());
			if (element instanceof TabIndexAwareElement) {
				tabIndex += ((TabIndexAwareElement) element).getTabIndexIncrement();
			}
		}
		html.append(startEndRenderer.getEnd());
		return html.toString();
	}
	
}

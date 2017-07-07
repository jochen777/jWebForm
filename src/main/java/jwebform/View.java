package jwebform;

import java.util.Map;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.RenderInfos;
import jwebform.element.structure.TabIndexAwareElement;
import jwebform.env.Env;
import jwebform.validation.ValidationResult;
import jwebform.view.StartEndRenderer;

public class View {

	private final Form form;
	private final Map<Element, ElementResult> elementResults;
	private final Env env;
	
	public View(Form form, Map<Element, ElementResult> elementResults, Env env) {
		this.form = form;
		this.elementResults = elementResults;
		this.env = env;
	}

	public String getHtml() {
		StartEndRenderer startEndRenderer = new StartEndRenderer(form);	// RFE: Remove new
		StringBuilder html = new StringBuilder();
		html.append(startEndRenderer.getStart());
		elementResults.forEach((element, elementResult)  -> {
		  String renderedHtml;
		  if (elementResult.getHtmlProducer() != null) {
		    renderedHtml = elementResult.getHtmlProducer().getHTML(elementResult.getValidationResult());
		  } else {
		    renderedHtml = elementResult.getHtml();
		  }
			html.append(renderedHtml);
		});
		
		/*for (Element element : form.getElements()) {
			ValidationResult overridenValidationResult = overridenValidationResults.get(element);
			if (overridenValidationResult == null) {
				overridenValidationResult = ValidationResult.undefined();
			}
			RenderInfos renderInfos = new RenderInfos(form.getId(), tabIndex, env, overridenValidationResult);
			ElementResult result = element.run(renderInfos);
			html.append(result.getHtml());
			if (element instanceof TabIndexAwareElement) {
				tabIndex += ((TabIndexAwareElement) element).getTabIndexIncrement();
			}
		}*/
		html.append(startEndRenderer.getEnd());
		return html.toString();
	}
	
}

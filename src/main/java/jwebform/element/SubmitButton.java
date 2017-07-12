package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.PrepareInfos;
import jwebform.element.structure.ProducerInfos;
import jwebform.validation.ValidationResult;

public class SubmitButton implements Element {

	private final String label;
	
	public SubmitButton(String label) {
		this.label = label;
	}

	@Override
	public ElementResult prepare(PrepareInfos renderInfos) {
		SubmitRenderer producer = (SubmitRenderer)renderInfos.getTheme().getHtmlProducer().get("jwebform.element.SubmitButton");
		if (producer == null) {
			producer = new DefaultSubmitRenderer();
		}
		producer.setLabel(label);
		return new ElementResult("submit", (HTMLProducer)producer, ValidationResult.ok(), "", 1, this, "jwebform.element.SubmitButton");
	}

	public class DefaultSubmitRenderer implements HTMLProducer, SubmitRenderer {
		
		private String label;
		
		@Override
		public String getHTML(ProducerInfos producerInfos){
			return "<input tabindex=\"" + producerInfos.getTabIndex() + "\" type=\"submit\" value=\"" + label + "\"><!-- own renderer -->";
		}

		@Override
		public void setLabel(String label) {
			this.label = label;
		}
		
	}
	
	public interface SubmitRenderer {
		public void setLabel(String label);
	}


}

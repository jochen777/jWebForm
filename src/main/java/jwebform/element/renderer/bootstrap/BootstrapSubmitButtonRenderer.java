package jwebform.element.renderer.bootstrap;

import jwebform.element.SubmitButton.SubmitRenderer;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;

public class BootstrapSubmitButtonRenderer implements HTMLProducer, SubmitRenderer{

	String label;
	
	@Override
	public String getHTML(ProducerInfos pi) {
		return "<input tabindex=\"" + pi.getTabIndex() + "\" type=\"submit\" value=\"" + label + "\">";
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}
}

package jwebform.element.renderer.bootstrap;

import jwebform.element.SubmitButton;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;

public class BootstrapSubmitButtonRenderer implements HTMLProducer{

	@Override
	public String getHTML(ProducerInfos pi) {
	    String label = ((SubmitButton)pi.getSource()).label;
		return "<input tabindex=\"" + pi.getTabIndex() + "\" type=\"submit\" value=\"" + label + "\">";
	}

}

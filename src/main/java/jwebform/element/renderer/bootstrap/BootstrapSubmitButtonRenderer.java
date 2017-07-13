package jwebform.element.renderer.bootstrap;

import jwebform.element.SubmitButton.SubmitButtonData;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;

public class BootstrapSubmitButtonRenderer implements HTMLProducer{

	@Override
	public String getHTML(ProducerInfos pi) {
	    SubmitButtonData data = (SubmitButtonData) pi.getStaticRenderData();
		return "<input tabindex=\"" + pi.getTabIndex() + "\" type=\"submit\" value=\"" + data.label + "\">";
	}

}

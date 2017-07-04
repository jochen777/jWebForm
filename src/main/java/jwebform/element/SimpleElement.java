package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.RenderInfos;

public class SimpleElement implements Element{

	
	@Override
	public ElementResult getHtml(RenderInfos renderInfos) {
		return new ElementResult("simple", "simple\n");
	}

	

}

package jwebform.element;

import jwebform.element.structure.Element;

public class SimpleElement implements Element{

	@Override
	public String getHtml(int tabIndex) {
		return "simple\n";
	}

}

package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.PrepareInfos;
import jwebform.validation.ValidationResult;

public class SimpleElement implements Element{

	@Override
	public ElementResult prepare(PrepareInfos renderInfos) {
		return new ElementResult("simple", producerInfos -> "simple\n");
	}

}

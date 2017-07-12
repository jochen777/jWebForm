package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.PrepareInfos;
import jwebform.element.structure.ProducerInfos;
import jwebform.validation.ValidationResult;

public class SimpleElement implements Element{

	@Override
	public ElementResult prepare(PrepareInfos renderInfos) {
		return new ElementResult("simple", new SimpleElementRenderer(), 
				ValidationResult.ok(), "", 0, this, "jwebform.element.SimpleElement");
	}

	public class SimpleElementRenderer implements HTMLProducer {
		@Override
		public String getHTML(ProducerInfos producerInfos){
			return "simple\n";
		}
		
	}

}

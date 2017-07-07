package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.RenderInfos;
import jwebform.element.structure.ValidationInfos;
import jwebform.validation.ValidationResult;

public class SimpleElement implements Element{

	
	@Override
	public ElementResult run(RenderInfos renderInfos) {
		return new ElementResult("simple", new SimpleElementRenderer(), "");
	}

	public class SimpleElementRenderer implements HTMLProducer {

		@Override
		public String getHTML(ValidationResult vr) {
			return "simple\n";
		}
		
	}

	@Override
	public ValidationResult validate(ValidationInfos validationInfos) {
		return ValidationResult.ok();
	}

}

package jwebform.element.structure;

import jwebform.validation.ValidationResult;

public interface Element {

	public ElementResult run(RenderInfos renderInfos);
	
	public ValidationResult validate(ValidationInfos validationInfos);
	
}

package jwebform.element.structure;

import jwebform.validation.ValidationResult;

public interface HTMLProducer {
	String getHTML(ValidationResult vr);
}

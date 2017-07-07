package jwebform.element.structure;

import jwebform.validation.ValidationResult;

@FunctionalInterface
public interface HTMLProducer {
	String getHTML(ValidationResult vr);
}

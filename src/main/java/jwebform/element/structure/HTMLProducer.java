package jwebform.element.structure;

import java.util.List;

import jwebform.validation.ValidationResult;

@FunctionalInterface
public interface HTMLProducer {
	String getHTML(Element inputSource, String formId, Object value, int tabIndex, ValidationResult vr, List<ElementResult> childs);

}

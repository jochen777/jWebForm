package jwebform;

import java.util.ArrayList;
import java.util.List;

import jwebform.element.Element;

// Represents a form
public class Form {
	
	List<Element> elements = new ArrayList<>();
	String id="id";
	
	
	public FormResult run(Env env) {
		return new FormResult(this);
	}

	public void addElement(Element element) {
		elements.add(element);
	}

	List<Element> getElements() {
		return elements;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

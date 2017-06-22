package jwebform;

import java.util.ArrayList;
import java.util.List;

import jwebform.element.structure.Element;
import jwebform.element.structure.TabIndexAwareElement;
import jwebform.element.structure.Validateable;
import jwebform.env.Env;
import jwebform.env.Request;

// Represents a form
public class Form {

	List<Element> elements = new ArrayList<>();
	String id = "id";

	public FormResult run() {
		// validate form
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

	public String getMethod() {
		return "POST"; // TODO: Make this configurable
	}

	public boolean isHtml5Validate() {
		return true; // TODO: Make this configurable
	}




}

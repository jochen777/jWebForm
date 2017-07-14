package jwebform.element.structure;

import java.util.List;

import jwebform.validation.ValidationResult;

public class ProducerInfos {
	private final String formId;
	private final int tabIndex;
	private final ValidationResult vr;
	private final List<ElementResult> childs;
	private final String name;
	private final String value;
	private final Element source;
	
	public ProducerInfos(String formId, int tabIndex, ValidationResult vr, 
	    List<ElementResult> childs, Element source, String name, String value) {
		this.formId = formId;
		this.tabIndex = tabIndex;
		this.vr = vr;
		this.childs = childs;
		this.source = source;
		this.name = name;
		this.value = value;
	}

	public String getFormId() {
		return formId;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public ValidationResult getVr() {
		return vr;
	}

	public List<ElementResult> getChilds() {
		return childs;
	}


  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

public Element getSource() {
	return source;
}
}

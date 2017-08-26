package jwebform.element.structure;

import java.util.List;

import jwebform.validation.ValidationResult;

// Infos, that the HTMLProducer needs to render the HTML. This will be provided by the form-run
public class ProducerInfos {
	private final String formId;
	private final int tabIndex;
	private final ValidationResult vr;
	private final List<ElementResult> childs;
	private final String name;
	private final String value;
	private final Element source;
	private final Theme theme;
	
	public ProducerInfos(String formId, int tabIndex, ValidationResult vr, 
	    List<ElementResult> childs, Element source, String name, String value, Theme theme) {
		this.formId = formId;
		this.tabIndex = tabIndex;
		this.vr = vr;
		this.childs = childs;
		this.source = source;
		this.name = name;
		this.value = value;
		this.theme = theme;
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

public Theme getTheme() {
	return theme;
}
}

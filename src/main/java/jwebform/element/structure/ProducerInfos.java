package jwebform.element.structure;

import java.util.List;

import jwebform.validation.ValidationResult;

public class ProducerInfos {
	private final String formId;
	private final int tabIndex;
	private final ValidationResult vr;
	private final List<ElementResult> childs;
	private final StaticRenderData staticRenderData;
	
	public ProducerInfos(String formId, int tabIndex, ValidationResult vr, List<ElementResult> childs, StaticRenderData staticRenderData) {
		this.formId = formId;
		this.tabIndex = tabIndex;
		this.vr = vr;
		this.childs = childs;
		this.staticRenderData = staticRenderData;
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

  public StaticRenderData getStaticRenderData() {
    return staticRenderData;
  }
}

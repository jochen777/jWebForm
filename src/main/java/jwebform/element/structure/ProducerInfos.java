package jwebform.element.structure;

// Infos, that the HTMLProducer needs to render the HTML. This will be provided by the form-run
public class ProducerInfos {
	private final String formId;
	private final int tabIndex;
	private final Theme theme;

	private final ElementResult elementResult;

	public ProducerInfos(String formId, int tabIndex, Theme theme, ElementResult elementResult) {
		this.formId = formId;
		this.tabIndex = tabIndex;
		this.theme = theme;
		this.elementResult = elementResult;
	}

	public String getFormId() {
		return formId;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public Theme getTheme() {
		return theme;
	}

	public ElementResult getElementResult() {
		return elementResult;
	}
}

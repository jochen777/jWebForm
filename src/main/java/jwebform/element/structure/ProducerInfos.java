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

  public String getHtml() {
    HTMLProducer producer = elementResult.getStaticElementInfo().getHtmlProducer();
    if (theme.getHtmlProducer().containsKey(elementResult.getStaticElementInfo().getRenderKey())) {
      producer = theme.getHtmlProducer().get(elementResult.getStaticElementInfo().getRenderKey());
    }
    return producer.getHTML(this);
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

  public String getNameOfInput() {
    return formId + "-" + elementResult.getStaticElementInfo().getName();
  }
}

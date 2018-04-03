package jwebform.element.structure;

import java.util.ArrayList;
import java.util.List;

// Infos, that the HTMLProducer needs to render the HTML. This will be provided by the form-run
public class ProducerInfos {
  private final String formId;
  private final int tabIndex;
  private final ElementContainer elementContainer;
  private final List<ProducerInfos> childs;

  private final static List<ProducerInfos> NO_CHILDS = new ArrayList<>();

  private final ElementResult elementResult;


  public ProducerInfos(String formId, int tabIndex, ElementResult elementResult,
      ElementContainer elementContainer, List<ProducerInfos> childs) {
    this.formId = formId;
    this.tabIndex = tabIndex;
    this.elementResult = elementResult;
    this.elementContainer = elementContainer;
    this.childs = childs;
  }

  public ProducerInfos(String formId, int tabIndex, ElementResult elementResult,
      ElementContainer elementContainer) {
    this.formId = formId;
    this.tabIndex = tabIndex;
    this.elementResult = elementResult;
    this.elementContainer = elementContainer;
    this.childs = NO_CHILDS;
  }



  public List<ProducerInfos> getChilds() {
    return childs;
  }


  public String getHtml() {
    return elementResult.getStaticElementInfo().getHtmlProducer().getHTML(this);
  }


  public String getFormId() {
    return formId;
  }

  public int getTabIndex() {
    return tabIndex;
  }

  public ElementResult getElementResult() {
    return elementResult;
  }

  public Element getElement() {
    return this.elementContainer.element;
  }

  public String getNameOfInput() {
    return elementResult.getStaticElementInfo().getName();
  }

  public String getElementTypeName() {
    return elementContainer.element.getClass().getName();
  }

  public ElementContainer getElementContainer() {
    return elementContainer;
  }

  public Decoration getDecoration() {
    return elementContainer.decoration;
  }


}

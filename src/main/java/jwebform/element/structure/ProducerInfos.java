package jwebform.element.structure;

import java.util.ArrayList;
import java.util.List;

// Infos, that the HTMLProducer needs to render the HTML. This will be provided by the form-run
public class ProducerInfos {
  private final String formId;
  private final int tabIndex;
  private final Theme theme;
  private final ElementContainer elementContainer;

  private final ElementResult elementResult;

  public ProducerInfos(String formId, int tabIndex, Theme theme, ElementResult elementResult,
      ElementContainer elementContainer) {
    this.formId = formId;
    this.tabIndex = tabIndex;
    this.theme = theme;
    this.elementResult = elementResult;
    this.elementContainer = elementContainer;
  }

  @Deprecated
  public ProducerInfos(String formId, int tabIndex, Theme theme, ElementResult elementResult) {
    this(formId, tabIndex, theme, elementResult, null); // RFE: Fix null
  }

  public List<Behaviour> getBehaviours() {
    // RFE: This may be faster with an immutable list impl.
    List<Behaviour> allBehaviours = new ArrayList<>();
    allBehaviours.addAll(theme.getGlobalBehaviours());
    if (elementContainer != null && elementContainer.behaviour != null) {
      allBehaviours.addAll(elementContainer.behaviour);
    }
    return allBehaviours;
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
    return elementResult.getStaticElementInfo().getName();
  }

  public ElementContainer getElementContainer() {
    return elementContainer;
  }


}

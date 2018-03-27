package jwebform.element.structure;

import java.util.ArrayList;
import java.util.List;

import jwebform.element.structure.behaviour.Behaviour;
import jwebform.view.Theme;

// Infos, that the HTMLProducer needs to render the HTML. This will be provided by the form-run
public class ProducerInfos {
  private final String formId;
  private final int tabIndex;
  private final Theme theme;
  private final ElementContainer elementContainer;
  private final List<ProducerInfos> childs;

  private final static List<ProducerInfos> NO_CHILDS = new ArrayList<>();

  private final ElementResult elementResult;
  // TODO: Do we need the decoration here, as we alreday have the elementContainer??!
  private final Decoration decoration;


  public ProducerInfos(String formId, int tabIndex, Theme theme, ElementResult elementResult,
      ElementContainer elementContainer, List<ProducerInfos> childs, Decoration decoration) {
    this.formId = formId;
    this.tabIndex = tabIndex;
    this.theme = theme;
    this.elementResult = elementResult;
    this.elementContainer = elementContainer;
    this.childs = childs;
    this.decoration = decoration;
  }

  public ProducerInfos(String formId, int tabIndex, Theme theme, ElementResult elementResult,
      ElementContainer elementContainer, Decoration decoration) {
    this.formId = formId;
    this.tabIndex = tabIndex;
    this.theme = theme;
    this.elementResult = elementResult;
    this.elementContainer = elementContainer;
    this.childs = NO_CHILDS;
    this.decoration = decoration;
  }



  public List<ProducerInfos> getChilds() {
    return childs;
  }



  public List<Behaviour> getBehaviours() {
    // RFE: This may be faster with an immutable list impl.
    List<Behaviour> allBehaviours = new ArrayList<>();
    allBehaviours.addAll(theme.getGlobalBehaviours());
    allBehaviours.addAll(elementContainer.behaviour);
    return allBehaviours;
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

  public Theme getTheme() {
    return theme;
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
    return decoration;
  }


}

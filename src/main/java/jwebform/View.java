package jwebform;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jwebform.element.renderer.bootstrap.BootstrapTheme;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.ProducerInfos;
import jwebform.element.structure.Theme;
import jwebform.view.StartEndRenderer;

public class View {

  private final Map<ElementContainer, ElementResult> elementResults;
  private final String formId;

  public View(String formId, Map<ElementContainer, ElementResult> elementResults) {
    this.formId = formId;
    this.elementResults = elementResults;
  }

  public String getHtml() {
    return this.getHtml(new BootstrapTheme(), "POST", true);
  }

  public String getHtml(Theme theme) {
    return this.getHtml(theme, "POST", true);
  }


  // fulll version
  public String getHtml(Theme theme, String method, boolean html5Validation) {
    StartEndRenderer startEndRenderer = new StartEndRenderer(formId, method, html5Validation); // RFE:
                                                                                               // Remove
                                                                                               // new
    StringBuilder html = new StringBuilder();
    html.append(startEndRenderer.getStart());
    int tabIndex = 1;
    ProducerInfos pi;
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      ElementResult elementResult = entry.getValue();
      ElementContainer container = entry.getKey();
      pi = new ProducerInfos(formId, tabIndex, theme, elementResult);
      String renderedHtml = pi.getHtml();
      if (container.behaviour != null) {
        renderedHtml = container.behaviour.getAllAround().wrap(renderedHtml);
      }
      html.append(renderedHtml);
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }

    html.append(startEndRenderer.getEnd());
    return html.toString();
  }

  ////////// For rendering within templates

  public List<String> getElementNames() {
    List<String> names = new ArrayList<>();
    elementResults.forEach((k, v) -> names.add(v.getStaticElementInfo().getName()));
    return names;
  }

  public Map<String, RenderElement> getElements() {
    Map<String, RenderElement> elements = new LinkedHashMap<>();
    int tabIndex = 1;
    BootstrapTheme theme = new BootstrapTheme(); // RFE: Maybe better empty theme here!
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      ElementResult elementResult = entry.getValue();
      ProducerInfos pi = new ProducerInfos(formId, tabIndex, theme, elementResult);
      elements.put(elementResult.getStaticElementInfo().getName(),
          new RenderElement(pi.getHtml(), pi, elementResult));
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }
    return elements;
  }

  public StartEndRenderer getStartEnd() {
    return new StartEndRenderer(formId, "POST", true);
  }

  public class RenderElement {
    private final String html;
    private final ProducerInfos producerInfos;
    private final ElementResult elementResult;

    public RenderElement(String html, ProducerInfos producerInfos, ElementResult elementResult) {
      super();
      this.html = html;
      this.producerInfos = producerInfos;
      this.elementResult = elementResult;
    }

    public String getHtml() {
      return html;
    }

    public ProducerInfos getProducerInfos() {
      return producerInfos;
    }

    public ElementResult getElementResult() {
      return elementResult;
    }

  }

}

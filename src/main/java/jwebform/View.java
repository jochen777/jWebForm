package jwebform;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.ForceFileuploadMethod;
import jwebform.element.structure.ProducerInfos;
import jwebform.view.StartEndRenderer;
import jwebform.view.Theme;
import jwebform.view.theme.BootstrapTheme;

public final class View {

  private final Map<ElementContainer, ElementResult> elementResults;
  private final String formId;

  public View(String formId, Map<ElementContainer, ElementResult> elementResults) {
    this.formId = formId;
    this.elementResults = elementResults;
  }

  public String getHtml() {
    return this.getHtml(BootstrapTheme.instance((msg) -> msg), "POST", true);
  }


  public String getHtml(Theme theme) {
    return this.getHtml(theme, "POST", true);
  }

  // fulll version
  public String getHtml(Theme theme, String method, boolean html5Validation) {

    StringBuilder html = new StringBuilder();
    html.append(
        theme.getStart(formId, method, html5Validation, determineUploadTypeAutomatically()));
    int tabIndex = 1;
    ProducerInfos pi;
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      ElementResult elementResult = entry.getValue();
      ElementContainer container = entry.getKey();
      pi = new ProducerInfos(formId, tabIndex, theme, elementResult, container);
      String renderedHtml = pi.getHtml();
      html.append(renderedHtml);
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }

    html.append(theme.getEnd());
    return html.toString();
  }

  private boolean determineUploadTypeAutomatically() {
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      if (entry.getKey().element instanceof ForceFileuploadMethod) {
        return true;
      } ;
    }
    return false;
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
    BootstrapTheme theme = BootstrapTheme.instance(msg -> msg); // RFE: Maybe better empty theme
                                                                // here!
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      ElementResult elementResult = entry.getValue();
      ProducerInfos pi = new ProducerInfos(formId, tabIndex, theme, elementResult, entry.getKey());
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

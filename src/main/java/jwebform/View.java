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
  private final Theme theme;

  public View(String formId, Map<ElementContainer, ElementResult> elementResults, Theme theme) {
    this.formId = formId;
    this.elementResults = elementResults;
    this.theme = theme;
  }

  public View(String formId, Map<ElementContainer, ElementResult> elementResults) {
    this.formId = formId;
    this.elementResults = elementResults;
    this.theme = null;
  }

  public String getHtml() {
    return getHtml("POST", true);
  }

  // fulll version
  public String getHtml(String method, boolean html5Validation) {
    StringBuilder html = new StringBuilder();
    html.append(ensureThemeIsThere().getStart(formId, method, html5Validation,
        determineUploadTypeAutomatically()));
    int tabIndex = 1;
    ProducerInfos pi;
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      ElementResult elementResult = entry.getValue();
      ElementContainer container = entry.getKey();
      pi = new ProducerInfos(formId, tabIndex, ensureThemeIsThere(), elementResult, container);
      String renderedHtml = pi.getHtml();
      html.append(renderedHtml);
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }

    html.append(ensureThemeIsThere().getEnd());
    return html.toString();
  }

  private Theme ensureThemeIsThere() {
    if (theme == null) {
      return BootstrapTheme.instance();
    } else {
      return theme;
    }

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

  public List<ProducerInfos> getUnrenderedElements() {
    List<ProducerInfos> elements = new ArrayList<>();
    int tabIndex = 1;
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      ElementResult elementResult = entry.getValue();
      ProducerInfos pi =
          new ProducerInfos(formId, tabIndex, ensureThemeIsThere(), elementResult, entry.getKey());
      elements.add(pi);
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }
    return elements;
  }


  public Map<String, ProducerInfos> getAllUnrenderedElements() {
    Map<String, ProducerInfos> elements = new LinkedHashMap<>();
    int tabIndex = 1;
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      ElementResult elementResult = entry.getValue();
      ProducerInfos pi =
          new ProducerInfos(formId, tabIndex, ensureThemeIsThere(), elementResult, entry.getKey());
      elements.put(elementResult.getStaticElementInfo().getName(), pi);
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }
    return elements;
  }


  public Map<String, RenderedElement> getElements() {
    Map<String, RenderedElement> elements = new LinkedHashMap<>();
    int tabIndex = 1;
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      ElementResult elementResult = entry.getValue();
      ProducerInfos pi =
          new ProducerInfos(formId, tabIndex, ensureThemeIsThere(), elementResult, entry.getKey());
      elements.put(elementResult.getStaticElementInfo().getName(),
          new RenderedElement(pi.getHtml(), pi, elementResult));
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }
    return elements;
  }

  public StartEndRenderer getStartEnd() {
    return new StartEndRenderer(formId, "POST", true, determineUploadTypeAutomatically());
  }

  public class RenderedElement {
    private final String html;
    private final ProducerInfos producerInfos;
    private final ElementResult elementResult;

    public RenderedElement(String html, ProducerInfos producerInfos, ElementResult elementResult) {
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

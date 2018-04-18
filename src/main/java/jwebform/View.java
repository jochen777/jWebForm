package jwebform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jwebform.element.NumberType;
import jwebform.element.PasswordType;
import jwebform.element.RadioType;
import jwebform.element.SelectType;
import jwebform.element.TextType;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.ForceFileuploadMethod;
import jwebform.element.structure.ProducerInfos;

public final class View {

  private final Map<ElementContainer, ElementResult> elementResults;


  private final String formId;
  private final String method;
  private final boolean html5Validaiton;



  public View(
      String formId,
      Map<ElementContainer, ElementResult> elementResults,
      String method,
      boolean html5Validation) {
    this.formId = formId;
    this.elementResults = elementResults;
    this.method = method;
    this.html5Validaiton = html5Validation;
  }

  public String getFormId() {
    return formId;
  }

  public String getMethod() {
    return method;
  }

  private List<ProducerInfos> createProducerInfoChilds(
      Map<ElementContainer, ElementResult> childs,
      int tabIndex) {
    List<ProducerInfos> listOfPis = new ArrayList<>(); // RFE: only, if childs is not empty!
    // RFE: This allows only one depth! It would be cooler, if we can do infinite depth
    childs.forEach((container, result) -> listOfPis
        .add(new ProducerInfos(formId, tabIndex, result, container)));
    return listOfPis;
  }

  public boolean isUploadEnctypeRequired() {
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      if (entry.getKey().element instanceof ForceFileuploadMethod) {
        return true;
      }
    }
    return false;
  }

  public boolean isHtml5Validaiton() {
    return html5Validaiton;
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
      ProducerInfos pi = new ProducerInfos(formId, tabIndex, elementResult,
          entry.getKey(), createProducerInfoChilds(elementResult.getChilds(), tabIndex));
      elements.add(pi);
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }
    return elements;
  }

  // for using with low capable template engines like mustache
  public List<DrawableElement> getDrawableElements() {
    List<DrawableElement> elements = new ArrayList<>();
    int tabIndex = 1;
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      ElementResult elementResult = entry.getValue();
      ProducerInfos pi = new ProducerInfos(formId, tabIndex, elementResult,
          entry.getKey(), createProducerInfoChilds(elementResult.getChilds(), tabIndex));
      elements.add(new DrawableElement(pi));
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
          new ProducerInfos(formId, tabIndex, elementResult, entry.getKey());
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
          new ProducerInfos(formId, tabIndex, elementResult, entry.getKey());
      elements.put(elementResult.getStaticElementInfo().getName(),
          new RenderedElement(pi.getHtml(), pi, elementResult));
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }
    return elements;
  }



  // this is for simple template-engines like mustache
  public class DrawableElement {
    private final ProducerInfos producerInfos;
    private final Map<String, Object> elementNameInfo;

    public Map<String, Object> getElementNameInfo() {
      return elementNameInfo;
    }

    public DrawableElement(ProducerInfos producerInfos) {
      this.producerInfos = producerInfos;
      elementNameInfo = new HashMap<>();
      elementNameInfo.put(
          producerInfos.getElementTypeName().replaceAll("jwebform\\.element\\.", ""), Boolean.TRUE);
      if (producerInfos.getElement() instanceof TextType) {
        elementNameInfo.put("type", "text");
      } else if (producerInfos.getElement() instanceof NumberType) {
        elementNameInfo.put("type", "number");
      } else if (producerInfos.getElement() instanceof PasswordType) {
        elementNameInfo.put("type", "password");
      }
      if (producerInfos.getElement() instanceof SelectType) {
        SelectType select = (SelectType) producerInfos.getElement();
        elementNameInfo.put("selected",
            select.getSelectListWithSelected(producerInfos.getElementResult().getValue()));
      }
      if (producerInfos.getElement() instanceof RadioType) {
        RadioType radio = (RadioType) producerInfos.getElement();
        elementNameInfo.put("radioElements",
            radio.getEntryListWithSelected(producerInfos.getElementResult().getValue()));
      }
    }

    public List<DrawableElement> getChilds() {
      List<DrawableElement> drawableChilds = new ArrayList<>();
      producerInfos.getChilds().forEach((producerInfos) -> drawableChilds.add(new DrawableElement(producerInfos)));
      return drawableChilds;
    }



    public ProducerInfos getProducerInfos() {
      return producerInfos;
    }

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

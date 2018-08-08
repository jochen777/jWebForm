package jwebform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import jwebform.element.NumberType;
import jwebform.element.PasswordType;
import jwebform.element.RadioType;
import jwebform.element.SelectType;
import jwebform.element.TextType;
import jwebform.element.structure.*;
import jwebform.processors.ElementResults;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.view.ProducerInfos;

public final class View {

  private final ElementResults elementResults;


  private final String formId;
  private final Method method;
  private final Html5Validation html5Validaiton;



  public View(String formId, ElementResults elementResults, Method method,
      Html5Validation html5Validation) {
    this.formId = formId;
    this.elementResults = elementResults;
    this.method = method;
    this.html5Validaiton = html5Validation;
  }

  public String getFormId() {
    return formId;
  }

  public String getMethod() {
    return method.name();
  }


  public boolean isUploadEnctypeRequired() {
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults) {
      if (entry.getKey().element instanceof ForceFileuploadMethod) {
        return true;
      }
    }
    return false;
  }

  public boolean isHtml5Validaiton() {
    return html5Validaiton == Html5Validation.on;
  }

  ////////// For rendering within templates



  public List<String> getElementNames() {
    List<String> names = new ArrayList<>();
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults) {
      names.add(entry.getValue().getStaticElementInfo().getName());
    }
    return names;
  }

  public List<ProducerInfos> getUnrenderedElements() {
    List<ProducerInfos> elements = new ArrayList<>();
    int tabIndex = 1;
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults) {
      ElementResult elementResult = entry.getValue();
      ProducerInfos pi = new ProducerInfos(formId, tabIndex, elementResult, entry.getKey(),
          createProducerInfoChilds(elementResult.getChilds(), tabIndex));
      elements.add(pi);
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }
    return elements;
  }

  // for using with low capable template engines like mustache
  public List<DrawableElement> getDrawableElements() {
    List<DrawableElement> elements = new ArrayList<>();
    int tabIndex = 1;
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults) {
      ElementResult elementResult = entry.getValue();
      ProducerInfos pi = new ProducerInfos(formId, tabIndex, elementResult, entry.getKey(),
          createProducerInfoChilds(elementResult.getChilds(), tabIndex));
      elements.add(new DrawableElement(pi));
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }
    return elements;
  }



  public Map<String, ProducerInfos> getAllUnrenderedElements() {
    Map<String, ProducerInfos> elements = new LinkedHashMap<>();
    int tabIndex = 1;
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults) {
      ElementResult elementResult = entry.getValue();
      ProducerInfos pi = new ProducerInfos(formId, tabIndex, elementResult, entry.getKey());
      elements.put(elementResult.getStaticElementInfo().getName(), pi);
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }
    return elements;
  }



  public Map<String, RenderedElement> getElements() {
    Map<String, RenderedElement> elements = new LinkedHashMap<>();
    int tabIndex = 1;
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults) {
      ElementResult elementResult = entry.getValue();
      ProducerInfos pi = new ProducerInfos(formId, tabIndex, elementResult, entry.getKey());
      elements.put(elementResult.getStaticElementInfo().getName(),
          new RenderedElement(pi.getHtml(), pi, elementResult));
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }
    return elements;
  }

  private List<ProducerInfos> createProducerInfoChilds(ElementResults childs, int tabIndex) {
    List<ProducerInfos> listOfPis = new ArrayList<>(); // RFE: only, if childs is not empty!
    // RFE: This allows only one depth! It would be cooler, if we can do infinite depth
    for (Entry<ElementContainer, ElementResult> elem : childs) {
      listOfPis.add(new ProducerInfos(formId, tabIndex, elem.getValue(), elem.getKey()));
    }
    return listOfPis;
  }

  public ViewElementContainer getViewElements() {
    Map<String, ViewElement> elements = new LinkedHashMap<>();
    List<ViewElement> elementList = new ArrayList<>();
    int tabIndex = 1;
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults) {
      ElementResult elementResult = entry.getValue();
      ViewElement ve = new ViewElement(entry.getKey(), entry.getValue(), tabIndex);
      elements.put(ve.name, ve);
      elementList.add(ve);
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }
    return new ViewElementContainer(elements, elementList);
  }

  public class ViewElementContainer {
    public Map<String, ViewElement> elementMap;
    public List<ViewElement> elementList;

    public ViewElementContainer(
      Map<String, ViewElement> elementMap, List<ViewElement> elementList) {
      this.elementMap = elementMap;
      this.elementList = elementList;
    }
  }

  public class ViewElement {

    public ViewElement(ElementContainer elementContainer, ElementResult elementResult, int tabIndex) {
      name = elementResult.getStaticElementInfo().getName();
      this.elementResult = elementResult;
      this.elementContainer = elementContainer;
      this.value = elementResult.getValue();
      this.valueObject = elementResult.getValueObject();
      this.validationResult = elementResult.getValidationResult();
      this.tabIndex = tabIndex;
      this.elementNameInfo = fillElementNameInfo(elementContainer.element, elementResult);

      childs = new ArrayList<>();
      elementResult.getChilds().forEach((elemRes) -> {
        elemRes.getValue().getChilds();
        childs.add(new ViewElement(elemRes.getKey(), elemRes.getValue(), tabIndex));
      });
      this.nameOfInput = getTypeName(elementContainer.element);

    }


    public ElementContainer elementContainer;
    public ElementResult elementResult;
    public String name;
    public String value;
    public Object valueObject;
    public ValidationResult validationResult;
    public List<ViewElement> childs;
    public int tabIndex;
    public String nameOfInput;
    public Map<String, Object> elementNameInfo;


    public String getHtml() {
      ProducerInfos pi = new ProducerInfos(formId, tabIndex, elementResult, elementContainer);
      return pi.getHtml();
    }

  }

  private Map<String, Object> fillElementNameInfo(Element element, ElementResult elementResult) {
    Map<String, Object> elementNameInfo = new HashMap<>();
    elementNameInfo.put(getTypeName(element), Boolean.TRUE);
    if (element instanceof TextType) {
      elementNameInfo.put("type", "text");
    } else if (element instanceof NumberType) {
      elementNameInfo.put("type", "number");
    } else if (element instanceof PasswordType) {
      elementNameInfo.put("type", "password");
    }
    if (element instanceof SelectType) {
      SelectType select = (SelectType) element;
      elementNameInfo.put("selected",
        select.getSelectListWithSelected(elementResult.getValue()));
    }
    if (element instanceof RadioType) {
      RadioType radio = (RadioType) element;
      elementNameInfo.put("radioElements",
        radio.getEntryListWithSelected(elementResult.getValue()));
    }
    return elementNameInfo;
  }

  private String getTypeName(Element element) {
    return element.getClass().getName().replaceAll("jwebform\\.element\\.", "");
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
      elementNameInfo = fillElementNameInfo(producerInfos.getElement(), producerInfos.getElementResult());
    }


    public List<DrawableElement> getChilds() {
      List<DrawableElement> drawableChilds = new ArrayList<>();
      producerInfos.getChilds()
          .forEach((producerInfos) -> drawableChilds.add(new DrawableElement(producerInfos)));
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

  public static enum Method {
    POST, GET
  }


  public static enum Html5Validation {
    on, off
  }
}



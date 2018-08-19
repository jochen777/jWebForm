package jwebform.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jwebform.element.NumberType;
import jwebform.element.PasswordType;
import jwebform.element.RadioType;
import jwebform.element.SelectType;
import jwebform.element.TextType;
import jwebform.element.structure.Decoration;
import jwebform.element.structure.FieldType;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;

// Infos, that the HTMLProducer needs to render the HTML. This will be provided by the form-run
public class ProducerInfos {
  private final String formId;
  private final int tabIndex;
  private final ElementContainer elementContainer;
  private final ElementResult elementResult;

  private final List<ProducerInfos> childs;

  private final static List<ProducerInfos> NO_CHILDS = new ArrayList<>();



  public ProducerInfos(String formId, int tabIndex, ElementResult elementResult,
      ElementContainer elementContainer, List<ProducerInfos> childs) {
    this.formId = formId;
    this.tabIndex = tabIndex;
    this.elementContainer = elementContainer;
    this.childs = childs;
    this.elementResult = elementResult;
  }

  public ProducerInfos(String formId, int tabIndex, ElementResult elementResult,
      ElementContainer elementContainer) {
    this(formId, tabIndex, elementResult, elementContainer, NO_CHILDS);
  }

  public List<ProducerInfos> getChilds() {
    return childs;
  }


  public ProducerInfos getTabIndexIncreased() {
    return new ProducerInfos(this.formId, tabIndex + 1, elementResult, elementContainer, childs);
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

  public FieldType getElement() {
    return this.elementContainer.element;
  }

  public String getName() {
    return elementResult.getStaticElementInfo().getName();
  }

  public String getElementTypeName() {
    return elementContainer.element.getClass().getName();
  }

  public Decoration getDecoration() {
    return elementContainer.decoration;
  }

  public String getValue() {
    return elementResult.getValue();
  }

  public Object getValueObject() {
    return elementResult.getValueObject();
  }

  public Validator getValidator() {
    return elementContainer.validator;
  }

  public ValidationResult getValidationResult() {
    return elementResult.getValidationResult();
  }

  public ElementResult getElementResult() {
    return elementResult;
  }

  public Map<String, Object> getElementInfoMap() {
    Map<String, Object> elementNameInfo = new HashMap<>();
    elementNameInfo.put(getTypeName(elementContainer.element), Boolean.TRUE);
    if (elementContainer.element instanceof TextType) {
      elementNameInfo.put("type", "text");
    } else if (elementContainer.element instanceof NumberType) {
      elementNameInfo.put("type", "number");
    } else if (elementContainer.element instanceof PasswordType) {
      elementNameInfo.put("type", "password");
    }
    if (elementContainer.element instanceof SelectType) {
      SelectType select = (SelectType) elementContainer.element;
      elementNameInfo.put("selected", select.getSelectListWithSelected(elementResult.getValue()));
    }
    if (elementContainer.element instanceof RadioType) {
      RadioType radio = (RadioType) elementContainer.element;
      elementNameInfo.put("radioElements",
          radio.getEntryListWithSelected(elementResult.getValue()));
    }
    return elementNameInfo;
  }

  private String getTypeName(FieldType element) {
    return element.getClass().getName().replaceAll("jwebform\\.element\\.", "");
  }


}

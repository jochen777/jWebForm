package jwebform.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jwebform.field.NumberType;
import jwebform.field.PasswordType;
import jwebform.field.RadioType;
import jwebform.field.SelectType;
import jwebform.field.TextType;
import jwebform.field.structure.Decoration;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.FieldType;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;

// Infos, that the HTMLProducer needs to render the HTML. This will be provided by the form-run
public class ProducerInfos {
  private final String formId;
  private final int tabIndex;
  private final Field field;
  private final FieldResult fieldResult;

  private final List<ProducerInfos> childs;

  private static final List<ProducerInfos> NO_CHILDS = new ArrayList<>();



  public ProducerInfos(String formId, int tabIndex, FieldResult fieldResult,
      Field field, List<ProducerInfos> childs) {
    this.formId = formId;
    this.tabIndex = tabIndex;
    this.field = field;
    this.childs = childs;
    this.fieldResult = fieldResult;
  }

  public ProducerInfos(String formId, int tabIndex, FieldResult fieldResult,
      Field field) {
    this(formId, tabIndex, fieldResult, field, NO_CHILDS);
  }

  public List<ProducerInfos> getChilds() {
    return childs;
  }


  public ProducerInfos getTabIndexIncreased() {
    return new ProducerInfos(this.formId, tabIndex + 1, fieldResult, field, childs);
  }


  public String getHtml() {
    return fieldResult.getStaticFieldInfo().getHtmlProducer().getHTML(this);
  }


  public String getFormId() {
    return formId;
  }

  public int getTabIndex() {
    return tabIndex;
  }

  public FieldType getType() {
    return this.field.fieldType;
  }

  public String getName() {
    return fieldResult.getStaticFieldInfo().getName();
  }

  public String getTypeName() {
    return field.fieldType.getClass().getName();
  }

  public Decoration getDecoration() {
    return field.decoration;
  }

  public String getValue() {
    return fieldResult.getValue();
  }

  public Object getValueObject() {
    return fieldResult.getValueObject();
  }

  public Validator getValidator() {
    // RFE: This must be injected!!
    return new Validator(field.criteria);
  }

  public ValidationResult getValidationResult() {
    return fieldResult.getValidationResult();
  }

  public FieldResult getFieldResult() {
    return fieldResult;
  }

  public Map<String, Object> getFieldInfoMap() {
    Map<String, Object> fieldNameInfo = new HashMap<>();
    fieldNameInfo.put(getTypeName(field.fieldType), Boolean.TRUE);
    if (field.fieldType instanceof TextType) {
      fieldNameInfo.put("type", "text");
    } else if (field.fieldType instanceof NumberType) {
      fieldNameInfo.put("type", "number");
    } else if (field.fieldType instanceof PasswordType) {
      fieldNameInfo.put("type", "password");
    }
    if (field.fieldType instanceof SelectType) {
      SelectType select = (SelectType) field.fieldType;
      fieldNameInfo.put("selected", select.getSelectListWithSelected(fieldResult.getValue()));
    }
    if (field.fieldType instanceof RadioType) {
      RadioType radio = (RadioType) field.fieldType;
      fieldNameInfo.put("radioFields",
          radio.getEntryListWithSelected(fieldResult.getValue()));
    }
    return fieldNameInfo;
  }

  private String getTypeName(FieldType fieldType) {
    return fieldType.getClass().getName().replaceAll("jwebform\\.fieldType\\.", "");
  }


}

package jwebform.integration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.field.CheckBoxType;
import jwebform.field.HiddenType;
import jwebform.field.HtmlType;
import jwebform.field.LabelType;
import jwebform.field.NumberType;
import jwebform.field.PasswordType;
import jwebform.field.RadioType;
import jwebform.field.SelectDateType;
import jwebform.field.SelectType;
import jwebform.field.SubmitType;
import jwebform.field.TextAreaType;
import jwebform.field.TextDateType;
import jwebform.field.TextType;
import jwebform.field.structure.Decoration;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldType;
import jwebform.integration.annotations.IgnoreField;
import jwebform.integration.annotations.UseDecoration;
import jwebform.integration.annotations.UseFieldType;
import jwebform.integration.beanvalidation.BeanValidationRuleDeliverer;
import jwebform.integration.beanvalidation.BeanValidationValidator;
import jwebform.integration.beanvalidation.ExternalValidation;
import jwebform.integration.beanvalidation.ExternalValidationDescription;
import jwebform.processor.FieldValdationResults;
import jwebform.validation.Criterion;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;
import jwebform.validation.criteria.Criteria;


public class Bean2From {

  final Map<Class, BiFunction<String, Object, FieldType>> fieldCreators;
  final BeanValidationValidator beanValidator;
  final BeanValidationRuleDeliverer beanValidationRuleDeliverer;

  public Bean2From() {
    // no bean validation at all!
    this((b) -> new ArrayList<>(), (bean, name) -> Collections.emptySet());
  }


  public Bean2From(BeanValidationValidator beanValidator,
      BeanValidationRuleDeliverer ruleDeliverer) {

    fieldCreators = new LinkedHashMap<>();
    this.beanValidator = beanValidator;
    this.beanValidationRuleDeliverer = ruleDeliverer;
    fillFieldCreators();
  }

  /**
   * Default mapping:
   * <p>
   * String: Text Boolean: Checkbox LocalDate: TextSelectDate Integer: Number
   *
   * @param bean
   * @return
   */
  public Form getFormFromBean(Object bean) {

    java.lang.reflect.Field[] fieldsOfBean = bean.getClass().getFields();

    List<Field> fields = new ArrayList<>();
    for (java.lang.reflect.Field fieldOfBean : fieldsOfBean) {

      if (isIgnore(fieldOfBean)) {
        continue;
      }

      String name = fieldOfBean.getName();
      Object initialValue = null;
      try {
        initialValue = fieldOfBean.get(bean);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      Class classOfField = fieldOfBean.getType();

      Decoration decoration = getDecoration(fieldOfBean, name);

      Optional<FieldType> oFieldType = checkAnnoation(fieldOfBean, name, initialValue);
      Criterion[] criteras = getCriterias(bean, name);
      if (oFieldType.isPresent()) {
        fields.add(new Field(oFieldType.get(), decoration, criteras));
      } else {
        if (fieldCreators.containsKey(classOfField)) {
          fields.add(new Field(fieldCreators.get(classOfField).apply(name, initialValue),
              decoration, criteras));
        } else {
          System.err.println("Unsupported type:" + classOfField);
        }
      }

    }
    // add formValdidator (with beanvalidation)
    Form f = FormBuilder.flexible("id", (a, b, c, d) -> new FormResultWithBean(a, b, c, d, bean))
        .fields(fields).validation(generateFormValidator(bean)).build();
    if (bean instanceof JWebFormBean) {
      Form processedByBean = ((JWebFormBean) bean).preRun(f);
      return processedByBean;
    }
    return f;
  }


  private Criterion[] getCriterias(Object bean, String name) {

    Set<ExternalValidationDescription> annotations =
        beanValidationRuleDeliverer.getCriteriaForField(bean, name);
    if (!annotations.isEmpty()) {
      List<Criterion> criterionList = new ArrayList<>();
      for (ExternalValidationDescription a : annotations) {
        System.err.println("MEE: " + a.parameters);
        System.err.println("MEEE: " + a.name);
        if (a.name.contains("NotEmpty")) {
          criterionList.add(Criteria.required());
        } else if (a.name.contains("Size")) {

          criterionList.add(Criteria.maxLength((Integer) a.parameters.get("max")));
        }

      }
      return criterionList.toArray(new Criterion[0]);
    }
    return new Criterion[0];
  }


  /**
   * this is a little hack, because formValidators will be called after formfield processing. And
   * not only the form validation via beanvalidation is done here, but also the filling of the bean!
   */
  private FormValidator generateFormValidator(Object bean) {
    return (fieldResults) -> {
      FillBeanWithFieldResults fillBeanWithFieldResults = new FillBeanWithFieldResults();
      fillBeanWithFieldResults.fill(bean, fieldResults);

      // bean validation
      List<ExternalValidation> validationResults = beanValidator.getValidationResults(bean);
      FieldValdationResults fieldValdationResults = new FieldValdationResults();

      validationResults.forEach(externalValidation -> {
        Field f = fieldResults.getField(externalValidation.fieldName);
        fieldValdationResults.put(f,
            ValidationResult.failWithTranslated(externalValidation.validationMessage));
      });
      return fieldValdationResults;
    };
  }

  private void fillFieldCreators() {
    BiFunction<String, Object, FieldType> bool2checkbox =
        (s, o) -> new CheckBoxType(s, getVal(o, Boolean.class));
    BiFunction<String, Object, FieldType> int2Number =
        (s, o) -> new NumberType(s, getVal(o, Integer.class));


    fieldCreators.put(CheckBoxType.class, (s, o) -> new CheckBoxType(s, getVal(o, Boolean.class)));
    fieldCreators.put(HiddenType.class, (s, o) -> new HiddenType(s, getVal(o, String.class)));
    fieldCreators.put(HtmlType.class, (s, o) -> new HtmlType(getVal(o, String.class)));
    fieldCreators.put(LabelType.class, (s, o) -> new LabelType(getVal(o, String.class)));
    fieldCreators.put(NumberType.class, (s, o) -> new NumberType(s, getVal(o, Integer.class)));
    fieldCreators.put(PasswordType.class, (s, o) -> new PasswordType(s));
    fieldCreators.put(RadioType.class,
        (s, o) -> new RadioType(s, getVal(o, String.class), getKeys(o), getVals(o)));
    fieldCreators.put(SelectDateType.class,
        (s, o) -> new SelectDateType(s, getVal(o, LocalDate.class), LocalDate.now().getYear() - 100,
            LocalDate.now().getYear() + 1));
    fieldCreators.put(SelectType.class,
        (s, o) -> new SelectType(s, getVal(o, String.class), getKeys(o), getVals(o)));
    fieldCreators.put(TextAreaType.class, (s, o) -> new TextAreaType(s, getVal(o, String.class)));
    fieldCreators.put(SubmitType.class, (s, o) -> new SubmitType(s));
    fieldCreators.put(TextDateType.class,
        (s, o) -> new TextDateType(s, getVal(o, LocalDate.class)));
    fieldCreators.put(TextType.class, (s, o) -> new TextType(s, getVal(o, String.class)));
    // UploadType must be handled differently!
    // XSRFType sould be added by frombuilder



    // Standard classes (without annoation)
    fieldCreators.put(String.class, (s, o) -> new TextType(s, getVal(o, String.class)));
    fieldCreators.put(Integer.class, int2Number);
    fieldCreators.put(int.class, int2Number);
    fieldCreators.put(Boolean.class, bool2checkbox);
    fieldCreators.put(boolean.class, bool2checkbox);
    fieldCreators.put(LocalDate.class, (s, o) -> new TextDateType(s, getVal(o, LocalDate.class)));

  }

  private <T> T getVal(Object o, Class<T> clss) {
    try {
      if (o instanceof ValuesOfObject) {
        ValuesOfObject valuesOfObject = (ValuesOfObject) o;
        return clss.cast(valuesOfObject.initialValue);
      }
      return clss.cast(o);
    } catch (Exception e) {
      throw new RuntimeException("I can not cast this!", e);
    }
  }

  private String[] getVals(Object o) {
    if (o instanceof ValuesOfObject) {
      ValuesOfObject valuesOfObject = (ValuesOfObject) o;
      return valuesOfObject.useFieldTypeAnnotation.vals();
    }
    throw new IllegalArgumentException(
        "Please call the getKeys just with a ValuesOfObject field. You called with: "
            + o.getClass());
  }

  private String[] getKeys(Object o) {
    if (o instanceof ValuesOfObject) {
      ValuesOfObject valuesOfObject = (ValuesOfObject) o;
      return valuesOfObject.useFieldTypeAnnotation.keys();
    }
    throw new IllegalArgumentException(
        "Please call the getKeys just with a ValuesOfObject field. You called with: "
            + o.getClass());
  }



  private boolean isIgnore(java.lang.reflect.Field fieldOfBean) {
    if (fieldOfBean.isAnnotationPresent(IgnoreField.class)) {
      return true;
    }
    return false;
  }

  private Decoration getDecoration(java.lang.reflect.Field fieldOfBean, String name) {
    UseDecoration decoration = fieldOfBean.getAnnotation(UseDecoration.class);
    if (decoration != null) {
      Decoration d =
          new Decoration(decoration.label(), decoration.helpText(), decoration.placeholder());
      return d;
    }
    return new Decoration(name);
  }

  private Optional<FieldType> checkAnnoation(java.lang.reflect.Field fieldOfBean, String name,
      Object initialValue) {
    UseFieldType useFieldType = fieldOfBean.getAnnotation(UseFieldType.class);
    if (useFieldType != null && fieldCreators.containsKey(useFieldType.type())) {
      ValuesOfObject valuesOfObject = new ValuesOfObject();
      valuesOfObject.initialValue = initialValue;
      valuesOfObject.useFieldTypeAnnotation = useFieldType;
      return Optional.of(fieldCreators.get(useFieldType.type()).apply(name, valuesOfObject));
    }
    return Optional.empty();
  }


  private class ValuesOfObject {
    Object initialValue;
    UseFieldType useFieldTypeAnnotation;
  }

}

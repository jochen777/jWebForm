package jwebform.integration;

import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.field.*;
import jwebform.field.structure.Decoration;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldType;
import jwebform.integration.annotations.IgnoreField;
import jwebform.integration.annotations.UseDecoration;
import jwebform.integration.annotations.UseFieldType;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;


public class Bean2From {

  // TODO: Vielleicht besser nicht "Object" übergeben, sondern besser Field
  final Map<Class, BiFunction<String, Object, FieldType>> fieldCreators;

  public Bean2From() {

    // TODO: Dass muss eine Liste werden. Denn evtl. kommmen subtypen vor
    fieldCreators = new LinkedHashMap<>();
    fillFieldCreators();
    // TODO: Add default converter, in case an annoation exists. (Maybe it is an enum!)
  }

  private void fillFieldCreators() {
    BiFunction<String, Object, FieldType> bool2checkbox = (s, o) -> new CheckBoxType(s, (Boolean) o);
    BiFunction<String, Object, FieldType> int2Number = (s, o) -> new NumberType(s, (Integer) o);


    // TODO: Create a object to type converter, that raises good readable exceptions
    fieldCreators.put(CheckBoxType.class, (s, o) -> new CheckBoxType(s, (Boolean) o));
    fieldCreators.put(TextAreaType.class, (s, o) -> new TextAreaType(s, (String) o));
    fieldCreators.put(HiddenType.class, (s, o) -> new HiddenType(s, (String) o));
    fieldCreators.put(HtmlType.class, (s, o) -> new HtmlType((String) o));
    fieldCreators.put(LabelType.class, (s, o) -> new LabelType((String) o));
    fieldCreators.put(NumberType.class, (s, o) -> new NumberType(s, (Integer) o));
    fieldCreators.put(PasswordType.class, (s, o) -> new PasswordType(s));

    fieldCreators.put(SelectType.class, (s, o) -> new SelectType(s, getVal(o, String.class), getKeys(o), getVals(o)));
    // what to do with RadioType?
    fieldCreators.put(RadioType.class, (s, o) -> new PasswordType(s));

    fieldCreators.put(SubmitType.class, (s, o) -> new SubmitType(s));

    // Standard classes (without annoation)
    fieldCreators.put(String.class, (s, o) -> new TextType(s, (String) o));
    fieldCreators.put(Integer.class,  int2Number);
    fieldCreators.put(int.class,      int2Number);
    fieldCreators.put(Boolean.class, bool2checkbox);
    fieldCreators.put(boolean.class, bool2checkbox);
    fieldCreators.put(LocalDate.class, (s, o) -> new TextDateType(s, (LocalDate) o));

  }

  private <T> T getVal(Object o, Class<T> clss){
    if (o instanceof ValuesOfObject) {
      ValuesOfObject valuesOfObject = (ValuesOfObject)o;
      return clss.cast(valuesOfObject.initialValue);
    }
    return clss.cast(o);
  }

  private String[] getVals(Object o){
    if (o instanceof ValuesOfObject) {
      ValuesOfObject valuesOfObject = (ValuesOfObject)o;
      return valuesOfObject.useFieldTypeAnnotation.vals();
    }
    throw new IllegalArgumentException("Please call the getKeys just with a ValuesOfObject field. You called with: " + o.getClass());
  }

  private String[] getKeys(Object o){
    if (o instanceof ValuesOfObject) {
      ValuesOfObject valuesOfObject = (ValuesOfObject)o;
      return valuesOfObject.useFieldTypeAnnotation.keys();
    }
    throw new IllegalArgumentException("Please call the getKeys just with a ValuesOfObject field. You called with: " + o.getClass());
  }


  /**
   * Default mapping:
   * <p>
   * String: Text
   * Boolean: Checkbox
   * LocalDate: TextSelectDate
   * Integer: Number
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
      if (oFieldType.isPresent()) {
        fields.add(new Field(oFieldType.get(),decoration));
      } else {
        if (fieldCreators.containsKey(classOfField)) {
          fields.add(new Field(fieldCreators.get(classOfField).apply(name, initialValue), decoration));
        } else {
          System.err.println("Unsupported type:" + classOfField);
        }
      }
    }
    return FormBuilder.simple().fields(fields).build();
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
      Decoration d = new Decoration(decoration.label(), decoration.helpText(), decoration.placeholder());
      return d;
    }
    return new Decoration(name);
  }

  private Optional<FieldType> checkAnnoation(
    java.lang.reflect.Field fieldOfBean, String name, Object initialValue) {
    UseFieldType useFieldType = fieldOfBean.getAnnotation(UseFieldType.class);
    if (useFieldType != null && fieldCreators.containsKey(useFieldType.type())) {
      ValuesOfObject valuesOfObject = new ValuesOfObject();
      valuesOfObject.initialValue = initialValue;
      valuesOfObject.useFieldTypeAnnotation = useFieldType;
      return Optional.of(fieldCreators.get(useFieldType.type()).apply(name, valuesOfObject));
    }
    return Optional.empty();
  }

  static ArrayList<Method> searchSetters(Class<?> c) {
    ArrayList<Method> list = new ArrayList<>();
    Method[] methods = c.getDeclaredMethods();
    for (Method method : methods)
      if (isSetter(method))
        list.add(method);
    return list;
  }

  public static boolean isSetter(Method method) {
    return Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(void.class)
      && method.getParameterTypes().length == 1 && method.getName().matches("^set[A-Z].*");
  }

  private class ValuesOfObject {
    Object initialValue;
    UseFieldType useFieldTypeAnnotation;
  }

}

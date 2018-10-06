package jwebform.integration;

import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldType;
import jwebform.integration.annotations.UseFieldType;
import jwebform.integration.fliedCreators.FieldCreator;
import jwebform.integration.fliedCreators.TextAreaCreator;
import jwebform.integration.methodconverters.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jwebform.field.builder.BuildInType.text;

public class Bean2From {

  final List<MethodConverter> converters;
  final List<FieldCreator> fieldCreators;

  public Bean2From() {
    converters = new ArrayList<>();
    converters.add(new StringConverter());
    converters.add(new IntegerConverter());
    converters.add(new BooleanConverter());
    converters.add(new LocalDateConverter());

    fieldCreators = new ArrayList<>();
    fieldCreators.add(new TextAreaCreator());
    // TODO: Add default converter, in case an annoation exists. (Maybe it is an enum!)
  }
  /**
   * Default mapping:
   *
   * String: Text
   * Boolean: Checkbox
   * LocalDate: TextSelectDate
   * Integer: Number
   *
   * @param bean
   * @return
   */
  public Form getFormFromBean(Object bean){

    java.lang.reflect.Field[] fieldsOfBean = bean.getClass().getFields();

    List<Field> fields = new ArrayList<>();
    for(java.lang.reflect.Field fieldOfBean: fieldsOfBean) {

      String name = fieldOfBean.getName();
      Class classOfField = fieldOfBean.getType();
      fields.add(checkAnnoation(fieldOfBean, name).orElseGet( () -> {
        for(MethodConverter converter: converters) {
          if (converter.supportsType(classOfField)) {
            return converter.convert(fieldOfBean, name, classOfField);
          }
        }
        return null;
      }));

    }
    return FormBuilder.simple().fields(fields).build();
  }

  private Optional<Field> checkAnnoation(java.lang.reflect.Field fieldOfBean, String name) {
    UseFieldType useFieldType = fieldOfBean.getAnnotation(UseFieldType.class);
    if (useFieldType != null) {
      for(FieldCreator fieldCreator: fieldCreators) {
        if (fieldCreator.supportsFieldType() == useFieldType.type()) {
          return Optional.of(fieldCreator.createField(useFieldType.type(), name));
        }
      }
      return Optional.empty();
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
    return Modifier.isPublic(method.getModifiers()) &&
      method.getReturnType().equals(void.class) &&
      method.getParameterTypes().length == 1 &&
      method.getName().matches("^set[A-Z].*");
  }
}

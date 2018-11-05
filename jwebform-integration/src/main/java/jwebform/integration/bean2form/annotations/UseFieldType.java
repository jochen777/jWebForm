package jwebform.integration.bean2form.annotations;



import jwebform.field.structure.FieldType;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface UseFieldType {

  Class<? extends FieldType> value();
  String [] keys() default {};
  String [] vals() default {};;

}

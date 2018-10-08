package jwebform.integration.annotations;



import jwebform.field.structure.FieldType;

import java.lang.annotation.*;

// use this to init the field with some data, that is not possible via normal value
// example: select-Boxes
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface UseInitData {

  String [] keys();
  String [] values();


}

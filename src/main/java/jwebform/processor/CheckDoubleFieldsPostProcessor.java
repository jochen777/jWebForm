package jwebform.processor;

import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;


public class CheckDoubleFieldsPostProcessor implements PostProcessor {

  @Override
  public FieldResults postProcess(FieldResults results) {
    Set<String> availElements = new HashSet<>();
    for (Entry<Field, FieldResult> entry : results) {
      // empty names are skipped
      if (entry.getValue().getStaticFieldInfo().getName() != FieldResult.NO_NAME
          && !availElements.add(entry.getValue().getStaticFieldInfo().getName())) {
        throw new DoubleTakenNameException(entry.getValue().getStaticFieldInfo().getName());
      }
    }
    return results;
  }


  // This exeption will be thrown, if you run a form and assigned elements with identical name
  public class DoubleTakenNameException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DoubleTakenNameException(String name) {
      super(String.format(
          "The name %s was taken more than once for this form. Make sure, that you use eache name of each fieldType only once!",
          name));
    }
  }

}

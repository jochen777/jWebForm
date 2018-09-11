package jwebform.processor;

import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;


/**
 * Post processor, that checks, if an indentical fieldname was given more than once.
 */
public class CheckDoubleFieldsPostProcessor implements PostProcessor {

  @Override
  public FieldResults postProcess(FieldResults results) {
    Set<String> availableFields = new HashSet<>();
    for (Entry<Field, FieldResult> entry : results) {
      // empty names are skipped
      if (entry.getValue().getStaticFieldInfo().getName() != FieldResult.NO_NAME
          && !availableFields.add(entry.getValue().getStaticFieldInfo().getName())) {
        throw new DoubleTakenNameException(entry.getValue().getStaticFieldInfo().getName());
      }
    }
    return results;
  }


  // This exeption will be thrown, if you run a form and assigned fields with identical name
  public class DoubleTakenNameException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DoubleTakenNameException(String name) {
      super(String.format(
          "The name %s was taken more than once for this form. Make sure, that you use eache name of each fieldType only once!",
          name));
    }
  }

}

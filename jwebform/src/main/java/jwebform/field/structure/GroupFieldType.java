package jwebform.field.structure;

import java.util.List;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.processor.FieldResults;
import jwebform.validation.FormValidator;

// a group of fields. this is basically a form.
/**
 * Needs: List of Field, FormValidator. Produces: List of FieldResults.
 *
 */
public interface GroupFieldType extends FieldType {
  List<Field> getChilds();

  FieldResult process(EnvWithSubmitInfo env, FieldResults childResults);

  List<FormValidator> getValidators(Field source);
}

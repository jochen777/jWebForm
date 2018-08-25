package jwebform.field.structure;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.processor.FieldResults;
import jwebform.validation.FormValidator;

import java.util.List;

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

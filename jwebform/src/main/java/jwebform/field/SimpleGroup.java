package jwebform.field;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.GroupFieldType;
import jwebform.field.structure.StaticFieldInfo;
import jwebform.processor.FieldResults;
import jwebform.validation.FormValidator;

import java.util.List;

public class SimpleGroup implements GroupFieldType {

  private final List<Field> childs;
  private final List<FormValidator> formValidators;

  public SimpleGroup(List<Field> fields, List<FormValidator> formValidators) {
    this.childs = fields;
    this.formValidators = formValidators;
  }



  @Override
  public List<Field> getChilds() {
    return childs;
  }

  @Override
  public List<FormValidator> getValidators(Field source) {
    return formValidators;
  }


  @Override
  public FieldResult process(EnvWithSubmitInfo env, FieldResults childs) {
    return FieldResult.builder().withStaticFieldInfo(new StaticFieldInfo("", t -> "", 0))
        .build();
  }

}

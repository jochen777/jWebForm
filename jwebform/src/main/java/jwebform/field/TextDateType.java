package jwebform.field;

import jwebform.field.helper.DateTypeHelper;
import jwebform.field.structure.Decoration;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.GroupFieldType;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.processor.FieldResults;
import jwebform.validation.FormValidator;
import jwebform.validation.criteria.Criteria;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Date-Input with simple text-fields
 * 
 * @author jochen
 *
 */
public class TextDateType implements GroupFieldType {


  private final  Field day;
  private final Field month;
  private final Field year;

  private final DateTypeHelper helper;


  public TextDateType(String name, LocalDate initialValue) {

    day = new TextType(name + "_day", String.valueOf(initialValue.getDayOfMonth()))
      .of(new Decoration("Day"), Criteria.number());

    month = new TextType(name + "_month", String.valueOf(initialValue.getMonthValue()))
      .of(new Decoration("Month"), Criteria.number());

    year = new TextType(name + "_year", String.valueOf(initialValue.getYear()))
      .of(new Decoration("Year"), Criteria.number());

    helper = new DateTypeHelper(day, month, year,  name);
  }


  @Override public List<Field> getChilds() {
    return Arrays.asList(day, month, year);
  }


  @Override public List<FormValidator> getValidators(Field source) {
    return helper.getValidators();
  }


  @Override public FieldResult process(EnvWithSubmitInfo env, FieldResults childs) {
    return helper.processDateVal(env, childs, "text Date");
  }
}

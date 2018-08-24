package jwebform.field;

import jwebform.field.helper.CommonSelects;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.field.structure.Decoration;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.GroupFieldType;
import jwebform.processor.FieldResults;
import jwebform.validation.FormValidator;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Date-Input with dropdown selects
 * 
 * @author jochen
 *
 */
public class SelectDateType implements GroupFieldType {


  final private Field day;
  final private Field month;
  final private Field year;

  final private DateTypeHelper helper;

  public SelectDateType(String name, LocalDate initialValue, int yearStart, int yearEnd) {
    Validator numberValidator = new Validator(Criteria.number());
    this.day = new SelectType(name + "_day", String.valueOf(initialValue.getDayOfMonth()),
        CommonSelects.build().buildDays()).of(numberValidator, new Decoration("Day"));
    this.month = new SelectType(name + "_month", String.valueOf(initialValue.getMonthValue()),
        CommonSelects.build().buildMonths()).of(numberValidator, new Decoration("Month"));
    this.year = new SelectType(name + "_year", String.valueOf(initialValue.getYear()),
        CommonSelects.build().getYears(yearStart, yearEnd)).of(numberValidator,
            new Decoration("Year"));
    helper = new DateTypeHelper(day, month, year, initialValue, name);
  }



  @Override
  public List<Field> getChilds() {
    return Arrays.asList(day, month, year);
  }


  @Override
  public FieldResult process(EnvWithSubmitInfo env, FieldResults childs) {
    return helper.processDateVal(env, childs, "select date");
  }


  @Override
  public List<FormValidator> getValidators(Field source) {
    return helper.getValidators();
  }


}

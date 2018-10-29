package jwebform.field;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.field.helper.CommonSelects;
import jwebform.field.helper.DateTypeHelper;
import jwebform.field.structure.Decoration;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.GroupFieldType;
import jwebform.processor.FieldResults;
import jwebform.validation.FormValidator;
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


  private final  Field day;
  private final Field month;
  private final Field year;

  private final  DateTypeHelper helper;

  public SelectDateType(String name, LocalDate initialValue, int yearStart, int yearEnd) {

    this.day = new SelectType(name + "_day", String.valueOf(initialValue.getDayOfMonth()),
        CommonSelects.build().buildDays()).of(new Decoration("Day"), Criteria.number());

    this.month = new SelectType(name + "_month", String.valueOf(initialValue.getMonthValue()),
        CommonSelects.build().buildMonths()).of(new Decoration("Month"), Criteria.number());

    this.year = new SelectType(name + "_year", String.valueOf(initialValue.getYear()),
        CommonSelects.build().getYears(yearStart, yearEnd)).of(
            new Decoration("Year"), Criteria.number());

    helper = new DateTypeHelper(day, month, year,  name);
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

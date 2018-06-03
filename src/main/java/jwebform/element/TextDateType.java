package jwebform.element;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import jwebform.element.structure.DateTypeHelper;
import jwebform.element.structure.Decoration;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.GroupType;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.processors.ElementResults;
import jwebform.validation.FormValidator;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;

/**
 * Date-Input with simple text-fields
 * 
 * @author jochen
 *
 */
public class TextDateType implements GroupType {


  final private ElementContainer day;
  final private ElementContainer month;
  final private ElementContainer year;

  final private DateTypeHelper helper;


  public TextDateType(String name, LocalDate initialValue) {
    Validator numberValidator = new Validator(Criteria.number());

    this.day = new TextType(name + "_day", String.valueOf(initialValue.getDayOfMonth()))
        .of(numberValidator, new Decoration("Day"));
    this.month = new TextType(name + "_month", String.valueOf(initialValue.getMonthValue()))
        .of(numberValidator, new Decoration("Month"));
    this.year = new TextType(name + "_year", String.valueOf(initialValue.getYear()))
        .of(numberValidator, new Decoration("Year"));
    helper = new DateTypeHelper(day, month, year, initialValue, name);
  }


  @Override
  public List<ElementContainer> getChilds() {
    return Arrays.asList(day, month, year);
  }


  @Override
  public List<FormValidator> getValidators(ElementContainer source) {
    return helper.getValidators(source);
  }


  @Override
  public ElementResult process(EnvWithSubmitInfo env, ElementResults childs) {
    return helper.processDateVal(env, childs, "text Date");
  }



}

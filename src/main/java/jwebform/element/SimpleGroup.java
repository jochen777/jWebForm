package jwebform.element;

import java.util.List;

import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.GroupElement;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.FormValidator;

public class SimpleGroup implements GroupElement {

  private final List<ElementContainer> childs;
  private final List<FormValidator> formValidators;

  public SimpleGroup(List<ElementContainer> elements, List<FormValidator> formValidators) {
    this.childs = elements;
    this.formValidators = formValidators;
  }


  @Override
  public ElementResult apply(EnvWithSubmitInfo t) {
    return new ElementResult(producerInfos -> "");
  }

  @Override
  public List<ElementContainer> getChilds() {
    return childs;
  }

  @Override
  public List<FormValidator> getValidators() {
    return formValidators;
  }

}

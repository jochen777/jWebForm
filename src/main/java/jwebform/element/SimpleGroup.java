package jwebform.element;

import java.util.List;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.GroupType;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.processors.ElementResults;
import jwebform.validation.FormValidator;

public class SimpleGroup implements GroupType {

  private final List<ElementContainer> childs;
  private final List<FormValidator> formValidators;

  public SimpleGroup(List<ElementContainer> elements, List<FormValidator> formValidators) {
    this.childs = elements;
    this.formValidators = formValidators;
  }



  @Override
  public List<ElementContainer> getChilds() {
    return childs;
  }

  @Override
  public List<FormValidator> getValidators(ElementContainer source) {
    return formValidators;
  }


  @Override
  public ElementResult process(EnvWithSubmitInfo env, ElementResults childs) {
    return ElementResult.builder().withStaticElementInfo(new StaticElementInfo("", t -> "", 0))
        .build();
  }

}

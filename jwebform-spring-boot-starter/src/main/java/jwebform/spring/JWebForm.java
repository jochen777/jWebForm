package jwebform.spring;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.View;
import jwebform.env.EnvBuilder;
import jwebform.processor.FormGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

// Container, that holds a jWebForm Form (or a normal bean) and provides a facade to jwebform objects
// typically this will be filled by the framework
public class JWebForm<T extends FormGenerator > {
  private final NativeWebRequest request;
  private final ModelAndViewContainer mavContainer;
  private final Class<T> typeOfBean;

  public JWebForm(
    Class<T> typeOfBean, NativeWebRequest request, ModelAndViewContainer mavContainer) {
    this.request = request;
    this.mavContainer = mavContainer;
    this.typeOfBean = typeOfBean;
  }

  public FormResult run(T input) {
    Form f = input.generateForm();
    FormResult fr =  f.run(new EnvBuilder().of(t -> request.getParameter(t)));

    // RFE: What can we do, if we have more than one Form on the page?
    // RFE: Should be configurable!
    mavContainer.addAttribute("form",fr.getView());

    return fr;
  }

  public FormResult run() {
    return run(BeanUtils.instantiateClass(typeOfBean));
  }


}

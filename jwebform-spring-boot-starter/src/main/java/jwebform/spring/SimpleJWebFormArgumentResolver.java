package jwebform.spring;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import jwebform.integration.Bean2Form;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import jwebform.processor.FormGenerator;
import jwebform.themes.FormRenderer;


public class SimpleJWebFormArgumentResolver implements HandlerMethodArgumentResolver {

  private final JWebFormProperties properties;
  private final FormRenderer formRenderer;
  private final Bean2Form bean2FromContract;

  public SimpleJWebFormArgumentResolver(
    Bean2Form bean2FromContract,
    JWebFormProperties properties,
    FormRenderer formRenderer) {
    this.properties = properties;
    this.formRenderer = formRenderer;
    this.bean2FromContract = bean2FromContract;
  }

  @Override
  public Object resolveArgument(MethodParameter methodParam, ModelAndViewContainer mavContainer,
      NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {

    methodParam.increaseNestingLevel();
    Class<FormGenerator> typeOfBean = (Class<FormGenerator>) methodParam.getNestedParameterType();
    methodParam.decreaseNestingLevel();

    SimpleJWebForm f = new SimpleJWebForm<FormGenerator>(typeOfBean, t -> request.getParameter(t),
        t -> request.getNativeRequest(HttpServletRequest.class).getAttribute(t),
        (t, v) -> request.getNativeRequest(HttpServletRequest.class).setAttribute(t, v),
        (t, v) -> mavContainer.addAttribute(t, v), bean2FromContract, properties, formRenderer);

    return f;
  }


  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(SimpleJWebForm.class);
  }



}

package jwebform.spring;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import jwebform.processor.FormGenerator;


public class SimpleJWebFormArgumentResolver implements HandlerMethodArgumentResolver {

  private final Validator validator;

  public SimpleJWebFormArgumentResolver(Validator validator) {
    this.validator = validator;
  }

  @Override
  public Object resolveArgument(MethodParameter methodParam, ModelAndViewContainer mavContainer,
      NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {


    methodParam.increaseNestingLevel();
    Class<FormGenerator> typeOfBean = (Class<FormGenerator>) methodParam.getNestedParameterType();
    methodParam.decreaseNestingLevel();

    // JWebForm<?> f = (JWebForm<?>)BeanUtils.instantiateClass(methodParam.getParameterType());

    SimpleJWebForm f = new SimpleJWebForm<FormGenerator>(typeOfBean, t -> request.getParameter(t),
        t -> request.getNativeRequest(HttpServletRequest.class).getAttribute(t),
        (t, v) -> request.getNativeRequest(HttpServletRequest.class).setAttribute(t, v),
        (t, v) -> mavContainer.addAttribute(t, v), validator);

    return f;
  }


  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(SimpleJWebForm.class);
  }



}

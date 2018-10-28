package jwebform.spring;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import jwebform.processor.FormGenerator;
import jwebform.themes.FormRenderer;


public class SimpleJWebFormArgumentResolver implements HandlerMethodArgumentResolver {

  private final Validator validator;
  private final JWebFormProperties properties;
  private final FormRenderer formRenderer;

  public SimpleJWebFormArgumentResolver(Validator validator, JWebFormProperties properties,
      FormRenderer formRenderer) {
    this.validator = validator;
    this.properties = properties;
    this.formRenderer = formRenderer;
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
        (t, v) -> mavContainer.addAttribute(t, v), validator, properties, formRenderer);

    return f;
  }


  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(SimpleJWebForm.class);
  }



}

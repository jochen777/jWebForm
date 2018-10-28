package jwebform.spring;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import jwebform.themes.FormRenderer;


public class JWebFormArgumentResolver implements HandlerMethodArgumentResolver {

  private final Validator validator;
  private final JWebFormProperties properties;
  private final FormRenderer formRenderer;

  public JWebFormArgumentResolver(Validator validator, JWebFormProperties properties,
      FormRenderer formRenderer) {
    this.validator = validator;
    this.properties = properties;
    this.formRenderer = formRenderer;
  }

  @Override
  public Object resolveArgument(MethodParameter methodParam, ModelAndViewContainer mavContainer,
      NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {


    JWebForm f = new JWebForm(t -> request.getParameter(t),
        t -> request.getNativeRequest(HttpServletRequest.class).getAttribute(t),
        (t, v) -> request.getNativeRequest(HttpServletRequest.class).setAttribute(t, v),
        (t, v) -> mavContainer.addAttribute(t, v), validator, properties, formRenderer);

    return f;
  }

  private String getParameterName(MethodParameter methodParam) {
    String parameterName = ModelFactory.getNameForParameter(methodParam);
    return parameterName;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(JWebForm.class);
  }


  private void getInfosAboutArgument(MethodParameter methodParam) {

    System.err.println(methodParam.getContainingClass());
    System.err.println(methodParam.getGenericParameterType());
    System.err.println(methodParam.getDeclaringClass());
    // System.err.println(methodParam.getNestedGenericParameterType());
    System.err.println(methodParam.getNestedParameterType());
    System.err.println(methodParam.getParameterType());
    System.err.println();

    System.err.println(methodParam.getNestingLevel());
    System.err.println("-------------------");
    // methodParam.decreaseNestingLevel();
    methodParam.increaseNestingLevel();

    System.err.println(methodParam.getContainingClass());
    System.err.println(methodParam.getGenericParameterType());
    System.err.println(methodParam.getDeclaringClass());
    // System.err.println(methodParam.getNestedGenericParameterType());
    System.err.println(methodParam.getNestedParameterType());

    System.err.println(methodParam.getParameterType());
    // Object bean = BeanUtils.instantiate(f.getType());

  }

}

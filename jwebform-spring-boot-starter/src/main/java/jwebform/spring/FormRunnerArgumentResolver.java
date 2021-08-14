package jwebform.spring;

import jwebform.integration.FormRunner;
import jwebform.integration.FormRunnerConfig;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;


public class FormRunnerArgumentResolver implements HandlerMethodArgumentResolver {

  private final FormRunnerConfig formRunnerConfig;

  public FormRunnerArgumentResolver(
    FormRunnerConfig formRunnerConfig) {
    this.formRunnerConfig = formRunnerConfig;
  }

  @Override
  public Object resolveArgument(MethodParameter methodParam, ModelAndViewContainer mavContainer,
      NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {


    FormRunner f = new FormRunner(t -> request.getParameter(t),
        t -> request.getNativeRequest(HttpServletRequest.class).getSession().getAttribute(t),
        (t, v) -> request.getNativeRequest(HttpServletRequest.class).getSession().setAttribute(t, v),
        (t, v) -> mavContainer.addAttribute(t, v), formRunnerConfig);

    return f;
  }

  private String getParameterName(MethodParameter methodParam) {
    String parameterName = ModelFactory.getNameForParameter(methodParam);
    return parameterName;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(FormRunner.class);
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

package jwebform.spring;

import jwebform.integration.ContainerFormRunner;
import jwebform.integration.FormRunnerConfig;
import jwebform.processor.FormGenerator;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;


public class ContainerFormRunnerArgumentResolver implements HandlerMethodArgumentResolver {

  private final FormRunnerConfig formRunnerConfig;

  public ContainerFormRunnerArgumentResolver(
    FormRunnerConfig formRunnerConfig) {
    this.formRunnerConfig = formRunnerConfig;
  }

  @Override
  public Object resolveArgument(MethodParameter methodParam, ModelAndViewContainer mavContainer,
      NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {

    methodParam.increaseNestingLevel();
    Class<FormGenerator> typeOfBean = (Class<FormGenerator>) methodParam.getNestedParameterType();
    methodParam.decreaseNestingLevel();

    ContainerFormRunner f = new ContainerFormRunner<FormGenerator>(
    		typeOfBean, t -> request.getParameter(t),
        t -> request.getNativeRequest(HttpServletRequest.class).getSession().getAttribute(t),
        (t, v) -> request.getNativeRequest(HttpServletRequest.class).getSession().setAttribute(t, v),
        (t, v) -> mavContainer.addAttribute(t, v), formRunnerConfig);

    return f;
  }


  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(ContainerFormRunner.class);
  }



}

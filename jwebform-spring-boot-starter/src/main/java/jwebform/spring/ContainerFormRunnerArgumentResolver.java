package jwebform.spring;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;
import jwebform.integration.ContainerFormRunner;
import jwebform.integration.FormRenderer;
import jwebform.integration.FormRunnerConfig;
import jwebform.integration.bean2form.Bean2Form;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import jwebform.processor.FormGenerator;


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

    ContainerFormRunner f = new ContainerFormRunner<FormGenerator>(typeOfBean, t -> request.getParameter(t),
        t -> request.getNativeRequest(HttpServletRequest.class).getAttribute(t),
        (t, v) -> request.getNativeRequest(HttpServletRequest.class).setAttribute(t, v),
        (t, v) -> mavContainer.addAttribute(t, v), formRunnerConfig);

    return f;
  }


  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(ContainerFormRunner.class);
  }



}
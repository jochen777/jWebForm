package jwebform.spring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.metadata.BeanDescriptor;
import jakarta.validation.metadata.ConstraintDescriptor;
import jakarta.validation.metadata.PropertyDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import jwebform.FormModel;
import jwebform.integration.FormRenderer;
import jwebform.integration.FormRunnerConfig;
import jwebform.integration.bean2form.Bean2Form;
import jwebform.integration.bean2form.DefaultBean2Form;
import jwebform.integration.beanvalidation.BeanValidationRuleDeliverer;
import jwebform.integration.beanvalidation.BeanValidationValidator;
import jwebform.integration.beanvalidation.ExternalValidation;
import jwebform.integration.beanvalidation.ExternalValidationDescription;
import jwebform.resultprocessor.ModelResultProcessor;
import jwebform.themes.sourcecode.BootstrapTheme;
import jwebform.themes.sourcecode.ThemeJavaRenderer;
import jwebform.themes.sourcecode.mapper.StandardMapper;

@Configuration
@ConditionalOnClass(FormRenderer.class)
@EnableConfigurationProperties(JWebFormProperties.class)
public class JWebFormAutoConfiguration implements WebMvcConfigurer {


  @Autowired
  public JWebFormProperties properties;

  @Autowired
  public FormRenderer formRenderer;

  @Autowired
  public Bean2Form bean2Form;

  @Autowired
  public ModelResultProcessor modelResultProcessor;

  @Autowired
  private MessageSource messageSource;



  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolver) {

    FormRunnerConfig formRunnerConfig = new FormRunnerConfig(formRenderer, bean2Form,
        modelResultProcessor, properties.getTemplateName());
    argumentResolver.add(new FormRunnerArgumentResolver(formRunnerConfig));
    argumentResolver.add(new ContainerFormRunnerArgumentResolver(formRunnerConfig));
  }



  @Configuration
  @ConditionalOnMissingBean(Bean2Form.class)
  @ConditionalOnClass(Validator.class)
  public static class JWebFormBean2FormDefaultConfig {


    @Bean
    Bean2Form bean2Form(Validator validator) {
      return new DefaultBean2Form(getBeanValidator(validator), getRuleDeliverer(validator));
    }


    private BeanValidationRuleDeliverer getRuleDeliverer(Validator validator) {
      return (bean, name) -> {
        Set<ExternalValidationDescription> criteraSet = new HashSet<>();
        BeanDescriptor i = validator.getConstraintsForClass(bean.getClass());
        PropertyDescriptor b = i.getConstraintsForProperty(name);
        if (b != null) {
          Set<ConstraintDescriptor<?>> z = b.getConstraintDescriptors();
          z.forEach(constraintDesc -> {
            criteraSet.add(new ExternalValidationDescription(
                constraintDesc.getAnnotation().annotationType().getSimpleName(),
                constraintDesc.getAttributes()));

          });
        }
        return criteraSet;
      };
    }

    private BeanValidationValidator getBeanValidator(Validator validator) {

      return (b) -> {
        Set<ConstraintViolation<Object>> vals = validator.validate(b);
        List<ExternalValidation> externalVals = new ArrayList<>();
        vals.forEach(constr -> {
          ExternalValidation e =
              new ExternalValidation(constr.getPropertyPath().toString(), constr.getMessage());
          externalVals.add(e);
        });

        return externalVals;
      };
    }
  }


  @Configuration
  @ConditionalOnMissingBean(value = FormRenderer.class)
  public static class JWebFormDefaultConfiguration {


    @Bean
    public FormRenderer formRenderer(MessageSource messageSource) {
      ThemeJavaRenderer renderer = new ThemeJavaRenderer(new StandardMapper(new BootstrapTheme(
          key -> messageSource.getMessage(key, null, LocaleContextHolder.getLocale()))));
      return renderer;
    }

  }

  @Configuration
  @ConditionalOnMissingBean(ModelResultProcessor.class)
  public static class FormModelBuilderDefaultConfiguration {


    @Bean
    public ModelResultProcessor formModelBuilder(FormRenderer formRenderer) {
      return new ModelResultProcessor(FormModel.Method.POST, FormModel.Html5Validation.ON);
    }

  }


}

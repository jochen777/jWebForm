package jwebform.spring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import jwebform.FormModel;
import jwebform.integration.FormRenderer;
import jwebform.integration.FormRunnerConfig;
import jwebform.integration.bean2form.Bean2Form;
import jwebform.integration.bean2form.DefaultBean2Form;
import jwebform.integration.beanvalidation.BeanValidationRuleDeliverer;
import jwebform.integration.beanvalidation.BeanValidationValidator;
import jwebform.integration.beanvalidation.ExternalValidation;
import jwebform.integration.beanvalidation.ExternalValidationDescription;
import jwebform.model.FormModelBuilder;
import jwebform.themes.sourcecode.ThemeJavaRenderer;
import jwebform.themes.sourcecode.mapper.StandardMapper;

@Configuration
@ConditionalOnClass(FormRenderer.class)
@EnableConfigurationProperties(JWebFormProperties.class)
public class JWebFormAutoConfiguration extends WebMvcConfigurerAdapter {


  @Autowired
  public JWebFormProperties properties;

  @Autowired
  public FormRenderer formRenderer;

  @Autowired
  public Bean2Form bean2Form;

  @Autowired
  public FormModelBuilder formModelBuilder;


  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolver) {

    FormRunnerConfig formRunnerConfig = new FormRunnerConfig(formRenderer, bean2Form,
        formModelBuilder, properties.getTemplateName());
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
    public FormRenderer formRenderer() {
      ThemeJavaRenderer renderer = new ThemeJavaRenderer(
          new StandardMapper(jwebform.themes.sourcecode.BootstrapTheme.instance(msg -> msg)));
      return renderer;
    }

  }

  @Configuration
  @ConditionalOnMissingBean(FormModelBuilder.class)
  public static class FormModelBuilderDefaultConfiguration {


    @Bean
    public FormModelBuilder formModelBuilder() {
      return FormModel::new;
    }

  }


}

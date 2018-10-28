package jwebform.spring;

import java.util.List;
import javax.validation.Validator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import jwebform.themes.FormRenderer;
import jwebform.themes.sourcecode.ThemeJavaRenderer;
import jwebform.themes.sourcecode.mapper.StandardMapper;

@Configuration
@ConditionalOnClass(FormRenderer.class)
@EnableConfigurationProperties(JWebFormProperties.class)
public class JWebFormAutoConfiguration extends WebMvcConfigurerAdapter
    implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Autowired
  private JWebFormProperties properties;

  @Autowired
  private FormRenderer formRenderer;


  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolver) {
    Validator validator = applicationContext.getBean(Validator.class);
    argumentResolver.add(new JWebFormArgumentResolver(validator, properties, formRenderer));
    argumentResolver.add(new SimpleJWebFormArgumentResolver(validator, properties, formRenderer));
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Configuration
  @ConditionalOnMissingBean(name = "formrRenderer")
  public static class JWebFormDefaultConfiguration {

    @Bean
    public FormRenderer formrRenderer() {
      ThemeJavaRenderer renderer = new ThemeJavaRenderer(
          new StandardMapper(jwebform.themes.sourcecode.BootstrapTheme.instance(msg -> msg)));
      return renderer;
    }
  }


}

package jwebform.spring;

import java.util.List;
import javax.validation.Validator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
// @ConditionalOnClass(FC.class)
// @EnableConfigurationProperties(JFormCheckerProperties.class)
public class JWebFormAutoConfiguration extends WebMvcConfigurerAdapter
    implements ApplicationContextAware {

  private ApplicationContext applicationContext;


  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolver) {
    Validator validator = applicationContext.getBean(Validator.class);
    argumentResolver.add(new JWebFormArgumentResolver());
    argumentResolver.add(new SimpleJWebFormArgumentResolver(validator));
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

}

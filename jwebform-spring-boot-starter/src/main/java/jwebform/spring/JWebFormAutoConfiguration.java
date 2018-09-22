package jwebform.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
//@ConditionalOnClass(FC.class)
//@EnableConfigurationProperties(JFormCheckerProperties.class)
public class JWebFormAutoConfiguration extends WebMvcConfigurerAdapter implements
  ApplicationContextAware {

  private ApplicationContext applicationContext;


  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolver) {
    argumentResolver.add(new JWebFormArgumentResolver(applicationContext ));
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

}

package jwebform.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwebform")
public class JWebFormProperties {

  private String templateName = "form";

  private boolean renderInTemplate = true;

  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public boolean isRenderInTemplate() {
    return renderInTemplate;
  }

  public void setRenderInTemplate(boolean renderInTemplate) {
    this.renderInTemplate = renderInTemplate;
  }



}

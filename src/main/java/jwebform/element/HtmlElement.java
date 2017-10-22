package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env.EnvWithSubmitInfo;

public class HtmlElement implements Element {

	public static String KEY = "jwebform.element.HTMLElement"; 
	
	public final String html;
	
  public HtmlElement(String html) {
	  this.html = html;
  }
  
	
  @Override
  public ElementResult prepare(EnvWithSubmitInfo env) {
    return new ElementResult("_html", t -> html, KEY, this);
  }


public String getHtml() {
	return html;
}

}

package jwebform.themes.mustache;

import java.util.Map;

// this interface to remove dependency to external lib
public interface MustacheRenderer {

  public String render(String templ, Map<String, Object> model);

}

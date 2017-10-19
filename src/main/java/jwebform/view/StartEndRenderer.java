package jwebform.view;

public class StartEndRenderer {

  private final String formId;
  private final String method;
  private final boolean html5Validation;

  public StartEndRenderer(String formId, String method, boolean html5Validation) {
    this.formId = formId;
    this.method = method;
    this.html5Validation = html5Validation;
  }

  public String getStart() {
    TagAttributes attribs = new TagAttributes();
    attribs.addToAttribute("name", formId + "-FORMCHECKER");
    attribs.addToAttribute("method", method);
    attribs.addToAttribute("id", formId);
    if (html5Validation) {
      attribs.addToAttribute("novalidate", "");
    }
    Tag startTag = new Tag("form", attribs);
    return startTag.getStartHtml() + "\n";
  }

  public String getEnd() {
    Tag startTag = new Tag("form");
    return startTag.getEndHtml();
  }
}

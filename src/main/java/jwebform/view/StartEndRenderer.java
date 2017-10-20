package jwebform.view;


public class StartEndRenderer {
	
  public final static String SUBMIT_KEY = "WF_SUBMITTED";
  public final static String SUBMIT_VALUE_PREFIX = "WF-";

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
//    String submittedTag = "<input type=\"hidden\" name=\"" + SUBMIT_KEY + "\" value=\""
//			+ SUBMIT_VALUE_PREFIX + formId + "\">\n";
    String submittedTag = "";
    return startTag.getStartHtml() + "\n" + submittedTag + "\n";
  }

  public String getEnd() {
    Tag startTag = new Tag("form");
    return startTag.getEndHtml();
  }
}

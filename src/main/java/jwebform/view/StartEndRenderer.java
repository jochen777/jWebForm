package jwebform.view;


public class StartEndRenderer {

  public final static String SUBMIT_KEY = "WF_SUBMITTED";
  public final static String SUBMIT_VALUE_PREFIX = "WF-";

  private final String formId;
  private final String method;
  private final boolean html5Validation;
  private final boolean uploadform;


  // public StartEndRenderer(String formId, String method, boolean html5Validation) {
  // this(formId, method, html5Validation, false);
  // }


  public StartEndRenderer(String formId, String method, boolean html5Validation,
      boolean uplaodform) {
    this.formId = formId;
    this.method = method;
    this.html5Validation = html5Validation;
    this.uploadform = uplaodform;
  }

  public String getStart() {
    TagAttributes attribs = new TagAttributes();
    attribs.addToAttribute("name", formId + "-FORMCHECKER");
    attribs.addToAttribute("method", method);
    attribs.addToAttribute("id", formId);
    String uploadStr = "";
    if (uploadform) {
      uploadStr = "enctype=\"multipart/form-data\"";
    }
    if (!html5Validation) {
      attribs.addToAttribute("novalidate", "");
    }
    Tag startTag = new Tag("form", attribs);
    String submittedTag = "<input type=\"hidden\" name=\"" + SUBMIT_KEY + "\" value=\""
        + SUBMIT_VALUE_PREFIX + formId + "\">\n";
    return startTag.getStartHtml(uploadStr) + "\n" + submittedTag + "\n";
  }

  public String getEnd() {
    Tag startTag = new Tag("form");
    return startTag.getEndHtml();
  }
}

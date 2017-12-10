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
    return getStart("");
  }
  
  public String getStart(String additional) {
    String formTag = "<form name=\"" + formId + "-FORMCHECKER\" method=\"" + method + "\" id=\"" + formId + "\"" + getUpload(uploadform) +
        getHtml5Validaiton(html5Validation) +
        ">\n";
    String submittedTag = "<input type=\"hidden\" name=\"" + SUBMIT_KEY + "\" value=\""
        + SUBMIT_VALUE_PREFIX + formId + "\">\n";
    return  formTag + submittedTag + "\n";
  }

  private String getHtml5Validaiton(boolean html5Validation) {
    if (!html5Validation) {
      return " novalidate ";
    } 
    return "";
  }

  private String getUpload(boolean uploadform2) {
    if (uploadform) {
      return " enctype=\"multipart/form-data\" ";
    } 
    return "";
  }

  public String getEnd() {
    Tag startTag = new Tag("form");
    return startTag.getEndHtml();
  }
}

package jwebform.themes.common;

import java.util.Map;
import jwebform.FormResult;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.ForceFileuploadMethod;
import jwebform.themes.sourcecode.Tag;

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


  public StartEndRenderer(FormResult result, String method, boolean html5Validation) {
    this.formId = result.getFormId();
    this.method = method;
    this.html5Validation = html5Validation;
    this.uploadform = determineUploadTypeAutomatically(result);
  }

  public String getStart() {
    return getStart("");
  }

  public String getStart(String additional) {
    String formTag =
        "<form name=\"" + formId + "-FORMCHECKER\" method=\"" + method + "\" id=\"" + formId + "\""
            + getUpload(uploadform) + getHtml5Validaiton(html5Validation) + additional + ">\n";
    String submittedTag = "<input type=\"hidden\" name=\"" + SUBMIT_KEY + "\" value=\""
        + SUBMIT_VALUE_PREFIX + formId + "\">\n";
    return formTag + submittedTag + "\n";
  }

  private boolean determineUploadTypeAutomatically(FormResult result) {
    for (Map.Entry<Field, FieldResult> entry : result.getFieldResults()) {
      if (entry.getKey().fieldType instanceof ForceFileuploadMethod) {
        return true;
      } ;
    }
    return false;
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

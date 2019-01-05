package jwebform.integration;

import java.util.HashMap;
import java.util.Map;
import jwebform.FormResult;
import jwebform.field.structure.FieldResult;

/*
 * json output in this format: http://labs.omniti.com/labs/jsend
 * 
 * { "status" : "fail", "data" : { "title" : "A title is required" } }
 *
 * 
 * { status : "success", data : {} }
 */
public class AjaxResult {
  public String status;
  public Map<String, String> data = new HashMap<>();

  // RFE: Provide Message source in case you want translated error-messages!
  public AjaxResult(FormResult formResult) {
    status = formResult.isValid() ? "success" : "fail";
    data = fillData(formResult);
  }

  private Map<String, String> fillData(FormResult formResult) {
    if (!formResult.isValid()) {
      formResult.getFieldResults().forEach(entry -> {
        FieldResult fieldResult = entry.getValue();
        if (fieldResult.getValidationResult().isError()) {
          data.put(fieldResult.getStaticFieldInfo().getName(),
              fieldResult.getValidationResult().getMessageKey());
        }
      });
    }
    return data;
  }
}

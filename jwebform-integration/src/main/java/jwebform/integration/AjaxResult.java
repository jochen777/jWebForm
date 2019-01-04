package jwebform.integration;

import jwebform.FormResult;

import java.util.Map;

/*
 * json output in this format: http://labs.omniti.com/labs/jsend
 * 
 * { "status" : "fail", "data" : { "title" : "A title is required" } }
 *
 * 
 * { status : "success", data : null }
 */
public class AjaxResult {
  public String status;
  public Map<String, String> data;

  public AjaxResult(FormResult formResult) {
    status = formResult.isValid() ? "success" : "fail";
  }
}

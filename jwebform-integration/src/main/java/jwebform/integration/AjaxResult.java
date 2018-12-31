package jwebform.integration;

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

  public AjaxResult(boolean ok, Map<String, String> data) {
    status = ok ? "success" : "fail";
  }
}

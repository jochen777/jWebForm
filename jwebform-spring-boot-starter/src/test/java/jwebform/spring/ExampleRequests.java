package jwebform.spring;

import jwebform.env.Request;
import jwebform.env.SessionGet;
import jwebform.env.SessionSet;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ExampleRequests {

  public static Request exampleSubmittedRequest(Map<String, String> elems) {
    elems.put("WF_SUBMITTED", "WF-id");
    return (t) -> elems.get(t);
  }

  public static Request notSubmittedRequest() {
    return (t) -> null;
  }

  public static Request exampleSubmittedRequest(String key, String value) {
    Map<String, String> exampleMap = new HashMap<>();
    exampleMap.put(key, value);
    return ExampleRequests.exampleSubmittedRequest(exampleMap);
  }


  public static SessionGet emptySessionGet() {
    return (s) -> "";
  }

  public static SessionSet emptySessionPut() {
    return (s, k) -> {
    };
  }

  public static BiConsumer<String, Object> stupidModel() {
    return (s, k) -> {
    };
  }

}

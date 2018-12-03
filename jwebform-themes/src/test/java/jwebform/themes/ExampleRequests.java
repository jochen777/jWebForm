package jwebform.themes;

import jwebform.env.Request;
import jwebform.env.SessionGet;
import jwebform.env.SessionSet;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ExampleRequests {

  private final String id;

  public ExampleRequests(String id) {
    this.id = id;
  }

  public Request exampleSubmittedRequest(Map<String, String> elems) {
    elems.put("WF_SUBMITTED", "WF-"+id);
    return (t) -> elems.get(t);
  }

  public Request notSubmittedRequest() {
    return (t) -> null;
  }

  public  Request exampleSubmittedRequest(String key, String value) {
    Map<String, String> exampleMap = new HashMap<>();
    exampleMap.put(key, value);
    return exampleSubmittedRequest(exampleMap);
  }


  public SessionGet emptySessionGet() {
    return (s) -> "";
  }

  public SessionSet emptySessionPut() {
    return (s, k) -> {
    };
  }

  public BiConsumer<String, Object> stupidModel() {
    return (s, k) -> {
    };
  }



}

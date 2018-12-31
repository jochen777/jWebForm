package jwebform.integration.fromBean;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import jwebform.env.Env;
import jwebform.env.EnvBuilder;
import jwebform.env.Request;
import jwebform.env.SessionGet;
import jwebform.env.SessionSet;

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

  public static Env fromRequest(Request r) {
    return new EnvBuilder().of(r);
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

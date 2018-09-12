package jwebform.themes.sourcecode;


// provides begin and end code, that can wrap something
public class Wrapper {
  public final String start;
  public final String end;
  public final static Wrapper emptyWrapper = Wrapper.of("", "");

  public static Wrapper of(String start, String end) {
    return new Wrapper(start, end);
  }

  public Wrapper(String start, String end) {
    this.start = start;
    this.end = end;
  }

  public String getStart() {
    return start;
  }

  public String getEnd() {
    return end;
  }

  public String wrap(String content) {
    if (this == emptyWrapper) {
      return content;
    } else {
      return new StringBuilder(start).append(content).append(end).toString();
    }
  }

  public static Wrapper empty() {
    return emptyWrapper;
  }

  public static Wrapper ofTag(String tagName) {
    return Wrapper.of("<" + tagName + ">", "</" + tagName + ">");
  }

}

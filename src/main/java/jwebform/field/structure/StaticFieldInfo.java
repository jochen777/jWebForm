package jwebform.field.structure;

// infos, that an fieldType provides, but that will not depend on request-params
public class StaticFieldInfo {
  private final String name; // static
  private final HTMLProducer htmlProducer; // static
  private final int tabIndexIncrement; // static

  public StaticFieldInfo(String name, HTMLProducer htmlProducer, int tabIndexIncrement) {
    this.name = name;
    this.htmlProducer = htmlProducer;
    this.tabIndexIncrement = tabIndexIncrement;
  }
  
  public StaticFieldInfo(String name, int tabIndexIncrement) {
    this(name, HTMLProducer.emptyHtmlProducer(), tabIndexIncrement);
  }

  public String getName() {
    return name;
  }

  public HTMLProducer getHtmlProducer() {
    return htmlProducer;
  }

  public int getTabIndexIncrement() {
    return tabIndexIncrement;
  }



}

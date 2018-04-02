package jwebform.element.structure;

// infos, that an element provides, but that will not depend on request-params
public class StaticElementInfo {
  private final String name; // static
  private final HTMLProducer htmlProducer; // static
  private final int tabIndexIncrement; // static

  public StaticElementInfo(String name, HTMLProducer htmlProducer, int tabIndexIncrement) {
    this.name = name;
    this.htmlProducer = htmlProducer;
    this.tabIndexIncrement = tabIndexIncrement;
  }
  
  public StaticElementInfo(String name, int tabIndexIncrement) {
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

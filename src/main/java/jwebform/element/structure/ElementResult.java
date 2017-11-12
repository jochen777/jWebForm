package jwebform.element.structure;

import java.util.ArrayList;
import java.util.List;

import jwebform.validation.ValidationResult;

// that wat is coming out of a "run" method of an element
public class ElementResult {

  // RFE: group this to reduce number of fields.
  private final ValidationResult validationResult; // not static
  private final String value; // not static
  private final Object valueObject;

  private final StaticElementInfo staticElementInfo;
  private final List<ElementResult> childs; // static

  public static final String EMPTY_STRING = "";
  public static final String NO_NAME = "";
  public static final List<ElementResult> NOCHILDS = new ArrayList<>();

  // RFE: Builder, der checkt, ob Themable element übergeben wird. wenn ja,
  // muss kein producer angegeben werden. ansonsten ist nur name mandatory

  // complete
  private ElementResult(ValidationResult vr, String value, StaticElementInfo staticElementInfo,
      List<ElementResult> childs, Object valueObject) {
    this.validationResult = vr;
    this.value = value;
    this.childs = childs;
    this.staticElementInfo = staticElementInfo;
    this.valueObject = valueObject;
  }

  // complete
  public ElementResult(String value, StaticElementInfo staticElementInfo,
      List<ElementResult> childs, Object valueObject) {
    this.validationResult = ValidationResult.undefined();
    this.value = value;
    this.childs = childs;
    this.staticElementInfo = staticElementInfo;
    this.valueObject = valueObject;
  }


  public ElementResult(ValidationResult vr, String value, StaticElementInfo staticElementInfo,
      List<ElementResult> childs) {
    // init sourceObject with string per defautl.
    this(vr, value, staticElementInfo, childs, value);
  }

  // very simple
  public ElementResult(String name, HTMLProducer htmlProducer) {
    this(name, htmlProducer, ValidationResult.ok());
  }

  // simplest
  public ElementResult(HTMLProducer htmlProducer) {
    this(NO_NAME, htmlProducer, ValidationResult.ok());
  }

  // simple with validation
  public ElementResult(String name, HTMLProducer htmlProducer, ValidationResult vr) {
    this(vr, EMPTY_STRING, new StaticElementInfo(name, htmlProducer, 0, EMPTY_STRING), NOCHILDS);
  }

  // simple with themable
  public ElementResult(String name, HTMLProducer htmlProducer, String renderkey) {
    this(ValidationResult.ok(), EMPTY_STRING,
        new StaticElementInfo(name, htmlProducer, 1, renderkey), NOCHILDS);
  }

  // simple with themable without name
  public ElementResult(HTMLProducer htmlProducer, String renderkey) {
    this(ValidationResult.ok(), EMPTY_STRING,
        new StaticElementInfo(NO_NAME, htmlProducer, 1, renderkey), NOCHILDS);
  }


  // completeWithout Childs
  public ElementResult(String value, StaticElementInfo staticElementInfo) {
    this(ValidationResult.undefined(), value, staticElementInfo, NOCHILDS);
  }

  public ElementResult cloneWithNewValidationResult(ValidationResult newValidationResult) {
    return new ElementResult(newValidationResult, value, staticElementInfo, childs,
        valueObject);
  }

  ///// end constructors

  public ElementResult ofValidationResult(ValidationResult vr) {
    return new ElementResult(vr, value, staticElementInfo, childs, valueObject);
  }


  public ValidationResult getValidationResult() {
    return validationResult;
  }

  public String getValue() {
    return value;
  }

  public List<ElementResult> getChilds() {
    return childs;
  }

  

  public StaticElementInfo getStaticElementInfo() {
    return staticElementInfo;
  }

  public Object getValueObject() {
    return valueObject;
  }

  @Override
  public String toString() {
    return String.format("ElementResult: %s", this.value);
  }

}

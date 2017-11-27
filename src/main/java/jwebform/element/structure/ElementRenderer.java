package jwebform.element.structure;

public interface ElementRenderer {

  String renderInput(
      String type,
      ProducerInfos pi,
      OneFieldDecoration decoration,
      String additional);

  String renderInputComplex(
      String tagname,
      String inBetweenHtml,
      ProducerInfos pi,
      OneFieldDecoration decoration);

  String renderInputFree(
      String free,
      ProducerInfos pi,
      OneFieldDecoration decoration,
      InputVariant variant);


  String renderAriaDescribedBy(ProducerInfos pi, OneFieldDecoration decoration);

  String renderValue(String value);

  // This may be used, to render label for radio-inputs. No error-highlighting, no required
  // highlighting!
  String renderSimpleLabel(String forAttribute, String label);


  public static enum InputVariant {
    normal, select, radio, checkbox
  }
}

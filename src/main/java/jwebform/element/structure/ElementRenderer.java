package jwebform.element.structure;

public interface ElementRenderer {

  String renderInput(String type, ProducerInfos pi, OneFieldDecoration decoration);

  String renderInputComplex(
      String tagname,
      String inBetweenHtml,
      ProducerInfos pi,
      OneFieldDecoration decoration);

  String renderInputFree(String free, ProducerInfos pi, OneFieldDecoration decoration);

  String renderInputFree(
      String free,
      ProducerInfos pi,
      OneFieldDecoration decoration,
      String classNameWrapper);

  String renderAriaDescribedBy(ProducerInfos pi, OneFieldDecoration decoration);

  String renderValue(String value);

  // This may be used, to render label for radio-inputs. No error-highlighting, no required
  // highlighting!
  String renderSimpleLabel(String forAttribute, String label);

  String renderInput(String type, String name, String id, String value, String additional);

  String getWrapperClassForCheckBox();

}

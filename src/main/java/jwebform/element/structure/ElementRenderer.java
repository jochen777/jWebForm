package jwebform.element.structure;

public interface ElementRenderer {

  String renderInput(String type, ProducerInfos pi, OneFieldDecoration decoration);

  String renderInputComplex(
      String tagname,
      String inBetweenHtml,
      ProducerInfos pi,
      OneFieldDecoration decoration);

  String renderInputFree(String free, ProducerInfos pi, OneFieldDecoration decoration);

  String renderAriaDescribedBy(ProducerInfos pi, OneFieldDecoration decoration);

  String renderValue(ProducerInfos pi);

}

package jwebform.element.renderer.bootstrap;

import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.ProducerInfos;


public interface ElementRenderer {

  String renderInput(String type, ProducerInfos pi, OneFieldDecoration decoration);

  String renderInputComplex(
      String tagname,
      String inBetweenHtml,
      ProducerInfos pi,
      OneFieldDecoration decoration);

  String renderInputFree(String free, ProducerInfos pi, OneFieldDecoration decoration);

}

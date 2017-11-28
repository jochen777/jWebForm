package jwebform.view;

import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.ProducerInfos;

public interface ElementRenderer {

  // Renders a single input-element including label and help
  String renderInput(
      String type,
      ProducerInfos pi,
      OneFieldDecoration decoration,
      String additional);

  // renders an input value, that is not a typical input (for example TextArea)
  String renderInputComplex(
      String tagname,
      String inBetweenHtml,
      ProducerInfos pi,
      OneFieldDecoration decoration);

  // just renders label and help, provide the input-element yourself
  String renderInputFree(
      String free,
      ProducerInfos pi,
      OneFieldDecoration decoration,
      InputVariant variant);

  // render the aria attribute
  String renderAriaDescribedBy(ProducerInfos pi, OneFieldDecoration decoration);

  // render the value attribute
  String renderValue(String value);

  // This may be used, to render label for radio-inputs. No error-highlighting, no required
  // highlighting!
  String renderSimpleLabel(String forAttribute, String label);


  public static enum InputVariant {
    normal, select, radio, checkbox
  }
}

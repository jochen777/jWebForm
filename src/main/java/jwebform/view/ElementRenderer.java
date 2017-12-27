package jwebform.view;

import jwebform.element.structure.Decoration;
import jwebform.element.structure.ProducerInfos;

public interface ElementRenderer {

  // Renders a single input-element including label and help
  String renderInput(
      String type,
      ProducerInfos pi,
      Decoration decoration,
      String additional);

  // renders an input value, that is not a typical input (for example TextArea)
  String renderInputComplex(
      String tagname,
      String inBetweenHtml,
      ProducerInfos pi,
      Decoration decoration);

  // just renders label and help, provide the input-element yourself
  String renderInputFree(
      String free,
      ProducerInfos pi,
      Decoration decoration,
      InputVariant variant);

  // render the aria attribute
  String renderAriaDescribedBy(ProducerInfos pi, Decoration decoration);

  // render the value attribute
  String renderValue(String value);

  // This may be used, to render label for radio-inputs. No error-highlighting, no required
  // highlighting!
  String renderSimpleLabel(String forAttribute, String label);


  public static enum InputVariant {
    normal, select, radio, checkbox
  }
}

package jwebform.processors;

import jwebform.FormResult;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.SingleType;

public class LoggingFormResult extends FormResult {
  public LoggingFormResult(String formId, ElementResults elementResults, boolean formIsValid) {
    super(formId, elementResults, formIsValid);
  }

  public void logForm(Logger logger) {
    StringBuilder b = new StringBuilder("\n");
    this.debugOutput(getElementResults(), b, "");
    logger.log(b.toString());
  }

  private String debugOutput(ElementResults elementResults, StringBuilder b, String indent) {
    elementResults.forEach(entry -> {
      ElementContainer container = entry.getKey();
      ElementResult result = entry.getValue();
      if (container.element instanceof SingleType) {
        appendSingleType(b, container, result, indent);
      } else {
        appendSingleType(b, container, result, indent);
        debugOutput(result.getChilds(), b, indent + "---- ");
      }
    });
    return "Form valid: " + this.isOk() + "\n " + b.toString();
  }

  private void appendSingleType(StringBuilder b, ElementContainer container, ElementResult result,
      String indent) {
    // @formatter:off
    b.append("---------------------\n")
    .append(indent).append("Typ    : ").append(container.element.getClass().getName()).append("\n")
    .append(indent).append("Name   : ").append(result.getStaticElementInfo().getName()).append("\n")
    .append(indent).append("Value  : ").append(result.getValue()).append("\n")
    // TODO: Append validation info, label, helptext?, 
    .append(indent).append("Valdid : ").append(result.getValidationResult().isValid() ? "OK" : "Not OK")
    .append("\n")
    ;
// @formatter:on

  }

}

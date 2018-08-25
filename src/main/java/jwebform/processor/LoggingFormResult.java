package jwebform.processor;

import jwebform.FormResult;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;

public class LoggingFormResult extends FormResult {
  public LoggingFormResult(String formId, FieldResults fieldResults, boolean formIsValid) {
    super(formId, fieldResults, formIsValid);
  }

  public void logForm(Logger logger) {
    StringBuilder b = new StringBuilder("\n");
    this.debugOutput(getFieldResults(), b, "");
    logger.log(b.toString());
  }

  private String debugOutput(FieldResults fieldResults, StringBuilder b, String indent) {
    fieldResults.forEach(entry -> {
      Field container = entry.getKey();
      FieldResult result = entry.getValue();
      if (container.fieldType instanceof SingleFieldType) {
        appendSingleType(b, container, result, indent);
      } else {
        appendSingleType(b, container, result, indent);
        debugOutput(result.getChilds(), b, indent + "---- ");
      }
    });
    return "Form valid: " + this.isOk() + "\n " + b.toString();
  }

  private void appendSingleType(StringBuilder b, Field container, FieldResult result,
      String indent) {
    // @formatter:off
    b.append("---------------------\n")
    .append(indent).append("Typ    : ").append(container.fieldType.getClass().getName()).append("\n")
    .append(indent).append("Name   : ").append(result.getStaticFieldInfo().getName()).append("\n")
    .append(indent).append("Value  : ").append(result.getValue()).append("\n")
    // TODO: Append validation info, label, helptext?, 
    .append(indent).append("Valdid : ").append(result.getValidationResult().isValid() ? "OK" : "Not OK")
    .append("\n")
    ;
// @formatter:on

  }

}

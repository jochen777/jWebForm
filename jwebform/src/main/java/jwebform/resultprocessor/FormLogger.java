package jwebform.resultprocessor;

import jwebform.FormResult;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.processor.FieldResults;


/**
 * A class, that can log the form in an convenient way
 * Use this for debugging! It contains a ResultProcessor
 *
 * Use it like this:
 * formResult.process(new FormLogger(System.out::print)).logForm();
 */
public class FormLogger {

  public static class LoggingFormResultProcessor implements ResultProcessor<FormLogger> {

    private final Logger logger;
    public LoggingFormResultProcessor(Logger logger) {
      this.logger = logger;
    }

    @Override public FormLogger process(FormResult formResult) {
      return new FormLogger(formResult, logger);
    }
  }

  private final FormResult formResult;
  private final Logger logger;

  public FormLogger(FormResult formResult, Logger logger) {
    this.formResult = formResult;
    this.logger = logger;
  }

  public void logForm() {
    StringBuilder b = new StringBuilder("\n");
    this.debugOutput(formResult.getFieldResults(), b, "");
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
    return "Form valid: " + formResult.isValid() + "\n " + b.toString();
  }

  private void appendSingleType(StringBuilder b, Field container, FieldResult result,
      String indent) {
    // @formatter:off
    b.append("---------------------\n")
    .append(indent).append("Typ    : ").append(container.fieldType.getClass().getName()).append("\n")
    .append(indent).append("Name   : ").append(result.getStaticFieldInfo().getName()).append("\n")
    .append(indent).append("Value  : ").append(result.getValue()).append("\n")
    // Append validation info, label, helptext?, 
    .append(indent).append("Valdid : ").append(result.getValidationResult().isValid() ? "OK" : "Not OK")
    .append("\n")
    ;
// @formatter:on

  }

}

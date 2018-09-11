package jwebform.usage;

import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.FormResult;
import jwebform.env.EnvBuilder;
import jwebform.field.builder.BuildInType;
import jwebform.processor.LoggingFormResult;
import jwebform.validation.criteria.Criteria;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

// tests the NumberInput
public class NumberInputTest {


  private FormBuilder getFormBuilder() {
    return FormBuilder.withLogging();
  }

  public static final String FIELD_NAME = "number";

  private Form buildFormWithNumber() {
    // @formatter:off
    return getFormBuilder()
        .typeBuilder(
             BuildInType.array(
                 BuildInType.number(FIELD_NAME, 5)
                 )
             )
        .build();
    // @formatter:on
  }

  private Form buildFormInputRequired() {
    // @formatter:off
    return getFormBuilder()
        .typeBuilder(
             BuildInType.array(
                 BuildInType.number(FIELD_NAME, 5).
                   criteria(Criteria.required())
                 )
             )
        .build();
    // @formatter:on
  }



  // @Test
  public void test_firstRun() {
    Form testForm = buildFormWithNumber();
    FormResult result = testForm.run(new EnvBuilder().of(it -> null // this simulates the first run
                                                                    // (all values null)
    ));
    // first run is never ok
    assertFalse(result.isOk());
  }

  @Test
  public void test_emptySubmit() {
    Form testForm = buildFormWithNumber();
    Map<String, String> params = new HashMap<>();
    params.put("WF_SUBMITTED", "WF-id");
    FormResult result = testForm.run(new EnvBuilder().of(it -> params.get(it)));
    assertTrue(result.isOk());
    assertEquals("", result.getStringValue(FIELD_NAME));
    assertEquals(Optional.empty(), result.getObectValue(FIELD_NAME));
  }

  @Test
  public void test_filledSubmit() {
    Form testForm = buildFormWithNumber();
    Map<String, String> params = new HashMap<>();
    params.put("WF_SUBMITTED", "WF-id");
    params.put(FIELD_NAME, "9");
    LoggingFormResult result =
      (LoggingFormResult) testForm.run(new EnvBuilder().of(it -> params.get(it)));
    //result.logForm(System.err::println);
    assertTrue(result.isOk());
    assertEquals("9", result.getStringValue(FIELD_NAME));
    assertEquals(Optional.of(9), result.getObectValue(FIELD_NAME));
  }

  @Test
  public void test_WrongFilledSubmit() {
    Form testForm = buildFormWithNumber();
    Map<String, String> params = new HashMap<>();
    params.put("WF_SUBMITTED", "WF-id");
    params.put(FIELD_NAME, "a");
    FormResult result = testForm.run(new EnvBuilder().of(it -> params.get(it)));
    assertTrue(!result.isOk());
    assertEquals("", result.getStringValue(FIELD_NAME));
    assertEquals(Optional.empty(), result.getObectValue(FIELD_NAME));
  }

  @Test
  public void test_emptyInputsButRequired() {
    Form testForm = buildFormInputRequired();
    Map<String, String> params = new HashMap<>();
    params.put("WF_SUBMITTED", "WF-id");
    FormResult result = testForm.run(new EnvBuilder().of(it -> params.get(it)));
    assertTrue(!result.isOk());
    assertEquals("", result.getStringValue(FIELD_NAME));
    assertEquals(Optional.empty(), result.getObectValue(FIELD_NAME));
  }



}

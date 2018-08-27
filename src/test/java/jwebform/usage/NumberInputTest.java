package jwebform.usage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Test;
import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.FormResult;
import jwebform.env.EnvBuilder;
import jwebform.field.builder.BuildInType;
import jwebform.validation.criteria.Criteria;

// tests the NumberInput
public class NumberInputTest {

  private Form buildFormWithNumber() {
    // @formatter:off
    return FormBuilder.simple()
        .typeBuilder(
             FormBuilder.array(
                 BuildInType.number("number", 5)
                 )
             )
        .build();
    // @formatter:on
  }

  private Form buildFormInputRequired() {
    // @formatter:off
    return FormBuilder.simple()
        .typeBuilder(
             FormBuilder.array(
                 BuildInType.number("number", 5).withCriteria(Criteria.required())
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
    assertEquals("", result.getStringValue("number"));
    assertEquals(Optional.empty(), result.getObectValue("number"));
  }

  @Test
  public void test_filledSubmit() {
    Form testForm = buildFormWithNumber();
    Map<String, String> params = new HashMap<>();
    params.put("WF_SUBMITTED", "WF-id");
    params.put("number", "9");
    FormResult result = testForm.run(new EnvBuilder().of(it -> params.get(it)));
    assertTrue(result.isOk());
    assertEquals("9", result.getStringValue("number"));
    assertEquals(Optional.of(9), result.getObectValue("number"));
  }

  @Test
  public void test_WrongFilledSubmit() {
    Form testForm = buildFormWithNumber();
    Map<String, String> params = new HashMap<>();
    params.put("WF_SUBMITTED", "WF-id");
    params.put("number", "a");
    FormResult result = testForm.run(new EnvBuilder().of(it -> params.get(it)));
    assertTrue(!result.isOk());
    assertEquals("", result.getStringValue("number"));
    assertEquals(Optional.empty(), result.getObectValue("number"));
  }

  @Test
  public void test_emptyInputsButRequired() {
    Form testForm = buildFormInputRequired();
    Map<String, String> params = new HashMap<>();
    params.put("WF_SUBMITTED", "WF-id");
    FormResult result = testForm.run(new EnvBuilder().of(it -> params.get(it)));
    assertTrue(!result.isOk());
    assertEquals("", result.getStringValue("number"));
    assertEquals(Optional.empty(), result.getObectValue("number"));
  }



}

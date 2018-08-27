package jwebform.usage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
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

// tests the DateInput Default and empty behaviour
public class DateInputValueTest {

  private Form buildFormWithDateInput() {
    // @formatter:off
    return FormBuilder.simple()
        .typeBuilder(
             FormBuilder.array(
                 BuildInType.textDate("date", LocalDate.of(2018, 8, 26))
                 )
             )
        .build();
    // @formatter:on
  }

  private Form buildFormWithDateInputRequired() {
    // @formatter:off
    return FormBuilder.simple()
        .typeBuilder(
             FormBuilder.array(
                 BuildInType.textDate("date", LocalDate.of(2018, 8, 26)).withCriteria(Criteria.required())
                 )
             )
        .build();
    // @formatter:on
  }

  // @Test
  public void testDateFirstRun() {
    Form testForm = buildFormWithDateInput();
    FormResult result = testForm.run(new EnvBuilder().of(it -> null // this simulates the first run
                                                                    // (all values null)
    ));
    // first run is never ok
    assertFalse(result.isOk());
  }

  @Test
  public void testDateEmptySubmit() {
    Form testForm = buildFormWithDateInput();
    Map<String, String> params = new HashMap<>();
    params.put("WF_SUBMITTED", "WF-id");
    FormResult result = testForm.run(new EnvBuilder().of(it -> params.get(it)));
    assertTrue(result.isOk());
    assertEquals("", result.getStringValue("date"));
    assertEquals(Optional.empty(), result.getObectValue("date"));
  }

  @Test
  public void testDateFilledSubmit() {
    Form testForm = buildFormWithDateInput();
    Map<String, String> params = new HashMap<>();
    params.put("WF_SUBMITTED", "WF-id");
    params.put("date_day", "5");
    params.put("date_month", "10");
    params.put("date_year", "2018");
    FormResult result = testForm.run(new EnvBuilder().of(it -> params.get(it)));
    assertTrue(result.isOk());
    assertEquals("2018-10-05", result.getStringValue("date"));
    assertEquals(Optional.of(LocalDate.of(2018, 10, 5)), result.getObectValue("date"));
  }

  @Test
  public void testWrongDateFilledSubmit() {
    Form testForm = buildFormWithDateInput();
    Map<String, String> params = new HashMap<>();
    params.put("WF_SUBMITTED", "WF-id");
    params.put("date_day", "31");
    params.put("date_month", "2");
    params.put("date_year", "2018");
    FormResult result = testForm.run(new EnvBuilder().of(it -> params.get(it)));
    assertTrue(!result.isOk());
    assertEquals("", result.getStringValue("date"));
    assertEquals(Optional.empty(), result.getObectValue("date"));
  }

  @Test
  public void test_emptyInputsButRequired() {
    Form testForm = buildFormWithDateInputRequired();
    Map<String, String> params = new HashMap<>();
    params.put("WF_SUBMITTED", "WF-id");
    FormResult result = testForm.run(new EnvBuilder().of(it -> params.get(it)));
    assertTrue(!result.isOk());
    assertEquals("", result.getStringValue("date"));
    assertEquals(Optional.empty(), result.getObectValue("date"));
  }



}

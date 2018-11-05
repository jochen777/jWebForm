package jwebform.integration.fromAnnoation;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.env.EnvBuilder;
import jwebform.field.SelectType;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.integration.bean2form.DefaultBean2Form;
import jwebform.integration.bean2form.annotations.UseFieldType;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class SelectBoxTest {

  public static final String INITIAL_VALUE = "m";
  Form form;

  @Before
  public void init() {
    SelectBoxTest.Bean bean = new SelectBoxTest.Bean();
    bean.gender = INITIAL_VALUE;
    form = new DefaultBean2Form().getFormFromBean(bean);
  }

  @Test
  public void test_beanWithString() {
    assertEquals(form.getFields().size(), 1);
    assertTrue(form.getFields().get(0).fieldType instanceof SelectType);
  }

  @Test
  public void test_beanNameCorrect() {
    FormResult fr = form.run(new EnvBuilder().of(t -> null));
    Field f = fr.getFieldResults().getField("gender");
    FieldResult fieldResult = fr.getFieldResults().get(f);
    assertEquals("gender", fieldResult.getStaticFieldInfo().getName());
  }


  @Test
  public void test_presetCorrect() {
    FormResult fr = form.run(new EnvBuilder().of(t -> null));
    Field f = fr.getFieldResults().getField("gender");
    FieldResult fieldResult = fr.getFieldResults().get(f);
    assertEquals( INITIAL_VALUE, fieldResult.getValueObject());
  }


  public class Bean {
    @UseFieldType(value = SelectType.class, keys = {"m", "f"}, vals = {"Male", "Female"})
    public String gender="";
  }
}

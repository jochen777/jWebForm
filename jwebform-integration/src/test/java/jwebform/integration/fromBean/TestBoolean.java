package jwebform.integration.fromBean;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.env.EnvBuilder;
import jwebform.field.CheckBoxType;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.integration.bean2form.DefaultBean2Form;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
// Tests if it works, to convert a bean containing a boolean to a form with a Checkbox field via Bean2Form
public class TestBoolean {

  public static final Boolean INITIAL_VALUE = true;
  Form form;

  @Before
  public void init() {
    Bean bean = new Bean();
    bean.check = INITIAL_VALUE;
    bean.check2 = !INITIAL_VALUE;
    form = new DefaultBean2Form().getFormFromBean(bean);
  }

  @Test
  public void test_beanWithString() {
    assertEquals(form.getFields().size(), 2);
    assertTrue(form.getFields().get(0).fieldType instanceof CheckBoxType);
    assertTrue(form.getFields().get(1).fieldType instanceof CheckBoxType);
  }

  @Test
  public void test_beanNameCorrect() {
    FormResult fr = form.run(new EnvBuilder().of(t -> null));
    Field f = fr.getFieldResults().getField("check");
    FieldResult fieldResult = fr.getFieldResults().get(f);
    assertEquals("check", fieldResult.getStaticFieldInfo().getName());
  }

  @Test
  public void test_beanName2Correct() {
    FormResult fr = form.run(new EnvBuilder().of(t -> null));
    Field f = fr.getFieldResults().getField("check2");
    FieldResult fieldResult = fr.getFieldResults().get(f);
    assertEquals("check2", fieldResult.getStaticFieldInfo().getName());
  }


  @Test
  public void test_presetCorrect() {
    FormResult fr = form.run(new EnvBuilder().of(t -> null));
    Field f = fr.getFieldResults().getField("check");
    FieldResult fieldResult = fr.getFieldResults().get(f);
    assertEquals( INITIAL_VALUE, fieldResult.getValueObject());
  }


  @Test
  public void test_preset2Correct() {
    FormResult fr = form.run(new EnvBuilder().of(t -> null));
    Field f = fr.getFieldResults().getField("check2");
    FieldResult fieldResult = fr.getFieldResults().get(f);
    assertEquals( !INITIAL_VALUE, fieldResult.getValueObject());
  }


  public class Bean {
    public Boolean check;
    public boolean check2;
  }
}

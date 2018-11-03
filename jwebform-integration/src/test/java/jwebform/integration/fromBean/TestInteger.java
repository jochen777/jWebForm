package jwebform.integration.fromBean;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.env.EnvBuilder;
import jwebform.field.NumberType;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.integration.bean2form.DefaultBean2Form;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

// Tests if it works, to convert a bean containing a String to a form with a TextType field via Bean2Form
public class TestInteger {

  public static final Integer INITIAL_VALUE = 144;
  Form form;

  @Before
  public void init() {
    Bean bean = new Bean();
    form = new DefaultBean2Form().getFormFromBean(bean);
  }

  @Test
  public void test_beanWithString() {
    assertEquals(form.getFields().size(), 2);
    assertTrue(form.getFields().get(0).fieldType instanceof NumberType);
    assertTrue(form.getFields().get(1).fieldType instanceof NumberType);
  }

  @Test
  public void test_beanNameCorrect() {
    NumberType numberType = (NumberType)form.getFields().get(0).fieldType;
    assertEquals(numberType.oneValueType.name, "numb");
  }

  @Test
  public void test_beanName2Correct() {
    NumberType numberType = (NumberType)form.getFields().get(1).fieldType;
    assertEquals(numberType.oneValueType.name, "numb2");
  }


  @Test
  public void test_presetCorrect() {
    FormResult fr = form.run(new EnvBuilder().of(t -> null));
    Field f = fr.getFieldResults().getField("numb");
    FieldResult fieldResult = fr.getFieldResults().get(f);


    assertEquals( INITIAL_VALUE, ((Optional<Integer>)fieldResult.getValueObject()).get());
  }


  @Test
  public void test_preset2Correct() {
    FormResult fr = form.run(new EnvBuilder().of(t -> null));
    Field f = fr.getFieldResults().getField("numb2");
    FieldResult fieldResult = fr.getFieldResults().get(f);

    Integer testVal = (INITIAL_VALUE+1);

    assertEquals( testVal , ((Optional<Integer>)fieldResult.getValueObject()).get());
  }


  public class Bean {
    public Integer numb = INITIAL_VALUE;
    public int numb2 = INITIAL_VALUE+1;
  }
}

package jwebform.integration.fromBean;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.env.EnvBuilder;
import jwebform.field.NumberType;
import jwebform.field.TextType;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.integration.Bean2From;
import org.junit.Before;
import org.junit.BeforeClass;
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
    bean.numb = INITIAL_VALUE;
    form = new Bean2From().getFormFromBean(bean);
  }

  @Test
  public void test_beanWithString() {
    assertEquals(form.getFields().size(), 1);
    assertTrue(form.getFields().get(0).fieldType instanceof NumberType);
  }

  @Test
  public void test_beanNameCorrect() {
    NumberType numberType = (NumberType)form.getFields().get(0).fieldType;
    assertEquals(numberType.oneValueType.name, "numb");
  }

  @Test
  public void test_presetCorrect() {
    FormResult fr = form.run(new EnvBuilder().of(t -> null));
    Field f = fr.getFieldResults().getField("numb");
    FieldResult fieldResult = fr.getFieldResults().get(f);


    assertEquals( INITIAL_VALUE, ((Optional<Integer>)fieldResult.getValueObject()).get());
  }



  public class Bean {
    public Integer numb = INITIAL_VALUE;
  }
}

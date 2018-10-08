package jwebform.integration.fromBean;

import jwebform.Form;
import jwebform.field.TextType;
import jwebform.integration.Bean2From;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

// Tests if it works, to convert a bean containing a String to a form with a TextType field via Bean2Form
public class TestString {

  public static final String INITIAL_VALUE = "testname";
  Form form;

  @BeforeClass
  public void init() {
    Bean bean = new Bean();
    bean.name = INITIAL_VALUE;
    form = new Bean2From().getFormFromBean(bean);
  }

  @Test
  public void test_beanWithString() {
    assertEquals(form.getFields().size(), 1);
    assertTrue(form.getFields().get(0).fieldType instanceof TextType);
  }

  @Test
  public void test_beanNameCorrect() {
    TextType textType = (TextType)form.getFields().get(0).fieldType;
    assertEquals(textType.oneValueField.name, "name");
  }

  @Test
  public void test_presetCorrect() {
    TextType textType = (TextType)form.getFields().get(0).fieldType;
    assertEquals( INITIAL_VALUE, textType.oneValueField.initialValue);
  }



  public class Bean {
    public String name;
  }
}

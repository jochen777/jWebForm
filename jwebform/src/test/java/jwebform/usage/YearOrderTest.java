package jwebform.usage;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;
import jwebform.field.SelectType.SelectInputEntry;
import jwebform.field.helper.CommonSelects;

// tests if the order of the years can be invidual
public class YearOrderTest {

  private static final int LARGE_YEAR = 2020;
  private static final int SMALL_YEAR = 2010;



  @Test
  public void testDatelargeToSmall() {
    List<SelectInputEntry> selects = CommonSelects.build().getYears(SMALL_YEAR, LARGE_YEAR);
    String keyOfFirstNotEmptySelect = selects.get(1).getKey();
    assertEquals(Integer.toString(SMALL_YEAR), keyOfFirstNotEmptySelect);
  }


  @Test
  public void testDateSmallToLarge() {
    List<SelectInputEntry> selects = CommonSelects.build().getYears(LARGE_YEAR, SMALL_YEAR);
    String keyOfFirstNotEmptySelect = selects.get(1).getKey();
    assertEquals(Integer.toString(LARGE_YEAR), keyOfFirstNotEmptySelect);
  }


}

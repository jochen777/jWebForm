package jwebform.themes.sourcecode;

/**
 * Some basic string utils to avoid dependance to commons
 * 
 * RFE: Make this public private!
 * 
 * @author jochen
 *
 */
public class StringUtils {

  public static final String EMPTY_STR = "";

  public static boolean isEmpty(final CharSequence cs) {
    return cs == null || cs.length() == 0;
  }

  public static String defaultString(final String str) {
    return str == null ? EMPTY_STR : str;
  }


}

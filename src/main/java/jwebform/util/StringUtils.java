package jwebform.util;

/**
 * Every project has this. So jWebform has it too!
 * 
 * Some basic string utils to avoid dependance to commons
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

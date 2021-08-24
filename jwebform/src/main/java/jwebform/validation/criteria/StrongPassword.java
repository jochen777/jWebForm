package jwebform.validation.criteria;

// See: http://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
/*
 * 
 * # start-of-string (?=.*[0-9]) 
 * # a digit must occur at least once (?=.*[a-z]) 
 * # a lower case letter must occur at least once (?=.*[A-Z]) 
 * # an upper case letter must occur at least once (?=.*[@#$%^&+=]) 
 * # a special character must occur at least once (?=\S+$) 
 * # no whitespace allowed in the entire string .{8,} 
 * # anything, at least eight places though $ 
 * # end-of-string
 * 
 */
public class StrongPassword extends Regex {
  private static final String REGEX =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=\\.\\!,])(?=\\S+$).{";
  private static final String REGEX1 = ",}$";

  StrongPassword(int minLen) {
    super(REGEX + minLen + REGEX1);
    setErrorMsg("jwebform.strong_password");
  }
}

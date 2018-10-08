package jwebform.integration.annotations;



import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface UseDecoration {

  String label();
  String helpText() default "";
  String placeholder() default "";


}

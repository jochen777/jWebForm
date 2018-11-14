package jwebform.integration.bean2form.annotations;



import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface UseDecoration {

  String label() default "";
  String helpText() default "";
  String placeholder() default "";


}

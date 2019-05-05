package jwebform.integration.bean2form.annotations;



import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Einriches a field in a bean with information about Decoration.
 * 
 * 
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface UseDecoration {

  String label() default "";

  String helpText() default "";

  String placeholder() default "";

  boolean isTranslated() default false;


}

package jwebform.integration.annotations;



import java.lang.annotation.*;

// use this to prevent a field in a bean to be converted to a jWebform Field
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreField {

}

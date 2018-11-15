package jwebform.integration.bean2form.annotations;



import java.lang.annotation.*;

// Indicates, that this field is required.
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface IsRequired {

}

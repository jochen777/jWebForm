package jwebform.field.structure;

import jwebform.env.Env.EnvWithSubmitInfo;

// Every jWebForm Type must implement this interface. It is just the "apply" fucntion of the
// "Function" (java.util.function.Function)
@FunctionalInterface
public interface SingleFieldType extends FieldType{
  FieldResult apply(EnvWithSubmitInfo envWithSubmitInfo);

}

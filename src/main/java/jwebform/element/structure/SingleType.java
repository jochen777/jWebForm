package jwebform.element.structure;

import jwebform.env.Env.EnvWithSubmitInfo;

// Every jWebForm Element must implement this interface. It is just the "apply" fucntion of the
// "Function" (java.util.function.Function)
@FunctionalInterface
public interface SingleType extends Element{
  ElementResult apply(EnvWithSubmitInfo elementWithSubmitInfo);



}

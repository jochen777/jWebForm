package jwebform.element.structure;

import java.util.function.Function;
import jwebform.env.Env.EnvWithSubmitInfo;

@FunctionalInterface
public interface Element extends Function<EnvWithSubmitInfo ,ElementResult> {

}

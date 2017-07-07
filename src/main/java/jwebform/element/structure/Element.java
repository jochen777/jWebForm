package jwebform.element.structure;

@FunctionalInterface
public interface Element {

	public ElementResult run(PrepareInfos renderInfos);
	
}

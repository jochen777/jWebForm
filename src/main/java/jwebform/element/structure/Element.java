package jwebform.element.structure;

@FunctionalInterface
public interface Element {

	public ElementResult prepare(PrepareInfos renderInfos);
	
}

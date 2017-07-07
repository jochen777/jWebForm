package jwebform.element.structure;

@FunctionalInterface
public interface Element {

	public ElementResult run(RenderInfos renderInfos);
	
}

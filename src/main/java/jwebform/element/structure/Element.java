package jwebform.element.structure;

@FunctionalInterface
public interface Element {

	// RFE: Find better name: "process", "validate", "Prepare Model"
	public ElementResult prepare(PrepareInfos renderInfos);
	
}

package jwebform.processor;

// will be called after the elements were processed
public interface PostProcessor {
  FieldResults postProcess(FieldResults results);
}

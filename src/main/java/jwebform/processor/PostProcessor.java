package jwebform.processor;

// will be called after the fields were processed
public interface PostProcessor {
  FieldResults postProcess(FieldResults results);
}

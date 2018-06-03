package jwebform.processors;

// will be called after the elements were processed
public interface PostProcessor {
  ElementResults postProcess(ElementResults results);
}

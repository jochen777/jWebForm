package jwebform.processors;

import java.util.List;

public interface Processors {
  public List<PreProcessor> getPreProssors();
  public List<Processor> getProcessors();
  public List<PostProcessor> getPostProcessors();
}

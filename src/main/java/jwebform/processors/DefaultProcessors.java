package jwebform.processors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultProcessors implements Processors {

  @Override
  public List<PreProcessor> getPreProssors() {
    return new ArrayList<>();
  }

  @Override
  public List<Processor> getProcessors() {
    return new ArrayList<>();
  }

  @Override
  public List<PostProcessor> getPostProcessors() {
    return Arrays.asList(new CheckDoubleElementsPostProcessor());
  }

}

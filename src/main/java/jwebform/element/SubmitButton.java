package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.PrepareInfos;
import jwebform.element.structure.ProducerInfos;
import jwebform.element.structure.StaticRenderData;
import jwebform.validation.ValidationResult;

public class SubmitButton implements Element {

  private final String label;

  public SubmitButton(String label) {
    this.label = label;
  }

  @Override
  public ElementResult prepare(PrepareInfos renderInfos) {
    HTMLProducer producer = renderInfos.getTheme().getHtmlProducer()
        .get("jwebform.element.SubmitButton");
    if (producer == null) {
      producer = new DefaultSubmitRenderer();
    }
    return new ElementResult("submit", (HTMLProducer) producer, ValidationResult.ok(), "", 1, this,
        "jwebform.element.SubmitButton", null, new SubmitButtonData(label));
  }

  public class DefaultSubmitRenderer implements HTMLProducer {

    @Override
    public String getHTML(ProducerInfos producerInfos) {
      return "<input tabindex=\"" + producerInfos.getTabIndex() + "\" type=\"submit\" value=\""
          + label + "\"><!-- own renderer -->";
    }

  }

  public class SubmitButtonData implements StaticRenderData {
    public final String label;

    public SubmitButtonData(String label) {
      this.label = label;
    }

  }

}

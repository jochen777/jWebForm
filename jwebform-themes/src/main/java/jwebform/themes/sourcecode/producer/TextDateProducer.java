package jwebform.themes.sourcecode.producer;

import java.util.List;

import jwebform.field.structure.HTMLProducer;
import jwebform.themes.sourcecode.Theme;
import jwebform.themes.sourcecode.mapper.Mapper;
import jwebform.validation.ValidationResult;
import jwebform.model.ProducerInfos;

public class TextDateProducer implements HTMLProducer {

  private final Theme theme;
  private final Mapper mapper;

  public TextDateProducer(Theme theme, Mapper mapper) {
    this.theme = theme;
    this.mapper = mapper;
  }

  @Override
  public String getHTML(ProducerInfos pi) {
    String errorMessage = "";

    ValidationResult vr = pi.getValidationResult();

    if (vr != ValidationResult.undefined() && !vr.isValid) {
      errorMessage = theme.getRenderer().renderErrorMessage(pi);
    }

    List<ProducerInfos> childs = pi.getChilds();
    StringBuffer html =
        new StringBuffer("\n" + pi.getDecoration().getLabel() + "<br/>" + errorMessage);
    for (ProducerInfos producerInfo : childs) {
      HTMLProducer producer = mapper.fromElement(producerInfo.getType()).get();
      html.append(producer.getHTML(producerInfo));
    }

    html.append("<br>" + pi.getDecoration().getHelptext());
    return html.toString();
  }

}

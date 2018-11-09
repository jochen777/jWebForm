package jwebform.themes.sourcecode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jwebform.FormResult;
import jwebform.FormModel.Method;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.HTMLProducer;
import jwebform.integration.FormRenderer;
import jwebform.integration.MessageSource;
import jwebform.processor.FieldResults;
import jwebform.themes.common.StartEndRenderer;
import jwebform.themes.sourcecode.mapper.Mapper;
import jwebform.model.ProducerInfos;

// Renders the form with pure java
public class ThemeJavaRenderer implements FormRenderer {

  public static final String BOOTSTRAP = "bootstrap";

  private final Mapper mapper;

  // IDEA: provide mapping between elements and Renderers. Default Mapping is provided
  public ThemeJavaRenderer(Mapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public String render(FormResult result, Method method, boolean html5Validation) {
    StringBuilder html = new StringBuilder();
    StartEndRenderer startEndRenderer =
        new StartEndRenderer(result, method.toString(), html5Validation);
    html.append(startEndRenderer.getStart() + "<fieldset>");
    int tabIndex = 1;
    ProducerInfos pi;
    for (Map.Entry<Field, FieldResult> entry : result.getFieldResults()) {
      FieldResult elementResult = entry.getValue();
      Field container = entry.getKey();
      // TODO: This must be done recursive!!
      List<ProducerInfos> childs =
          createProducerInfoChilds(elementResult.getChilds(), tabIndex, result.getFormId());
      pi = new ProducerInfos(result.getFormId(), tabIndex, elementResult, container, childs);
      Optional<HTMLProducer> producerOptional = mapper.fromElement(container.fieldType);
      // if we have a producer from the mapper, we take that.
      if (producerOptional.isPresent()) {
        html.append(producerOptional.get().getHTML(pi));
      } else {
        // no mapper avail? then take the element-included htmlProducuer
        html.append(elementResult.getStaticFieldInfo().getHtmlProducer().getHTML(pi));
      }
      tabIndex += elementResult.getStaticFieldInfo().getTabIndexIncrement();
    }

    html.append("\n</fieldset>" + startEndRenderer.getEnd());
    return html.toString();
  }

  private List<ProducerInfos> createProducerInfoChilds(FieldResults childs, int tabIndex,
      String formId) {
    List<ProducerInfos> listOfPis = new ArrayList<>(); // RFE: only, if childs is not empty!
    // RFE: This allows only one depth! It would be cooler, if we can do infinite depth
    for (Map.Entry<Field, FieldResult> entry : childs) {
      listOfPis.add(new ProducerInfos(formId, tabIndex, entry.getValue(), entry.getKey()));
      tabIndex++;
    }
    return listOfPis;
  }



}

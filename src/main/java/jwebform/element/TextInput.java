package jwebform.element;

import java.util.LinkedHashMap;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.RenderInfos;
import jwebform.env.Request;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.view.StringUtils;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class TextInput implements Element {

  final private String name; //
  // TBD: Does it make sense to introduce a Label class here?

  final private String initialValue;

  final private Validator validator; //

  final private OneFieldDecoration decoration;

  public TextInput(String name, OneFieldDecoration decoration, String initialValue,
      Validator validator) {
    this.name = name;
    this.validator = validator;
    this.initialValue = initialValue;
    this.decoration = decoration;
  }

  @Override
  public ElementResult run(RenderInfos renderInfos) {
    String formId = renderInfos.getFormId() + "-";
    String value = this.setupValue(renderInfos.getEnv().getRequest(), initialValue, formId);

    TextInputRenderer renderer = new TextInputRenderer(formId, value);
    ValidationResult vr = this.validate(renderInfos.getEnv().getRequest(), value, formId);
    
    ElementResult result = new ElementResult(name, renderer, vr, value, 1);
    return result;
  }

  public class TextInputRenderer implements HTMLProducer {

    private final String formId;
    private final String value;
    
    public TextInputRenderer(String formId, String value) {
      this.formId = formId;
      this.value = value;
    }

    

    @Override
    public String getHTML(ValidationResult vr, int tabIndex) {

      String errorMessage = "";
      Tag wrapper = new Tag("div", "class", "form-group");
      if (vr != ValidationResult.undefined()
          && vr.isValid) {
        wrapper.getTagAttributes().addToAttribute("class", " has-success");
      }
      if (vr != ValidationResult.undefined()
          && !vr.isValid) {
        wrapper.getTagAttributes().addToAttribute("class", " has-error");
        errorMessage = "Problem: " + vr.getMessage() + "<br>";
      }
      TagAttributes labelTagAttr = new TagAttributes("for", formId + name);
      Tag labelTag = new Tag("label", labelTagAttr, decoration.getLabel() + ":");

      LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
      attrs.put("tabindex", Integer.toString(tabIndex));
      attrs.put("type", "text");
      attrs.put("name", formId + name);
      attrs.put("value", value);

      if (!StringUtils.isEmpty(decoration.getPlaceholder())) {
        attrs.put("placeholder", decoration.getPlaceholder());
      }

      String helpHTML = "";
      if (!StringUtils.isEmpty(decoration.getHelptext())) {
        TagAttributes helpAttributes = new TagAttributes();
        helpAttributes.addToAttribute("id", "helpBlock-" + name);
        helpAttributes.addToAttribute("class", "help-block");
        Tag help = new Tag("span", helpAttributes, decoration.getHelptext());
        helpHTML = help.getComplete();
        attrs.put("aria-describedby", "helpBlock-" + name);
      }

      TagAttributes inputTagAttr = new TagAttributes(attrs);
      Tag inputTag = new Tag("input", inputTagAttr);
      String html = wrapper.getStartHtml() + errorMessage + labelTag.getComplete()
          + inputTag.getStartHtml() + helpHTML + wrapper.getEndHtml() + "\n";
      return html;
    }

  }


  private String setupValue(Request request, String initialValue, String formId) {
    if (request.getParameter(formId + name) != null) {
      return request.getParameter(formId + name);
    }
    return initialValue;
  }

  private ValidationResult validate(Request request, String value, String formId) {
    if (request.getParameter(formId + name) != null) {
      return validator.validate(value);
    }
    return ValidationResult.undefined();
  }

 

  @Override
  public String toString() {
    return String.format("TextInput. name=%s", name);
  }
}

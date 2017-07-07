package jwebform.element;

import java.util.LinkedHashMap;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.RenderInfos;
import jwebform.element.structure.TabIndexAwareElement;
import jwebform.element.structure.ValidationInfos;
import jwebform.env.Request;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.view.StringUtils;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class TextInput implements TabIndexAwareElement {

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

    TextInputRenderer renderer = new TextInputRenderer(formId, value, renderInfos.getTabIndex());
    ValidationResult vr = this.validate(renderInfos.getEnv().getRequest(), value, formId);
    /*
    ValidationResult validationResultToWorkWith = renderInfos.getOverrideValidationResult();
    ValidationResult vr = this.validate(renderInfos.getEnv().getRequest(), value, formId);
    validationResultToWorkWith = vr;

    String errorMessage = "";
    Tag wrapper = new Tag("div", "class", "form-group");
    if (validationResultToWorkWith != ValidationResult.undefined()
        && validationResultToWorkWith.isValid) {
      wrapper.getTagAttributes().addToAttribute("class", " has-success");
    }
    if (validationResultToWorkWith != ValidationResult.undefined()
        && !validationResultToWorkWith.isValid) {
      wrapper.getTagAttributes().addToAttribute("class", " has-error");
      errorMessage = "Problem: " + validationResultToWorkWith.getMessage() + "<br>";
    }
    TagAttributes labelTagAttr = new TagAttributes("for", formId + name);
    Tag labelTag = new Tag("label", labelTagAttr, decoration.getLabel() + ":");

    LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
    attrs.put("tabindex", Integer.toString(renderInfos.getTabIndex()));
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
    */
    ElementResult result = new ElementResult(name, renderer, "", vr, value);
    return result;
  }

  public class TextInputRenderer implements HTMLProducer {

    private final String formId;
    private final String value;
    private final int tabIndex;
    
    public TextInputRenderer(String formId, String value, int tabIndex) {
      this.formId = formId;
      this.value = value;
      this.tabIndex = tabIndex;
    }

    

    @Override
    public String getHTML(ValidationResult vr) {

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


  @Override
  public int getTabIndexIncrement() {
    return 1;
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
  public ValidationResult validate(ValidationInfos validationInfos) {
    String formId = validationInfos.getFormId() + "-";
    String value = this.setupValue(validationInfos.getEnv().getRequest(), initialValue, formId);
    ValidationResult vr = this.validate(validationInfos.getEnv().getRequest(), value, formId);
    return vr;
  }

  @Override
  public String toString() {
    return String.format("TextInput. name=%s", name);
  }
}

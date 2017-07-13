package jwebform.element;

import java.util.LinkedHashMap;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.PrepareInfos;
import jwebform.element.structure.ProducerInfos;
import jwebform.element.structure.StaticRenderData;
import jwebform.env.Request;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class TextInput implements Element {

	final private String name; //
	// TBD: Does it make sense to introduce a Label class here?

	final private String initialValue;

	final private Validator validator; //

	final private OneFieldDecoration decoration;

	public TextInput(String name, OneFieldDecoration decoration, String initialValue, Validator validator) {
		this.name = name;
		this.validator = validator;
		this.initialValue = initialValue;
		this.decoration = decoration;
	}

	@Override
	public ElementResult prepare(PrepareInfos renderInfos) {
		String formId = renderInfos.getFormId() + "-";
		String value = this.setupValue(renderInfos.getEnv().getRequest(), initialValue, formId);

		ValidationResult vr = this.validate(renderInfos.getEnv().getRequest(), value, formId);
		HTMLProducer producer = renderInfos.getTheme().getHtmlProducer().get("jwebform.element.TextInput");
		TextInputRenderData renderData = new TextInputRenderData(value, decoration, name);
		if (producer == null) {
			producer = new TextInputRenderer();
		}

		return new ElementResult(name, producer, vr, value, 1, this, "jwebform.element.TextInput", null, renderData);
	}

	public class TextInputRenderer implements HTMLProducer {

		@Override
		public String getHTML(ProducerInfos producerInfos) {
		    TextInputRenderData data = (TextInputRenderData)producerInfos.getStaticRenderData();
			// very simple version!
			String errorMessage = "Problem: " + producerInfos.getVr().getMessage() + "<br>";
			LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
			attrs.put("tabindex", Integer.toString(producerInfos.getTabIndex()));
			attrs.put("type", "text");
			attrs.put("name", producerInfos.getFormId() + "-" + name);
			attrs.put("value", data.value);
			TagAttributes inputTagAttr = new TagAttributes(attrs);
			Tag inputTag = new Tag("input", inputTagAttr);
			String html = decoration.getLabel() + errorMessage + inputTag.getStartHtml();

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

	public class TextInputRenderData implements StaticRenderData{
      public final String value; 
      public final OneFieldDecoration decoration; 
      public final String name;
      public TextInputRenderData(String value, OneFieldDecoration decoration, String name) {
        this.value = value;
        this.decoration = decoration;
        this.name = name;
      }
	}
}

package jwebform.element.renderer.bootstrap;

import java.util.LinkedHashMap;

import com.coverity.security.Escape;

import jwebform.element.OneFieldDecoration;
import jwebform.element.TextInput;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;
import jwebform.validation.ValidationResult;
import jwebform.view.StringUtils;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class FastBootstrapTextInputRenderer implements HTMLProducer {

	@Override
	public String getHTML(ProducerInfos pi) {
		OneFieldDecoration decoration = ((TextInput) pi.getSource()).decoration;
		String formId = pi.getFormId() + "-";
		String errorMessage = "";
		
		String errorClass = "";
		if (pi.getVr() != ValidationResult.undefined() && pi.getVr().isValid) {
			errorClass = " has-success";
		}
		if (pi.getVr() != ValidationResult.undefined() && !pi.getVr().isValid) {
			errorClass = " has-error";
			errorMessage = "Problem: " + pi.getVr().getMessage() + "<br>";
		}
		
		String labelStr = "<label for=\""+formId+""+pi.getName()+"\">"+decoration.getLabel()+":</label>";

		// <input tabindex="5" type="text" name="fid-textInput2" value="Peter&quot;Paul" placeholder="Placeholder" aria-describedby="helpBlock-textInput2">
		String placeholder = "";
		if (!StringUtils.isEmpty(decoration.getPlaceholder())) {
			placeholder = " placeholder=\""+decoration.getPlaceholder()+"\"";
		}

		String helpHTML;
		String aria;
		if (!StringUtils.isEmpty(decoration.getHelptext())) {
			helpHTML = "<span id=\"helpBlock-"+pi.getName()+"\" class=\"help-block\">"+decoration.getHelptext()+"</span>";
			aria = " aria-describedby=\"helpBlock-"+pi.getName()+"\"";
		} else {
			helpHTML = "";
			aria = "";
		}
		String val = pi.getValue();
		if (val.length() > 0) {
			val = "=\"" + Escape.html(val) + "\"";
		}
		String inputHtml = "<input tabindex=\""+ pi.getTabIndex()+"\" type=\"text\" name=\""+formId+""+pi.getName()+"\" value"+val+""+placeholder+""+aria+">";

		String html = "<div class=\"form-group" + errorClass +"\">" + errorMessage + labelStr + inputHtml
				+ helpHTML + "</div>\n"; 
		return html;
	}

}

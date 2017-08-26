package jwebform.element.renderer.bootstrap;

import jwebform.element.OneFieldDecoration;
import jwebform.element.TextDateInput;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;
import jwebform.validation.ValidationResult;

public class BootstrapTextDateInputRenderer implements HTMLProducer{

	@Override
	public String getHTML(ProducerInfos pi) {
      OneFieldDecoration decoration = ((TextDateInput)pi.getSource()).decoration; 
	  String errorMessage = "";
      if (pi.getVr() != ValidationResult.undefined() && !pi.getVr().isValid) {
        errorMessage = "Problem: " + pi.getVr().getMessage() + "<br>";
      }
      ElementResult dayResult = pi.getChilds().get(0);
      ElementResult monthResult = pi.getChilds().get(1);
      ElementResult yearResult = pi.getChilds().get(2);
      String html = decoration.getLabel() + "<br/>" + errorMessage
          + dayResult.getHtml(new ProducerInfos(pi.getFormId(), pi.getTabIndex(),
                  dayResult.getValidationResult(), null, dayResult.getSource(), dayResult.getName(), dayResult.getValue(), pi.getTheme()))

          + monthResult.getHtml(new ProducerInfos(pi.getFormId(), pi.getTabIndex() + 1,
                  monthResult.getValidationResult(), null, 
                  monthResult.getSource(),
                  monthResult.getName(), monthResult.getValue(), pi.getTheme()))
              
          + yearResult.getHtml(new ProducerInfos(pi.getFormId(),
              pi.getTabIndex() + 2, yearResult.getValidationResult(), null, yearResult.getSource(),
              yearResult.getName(), yearResult.getValue(), pi.getTheme()))
          + "<br>" + decoration.getHelptext();
      return html;	}

}

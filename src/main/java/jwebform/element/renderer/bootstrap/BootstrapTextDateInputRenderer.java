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
      OneFieldDecoration decoration = ((TextDateInput)pi.getElementResult().getSource()).decoration; 
	  String errorMessage = "";
	  
		ValidationResult vr = pi.getElementResult().getValidationResult();

      if (vr != ValidationResult.undefined() && !vr.isValid) {
        errorMessage = "Problem: " + vr.getMessage() + "<br>";
      }
      ElementResult dayResult = pi.getElementResult().getChilds().get(0);
      ElementResult monthResult = pi.getElementResult().getChilds().get(1);
      ElementResult yearResult = pi.getElementResult().getChilds().get(2);
      String html = decoration.getLabel() + "<br/>" + errorMessage
          + dayResult.getHtml(new ProducerInfos(pi.getFormId(), pi.getTabIndex(),
                   pi.getTheme(), dayResult))

          + monthResult.getHtml(new ProducerInfos(pi.getFormId(), pi.getTabIndex() + 1,
                   pi.getTheme(), monthResult))
              
          + yearResult.getHtml(new ProducerInfos(pi.getFormId(),
              pi.getTabIndex() + 2,  pi.getTheme(), yearResult))
          + "<br>" + decoration.getHelptext();
      return html;	}

}

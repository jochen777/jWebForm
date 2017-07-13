package jwebform.element.renderer.bootstrap;

import jwebform.element.TextDateInput.TextDateData;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;
import jwebform.validation.ValidationResult;

public class BootstrapTextDateInputRenderer implements HTMLProducer{

	@Override
	public String getHTML(ProducerInfos pi) {
	  TextDateData data = (TextDateData)pi.getStaticRenderData();
	  String errorMessage = "";
      if (pi.getVr() != ValidationResult.undefined() && !pi.getVr().isValid) {
        errorMessage = "Problem: " + pi.getVr().getMessage() + "<br>";
      }
      ElementResult dayResult = pi.getChilds().get(0);
      ElementResult monthResult = pi.getChilds().get(1);
      ElementResult yearResult = pi.getChilds().get(2);
      String html = data.decoration.getLabel() + "<br/>" + errorMessage
          + dayResult.getHtmlProducer()
              .getHTML(new ProducerInfos(pi.getFormId(), pi.getTabIndex(),
                  dayResult.getValidationResult(), null, dayResult.getStaticRenderData()))

          + monthResult.getHtmlProducer()
              .getHTML(new ProducerInfos(pi.getFormId(), pi.getTabIndex() + 1,
                  monthResult.getValidationResult(), null, monthResult.getStaticRenderData()))
          + yearResult.getHtmlProducer().getHTML(new ProducerInfos(pi.getFormId(),
              pi.getTabIndex() + 2, yearResult.getValidationResult(), null,
              yearResult.getStaticRenderData()))
          + "<br>" + data.decoration.getHelptext();
      return html;	}

}

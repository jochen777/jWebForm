package jwebform.element.renderer.bootstrap;

import java.util.HashMap;
import java.util.Map;

import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.Theme;

public class BootstrapTheme implements Theme {
	public Map<String, HTMLProducer> htmlProducer;
	
	public BootstrapTheme() {
		htmlProducer = new HashMap<>();
		htmlProducer.put( "jwebform.element.TextInput", new BootstrapTextInputRenderer());
		htmlProducer.put( "jwebform.element.TextDateInput", new BootstrapTextDateInputRenderer());
		htmlProducer.put( "jwebform.element.SubmitButton", new BootstrapSubmitButtonRenderer());
	}

	public Map<String, HTMLProducer> getHtmlProducer() {
		return htmlProducer;
	}
}

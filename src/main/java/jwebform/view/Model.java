package jwebform.view;

import java.util.HashMap;
import java.util.Map;

// Model to transport values from from to view
@Deprecated
public class Model {
	Map<String, Object> content = new HashMap<>();
	
	private Model (Map<String, Object> initialContent) {
		content.putAll(initialContent);
	}
	
	private Model (String key, Object value) {
		this.put(key, value);
	}
	
	private Model() {
	}
	
	
	public Model put(String key, Object value) {
		content.put(key, value);
		return this;
	}
	
	public Object get(String key) {
		return content.get(key);
	}

	public String getString(String key) {
		return content.get(key).toString();
	}
	
}

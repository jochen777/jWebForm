package jwebform.view;

import java.util.HashMap;
import java.util.Map;

// Model to transport values from from to view
public class Model {
	Map<String, Object> content = new HashMap<>();
	
	public Model (Map<String, Object> initialContent) {
		content.putAll(initialContent);
	}
	
	public Model (String key, Object value) {
		this.put(key, value);
	}
	
	public Model() {
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

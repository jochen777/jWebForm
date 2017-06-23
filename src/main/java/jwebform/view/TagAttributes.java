package jwebform.view;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.coverity.security.Escape;

// class that holds html tag-attributes
// RFE: make it immutable
public class TagAttributes {
	LinkedHashMap<String, String> attributes;

	public TagAttributes(Map<String, String> attribs) {
		attributes = new LinkedHashMap<>(attribs);
	}

	public TagAttributes(LinkedHashMap<String, String> attribs) {
		attributes = attribs;
	}

	public TagAttributes() {
		this(new LinkedHashMap<>());
	}

	public TagAttributes(String key, String value) {
		this();
		this.put(key, value);
	}

	public TagAttributes put(String key, String value) {
		attributes.put(key, value);
		return this;
	}

	public String get(String key) {
		return attributes.get(key);
	}

	public void addToAttribute(String key, String value) {
		if (!attributes.containsKey(key)) {
			attributes.put(key, value);
		} else {
			attributes.put(key, attributes.get(key) + value);
		}
	}

	public Set<String> keySet() {
		return attributes.keySet();
	}
	
	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void add(TagAttributes formAttributes) {
		if (formAttributes != null) {
			formAttributes.attributes.forEach((key, value) -> this.addToAttribute(key, value));
		}
	}

	public void add(LinkedHashMap<String, String> attribs) {
		if (attribs != null) {
			attribs.forEach((key, value) -> this.addToAttribute(key, value));
		}
	}

	public String renderHtml() {
		return this.buildAttributes().trim();
	}
	
	
	public  String buildAttributes() {
		StringBuilder attrStr = new StringBuilder();
		attributes.forEach((k,v) ->
			attrStr.append(buildSingleAttribute(k, attributes.get(k)))
				);
		return attrStr.toString();
	}

	public String buildSingleAttribute(String key, String value) {
		StringBuilder attrStr = new StringBuilder();
		if (StringUtils.isEmpty(value)) {
			attrStr.append(key);
		} else {
			// avoid quoting space:
			String escapedValue = Escape.html(value);
			escapedValue = escapedValue.replaceAll("&#x20;", " ");
			attrStr.append(key).append("=\"").append(escapedValue).append("\"");
		}
		attrStr.append(" ");
		return attrStr.toString();
	}



}

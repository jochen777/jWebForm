package jwebform.element.structure;

// provides begin and end code, that can wrap something
public class Wrapper {
	public final String start;
	public final String end;

	public static Wrapper of(String start, String end) {
		return new Wrapper(start, end);
	}
	
	public Wrapper(String start, String end) {
		this.start = start;
		this.end = end;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}
	
	public String wrap(String content) {
		return new StringBuffer(start).append(content).append(end).toString();
	}

	public static Wrapper empty() {
		return Wrapper.of("", "");
	}
	
	public static Wrapper ofTag(String tagName) {
		return Wrapper.of("<" + tagName + ">", "</" + tagName + ">");
	}
	
}

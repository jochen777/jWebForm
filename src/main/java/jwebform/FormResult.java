package jwebform;

public class FormResult {

	private Form form;
	private boolean valid;
	
	public FormResult(Form form, boolean valid) {
		this.form = form;
		this.valid = valid;
	}
	 
	public boolean isOk() {
		return valid;
	}

	public View getView() {
		return new View(form);
	}

}

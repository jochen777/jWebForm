package jwebform;

public class FormResult {

	private Form form;
	
	public FormResult(Form form) {
		this.form = form;
	}
	 
	public boolean isOk() {
		// TODO Auto-generated method stub
		return true;
	}

	public View getView() {
		return new View(form);
	}

}

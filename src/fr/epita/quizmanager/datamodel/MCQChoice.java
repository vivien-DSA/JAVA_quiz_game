package fr.epita.quizmanager.datamodel;

public class MCQChoice {

	//renamed to choiceLabel : less confusing
	private String choiceLabel;
	
	
	private boolean valid;
	
	public String getChoiceLabel() {
		return choiceLabel;
	}

	public void setChoiceLabel(String choiceLabel) {
		this.choiceLabel = choiceLabel;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	
}

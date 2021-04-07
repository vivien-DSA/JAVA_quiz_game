package fr.epita.quizmanager.exception;

public class DeleteFailedException extends Exception {
	
	private Object obj;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}

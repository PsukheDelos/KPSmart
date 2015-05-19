package kps.distribution.exception;

public class InvalidPriorityException extends RuntimeException {

	private String msg;

	public InvalidPriorityException(String msg) {
		this.msg = msg;
	}

	public String toString(){
		return msg;
	}
}

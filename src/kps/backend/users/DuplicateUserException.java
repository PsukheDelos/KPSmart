package kps.backend.users;

public class DuplicateUserException extends Exception {
	public final String message;
	
	public DuplicateUserException(String message){
		this.message = message;
	}
	
	public String toString(){
		return message;
	}
}

package kps.distributionNetwork;

public class InvalidRouteException extends Exception {

	private String message;

	public InvalidRouteException(String message) {
		this.message = message;
	}
	
	public String toString(){
		return this.message;
	}
}

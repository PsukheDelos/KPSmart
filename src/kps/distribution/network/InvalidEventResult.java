package kps.distribution.network;

import kps.distribution.event.EventResult;

public class InvalidEventResult extends EventResult {

	private String msg;

	public InvalidEventResult(String msg) {
		this.msg = msg;
	}
	
	public String toString(){
		return this.msg;
	}

}

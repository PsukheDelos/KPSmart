package kps.distribution.network;

import java.io.Serializable;

import kps.distribution.exception.InvalidPriorityException;

public enum Priority implements Serializable{
	INTERNATIONAL_AIR,
	INTERNATIONAL_STANDARD,
	DOMESTIC_AIR,
	DOMESTIC_STANDARD;

	public static Priority fromString(String s){
		String lower = s.toLowerCase();
		if (lower.contains("international") && lower.contains("air"))
			return INTERNATIONAL_AIR;
		if (lower.contains("international") && lower.contains("standard"))
			return INTERNATIONAL_STANDARD;
		if (lower.contains("domestic") && lower.contains("air"))
			return DOMESTIC_AIR;
		if (lower.contains("domestic") && lower.contains("standard"))
			return DOMESTIC_STANDARD;

		throw new InvalidPriorityException(s + " is not a valid Priority string.");
	}
}

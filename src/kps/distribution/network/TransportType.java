package kps.distribution.network;

import java.security.InvalidParameterException;

public enum TransportType {
	LAND,
	SEA,
	AIR;

	public static TransportType fromString(String s){
		s = s.trim().toUpperCase();
		if (s.equals("LAND"))
			return LAND;
		if (s.equals("SEA"))
			return SEA;
		if (s.equals("AIR"))
			return AIR;
		else throw new InvalidParameterException(s + " is not a recognised transport type");
	}
}

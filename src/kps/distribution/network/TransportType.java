package kps.distribution.network;

import java.security.InvalidParameterException;

public enum TransportType {
	LAND,
	SEA,
	AIR;

	public static TransportType fromString(String s){
		if (s.equalsIgnoreCase("LAND"))
			return LAND;
		if (s.equalsIgnoreCase("SEA"))
			return SEA;
		if (s.equalsIgnoreCase("AIR"))
			return AIR;
		else throw new InvalidParameterException(s + " is not a recognised transport type");
	}
}

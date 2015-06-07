package kps.distribution.network;

import java.io.Serializable;

public class Company implements Serializable{
	public final String name;

	public Company(String name){
		this.name = name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		return ((Company)obj).name.equals(name);
	}
}

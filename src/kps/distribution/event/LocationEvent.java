package kps.distribution.event;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "location")
@XmlType(propOrder = {"location", "longitude", "latitude"})
public abstract class LocationEvent extends DistributionNetworkEvent {
	
	private static final long serialVersionUID = 1L;

	public final String location;
	public final double longtitude;
	public final double latitude;
	
	@XmlAttribute
	public final String action;
	
	@XmlTransient
	public final UUID id;

	public LocationEvent(String location, double longtitude, double latitude, String action) {
		this.location = location;
		this.longtitude = longtitude;
		this.latitude = latitude;
		
		this.action = action;
		this.id = UUID.randomUUID();
	}

}

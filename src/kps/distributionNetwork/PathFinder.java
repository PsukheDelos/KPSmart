package kps.distributionNetwork;

import kps.pathFinder.PathNotFoundException;

public interface PathFinder {
	public abstract MailDelivery getPath(Mail mail) throws PathNotFoundException;
}

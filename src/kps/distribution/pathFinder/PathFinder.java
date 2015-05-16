package kps.distribution.pathFinder;

import kps.distribution.network.Mail;
import kps.distribution.network.MailDelivery;

public interface PathFinder {
	public abstract MailDelivery getPath(Mail mail) throws PathNotFoundException;
}

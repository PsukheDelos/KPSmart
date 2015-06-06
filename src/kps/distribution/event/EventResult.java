package kps.distribution.event;

import java.util.UUID;

import kps.net.event.Event;

public abstract class EventResult extends Event{
	private static final long serialVersionUID = 1L;
	public UUID id;
}

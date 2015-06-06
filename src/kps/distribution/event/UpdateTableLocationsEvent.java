package kps.distribution.event;

import javax.swing.JTable;

import kps.backend.database.LocationRepository;

public class UpdateTableLocationsEvent extends UpdateTableEvent{
	public UpdateTableLocationsEvent(JTable table) {
		super(table, LocationRepository.getModel());
	}

	private static final long serialVersionUID = 1L;
}

package kps.distribution.event;

import javax.swing.JTable;

import kps.backend.database.UserRepository;

public class UpdateTableUserEvent extends UpdateTableEvent{
	public UpdateTableUserEvent(JTable table) {
		super(table, UserRepository.getUserModel());
	}

	private static final long serialVersionUID = 1L;

}

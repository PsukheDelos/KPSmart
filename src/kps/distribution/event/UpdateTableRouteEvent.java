package kps.distribution.event;

import javax.swing.JTable;

import kps.backend.database.CostRepository;

public class UpdateTableRouteEvent extends UpdateTableEvent{
	public UpdateTableRouteEvent(JTable table) {
		super(table, CostRepository.getRoutesModel());
	}

	private static final long serialVersionUID = 1L;
}

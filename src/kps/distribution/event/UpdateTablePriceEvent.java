package kps.distribution.event;

import javax.swing.JTable;

import kps.backend.database.PriceRepository;

public class UpdateTablePriceEvent extends UpdateTableEvent{
	public UpdateTablePriceEvent(JTable table) {
		super(table, PriceRepository.getPricesModel());
	}

	private static final long serialVersionUID = 1L;
}

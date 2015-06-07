package kps.distribution.event;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import kps.interfaces.IGlobalEvent;

public abstract class UpdateTableEvent extends DistributionNetworkEvent implements IGlobalEvent{
	private static final long serialVersionUID = 1L;
	
	public UpdateTableEvent(JTable table, TableModel model){
			table.setModel(model);
	}
}

package kps.distribution.event;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public abstract class UpdateTableEvent extends DistributionNetworkEvent{
	private static final long serialVersionUID = 1L;
	
	public UpdateTableEvent(JTable table, TableModel model){
			table.setModel(model);
	}
}

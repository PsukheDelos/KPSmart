package kps.net.event;

import javax.swing.table.TableModel;

public class XMLReplyEvent extends Event{
	
	public final TableModel tableModel;
	
	public XMLReplyEvent(TableModel tableModel){
		this.tableModel = tableModel;
	}

}

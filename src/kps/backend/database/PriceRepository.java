package kps.backend.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kps.distribution.network.Location;

public class PriceRepository {

	private static Connection db = null;

	public static boolean thereIsAConnectionToTheDatabase(){
		return db != null;
	}

	public static JTable getPricesTable(){
		if(!thereIsAConnectionToTheDatabase()) db = KPSDatabase.createConnection();
		try {
		    DefaultTableModel model = new DefaultTableModel();
			JTable prices = new JTable(model);
			model.addColumn("from");
			model.addColumn("to");
			model.addColumn("priority");
			model.addColumn("price per gram");
			model.addColumn("price per volume");
			Statement statement = db.createStatement();
			String query = "SELECT * FROM price";
			ResultSet result = statement.executeQuery(query);
			while(result.next()){
			    model.addRow(new Object[] {result.getString(1), result.getString(2), result.getString(3), result.getDouble(4), result.getDouble(5)});
			}
			return prices;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
}

package kps.backend;

import kps.distribution.event.*;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLFileHandler {

	/**
	 * Writes to the given file the Distribution Network expressed in xml format.
	 * @param file : The file name/path to where the xml will be saved.
	 */
	public static void write(CustomerPriceEvent event){
		
		//Creates the new file.
		File f = new File("lastUpdate.xml");
		
		//Checks to see if the file already exists.
		/*
		if(f.exists() && !f.isDirectory()){
			
			//If it does exists ask user if they would like to overwrite the file or not.
			if(JOptionPane.showConfirmDialog(null, "File already exists.\nDo you want to overwrite?", "File already exists", 0)==1) return;
		}
		*/
		
		//Begins writing state to file
		try{
			
			//Creates the context/template to generate marshaller from.
			JAXBContext context = JAXBContext.newInstance(CustomerPriceEvent.class);
			
			//Creates a marshaller to be used to generate xml.
			Marshaller m = context.createMarshaller();
			
			//Sets the output to be nicely formatted.
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			//Writes the object out in xml to the given file.
			m.marshal(event, f);
		}catch (JAXBException e){
			e.printStackTrace();
		}
		
		String update = "";
		
		try{
			Scanner scan = new Scanner(f);
			
			//Read past the declaration
			scan.nextLine();
			
			//Read the update
			while(scan.hasNextLine()){
				update += "    " + scan.nextLine() + "\n";
			}
			
			scan.close();
			
			//Read the log file
			scan = new Scanner(new File("log.xml"));
			
			String data = "";
			String line = "";
			
			//Save all prior logs
			while(true){
				//System.out.println(line);
				line = scan.nextLine();
				if(line.equals("</events>")) break;
				data += line + "\n";
			}
			
			//Add new log in
			data += update;
			
			//Close off log file
			data += "</events>";
			
			//Append it to log file
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.xml",false)));
			out.print(data);
			out.close();
			scan.close();
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	
	public static TableModel loadLog(){
		
		//Create empty table
		DefaultTableModel model = new DefaultTableModel(){
			 @Override
			 public boolean isCellEditable(int row, int column){
				 return false;
			 }
		};
		
		//Add in the different columns
		model.addColumn("Action");
		model.addColumn("To");
		model.addColumn("From");
		model.addColumn("Priority");
		model.addColumn("Weight Cost");
		model.addColumn("Volume Cost");
		
		//Read in the data
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		try{
			builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(new FileInputStream("log.xml"));
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			String expression = "//price";
			
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			
			for(int i = 0; i<nodeList.getLength();i++){
				System.out.println(nodeList.item(i).getFirstChild().getNodeValue());
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		
		//Return the updated table
		return model;
	}
	
	public static void main(String[] args){
		XMLFileHandler.write(new CustomerPriceUpdateEvent("Wellington","Auckland","Air",3.00,5.00));
		//XMLFileHandler.loadLog();
	}
}

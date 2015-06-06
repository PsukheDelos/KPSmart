package kps.backend;

import kps.distribution.event.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
				update += scan.nextLine() + "\n";
			}
			
			//Append it to log file
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.xml",true)));
			
			out.print(update);
			out.close();
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		//If file is generated correctly save it to current directory.
		/*try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	//Not used
	/*public static DistributionNetwork read(String file){
		
		//Blank variable to be filled by xml data
		DistributionNetwork DN = null;
		
		//Attempt to read from the xml
		try{
			
			//Creates the context/template to generate unmarshaller from.
			JAXBContext context = JAXBContext.newInstance(DistributionNetwork.class);
			
			//Creates a unmarshaller to be used to read from xml.
			Unmarshaller un = context.createUnmarshaller();
			
			//Read the xml and create a new Distribution Network
			DN = (DistributionNetwork) un.unmarshal(new File(file));
			
		}catch(JAXBException e){
			e.printStackTrace();
		}
		
		//Return the generated Distribution Network
		return DN;
	}*/
	
	public static void main(String[] args){
		XMLFileHandler.write(new CustomerPriceUpdateEvent("Wellington","Auckland","Air",3.00,5.00));
	}
}

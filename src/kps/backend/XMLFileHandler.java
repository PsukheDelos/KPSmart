package kps.backend;

import kps.distribution.network.DistributionNetwork;

import java.io.File;
import java.io.IOException;

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
	public static void write(String file, DistributionNetwork network){
		
		//Creates the new file.
		File f = new File(file + ".xml");
		
		//Checks to see if the file already exists.
		if(f.exists() && !f.isDirectory()){
			
			//If it does exists ask user if they would like to overwrite the file or not.
			if(JOptionPane.showConfirmDialog(null, "File already exists.\nDo you want to overwrite?", "File already exists", 0)==1) return;
		}
		
		//Begins writing state to file
		try{
			
			//Creates the context/template to generate marshaller from.
			JAXBContext context = JAXBContext.newInstance(DistributionNetwork.class);
			
			//Creates a marshaller to be used to generate xml.
			Marshaller m = context.createMarshaller();
			
			//Sets the output to be nicely formatted.
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			//Writes the object out in xml to the given file.
			m.marshal(network, f);
		}catch (JAXBException e){
			e.printStackTrace();
		}
		
		//If file is generated correctly save it to current directory.
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DistributionNetwork read(String file){
		
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
	}
	
	public static void main(String[] args){
		XMLFileHandler.write("test", new DistributionNetwork());
	}
}

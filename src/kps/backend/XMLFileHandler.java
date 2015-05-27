package kps.backend;

import java.io.File;
import java.io.IOException;

public class XMLFileHandler {

	/**
	 * Writes to the given file the Mail System expressed in xml format.
	 * @param file : The file name/path to where the xml will be saved.
	 */
	public static void write(String file){
		
		File f = new File(file + ".xml");
		try {
			if(!f.createNewFile()) System.out.println("File already exists.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String read(){
		return "";
	}
	
	public static void main(String[] args){
		XMLFileHandler.write("troll");
	}
}

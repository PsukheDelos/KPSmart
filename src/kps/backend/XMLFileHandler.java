package kps.backend;

import kps.distribution.event.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLFileHandler {	
	
	/**
	 * Writes to the given file the Distribution Network expressed in xml format.
	 * @param file : The file name/path to where the xml will be saved.
	 */
	public static void write(DistributionNetworkEvent event){
		
		//Creates the new file.
		File f = new File("lastUpdate.xml");
		
		//Begins writing state to file
		try{
			
			//Creates the context/template to generate marshaller from.
			JAXBContext context;
			if(event instanceof CustomerPriceEvent){
				context = JAXBContext.newInstance(CustomerPriceEvent.class);
				CustomerPriceEvent castEvent = (CustomerPriceEvent) event;
				//Creates a marshaller to be used to generate xml.
				Marshaller m = context.createMarshaller();
				
				//Sets the output to be nicely formatted.
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				
				//Writes the object out in xml to the given file.
				m.marshal(castEvent, f);
			}else if(event instanceof LocationEvent){
				context = JAXBContext.newInstance(LocationEvent.class);
				LocationEvent castEvent = (LocationEvent) event;
				//Creates a marshaller to be used to generate xml.
				Marshaller m = context.createMarshaller();
				
				//Sets the output to be nicely formatted.
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				
				//Writes the object out in xml to the given file.
				m.marshal(castEvent, f);
			}else if(event instanceof MailDeliveryEvent){
				context = JAXBContext.newInstance(MailDeliveryEvent.class);
				MailDeliveryEvent castEvent = (MailDeliveryEvent) event;
				//Creates a marshaller to be used to generate xml.
				Marshaller m = context.createMarshaller();
				
				//Sets the output to be nicely formatted.
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				
				//Writes the object out in xml to the given file.
				m.marshal(castEvent, f);
			}else if(event instanceof TransportCostEvent){
				context = JAXBContext.newInstance(TransportCostEvent.class);
				TransportCostEvent castEvent = (TransportCostEvent) event;
				//Creates a marshaller to be used to generate xml.
				Marshaller m = context.createMarshaller();
				
				//Sets the output to be nicely formatted.
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				
				//Writes the object out in xml to the given file.
				m.marshal(castEvent, f);
			}else{
				System.out.println("Unable to write to xml.");
				return;
			}
			
			
			
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
		/*
		model.addColumn("Action");
		model.addColumn("To");
		model.addColumn("From");
		model.addColumn("Priority");
		model.addColumn("Weight Cost");
		model.addColumn("Volume Cost");
		*/
		
		model.addColumn("Index");
		model.addColumn("Type");
		model.addColumn("Action");
		model.addColumn("Description");
		
		//Read in the data
		File file = new File("log.xml");
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		try{
			builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(file);
			document.getDocumentElement().normalize();
			
			NodeList nodeList = document.getElementsByTagName("event");
			
			for(int i=nodeList.getLength()-1;i>-1;i--){
				Node node = nodeList.item(i);
				
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element element = (Element) node;
					
					/*
					model.addRow(new Object[] {
							element.getAttribute("action"),
							element.getElementsByTagName("to").item(0).getTextContent(),
							element.getElementsByTagName("from").item(0).getTextContent(),
							element.getElementsByTagName("priority").item(0).getTextContent(),
							element.getElementsByTagName("weightCost").item(0).getTextContent(),
							element.getElementsByTagName("volumeCost").item(0).getTextContent()
					});
					*/
					String description = "";
					if(element.getAttribute("xmlType").equals("cost")){
						description = "To:" + element.getElementsByTagName("to").item(0).getTextContent()
								+ "; From:" + element.getElementsByTagName("from").item(0).getTextContent()
								+ "; Type:" + element.getElementsByTagName("type").item(0).getTextContent()
								+ "; WeightCost:" + element.getElementsByTagName("weightCost").item(0).getTextContent()
								+ "; VolumeCost:" + element.getElementsByTagName("volumeCost").item(0).getTextContent();
					}else if(element.getAttribute("xmlType").equals("price")){
						description = "To:" + element.getElementsByTagName("to").item(0).getTextContent()
								+ "; From:" + element.getElementsByTagName("from").item(0).getTextContent()
								+ "; Type:" + element.getElementsByTagName("priority").item(0).getTextContent()
								+ "; WeightCost:" + element.getElementsByTagName("weightCost").item(0).getTextContent()
								+ "; VolumeCost:" + element.getElementsByTagName("volumeCost").item(0).getTextContent();
					}else if(element.getAttribute("xmlType").equals("location")){
						description = "Location:" + element.getElementsByTagName("location").item(0).getTextContent()
								+ "; Longtitude:" + element.getElementsByTagName("longtitude").item(0).getTextContent()
								+ "; Latitude:" + element.getElementsByTagName("latitude").item(0).getTextContent();
					}else if(element.getAttribute("xmlType").equals("mail")){
						description = "To:" + element.getElementsByTagName("to").item(0).getTextContent()
								+ "; From:" + element.getElementsByTagName("from").item(0).getTextContent()
								+ "; Type:" + element.getElementsByTagName("priority").item(0).getTextContent()
								+ "; Weight:" + element.getElementsByTagName("weight").item(0).getTextContent()
								+ "; Volume:" + element.getElementsByTagName("volume").item(0).getTextContent();
					}
					
					model.addRow(new Object[] {
							nodeList.getLength()-i,
							element.getAttribute("xmlType"),
							element.getAttribute("action"),
							description
					});
				}
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		//Return the updated table
		return model;
	}
	
	public static void main(String[] args){
		XMLFileHandler.write(new CustomerPriceUpdateEvent("Wellington","Auckland","Air",3.00,5.00));
		XMLFileHandler.write(new LocationAddEvent("Dubai",50.00,1000.00));
		XMLFileHandler.write(new MailDeliveryEvent());
		XMLFileHandler.write(new TransportCostUpdateEvent("NZAir", "Wellington", "Dubai", "Air", 2.00, 5.00, 100.00, 50.00, 5.00, 2.00, "Holiday"));
		XMLFileHandler.write(new CustomerPriceUpdateEvent("Wellington","Auckland","Air",20.00,5.00));
		//XMLFileHandler.loadLog();
	}
}

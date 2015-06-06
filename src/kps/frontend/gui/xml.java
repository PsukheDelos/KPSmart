package kps.frontend.gui;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;
public class xml {


	public static void main(String[] args) {
		new xml("sample1.xml");
	}
	
	public  xml(String filename){
		// TODO Auto-generated method stub
				DocumentBuilderFactory builderFactory =
				        DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = null;
				Document xmlDocument = null;
				try {
				    builder = builderFactory.newDocumentBuilder();
				} catch (ParserConfigurationException e) {
				    e.printStackTrace();  
				}
				
				try {
				    xmlDocument = builder.parse(
				            new FileInputStream(filename));
				} catch (SAXException e) {
				    e.printStackTrace();
				} catch (IOException e) {
				    e.printStackTrace();
				}
				
//				String xml = ...;
//				Document xmlDocument = builder.parse(new ByteArrayInputStream(xml.getBytes()));
						//read a string value
				
				try {
					XPath xPath =  XPathFactory.newInstance().newXPath();
					String expression = "/simulation/cost/company";
					NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
					for (int i = 0; i < nodeList.getLength(); i++) {
					    System.out.println(nodeList.item(i).getFirstChild().getNodeValue()); 
					}
//					String email = xPath.compile(expression).evaluate(xmlDocument);
//					Node node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);
//					NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
//					System.out.println(email);
				} catch (XPathExpressionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	}

}

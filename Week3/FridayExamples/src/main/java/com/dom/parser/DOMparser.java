package com.dom.parser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMparser {
	public static void main(String[] args){
		File readXML = new File("C:/Users/jathm/my_git_repos/1708Aug14Code/Week3/FridayExamples/src/main/resources/XML/revature.xml");
		
		try{
			DocumentBuilderFactory dbFactory= DocumentBuilderFactory.newInstance();
			
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document doc = builder.parse(readXML);		
			
			doc.getDocumentElement().normalize();
			String root = doc.getDocumentElement().getNodeName();
			System.out.println(root);
			
			NodeList nodes = doc.getElementsByTagName("employee");
			for(int i = 0; i < nodes.getLength(); i++){
			Node curr = nodes.item(i);
			System.out.println("Current Employee " + curr.getNodeName());
			
			if (curr.getNodeType()== Node.ELEMENT_NODE){
				Element element = (Element) curr;
				
				System.out.println("Id1" + element.getAttribute("id"));
				System.out.println("Name: " + element.getElementsByTagName("firstname").item(0).getTextContent() 
						+ " " + element.getElementsByTagName("lastname").item(0).getTextContent()+ "" );
				System.out.println("Nick Name: " + element.getElementsByTagName("nickname").item(0).getTextContent() );
				System.out.println("Salary: " + element.getElementsByTagName("salary").item(0).getTextContent() );
			}
			}
				
			
		}catch(ParserConfigurationException e){
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
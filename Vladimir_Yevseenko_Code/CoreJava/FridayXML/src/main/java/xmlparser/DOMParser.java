package xmlparser;

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

public class DOMParser {
	public static void main(String[] args) {
		File xmlFile = new File("C:/Users/vlad/my_git_repos/1708Aug14Code/Vladimir_Yevseenko_Code/CoreJava/FridayXML/src/main/resources/xml/revature.xml");
		
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			Document doc = db.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList nodes = doc.getElementsByTagName("employee");
			
			for (int i=0; i < nodes.getLength(); i++) {
				Node currNode = nodes.item(i);
				
				if (currNode.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) currNode;
					
					System.out.println(e.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
					System.out.println(e.getElementsByTagName("firstname").item(0).getFirstChild().getNodeValue());
					System.out.println(e.getElementsByTagName("lastname").item(0).getFirstChild().getNodeValue());
					System.out.println(e.getElementsByTagName("nickname").item(0).getFirstChild().getNodeValue());
					System.out.println(e.getElementsByTagName("salary").item(0).getFirstChild().getNodeValue());
				}
				System.out.println();
			}
				
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
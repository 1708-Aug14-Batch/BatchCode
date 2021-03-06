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
	/*
	 * In this file, we will read an XML file via DOM
	 * XML Parer. the DOM parser parses the entire XML
	 * document and loads it into memory. It then models
	 * a tree structure for easy traversal/manipulation
	 * warning: the DOM Parser is slow and consumes a lot
	 * of memory when it loads a large XML doc. 
	 */

	public static void main(String[] args) {

		File revXML = new File("C:/Users/Genesis/my_git_repos/1708Aug14Code/TheWeb/friday/src/main/resources/xml/revature.xml");

		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();

			DocumentBuilder builder = 
					dbFactory.newDocumentBuilder();

			Document doc = builder.parse(revXML);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			String root = doc.getDocumentElement().getNodeName();
			System.out.println(root);

			NodeList nodes = doc.getElementsByTagName("employee");
			/*
			 * The node object is the primary data type for the
			 * entire DOM. A node can be an element node, an 
			 * attribute node, a text node, etc.
			 * An XML element is everything from the element's 
			 * start tag to the end tag
			 */
			for(int i = 0; i < nodes.getLength(); i++ ){

				Node curr = nodes.item(i);

				System.out.println("Current Element " 
						+  curr.getNodeName());

				if(curr.getNodeType() == Node.ELEMENT_NODE){
					Element element = (Element) curr;
					
					System.out.println("ID: " +
							element.getAttribute("id"));
					
					System.out.println("Name: " +
							element.getElementsByTagName("firstname")
					.item(0).getTextContent() + " "
					+ element.getElementsByTagName("lastname")
					.item(0).getTextContent());
				}



			}


		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}

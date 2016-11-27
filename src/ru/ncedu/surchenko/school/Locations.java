/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.surchenko.school;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**
 *
 * @author Александр
 */
public class Locations {
	private int locationID;
	private String locationName;
	private int postCode;

	private static HashSet<Locations> hashSet = new HashSet<>();
	
	public Locations(int locationID, String locationName) {
		this.locationID = locationID;
		this.locationName = locationName;
	}
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public int getPostCode() {
		return postCode;
	}
	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}
	public static HashSet<Locations> readLocations(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xmlDocPath);
			
			if (!xmlDoc.getDocumentElement()
					.getNodeName().equals("locations")) {
				System.err.println("Wrong root name of XML");
			}
			
			NodeList nList = xmlDoc.getElementsByTagName("location");
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)node;
					String id = elem.getAttribute("id");
					String postCode = elem
							.getElementsByTagName("postCode")
							.item(0).getTextContent();
					Locations temp = new Locations(Integer.parseInt(id),
							elem.getAttribute("name"));
					temp.setPostCode(postCode.equals("") ? 0 :
							Integer.parseInt(postCode));
					hashSet.add(temp);
				}
			}
			return hashSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void writeLocations(String xmlDocPath) {
		try {
			for (Locations temp: hashSet) {
				temp.addLocations(xmlDocPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void listLocations(String xmlDocPath) {
		for (Locations temp: hashSet) {
			System.out.println("\nlocationID: " + 
					String.valueOf(temp.locationID));
			System.out.println("locationName: " + temp.getLocationName());
			System.out.println("postCode: " + 
					String.valueOf(temp.getPostCode()));
		}
	}
	public void addLocations(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc;
			if (new File(xmlDocPath).exists()) {
				xmlDoc = builder.parse(xmlDocPath);
			} else {
				xmlDoc = builder.newDocument();
				Element rootElem = xmlDoc.createElement("locations");
				xmlDoc.appendChild(rootElem);
			}
			
			Element rootElem = xmlDoc.getDocumentElement();
			Element location = xmlDoc.createElement("location");
			rootElem.appendChild(location);
			Attr idAttr = xmlDoc.createAttribute("id");
			idAttr.setValue(String.valueOf(this.getLocationID()));
			location.setAttributeNode(idAttr);
			if (!this.getLocationName().equals("")) {
				Attr nameAttr = xmlDoc.createAttribute("name");
				nameAttr.setValue(this.getLocationName());
				location.setAttributeNode(nameAttr);
			}
			if (this.getPostCode() != 0) {
				Element postCodeElem = xmlDoc.createElement("postCode");
				postCodeElem.appendChild(xmlDoc.createTextNode(String
						.valueOf(this.getPostCode())));
				location.appendChild(postCodeElem);
			}

			TransformerFactory transformerFactory = 
					TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult res = new StreamResult(new File(xmlDocPath));
			transformer.transform(source, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void modifyLocations(String xmlDocPath, String changes, 
			String... newParams) {
		hashSet.remove(this);
		try {
			Pattern p = Pattern.compile("[np]+");
			Matcher m = p.matcher(changes);
			if (!m.matches()) {
				System.err.println("Wrong query");
			}
			if (changes.length() != newParams.length) {
				System.err.println("Wrong number of parameters");
			}
			ArrayList<String> paramArray = new ArrayList<>();
			for (String newParam : newParams) {
				paramArray.add(newParam);
			}
			for (int i = 0; i < changes.length(); i++) {
				if (changes.charAt(i) == 'n') {
					this.setLocationName(paramArray.get(i));
				}
				if (changes.charAt(i) == 't') {
					this.setPostCode(Integer.parseInt(paramArray.get(i)));
				}
			}
			hashSet.add(this);
			File xml = new File(xmlDocPath);
			xml.delete();
			Locations.writeLocations(xmlDocPath);
		} catch (Exception e) {
			System.out.println("Wrong parameters");
		}
	}
	public void removeLocations(String xmlDocPath) {
		hashSet.remove(this);
		File xml = new File(xmlDocPath);
		xml.delete();
		Locations.writeLocations(xmlDocPath);
	}
}

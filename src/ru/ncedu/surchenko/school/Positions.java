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
public class Positions {
	private int positionID;
	private String positionName;
	private int salary;

	private static HashSet<Positions> hashSet = new HashSet<>();
	
	public Positions(int positionID, String positionName) {
		this.positionID = positionID;
		this.positionName = positionName;
	}
	public int getPositionID() {
		return positionID;
	}
	public void setPositionID(int positionID) {
		this.positionID = positionID;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public static HashSet<Positions> readPositions(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xmlDocPath);
			
			if (!xmlDoc.getDocumentElement()
					.getNodeName().equals("positions")) {
				System.err.println("Wrong root name of XML");
			}
			
			NodeList nList = xmlDoc.getElementsByTagName("position");
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)node;
					String id = elem.getAttribute("id");
					String salary = elem
							.getElementsByTagName("salary")
							.item(0).getTextContent();
					Positions temp = new Positions(Integer.parseInt(id),
							elem.getAttribute("name"));
					temp.setSalary(salary.equals("") ? 0 :
							Integer.parseInt(salary));
					hashSet.add(temp);
				}
			}
			return hashSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void writePositions(String xmlDocPath) {
		try {
			for (Positions temp: hashSet) {
				temp.addPositions(xmlDocPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void listPositions(String xmlDocPath) {
		for (Positions temp: hashSet) {
			System.out.println("\npositionID: " + 
					String.valueOf(temp.positionID));
			System.out.println("positionName: " + temp.getPositionName());
			System.out.println("salary: " + 
					String.valueOf(temp.getSalary()));
		}
	}
	public void addPositions(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc;
			if (new File(xmlDocPath).exists()) {
				xmlDoc = builder.parse(xmlDocPath);
			} else {
				xmlDoc = builder.newDocument();
				Element rootElem = xmlDoc.createElement("positions");
				xmlDoc.appendChild(rootElem);
			}
			
			Element rootElem = xmlDoc.getDocumentElement();
			Element position = xmlDoc.createElement("position");
			rootElem.appendChild(position);
			Attr idAttr = xmlDoc.createAttribute("id");
			idAttr.setValue(String.valueOf(this.getPositionID()));
			position.setAttributeNode(idAttr);
			if (!this.getPositionName().equals("")) {
				Attr nameAttr = xmlDoc.createAttribute("name");
				nameAttr.setValue(this.getPositionName());
				position.setAttributeNode(nameAttr);
			}
			if (this.getSalary() != 0) {
				Element salaryElem = xmlDoc.createElement("salary");
				salaryElem.appendChild(xmlDoc.createTextNode(String
						.valueOf(this.getSalary())));
				position.appendChild(salaryElem);
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
	public void modifyPositions(String xmlDocPath, String changes, 
			String... newParams) {
		hashSet.remove(this);
		try {
			Pattern p = Pattern.compile("[ns]+");
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
					this.setPositionName(paramArray.get(i));
				}
				if (changes.charAt(i) == 's') {
					this.setSalary(Integer.parseInt(paramArray.get(i)));
				}
			}
			hashSet.add(this);
			File xml = new File(xmlDocPath);
			xml.delete();
			Positions.writePositions(xmlDocPath);
		} catch (Exception e) {
			System.out.println("Wrong parameters");
		}
	}
	public void removePositions(String xmlDocPath) {
		hashSet.remove(this);
		File xml = new File(xmlDocPath);
		xml.delete();
		Positions.writePositions(xmlDocPath);
	}
}

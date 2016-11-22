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
public class Classes {
	private int classID;
	private String className;
	private int roomNum;
	private int classTeacherID;
	
	private static HashSet<Classes> hashSet = new HashSet<>();

	public Classes(int classID, String className) {
		this.classID = classID;
		this.className = className;
	}
	public int getClassID() {
		return classID;
	}
	public void setClassID(int classID) {
		this.classID = classID;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public int getClassTeacherID() {
		return classTeacherID;
	}
	public void setClassTeacherID(int classTeacherID) {
		this.classTeacherID = classTeacherID;
	}
	public static HashSet<Classes> readClasses(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xmlDocPath);
			
			if (!xmlDoc.getDocumentElement()
					.getNodeName().equals("classes")) {
				System.err.println("Wrong root name of XML");
			}
			
			NodeList nList = xmlDoc.getElementsByTagName("class");
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)node;
					String id = elem.getAttribute("id");
					String roomNum = elem
							.getElementsByTagName("roomNum").item(0)
							.getTextContent();
					String classTeacherID = elem
							.getElementsByTagName("classTeacherID")
							.item(0).getTextContent();
					Classes temp = new Classes(
							id.equals("") ? 0 : Integer.parseInt(id),
							elem.getAttribute("name"));
					temp.setRoomNum(roomNum.equals("") ? 0 : 
							Integer.parseInt(roomNum));
					temp.setClassTeacherID(classTeacherID.equals("") ? 0 :
							Integer.parseInt(classTeacherID));
					hashSet.add(temp);
				}
			}
			return hashSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void writeClasses(String xmlDocPath) {
		try {
			for (Classes temp: hashSet) {
				temp.addClasses(xmlDocPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void listClasses(String xmlDocPath) {
		for (Classes temp: hashSet) {
			System.out.println("\nclassID: " + 
					String.valueOf(temp.classID));
			System.out.println("className: " + temp.getClassName());
			System.out.println("roomNum: " + 
					String.valueOf(temp.getRoomNum()));
			System.out.println("classTeacherID: " + 
					String.valueOf(temp.getClassTeacherID()));
		}
	}
	public void addClasses(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc;
			if (new File(xmlDocPath).exists()) {
				xmlDoc = builder.parse(xmlDocPath);
			} else {
				xmlDoc = builder.newDocument();
				Element rootElem = xmlDoc.createElement("classes");
				xmlDoc.appendChild(rootElem);
			}
			
			Element rootElem = xmlDoc.getDocumentElement();
			Element _class = xmlDoc.createElement("class");
			rootElem.appendChild(_class);
			if (this.getClassID() != 0) {
				Attr idAttr = xmlDoc.createAttribute("id");
				idAttr.setValue(String.valueOf(this.getClassID()));
				_class.setAttributeNode(idAttr);
			}
			if (!this.getClassName().equals("")) {
				Attr nameAttr = xmlDoc.createAttribute("name");
				nameAttr.setValue(this.getClassName());
				_class.setAttributeNode(nameAttr);
			}
			if (this.getRoomNum() != 0) {
				Element roomNumElem = xmlDoc.createElement("roomNum");
				roomNumElem.appendChild(xmlDoc
						.createTextNode(String.valueOf(this.getRoomNum())));
				_class.appendChild(roomNumElem);
			}
			if (this.getClassTeacherID() != 0) {
				Element classTeacherIDElem = 
						xmlDoc.createElement("classTeacherID");
				classTeacherIDElem.appendChild(xmlDoc
						.createTextNode(String
						.valueOf(this.getClassTeacherID())));
				_class.appendChild(classTeacherIDElem);
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
	public void modifyClasses(String xmlDocPath, String changes, 
			String... newParams) {
		hashSet.remove(this);
		try {
			Pattern p = Pattern.compile("[nrt]+");
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
					this.setClassName(paramArray.get(i));
				}
				if (changes.charAt(i) == 'r') {
					this.setRoomNum(Integer.parseInt(paramArray.get(i)));
				}
				if (changes.charAt(i) == 't') {
					this.setClassTeacherID(Integer.parseInt(paramArray.get(i)));
				}
			}
			hashSet.add(this);
			File xml = new File(xmlDocPath);
			xml.delete();
			Classes.writeClasses(xmlDocPath);
		} catch (Exception e) {
			System.out.println("Wrong parameters");
		}
	}
	public void removeClasses(String xmlDocPath) {
		hashSet.remove(this);
		File xml = new File(xmlDocPath);
		xml.delete();
		Classes.writeClasses(xmlDocPath);
	}
}

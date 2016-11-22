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
public class Students {
	private int studentID;
	private String studentName;
	private int classID;
	private byte studentRating;

	private static HashSet<Students> hashSet = new HashSet<>();
	
	public Students(int studentID, String studentName) {
		this.studentID = studentID;
		this.studentName = studentName;
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getClassID() {
		return classID;
	}
	public void setClassID(int classID) {
		this.classID = classID;
	}
	public byte getStudentRating() {
		return studentRating;
	}
	public void setStudentRating(byte studentRating) {
		this.studentRating = studentRating;
	}
	public static HashSet<Students> readStudents(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xmlDocPath);
			
			if (!xmlDoc.getDocumentElement()
					.getNodeName().equals("students")) {
				System.err.println("Wrong root name of XML");
			}
			
			NodeList nList = xmlDoc.getElementsByTagName("student");
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)node;
					String id = elem.getAttribute("id");
					String classID = elem.getAttribute("classID");
					String studentRating = elem
							.getElementsByTagName("studentRating")
							.item(0).getTextContent();
					Students temp = new Students(
							id.equals("") ? 0 : Integer.parseInt(id),
							elem.getAttribute("name"));
					temp.setClassID(classID.equals("") ? 0 : 
							Integer.parseInt(classID));
					temp.setStudentRating(studentRating.equals("") ? 0 :
							Byte.parseByte(studentRating));
					hashSet.add(temp);
				}
			}
			return hashSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void writeStudents(String xmlDocPath) {
		try {
			for (Students temp: hashSet) {
				temp.addStudents(xmlDocPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void listStudents(String xmlDocPath) {
		for (Students temp: hashSet) {
			System.out.println("\nstudentID: " + 
					String.valueOf(temp.studentID));
			System.out.println("studentName: " + temp.getStudentName());
			System.out.println("classID: " + 
					String.valueOf(temp.getClassID()));
			System.out.println("studentRating: " + 
					String.valueOf(temp.getStudentRating()));
		}
	}
	public void addStudents(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc;
			if (new File(xmlDocPath).exists()) {
				xmlDoc = builder.parse(xmlDocPath);
			} else {
				xmlDoc = builder.newDocument();
				Element rootElem = xmlDoc.createElement("students");
				xmlDoc.appendChild(rootElem);
			}
			
			Element rootElem = xmlDoc.getDocumentElement();
			Element student = xmlDoc.createElement("student");
			rootElem.appendChild(student);
			if (this.getStudentID() != 0) {
				Attr idAttr = xmlDoc.createAttribute("id");
				idAttr.setValue(String.valueOf(this.getStudentID()));
				student.setAttributeNode(idAttr);
			}
			if (!this.getStudentName().equals("")) {
				Attr nameAttr = xmlDoc.createAttribute("name");
				nameAttr.setValue(this.getStudentName());
				student.setAttributeNode(nameAttr);
			}
			if (this.getClassID() != 0) {
				Element classIDElem = xmlDoc.createElement("classID");
				classIDElem.appendChild(xmlDoc
						.createTextNode(String.valueOf(this.getClassID())));
				student.appendChild(classIDElem);
			}
			if (this.getStudentRating() != 0) {
				Element studentRatingElem = 
						xmlDoc.createElement("studentRating");
				studentRatingElem.appendChild(xmlDoc
						.createTextNode(String
						.valueOf(this.getStudentRating())));
				student.appendChild(studentRatingElem);
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
	public void modifyStudents(String xmlDocPath, String changes, 
			String... newParams) {
		hashSet.remove(this);
		try {
			Pattern p = Pattern.compile("[ncr]+");
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
					this.setStudentName(paramArray.get(i));
				}
				if (changes.charAt(i) == 'c') {
					this.setClassID(Integer.parseInt(paramArray.get(i)));
				}
				if (changes.charAt(i) == 'r') {
					this.setStudentRating(Byte.parseByte(paramArray.get(i)));
				}
			}
			hashSet.add(this);
			File xml = new File(xmlDocPath);
			xml.delete();
			Students.writeStudents(xmlDocPath);
		} catch (Exception e) {
			System.out.println("Wrong parameters");
		}
	}
	public void removeStudents(String xmlDocPath) {
		hashSet.remove(this);
		File xml = new File(xmlDocPath);
		xml.delete();
		Students.writeStudents(xmlDocPath);
	}
}

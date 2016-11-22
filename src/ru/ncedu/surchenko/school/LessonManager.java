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
public class LessonManager {
	private int studentID;
	private int lessonID;

	private static HashSet<LessonManager> hashSet = new HashSet<>();
	
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public int getLessonID() {
		return lessonID;
	}
	public void setLessonID(int lessonID) {
		this.lessonID = lessonID;
	}
	public static HashSet<LessonManager> readLinks(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xmlDocPath);
			
			if (!xmlDoc.getDocumentElement()
					.getNodeName().equals("lessonManager")) {
				System.err.println("Wrong root name of XML");
			}
			
			NodeList nList = xmlDoc.getElementsByTagName("link");
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)node;
					String studentID = elem
							.getElementsByTagName("studentID")
							.item(0).getTextContent();
					String lessonID = elem
							.getElementsByTagName("lessonID")
							.item(0).getTextContent();
					LessonManager temp = new LessonManager();
					temp.setStudentID(studentID.equals("") ? 0 : 
							Integer.parseInt(studentID));
					temp.setLessonID(lessonID.equals("") ? 0 :
							Byte.parseByte(lessonID));
					hashSet.add(temp);
				}
			}
			return hashSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void writeLessonManager(String xmlDocPath) {
		try {
			for (LessonManager temp: hashSet) {
				temp.addLessonManager(xmlDocPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void listLessonManager(String xmlDocPath) {
		for (LessonManager temp: hashSet) {
			System.out.println("\nstudentID: " + 
					String.valueOf(temp.getStudentID()));
			System.out.println("lessonID: " + 
					String.valueOf(temp.getLessonID()));
		}
	}
	public void addLessonManager(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc;
			if (new File(xmlDocPath).exists()) {
				xmlDoc = builder.parse(xmlDocPath);
			} else {
				xmlDoc = builder.newDocument();
				Element rootElem = xmlDoc.createElement("lessonManager");
				xmlDoc.appendChild(rootElem);
			}
			
			Element rootElem = xmlDoc.getDocumentElement();
			Element link = xmlDoc.createElement("link");
			rootElem.appendChild(link);
			
			if (this.getStudentID() != 0) {
				Element studentIDElem = xmlDoc.createElement("studentID");
				studentIDElem.appendChild(xmlDoc
						.createTextNode(String.valueOf(this.getStudentID())));
				link.appendChild(studentIDElem);
			}
			if (this.getLessonID() != 0) {
				Element lessonIDElem = xmlDoc.createElement("lessonID");
				lessonIDElem.appendChild(xmlDoc
						.createTextNode(String.valueOf(this.getLessonID())));
				link.appendChild(lessonIDElem);
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
	public void modifyLessonManager(String xmlDocPath, String changes, 
			String... newParams) {
		hashSet.remove(this);
		try {
			Pattern p = Pattern.compile("[sl]+");
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
				if (changes.charAt(i) == 's') {
					this.setStudentID(Integer.parseInt(paramArray.get(i)));
				}
				if (changes.charAt(i) == 'l') {
					this.setLessonID(Integer.parseInt(paramArray.get(i)));
				}
			}
			hashSet.add(this);
			File xml = new File(xmlDocPath);
			xml.delete();
			LessonManager.writeLessonManager(xmlDocPath);
		} catch (Exception e) {
			System.out.println("Wrong parameters");
		}
	}
	public void removeLessonManager(String xmlDocPath) {
		hashSet.remove(this);
		File xml = new File(xmlDocPath);
		xml.delete();
		LessonManager.writeLessonManager(xmlDocPath);
	}
}

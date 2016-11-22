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
public class Lessons {
	private int lessonID;
	private String lessonName;
	private int teacherID;

	private static HashSet<Lessons> hashSet = new HashSet<>();
	
	public Lessons(int lessonID, String lessonName) {
		this.lessonID = lessonID;
		this.lessonName = lessonName;
	}
	public int getLessonID() {
		return lessonID;
	}
	public void setLessonID(int lessonID) {
		this.lessonID = lessonID;
	}
	public String getLessonName() {
		return lessonName;
	}
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	public int getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}
	public static HashSet<Lessons> readLessons(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xmlDocPath);
			
			if (!xmlDoc.getDocumentElement()
					.getNodeName().equals("lessons")) {
				System.err.println("Wrong root name of XML");
			}
			
			NodeList nList = xmlDoc.getElementsByTagName("lesson");
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)node;
					String id = elem.getAttribute("id");
					String teacherID = elem
							.getElementsByTagName("teacherID")
							.item(0).getTextContent();
					Lessons temp = new Lessons(
							id.equals("") ? 0 : Integer.parseInt(id),
							elem.getAttribute("name"));
					temp.setTeacherID(teacherID.equals("") ? 0 :
							Integer.parseInt(teacherID));
					hashSet.add(temp);
				}
			}
			return hashSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void writeLessons(String xmlDocPath) {
		try {
			for (Lessons temp: hashSet) {
				temp.addLessons(xmlDocPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void listLessons(String xmlDocPath) {
		for (Lessons temp: hashSet) {
			System.out.println("\nlessonID: " + 
					String.valueOf(temp.lessonID));
			System.out.println("lessonName: " + temp.getLessonName());
			System.out.println("teacherID: " + 
					String.valueOf(temp.getTeacherID()));
		}
	}
	public void addLessons(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc;
			if (new File(xmlDocPath).exists()) {
				xmlDoc = builder.parse(xmlDocPath);
			} else {
				xmlDoc = builder.newDocument();
				Element rootElem = xmlDoc.createElement("lessons");
				xmlDoc.appendChild(rootElem);
			}
			
			Element rootElem = xmlDoc.getDocumentElement();
			Element lesson = xmlDoc.createElement("lesson");
			rootElem.appendChild(lesson);
			if (this.getLessonID() != 0) {
				Attr idAttr = xmlDoc.createAttribute("id");
				idAttr.setValue(String.valueOf(this.getLessonID()));
				lesson.setAttributeNode(idAttr);
			}
			if (!this.getLessonName().equals("")) {
				Attr nameAttr = xmlDoc.createAttribute("name");
				nameAttr.setValue(this.getLessonName());
				lesson.setAttributeNode(nameAttr);
			}
			if (this.getTeacherID() != 0) {
				Element teacherIDElem = xmlDoc.createElement("teacherID");
				teacherIDElem.appendChild(xmlDoc.createTextNode(String
						.valueOf(this.getTeacherID())));
				lesson.appendChild(teacherIDElem);
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
	public void modifyLessons(String xmlDocPath, String changes, 
			String... newParams) {
		hashSet.remove(this);
		try {
			Pattern p = Pattern.compile("[nt]+");
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
					this.setLessonName(paramArray.get(i));
				}
				if (changes.charAt(i) == 't') {
					this.setTeacherID(Integer.parseInt(paramArray.get(i)));
				}
			}
			hashSet.add(this);
			File xml = new File(xmlDocPath);
			xml.delete();
			Lessons.writeLessons(xmlDocPath);
		} catch (Exception e) {
			System.out.println("Wrong parameters");
		}
	}
	public void removeLessons(String xmlDocPath) {
		hashSet.remove(this);
		File xml = new File(xmlDocPath);
		xml.delete();
		Lessons.writeLessons(xmlDocPath);
	}
}

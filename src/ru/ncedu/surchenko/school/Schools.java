/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.surchenko.school;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**
 *
 * @author Александр
 */
public class Schools {
	private int schoolID;
	private String schoolName;
	private int locationID;
	private String schoolType;
	private int capacity;
	private Calendar openSince;
	private byte schoolRating;

	private static HashSet<Schools> hashSet = new HashSet<>();
	
	public Schools(int schoolID, String schoolName, int locationID) {
		this.schoolID = schoolID;
		this.schoolName = schoolName;
		this.locationID = locationID;
	}
	public int getSchoolID() {
		return schoolID;
	}
	public void setSchoolID(int schoolID) {
		this.schoolID = schoolID;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public String getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public Calendar getOpenSince() {
		return openSince;
	}
	public void setOpenSince(Calendar openSince) {
		this.openSince = openSince;
	}
	public byte getSchoolRating() {
		return schoolRating;
	}
	public void setSchoolRating(byte schoolRating) {
		this.schoolRating = schoolRating;
	}
	public static HashSet<Schools> readSchools(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xmlDocPath);
			
			if (!xmlDoc.getDocumentElement()
					.getNodeName().equals("schools")) {
				System.err.println("Wrong root name of XML");
			}
			
			NodeList nList = xmlDoc.getElementsByTagName("school");
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)node;
					String id = elem.getAttribute("id");
					String locationID = elem.getAttribute("locationID");
					String schoolType = elem
							.getElementsByTagName("schoolType").item(0)
							.getTextContent();
					String capacity = elem
							.getElementsByTagName("capacity")
							.item(0).getTextContent();
					String openSince = elem.getElementsByTagName("openSince")
							.item(0).getTextContent();
					String schoolRating = elem
							.getElementsByTagName("schoolRating")
							.item(0).getTextContent();
					Schools temp = new Schools(
							id.equals("") ? 0 : Integer.parseInt(id),
							elem.getAttribute("name"),
							locationID.equals("") ? 0 : 
									Integer.parseInt(locationID));
					temp.setSchoolType(schoolType);
					temp.setCapacity(capacity.equals("") ? 0 :
							Integer.parseInt(capacity));
					if (!openSince.equals("")) {
						Calendar newOpenSince = Calendar.getInstance();
						newOpenSince.setTime(new SimpleDateFormat("dd.MM.yyyy")
								.parse(openSince));
						temp.setOpenSince(newOpenSince);
					}
					temp.setSchoolRating(schoolRating.equals("") ? 0 :
							Byte.parseByte(schoolRating));
					hashSet.add(temp);
				}
			}
			return hashSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void writeSchools(String xmlDocPath) {
		try {
			for (Schools temp: hashSet) {
				temp.addSchools(xmlDocPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void listSchools(String xmlDocPath) {
		for (Schools temp: hashSet) {
			System.out.println("\nschoolID: " + 
					String.valueOf(temp.schoolID));
			System.out.println("schoolName: " + temp.getSchoolName());
			System.out.println("locationID: " + 
					String.valueOf(temp.getLocationID()));
			System.out.println("capacity: " + 
					String.valueOf(temp.getCapacity()));
			System.out.println("openSince: " + 
					new SimpleDateFormat("dd.MM.yyyy").format(temp
					.getOpenSince().getTime()));
			System.out.println("schoolRating: " + 
					String.valueOf(temp.getSchoolRating()));
		}
	}
	public void addSchools(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc;
			if (new File(xmlDocPath).exists()) {
				xmlDoc = builder.parse(xmlDocPath);
			} else {
				xmlDoc = builder.newDocument();
				Element rootElem = xmlDoc.createElement("schools");
				xmlDoc.appendChild(rootElem);
			}
			
			Element rootElem = xmlDoc.getDocumentElement();
			Element school = xmlDoc.createElement("school");
			rootElem.appendChild(school);
			if (this.getSchoolID() != 0) {
				Attr idAttr = xmlDoc.createAttribute("id");
				idAttr.setValue(String.valueOf(this.getSchoolID()));
				school.setAttributeNode(idAttr);
			}
			if (!this.getSchoolName().equals("")) {
				Attr nameAttr = xmlDoc.createAttribute("name");
				nameAttr.setValue(this.getSchoolName());
				school.setAttributeNode(nameAttr);
			}
			if (this.getLocationID() != 0) {
				Attr locationIDAttr = xmlDoc.createAttribute("locationID");
				locationIDAttr.setValue(String.valueOf(this.getLocationID()));
				school.setAttributeNode(locationIDAttr);
			}
			if (!this.getSchoolType().equals("")) {
				Element schoolTypeElem = 
						xmlDoc.createElement("schoolType");
				schoolTypeElem.appendChild(xmlDoc
						.createTextNode(this.getSchoolType()));
				school.appendChild(schoolTypeElem);
			}
			if (this.getCapacity() != 0) {
				Element capacityElem = 
						xmlDoc.createElement("capacity");
				capacityElem.appendChild(xmlDoc
						.createTextNode(String.valueOf(this.getCapacity())));
				school.appendChild(capacityElem);
			}
			if (this.getOpenSince() != null) {
				Element openSinceElem = xmlDoc.createElement("openSince");
				openSinceElem.appendChild(xmlDoc
						.createTextNode(new SimpleDateFormat("dd.MM.yyyy")
						.format(this.getOpenSince().getTime())));
				school.appendChild(openSinceElem);
			}
			if (this.getSchoolRating() != 0) {
				Element schoolRatingElem = 
						xmlDoc.createElement("schoolRating");
				schoolRatingElem.appendChild(xmlDoc
						.createTextNode(String
						.valueOf(this.getSchoolRating())));
				school.appendChild(schoolRatingElem);
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
	public void modifySchools(String xmlDocPath, String changes, 
			String... newParams) {
		hashSet.remove(this);
		try {
			Pattern p = Pattern.compile("[nltcor]+");
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
					this.setSchoolName(paramArray.get(i));
				}
				if (changes.charAt(i) == 'l') {
					this.setLocationID(Integer.parseInt(paramArray.get(i)));
				}
				if (changes.charAt(i) == 't') {
					this.setSchoolType(paramArray.get(i));
				}
				if (changes.charAt(i) == 'c') {
					this.setCapacity(Integer.parseInt(paramArray.get(i)));
				}
				if (changes.charAt(i) == 'o') {
					Calendar newOpenSince = Calendar.getInstance();
					newOpenSince.setTime(new SimpleDateFormat("dd.MM.yyyy")
							.parse(paramArray.get(i)));
					this.setOpenSince(newOpenSince);
				}
				if (changes.charAt(i) == 'r') {
					this.setSchoolRating(Byte.parseByte(paramArray.get(i)));
				}
			}
			hashSet.add(this);
			File xml = new File(xmlDocPath);
			xml.delete();
			Schools.writeSchools(xmlDocPath);
		} catch (Exception e) {
			System.out.println("Wrong parameters");
		}
	}
	public void removeSchools(String xmlDocPath) {
		hashSet.remove(this);
		File xml = new File(xmlDocPath);
		xml.delete();
		Schools.writeSchools(xmlDocPath);
	}
}

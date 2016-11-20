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
public class Employees {
	private int employeeID;
	private int bossID;
	private int schoolID;
	private String employeeName;
	private int positionID;
	private Calendar hireDate = Calendar.getInstance();
	private byte employeeRating;
	private double bonusFee;
	
	private static HashSet<Employees> hashSet = new HashSet<>();

	public Employees(int employeeID, int bossID, 
			int schoolID, String employeeName) {
		this.employeeID = employeeID;
		this.bossID = bossID;
		this.schoolID = schoolID;
		this.employeeName = employeeName;
	}
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public int getBossID() {
		return bossID;
	}
	public void setBossID(int bossID) {
		this.bossID = bossID;
	}
	public int getSchoolID() {
		return schoolID;
	}
	public void setSchoolID(int schoolID) {
		this.schoolID = schoolID;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public int getPositionID() {
		return positionID;
	}
	public void setPositionID(int positionID) {
		this.positionID = positionID;
	}
	public Calendar getHireDate() {
		return hireDate;
	}
	public void setHireDate(Calendar hireDate) {
		this.hireDate = hireDate;
	}
	public byte getEmployeeRating() {
		return employeeRating;
	}
	public void setEmployeeRating(byte employeeRating) {
		this.employeeRating = employeeRating;
	}
	public double getBonusFee() {
		return bonusFee;
	}
	public void setBonusFee(double bonusFee) {
		this.bonusFee = bonusFee;
	}
	public static HashSet<Employees> readEmployees(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xmlDocPath);
			
			if (!xmlDoc.getDocumentElement()
					.getNodeName().equals("employees")) {
				System.err.println("Wrong root name of XML");
			}
			
			NodeList nList = xmlDoc.getElementsByTagName("employee");
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)node;
					String id = elem.getAttribute("id");
					String bossID = elem.getAttribute("bossID");
					String schoolID = elem.getAttribute("schoolID");
					String positionID = elem
							.getElementsByTagName("positionID").item(0)
							.getTextContent();
					String hireDate = elem.getElementsByTagName("hireDate")
							.item(0).getTextContent();
					String employeeRating = elem
							.getElementsByTagName("employeeRating")
							.item(0).getTextContent();
					String bonusFee = elem
							.getElementsByTagName("bonusFee")
							.item(0).getTextContent();
					Employees temp = new Employees(
							id.equals("") ? 0 : Integer.parseInt(id),
							bossID.equals("") ? 0 : Integer.parseInt(bossID),
							schoolID.equals("") ? 0 : 
									Integer.parseInt(schoolID),
							elem.getAttribute("name"));
					temp.setPositionID(positionID.equals("") ? 0 : 
							Integer.parseInt(positionID));
					if (!hireDate.equals("")) {
						Calendar newHireDate = Calendar.getInstance();
						newHireDate.setTime(new SimpleDateFormat("dd.MM.yyyy")
								.parse(hireDate));
						temp.setHireDate(newHireDate);
					}
					temp.setEmployeeRating(employeeRating.equals("") ? 0 :
							Byte.parseByte(employeeRating));
					temp.setBonusFee(bonusFee.equals("") ? 0 :
							Double.parseDouble(bonusFee));
					hashSet.add(temp);
				}
			}
			return hashSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void writeEmployees(String xmlDocPath) {
		try {
			for (Employees temp: hashSet) {
				temp.addEmployees(xmlDocPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void listEmployees(String xmlDocPath) {
		hashSet = Employees.readEmployees(xmlDocPath);
		for (Employees temp: hashSet) {
			System.out.println("\nemployeeID: " + 
					String.valueOf(temp.employeeID));
			System.out.println("bossID: " + String.valueOf(temp.getBossID()));
			System.out.println("schoolID " + 
					String.valueOf(temp.getSchoolID()));
			System.out.println("employeeName: " + temp.getEmployeeName());
			System.out.println("positionID: " + 
					String.valueOf(temp.getPositionID()));
			System.out.println("hireDate: " + new SimpleDateFormat("dd.MM.yyyy")
					.format(temp.getHireDate().getTime()));
			System.out.println("employeeRating: " + 
					String.valueOf(temp.getEmployeeRating()));
			System.out.println("bonusFee: " + 
					String.valueOf(temp.getBonusFee()));
		}
	}
	public void addEmployees(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc;
			if (new File(xmlDocPath).exists()) {
				xmlDoc = builder.parse(xmlDocPath);
			} else {
				xmlDoc = builder.newDocument();
				Element rootElem = xmlDoc.createElement("employees");
				xmlDoc.appendChild(rootElem);
			}
			
			Element rootElem = xmlDoc.getDocumentElement();
			Element employee = xmlDoc.createElement("employee");
			rootElem.appendChild(employee);
			if (this.getEmployeeID() != 0) {
				Attr idAttr = xmlDoc.createAttribute("id");
				idAttr.setValue(String.valueOf(this.getEmployeeID()));
				employee.setAttributeNode(idAttr);
			}
			if (this.getBossID() != 0) {
				Attr bossIDAttr = xmlDoc.createAttribute("bossID");
				bossIDAttr.setValue(String.valueOf(this.getBossID()));
				employee.setAttributeNode(bossIDAttr);
			}
			if (this.getSchoolID() != 0) {
				Attr schoolIDAttr = xmlDoc.createAttribute("schoolID");
				schoolIDAttr.setValue(String.valueOf(this.getSchoolID()));
				employee.setAttributeNode(schoolIDAttr);
			}
			if (!this.getEmployeeName().equals("")) {
				Attr nameAttr = xmlDoc.createAttribute("name");
				nameAttr.setValue(this.getEmployeeName());
				employee.setAttributeNode(nameAttr);
			}
			if (this.getPositionID() != 0) {
				Element positionIDElem = xmlDoc.createElement("positionID");
				positionIDElem.appendChild(xmlDoc
						.createTextNode(String.valueOf(this.getPositionID())));
				employee.appendChild(positionIDElem);
			}
			if (this.getHireDate() != null) {
				Element hireDateElem = xmlDoc.createElement("hireDate");
				hireDateElem.appendChild(xmlDoc
						.createTextNode(new SimpleDateFormat("dd.MM.yyyy")
						.format(this.getHireDate().getTime())));
				employee.appendChild(hireDateElem);
			}
			if (this.getEmployeeRating() != 0) {
				Element employeeRatingElem = 
						xmlDoc.createElement("employeeRating");
				employeeRatingElem.appendChild(xmlDoc
						.createTextNode(String
						.valueOf(this.getEmployeeRating())));
				employee.appendChild(employeeRatingElem);
			}
			Element bonusFeeElem = xmlDoc.createElement("bonusFee");
			bonusFeeElem.appendChild(xmlDoc
					.createTextNode(String.valueOf(this.getBonusFee())));
			employee.appendChild(bonusFeeElem);

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
	public void modifyEmployees(String xmlDocPath, String changes, 
			String... newParams) {
		hashSet.remove(this);
		try {
			Pattern p = Pattern.compile("[bsnphrf]+");
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
				if (changes.charAt(i) == 'b') {
					this.setBossID(Integer.parseInt(paramArray.get(i)));
				}
				if (changes.charAt(i) == 's') {
					this.setSchoolID(Integer.parseInt(paramArray.get(i)));
				}
				if (changes.charAt(i) == 'n') {
					this.setEmployeeName(paramArray.get(i));
				}
				if (changes.charAt(i) == 'p') {
					this.setPositionID(Integer.parseInt(paramArray.get(i)));
				}
				if (changes.charAt(i) == 'h') {
					Calendar newHireDate = Calendar.getInstance();
					newHireDate.setTime(new SimpleDateFormat("dd.MM.yyyy")
							.parse(paramArray.get(i)));
					this.setHireDate(newHireDate);
				}
				if (changes.charAt(i) == 'r') {
					this.setEmployeeRating(Byte.parseByte(paramArray.get(i)));
				}
				if (changes.charAt(i) == 'f') {
					this.setBonusFee(Double.parseDouble(paramArray.get(i)));
				}
			}
			hashSet.add(this);
			File xml = new File(xmlDocPath);
			xml.delete();
			Employees.writeEmployees(xmlDocPath);
		} catch (Exception e) {
			System.out.println("Wrong parameters");
		}
	}
	public void removeEmployees(String xmlDocPath) {
		hashSet.remove(this);
		File xml = new File(xmlDocPath);
		xml.delete();
		Employees.writeEmployees(xmlDocPath);
	}
}

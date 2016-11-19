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
			HashSet<Employees> hashSet = new HashSet<>();
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
					temp.positionID = positionID.equals("") ? 0 : 
							Integer.parseInt(positionID);
					if (!hireDate.equals("")) {
						temp.hireDate.setTime(new SimpleDateFormat("dd.MM.yyyy")
							.parse(hireDate));
					}
					temp.employeeRating = employeeRating.equals("") ? 0 :
							Byte.parseByte(employeeRating);
					temp.bonusFee = bonusFee.equals("") ? 0 :
							Double.parseDouble(bonusFee);
					hashSet.add(temp);
				}
			}
			return hashSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void writeEmployees(HashSet<Employees> hashSet,
			String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc = builder.newDocument();
			
			Element rootElem = xmlDoc.createElement("employees");
			xmlDoc.appendChild(rootElem);
			for (Employees temp: hashSet) {
				Element employee = xmlDoc.createElement("employee");
				rootElem.appendChild(employee);
				if (temp.employeeID != 0) {
					Attr id = xmlDoc.createAttribute("id");
					id.setValue(String.valueOf(temp.employeeID));
					employee.setAttributeNode(id);
				}
				if (temp.bossID != 0) {
					Attr bossID = xmlDoc.createAttribute("bossID");
					bossID.setValue(String.valueOf(temp.bossID));
					employee.setAttributeNode(bossID);
				}
				if (temp.schoolID != 0) {
					Attr schoolID = xmlDoc.createAttribute("schoolID");
					schoolID.setValue(String.valueOf(temp.schoolID));
					employee.setAttributeNode(schoolID);
				}
				if (!temp.employeeName.equals("")) {
					Attr name = xmlDoc.createAttribute("name");
					name.setValue(temp.employeeName);
					employee.setAttributeNode(name);
				}
				if (temp.positionID != 0) {
					Element positionID = xmlDoc.createElement("positionID");
					positionID.appendChild(xmlDoc
							.createTextNode(String.valueOf(temp.positionID)));
					employee.appendChild(positionID);
				}
				if (temp.hireDate == null) {
					Element hireDate = xmlDoc.createElement("hireDate");
					hireDate.appendChild(xmlDoc
							.createTextNode(new SimpleDateFormat("dd.MM.yyyy")
							.format(temp.hireDate.getTime())));
					employee.appendChild(hireDate);
				}
				if (temp.employeeRating != 0) {
					Element employeeRating = 
							xmlDoc.createElement("employeeRating");
					employeeRating.appendChild(xmlDoc
							.createTextNode(String.valueOf(temp.bonusFee)));
					employee.appendChild(employeeRating);
				}
				if (temp.bonusFee != 0) {
					Element bonusFee = xmlDoc.createElement("bonusFee");
					bonusFee.appendChild(xmlDoc
							.createTextNode(String.valueOf(temp.employeeName)));
					employee.appendChild(bonusFee);
				}
				
				TransformerFactory transformerFactory = 
						TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(xmlDoc);
				StreamResult res = new StreamResult(new File(xmlDocPath));
				transformer.transform(source, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void listEmployees(String xmlDocPath) {
		HashSet<Employees> hashSet = Employees.readEmployees(xmlDocPath);
		for (Employees temp: hashSet) {
			System.out.println("\nemployeeID: " + 
					String.valueOf(temp.employeeID));
			System.out.println("bossID: " + String.valueOf(temp.bossID));
			System.out.println("schoolID " + String.valueOf(temp.schoolID));
			System.out.println("employeeName: " + temp.employeeName);
			System.out.println("positionID: " + 
					String.valueOf(temp.positionID));
			System.out.println("hireDate: " + new SimpleDateFormat("dd.MM.yyyy")
					.format(temp.hireDate.getTime()));
			System.out.println("employeeRating: " + 
					String.valueOf(temp.employeeRating));
			System.out.println("bonusFee: " + String.valueOf(temp.bonusFee));
		}
	}
}

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
public class Rooms {
	private int roomNum;
	private byte floor;
	private byte capacity;

	private static HashSet<Rooms> hashSet = new HashSet<>();
	
	public Rooms(int roomNum, byte floor) {
		this.roomNum = roomNum;
		this.floor = floor;
	}
	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public byte getFloor() {
		return floor;
	}
	public void setFloor(byte floor) {
		this.floor = floor;
	}
	public byte getCapacity() {
		return capacity;
	}
	public void setCapacity(byte capacity) {
		this.capacity = capacity;
	}
	public static HashSet<Rooms> readRooms(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xmlDocPath);
			
			if (!xmlDoc.getDocumentElement()
					.getNodeName().equals("rooms")) {
				System.err.println("Wrong root name of XML");
			}
			
			NodeList nList = xmlDoc.getElementsByTagName("room");
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)node;
					String num = elem.getAttribute("num");
					String floor = elem.getAttribute("floor");
					String capacity = elem
							.getElementsByTagName("capacity")
							.item(0).getTextContent();
					Rooms temp = new Rooms(
							num.equals("") ? 0 : Integer.parseInt(num),
							floor.equals("") ? 0 : Byte.parseByte(floor));
					temp.setCapacity(capacity.equals("") ? 0 :
							Byte.parseByte(capacity));
					hashSet.add(temp);
				}
			}
			return hashSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void writeRooms(String xmlDocPath) {
		try {
			for (Rooms temp: hashSet) {
				temp.addRooms(xmlDocPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void listRooms(String xmlDocPath) {
		for (Rooms temp: hashSet) {
			System.out.println("\nroomNum: " + 
					String.valueOf(temp.roomNum));
			System.out.println("floor: " + temp.getFloor());
			System.out.println("capacity: " + 
					String.valueOf(temp.getCapacity()));
		}
	}
	public void addRooms(String xmlDocPath) {
		try {
			DocumentBuilderFactory dbFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc;
			if (new File(xmlDocPath).exists()) {
				xmlDoc = builder.parse(xmlDocPath);
			} else {
				xmlDoc = builder.newDocument();
				Element rootElem = xmlDoc.createElement("rooms");
				xmlDoc.appendChild(rootElem);
			}
			
			Element rootElem = xmlDoc.getDocumentElement();
			Element room = xmlDoc.createElement("room");
			rootElem.appendChild(room);
			if (this.getRoomNum() != 0) {
				Attr numAttr = xmlDoc.createAttribute("num");
				numAttr.setValue(String.valueOf(this.getRoomNum()));
				room.setAttributeNode(numAttr);
			}
			if (this.getFloor() != 0) {
				Attr floorAttr = xmlDoc.createAttribute("floor");
				floorAttr.setValue(String.valueOf(this.getFloor()));
				room.setAttributeNode(floorAttr);
			}
			if (this.getCapacity() != 0) {
				Element capacityElem = xmlDoc.createElement("capacity");
				capacityElem.appendChild(xmlDoc.createTextNode(String
						.valueOf(this.getCapacity())));
				room.appendChild(capacityElem);
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
	public void modifyRooms(String xmlDocPath, String changes, 
			String... newParams) {
		hashSet.remove(this);
		try {
			Pattern p = Pattern.compile("[fc]+");
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
				if (changes.charAt(i) == 'f') {
					this.setFloor(Byte.parseByte(paramArray.get(i)));
				}
				if (changes.charAt(i) == 'c') {
					this.setCapacity(Byte.parseByte(paramArray.get(i)));
				}
			}
			hashSet.add(this);
			File xml = new File(xmlDocPath);
			xml.delete();
			Rooms.writeRooms(xmlDocPath);
		} catch (Exception e) {
			System.out.println("Wrong parameters");
		}
	}
	public void removeRooms(String xmlDocPath) {
		hashSet.remove(this);
		File xml = new File(xmlDocPath);
		xml.delete();
		Rooms.writeRooms(xmlDocPath);
	}
}

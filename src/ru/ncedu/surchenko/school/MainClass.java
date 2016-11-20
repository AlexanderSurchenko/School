/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.surchenko.school;

import java.util.*;

/**
 *
 * @author Александр
 */
public class MainClass {
	public static void main(String args[]) {
		String xmlDocPath = "E:\\Programs\\NetBeans\\NetBeans 8.1"
				+ "\\Projects\\School\\xml\\employeesc.xml";
		HashSet<Employees> hashSet = Employees.readEmployees(xmlDocPath);
		/*Employees.listEmployees("E:\\Programs\\NetBeans\\NetBeans 8.1"
				+ "\\Projects\\School\\xml\\employees.xml");*/
		Employees thisOne = null;
		for (Employees emp: hashSet) {
			if (emp.getEmployeeID() == 7502) {
				thisOne = emp;
			}
		}
		thisOne.modifyEmployees(xmlDocPath, "bs", "7503", "102");
		thisOne.removeEmployees(xmlDocPath);
	}
}

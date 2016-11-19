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
		HashSet<Employees> hashSet = Employees.readEmployees("E:\\Programs"
				+ "\\NetBeans\\NetBeans 8.1\\Projects\\School"
				+ "\\xml\\employees.xml");
		Employees.writeEmployees(hashSet, "E:\\Programs\\NetBeans\\NetBeans 8.1"
				+ "\\Projects\\School\\xml\\employees2.xml");
		Employees.listEmployees("E:\\Programs\\NetBeans\\NetBeans 8.1"
				+ "\\Projects\\School\\xml\\employees.xml");
	}
}

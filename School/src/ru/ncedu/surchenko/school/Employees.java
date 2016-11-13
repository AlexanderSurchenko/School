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
public class Employees {
	private int employeeID;
	private int bossID;
	private int positionID;
	private Calendar hireDate;
	private byte employeeRating;
	private double bonusFee;
	private int schoolID;
	private String employeeName;

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
}

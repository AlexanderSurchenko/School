/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.surchenko.school;

/**
 *
 * @author Александр
 */
public class Positions {
	private int positionID;
	private String positionName;
	private int salary;

	public Positions(int positionID, String positionName) {
		this.positionID = positionID;
		this.positionName = positionName;
	}

	public int getPositionID() {
		return positionID;
	}

	public void setPositionID(int positionID) {
		this.positionID = positionID;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
}

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
public class Schools {
	private int schoolID;
	private String schoolName;
	private int locationID;
	private String schoolType;
	private int capacity;
	private Calendar openSince;
	private byte schoolRating;

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
	
}

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
public class Students {
	private int studentID;
	private String studentName;
	private int classID;
	private byte studentRating;

	public Students(int studentID, String studentName, int classID) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.classID = classID;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getClassID() {
		return classID;
	}

	public void setClassID(int classID) {
		this.classID = classID;
	}

	public byte getStudentRating() {
		return studentRating;
	}

	public void setStudentRating(byte studentRating) {
		this.studentRating = studentRating;
	}
	
}

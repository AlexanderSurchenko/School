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
public class Classes {
	private int classID;
	private String className;
	private int roomNum;
	private int classTeacherID;

	public Classes(int classID, String className) {
		this.classID = classID;
		this.className = className;
	}
	
	public int getClassID() {
		return classID;
	}

	public void setClassID(int classID) {
		this.classID = classID;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public int getClassTeacherID() {
		return classTeacherID;
	}

	public void setClassTeacherID(int classTeacherID) {
		this.classTeacherID = classTeacherID;
	}
	
}

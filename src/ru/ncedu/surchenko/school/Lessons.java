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
public class Lessons {
	private int lessonID;
	private String lessonName;
	private int teacherID;

	public Lessons(int lessonID, String lessonName) {
		this.lessonID = lessonID;
		this.lessonName = lessonName;
	}
	
	public int getLessonID() {
		return lessonID;
	}

	public void setLessonID(int lessonID) {
		this.lessonID = lessonID;
	}

	public String getLessonName() {
		return lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public int getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}
	
}

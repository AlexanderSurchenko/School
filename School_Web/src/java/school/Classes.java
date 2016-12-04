/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Александр
 */
@Entity @Table(name="Classes")
public class Classes implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String className;
	private Long roomID;
	private Long classTeacherID;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoomID() {
		return roomID;
	}

	public void setRoomID(Long roomID) {
		this.roomID = roomID;
	}

	public Long getClassTeacherID() {
		return classTeacherID;
	}

	public void setClassTeacherID(Long classTeacherID) {
		this.classTeacherID = classTeacherID;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work 
		// in the case the id fields are not set
		if (!(object instanceof Classes)) {
			return false;
		}
		Classes other = (Classes) object;
		if ((this.id == null && other.id != null) || 
				(this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "school.Classes[ id=" + id + " ]";
	}
	
}

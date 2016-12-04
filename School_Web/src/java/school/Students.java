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
@Entity @Table(name = "Students")
public class Students implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String studentName;
	private Long classID;
	private Byte studentRating;

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Long getClassID() {
		return classID;
	}

	public void setClassID(Long classID) {
		this.classID = classID;
	}

	public Byte getStudentRating() {
		return studentRating;
	}

	public void setStudentRating(Byte studentRating) {
		this.studentRating = studentRating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		if (!(object instanceof Students)) {
			return false;
		}
		Students other = (Students) object;
		if ((this.id == null && other.id != null) || 
				(this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "school.Students[ id=" + id + " ]";
	}
	
}

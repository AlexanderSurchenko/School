/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.persistence.*;

/**
 *
 * @author Александр
 */
@Entity @Table(name = "Schools")
public class Schools implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String schoolName;
	private Long locationID;
	private String schoolType;
	private Integer capacity;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Calendar openSince;
	private Byte schoolRating;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Long getLocationID() {
		return locationID;
	}

	public void setLocationID(Long locationID) {
		this.locationID = locationID;
	}

	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getOpenSince() {
		if (openSince != null) {
			return new SimpleDateFormat("dd.MM.yyyy")
					.format(openSince.getTime());
		}
		return null;
	}

	public void setOpenSince(String openSince) throws ParseException {
		this.openSince
				.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(openSince));
	}

	public Byte getSchoolRating() {
		return schoolRating;
	}

	public void setSchoolRating(Byte schoolRating) {
		this.schoolRating = schoolRating;
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
		if (!(object instanceof Schools)) {
			return false;
		}
		Schools other = (Schools) object;
		if ((this.id == null && other.id != null) || 
				(this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "school.Schools[ id=" + id + " ]";
	}
	
}

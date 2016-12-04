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
@Entity @Table(name="Employees")
public class Employees implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(optional = true)
	private Employees boss;
	private Long schoolID;
	private String employeeName;
	private Long positionID;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Calendar hireDate = null;
	private Byte employeeRating;
	private Double bonusFee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Employees getBoss() {
        return boss;
    }

    public void setBoss(Employees boss) {
        this.boss = boss;
    }

	public Long getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(Long schoolID) {
		this.schoolID = schoolID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getPositionID() {
		return positionID;
	}

	public void setPositionID(Long positionID) {
		this.positionID = positionID;
	}

	public String getHireDate() {
		if (hireDate != null) {
			return new SimpleDateFormat("dd.MM.yyyy")
					.format(hireDate.getTime());
		}
		return null;
	}

	public void setHireDate(String hireDate) throws ParseException {
		this.hireDate
				.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(hireDate));
	}

	public Byte getEmployeeRating() {
		return employeeRating;
	}

	public void setEmployeeRating(Byte employeeRating) {
		this.employeeRating = employeeRating;
	}

	public Double getBonusFee() {
		return bonusFee;
	}

	public void setBonusFee(Double bonusFee) {
		this.bonusFee = bonusFee;
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
		if (!(object instanceof Employees)) {
			return false;
		}
		Employees other = (Employees) object;
		if ((this.id == null && other.id != null) || 
				(this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "school.Employees[ id=" + id + " ]";
	}
	
}

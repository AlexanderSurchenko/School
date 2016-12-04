package beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Александр
 */
@ViewScoped @ManagedBean
public class EmployeesEditBean implements Serializable {
	private Long id;
	private Long bossID;
	private Long schoolID;
	private String employeeName;
	private Long positionID;
	private Calendar hireDate = Calendar.getInstance();
	private Byte employeeRating;
	private Double bonusFee;

	public EmployeesEditBean() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBossID() {
		return bossID;
	}

	public void setBossID(Long bossID) {
		this.bossID = bossID;
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
		return new SimpleDateFormat("dd.MM.yyyy").format(hireDate.getTime());
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
	
}

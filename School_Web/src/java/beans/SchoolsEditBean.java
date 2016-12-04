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
public class SchoolsEditBean implements Serializable {
	private Long id;
	private String schoolName;
	private Long locationID;
	private String schoolType;
	private Integer capacity;
	private Calendar openSince = Calendar.getInstance();
	private Byte schoolRating;

	public SchoolsEditBean() {
	}

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
		return new SimpleDateFormat("dd.MM.yyyy").format(openSince.getTime());
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
	
}

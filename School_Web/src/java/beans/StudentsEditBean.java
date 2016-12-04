package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Александр
 */
@ViewScoped @ManagedBean
public class StudentsEditBean implements Serializable {
	private Long id;
	private String studentName;
	private Long classID;
	private Byte studentRating;

	public StudentsEditBean() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
}

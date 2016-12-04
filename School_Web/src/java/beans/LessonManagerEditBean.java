package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import school.Lessons;
import school.Students;

/**
 *
 * @author Александр
 */
@ViewScoped @ManagedBean
public class LessonManagerEditBean implements Serializable {
	private Long id;
	private Long studentID;
	private Long lessonID;

	public LessonManagerEditBean() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStudentID() {
		return studentID;
	}

	public void setStudentID(Long studentID) {
		this.studentID = studentID;
	}

	public Long getLessonID() {
		return lessonID;
	}

	public void setLessonID(Long lessonID) {
		this.lessonID = lessonID;
	}
	
}


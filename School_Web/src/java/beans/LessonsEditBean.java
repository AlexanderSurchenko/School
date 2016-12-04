package beans;

import java.io.Serializable;
import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import school.Students;

/**
 *
 * @author Александр
 */
@ViewScoped @ManagedBean
public class LessonsEditBean implements Serializable {
	private Long id;
	private String lessonName;
	private Long teacherID;
	
	public LessonsEditBean() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLessonName() {
		return lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public Long getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(Long teacherID) {
		this.teacherID = teacherID;
	}
}

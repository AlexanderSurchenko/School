package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Александр
 */
@ViewScoped @ManagedBean
public class ClassesEditBean implements Serializable {
	private Long id;
	private String className;
	private Long roomID;
	private Long classTeacherID;
	
	public ClassesEditBean() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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
	
}

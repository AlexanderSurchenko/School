package beans;

import school.Students;
import school.Classes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Vitaly
 */
@ManagedBean
@ViewScoped
public class StudentsBean implements Serializable {
	
	@PersistenceContext
	private EntityManager em;
	@Resource
	UserTransaction utx;
	
	public List<Students> getStudents() {
		return em.createQuery("select s from Students s", Students.class)
				.getResultList();
	}
	
	public void deleteStudents(Long sID) {
		if (sID == null) {
			return;
		}
		try {
			utx.begin();
			Students s = em.find(Students.class, sID);
			if(s != null) {
				em.remove(s);
			}
			utx.commit();
		} catch (Exception ex) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"DB Error:", ex.getLocalizedMessage()));
			ex.printStackTrace(System.err);
			try {
				utx.rollback();
			} catch (Exception exc) {
				exc.printStackTrace(System.err);
				ctx.addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"DB Error:", exc.getLocalizedMessage()));
			}
		}
	}
	
	public void createStudents(StudentsEditBean newS){
		if (newS == null || newS.getId() == null) {     
			return;
		}
		try {
			utx.begin();
			Students s = new Students();
			s.setId(newS.getId());
			s.setStudentName(newS.getStudentName());
			s.setClassID(newS.getClassID());
			s.setStudentRating(newS.getStudentRating());
			if(s != null) {
				em.persist(s);
			}
			utx.commit();
		} catch (Exception ex) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"DB Error:", ex.getLocalizedMessage()));
			ex.printStackTrace(System.err);
			try {
				utx.rollback();
			} catch (Exception exc) {
				exc.printStackTrace(System.err);
			}
		}
	}
}


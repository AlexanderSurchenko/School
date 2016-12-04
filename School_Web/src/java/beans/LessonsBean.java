package beans;

import school.Lessons;
import school.Employees;
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
public class LessonsBean implements Serializable {
	
	@PersistenceContext
	private EntityManager em;
	@Resource
	UserTransaction utx;
	
	public List<Lessons> getLessons() {
		return em.createQuery("select l from Lessons l", Lessons.class)
				.getResultList();
	}
	
	public void deleteLessons(Long lID) {
		if (lID == null) {
			return;
		}
		try {
			utx.begin();
			Lessons l = em.find(Lessons.class, lID);
			if(l != null) {
				em.remove(l);
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
	
	public void createLessons(LessonsEditBean newL){
		if (newL == null || newL.getId() == null) {     
			return;
		}
		try {
			utx.begin();
			Lessons l = new Lessons();
			l.setId(newL.getId());
			l.setLessonName(newL.getLessonName());
			l.setTeacherID(newL.getTeacherID());
			if(l != null) {
				em.persist(l);
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

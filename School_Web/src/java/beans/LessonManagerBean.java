package beans;

import school.LessonManager;
import school.Students;
import school.Lessons;
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
public class LessonManagerBean implements Serializable {
	
	@PersistenceContext
	private EntityManager em;
	@Resource
	UserTransaction utx;
	
	public List<LessonManager> getLink() {
		return em.createQuery("select l from LessonManager l", 
				LessonManager.class).getResultList();
	}
	
	public void deleteLink(Long lID) {
		if (lID == null) {
			return;
		}
		try {
			utx.begin();
			LessonManager l = em.find(LessonManager.class, lID);
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
	
	public void createLink(LessonManagerEditBean newL){
		if (newL == null || newL.getId() == null) {     
			return;
		}
		try {
			utx.begin();
			LessonManager l = new LessonManager();
			l.setId(newL.getId());
			l.setStudentID(newL.getStudentID());
			l.setLessonID(newL.getLessonID());
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


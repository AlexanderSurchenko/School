package beans;

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
public class ClassesBean implements Serializable {
	
	@PersistenceContext
	private EntityManager em;
	@Resource
	UserTransaction utx;
	
	public List<Classes> getClasses() {
		return em.createQuery("select c from Classes c", Classes.class)
				.getResultList();
	}
	
	public void deleteClasses(Long cID) {
		if (cID == null) {
			return;
		}
		try {
			utx.begin();
			Classes c = em.find(Classes.class, cID);
			if(c != null) {
				em.remove(c);
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
	
	public void createClasses(ClassesEditBean newC){
		if (newC == null || newC.getId() == null) {     
			return;
		}
		try {
			utx.begin();
			Classes cl = new Classes();
			cl.setId(newC.getId());
			cl.setClassName(newC.getClassName());
			cl.setRoomID(newC.getRoomID());
			cl.setClassTeacherID(newC.getClassTeacherID());
			if(cl != null) {
				em.persist(cl);
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

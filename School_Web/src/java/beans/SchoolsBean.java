package beans;

import school.Schools;
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
public class SchoolsBean implements Serializable {
	
	@PersistenceContext
	private EntityManager em;
	@Resource
	UserTransaction utx;
	
	public List<Schools> getSchools() {
		return em.createQuery("select s from Schools s", Schools.class)
				.getResultList();
	}
	
	public void deleteSchools(Long sID) {
		if (sID == null) {
			return;
		}
		try {
			utx.begin();
			Schools s = em.find(Schools.class, sID);
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
	
	public void createSchools(SchoolsEditBean newS){
		if (newS == null || newS.getId() == null) {     
			return;
		}
		try {
			utx.begin();
			Schools s = new Schools();
			s.setId(newS.getId());
			s.setSchoolName(newS.getSchoolName());
			s.setLocationID(newS.getLocationID());
			s.setSchoolType(newS.getSchoolName());
			s.setCapacity(newS.getCapacity());
			s.setOpenSince(newS.getOpenSince());
			s.setSchoolRating(newS.getSchoolRating());
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

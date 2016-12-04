package beans;

import school.Positions;
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
public class PositionsBean implements Serializable {
	
	@PersistenceContext
	private EntityManager em;
	@Resource
	UserTransaction utx;
	
	public List<Positions> getPositions() {
		return em.createQuery("select p from Positions p", Positions.class)
				.getResultList();
	}
	
	public void deletePositions(Long pID) {
		if (pID == null) {
			return;
		}
		try {
			utx.begin();
			Positions p = em.find(Positions.class, pID);
			if(p != null) {
				em.remove(p);
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
	
	public void createPositions(PositionsEditBean newP){
		if (newP == null || newP.getId() == null) {     
			return;
		}
		try {
			utx.begin();
			Positions p = new Positions();
			p.setId(newP.getId());
			p.setPositionName(newP.getPositionName());
			p.setSalary(newP.getSalary());
			if(p != null) {
				em.persist(p);
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


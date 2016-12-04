package beans;

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
public class EmployeesBean implements Serializable {
	
	@PersistenceContext
	private EntityManager em;
	@Resource
	UserTransaction utx;
	
	public List<Employees> getEmployees() {
		return em.createQuery("select e from Employees e", Employees.class)
				.getResultList();
	}
	
	public void deleteEmployees(Long eID) {
		if (eID == null) {
			return;
		}
		try {
			utx.begin();
			Employees e = em.find(Employees.class, eID);
			if(e != null) {
				em.remove(e);
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
	
	public void createEmployees(EmployeesEditBean newE){
		if (newE == null || newE.getId() == null) {     
			return;
		}
		try {
			utx.begin();
			Employees e = new Employees();
			e.setId(newE.getId());
			if (newE.getBossID() != null) {
				e.setBoss(em.find(Employees.class, newE.getBossID()));
			}
			e.setSchoolID(newE.getSchoolID());
			e.setEmployeeName(newE.getEmployeeName());
			e.setPositionID(newE.getPositionID());
			e.setHireDate(newE.getHireDate());
			e.setEmployeeRating(newE.getEmployeeRating());
			e.setBonusFee(newE.getBonusFee());
			if(e != null) {
				em.persist(e);
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

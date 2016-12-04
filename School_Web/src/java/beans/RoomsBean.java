package beans;

import school.Rooms;
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
public class RoomsBean implements Serializable {
	
	@PersistenceContext
	private EntityManager em;
	@Resource
	UserTransaction utx;
	
	public List<Rooms> getRooms() {
		return em.createQuery("select l from Locations l", Rooms.class)
				.getResultList();
	}
	
	public void deleteRooms(Long rN) {
		if (rN == null) {
			return;
		}
		try {
			utx.begin();
			Rooms r = em.find(Rooms.class, rN);
			if(r != null) {
				em.remove(r);
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
	
	public void createRooms(RoomsEditBean newR){
		if (newR == null || newR.getRoomNum() == null) {     
			return;
		}
		try {
			utx.begin();
			Rooms r = new Rooms();
			r.setRoomNum(newR.getRoomNum());
			r.setFloor(newR.getFloor());
			r.setCapacity(newR.getCapacity());
			if(r != null) {
				em.persist(r);
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



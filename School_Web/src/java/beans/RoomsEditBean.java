package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Александр
 */
@ViewScoped @ManagedBean
public class RoomsEditBean implements Serializable {
	private Long roomNum;
	private Byte floor;
	private Byte capacity;

	public RoomsEditBean() {
	}

	public Long getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Long roomNum) {
		this.roomNum = roomNum;
	}

	public Byte getFloor() {
		return floor;
	}

	public void setFloor(Byte floor) {
		this.floor = floor;
	}

	public Byte getCapacity() {
		return capacity;
	}

	public void setCapacity(Byte capacity) {
		this.capacity = capacity;
	}
	
}

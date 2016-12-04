/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Александр
 */
@Entity @Table(name = "Rooms")
public class Rooms implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long roomNum;
	private Byte floor;
	private Byte capacity;

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

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (roomNum != null ? roomNum.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work 
		// in the case the id fields are not set
		if (!(object instanceof Rooms)) {
			return false;
		}
		Rooms other = (Rooms) object;
		if ((this.roomNum == null && other.roomNum != null) || 
				(this.roomNum != null && !this.roomNum.equals(other.roomNum))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "school.Rooms[ roomNum=" + roomNum + " ]";
	}
	
}

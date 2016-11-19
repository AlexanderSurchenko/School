/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.surchenko.school;

/**
 *
 * @author Александр
 */
public class Rooms {
	private int roomNum;
	private byte floor;
	private byte capacity;

	public Rooms(int roomNum, byte floor) {
		this.roomNum = roomNum;
		this.floor = floor;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public byte getFloor() {
		return floor;
	}

	public void setFloor(byte floor) {
		this.floor = floor;
	}

	public byte getCapacity() {
		return capacity;
	}

	public void setCapacity(byte capacity) {
		this.capacity = capacity;
	}
	
}

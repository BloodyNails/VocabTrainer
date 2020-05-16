package com.bloodynails.database;

public class DBObj {
	protected Long ID;
	protected DBObjType type;

	public DBObj(Long ID, DBObjType type) {
		
		if(ID == null) throw new NullPointerException("ID must not be null");
		if(ID < 0) throw new IllegalArgumentException("ID must be equal to or greater than 0");
		if(type == null) throw new NullPointerException("type must be specified");
		
		this.ID = ID;
		this.type = type;
	}
	
	public Long getID() {
		return ID;
	}
	
	public DBObjType getType() {
		return type;
	}
}

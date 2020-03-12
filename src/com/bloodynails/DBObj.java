package com.bloodynails;

public class DBObj {
	private Long ID;
	private DBObjType type;
	
	public DBObj(Long id, DBObjType type) {
		this.ID = id;
		this.type = type;
	}
	
	public Long getID() {
		return this.ID;
	}
	
	public DBObjType getType() {
		return this.type;
	}
}

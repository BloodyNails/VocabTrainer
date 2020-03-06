package com.bloodynails;

public class DBObj {
	private Long id;
	private DBObjType type;
	
	public DBObj(Long id, DBObjType type) {
		this.id = id;
		this.type = type;
	}
	
	public Long getID() {
		return this.id;
	}
	
	public DBObjType getType() {
		return this.type;
	}
}

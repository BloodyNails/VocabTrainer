package com.bloodynails;

public class DBObj {
	private Long ID;
	private DBObjType type;

	public DBObj(Long ID, DBObjType type) {
		this.ID = ID;
		this.type = type;
	}

	public Long getID() {
		return this.ID;
	}

	public DBObjType getType() {
		return this.type;
	}
}

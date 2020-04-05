package com.bloodynails.database;

public class DBObj {
	protected Long ID;
	protected DBObjType type;

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

package com.bloodynails;

import java.io.Serializable;

public class DBObj implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private DBObjType type;
	
	public DBObj() {
		this.type = null;
	}
	
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

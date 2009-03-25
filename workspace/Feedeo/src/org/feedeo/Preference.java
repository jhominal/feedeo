package org.feedeo;

import org.feedeo.util.HibernateObject;
import org.feedeo.util.JsonObjectSerializable;

public class Preference extends HibernateObject {
	
	private String name;
	
	public Preference() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

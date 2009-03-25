package org.feedeo.util;

import java.util.Map;

public interface JsonObjectSerializable {
	
	public Map<String,Object> toMap(boolean shallow);
	

}

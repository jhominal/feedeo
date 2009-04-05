package org.feedeo.clientcomm;

import java.util.Map;

/**
 * Type a description for JsonException here.
 * 
 * @author Feedeo Team
 *
 */
public final class JsonException implements JsonObjectSerializable {

	private Exception exception;
	
	JsonException(Exception e) {
		exception = e;
	}
	
	/* (non-Javadoc)
	 * @see org.feedeo.clientcomm.JsonObjectSerializable#toMap(boolean)
	 */
	@Override
	public Map<String, Object> toMap(boolean deep) {
		// TODO Auto-generated method stub
		return null;
	}

}

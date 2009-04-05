package org.feedeo.clientcomm;

import java.util.HashMap;
import java.util.Map;

/**
 * Type a description for JsonExceptionPrinter here.
 * 
 * @author Feedeo Team
 * 
 */
final class JsonExceptionPrinter implements JsonObjectSerializable {

	private Exception exception;
	private String transactionRollback;

	JsonExceptionPrinter(Exception e) {
		exception = e;
	}

	void setTransactionRollback(String message) {
		transactionRollback = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.feedeo.clientcomm.JsonObjectSerializable#toMap(boolean)
	 */
	@Override
	public Map<String, Object> toMap(boolean deep) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("message", exception.getMessage());
		result.put("class", exception.getClass().toString());
		if (transactionRollback != null) {
			result.put("rollback", transactionRollback);
		}
		if (deep) {
			Map<Integer, String> stackTraceMap = new HashMap<Integer, String>();
			StackTraceElement[] stackTraceArray = exception.getStackTrace();
			for (int i = 0; i < stackTraceArray.length; i++) {
				stackTraceMap.put(i, stackTraceArray[i].toString());
			}
			result.put("stacktrace", stackTraceMap);
		}
		return result;
	}

}

package org.feedeo.clientcomm;

import java.util.HashMap;
import java.util.Map;

/**
 * This class makes it easy to print an exception, with its whole stacktrace, as
 * a JSON object.
 * 
 * @author Feedeo Team
 * 
 */
final class JsonExceptionPrinter implements JsonObjectSerializable {

	private Exception exception;
	private String transactionRollback;

	/**
	 * Builds a JsonExceptionPrinter object, given a JavaException.
	 * @param e the referenced Java Exception
	 */
	JsonExceptionPrinter(Exception e) {
		exception = e;
	}

	/**
	 * sets a message, whose goal is to tell whether the
	 * transaction's rollback was successful.
	 * 
	 * @param message
	 */
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

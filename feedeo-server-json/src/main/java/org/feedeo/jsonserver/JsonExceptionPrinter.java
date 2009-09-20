package org.feedeo.jsonserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class makes it easy to print an exception, with its whole stacktrace, as
 * a JSON object.
 * 
 * @author Feedeo Team
 * 
 */
public final class JsonExceptionPrinter {

  private Exception exception;
  private String    transactionRollback;

  /**
   * Builds a JsonExceptionPrinter object, given a JavaException.
   * 
   * @param e
   *          the referenced Java Exception
   */
  public JsonExceptionPrinter(Exception e) {
    exception = e;
  }

  /**
   * sets a message, whose goal is to tell whether the transaction's rollback
   * was successful.
   * 
   * @param message
   */
  public void setTransactionRollback(String message) {
    transactionRollback = message;
  }

  public Map<String, Object> toMap(boolean deep) {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("message", exception.getMessage());
    result.put("class", exception.getClass().toString());
    if (transactionRollback != null) {
      result.put("rollback", transactionRollback);
    }
    if (deep) {
      List<String> stackTraceList = new ArrayList<String>();
      StackTraceElement[] stackTraceArray = exception.getStackTrace();
      for (int i = 0; i < stackTraceArray.length; i++) {
        stackTraceList.add(i + ": " + stackTraceArray[i].toString());
      }
      result.put("stacktrace", stackTraceList);
    }
    return result;
  }

}

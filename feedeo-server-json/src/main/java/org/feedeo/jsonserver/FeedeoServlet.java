package org.feedeo.jsonserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONWriter;

/**
 * This servlet is the one called when the Feedeo client posts data to the
 * server. It seamlessly replaces the former index.jsp file.
 */
public final class FeedeoServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public FeedeoServlet() {
    super();
  }

  /**
   * This method passes the request and the response to the doPost method of
   * this class. This method is used mainly for testing purposes.
   * 
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
  {
    doPost(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
  {
    response.setContentType("application/json;charset=UTF-8");
    HttpSession session = request.getSession();

    Map<String, Object> jsonResponse = new HashMap<String, Object>();

    // cas par défaut
    jsonResponse.put("success", Boolean.FALSE);
    jsonResponse.put("error", "No handler for your request");

    // jsonResponse.put("debug",request.getQueryString());

    JSONReader jsonReader = new JSONReader();

    String userName = (String) session.getAttribute("userName");

    // FeedeoHandler handler = new FeedeoHandler(userName);

    String jsonRequest = request.getParameter("request");
    String requestType = request.getParameter("type");

    if (userName == null) {
      // pas loggé
      // traite une requete de login ou de creation
      try {
        // Suppress warning because the Feedeo client/server (implicit) API specifies that.
        @SuppressWarnings("unchecked")
        Map<String, Object> loginOrCreateRequest = (Map<String, Object>) jsonReader
            .read(jsonRequest);
        String action = (String) loginOrCreateRequest.get("action");
        if (action != null && action.equals("login")) {
          userName = FeedeoHandler.login(loginOrCreateRequest);
          if (userName != null) {
            session.setAttribute("userName", userName);
            jsonResponse.put("success", Boolean.TRUE);
            jsonResponse.put("msg", "login successful");
            // ajouter qq données sur l'user
          } else {
            jsonResponse.put("success", Boolean.TRUE);
            jsonResponse.put("error", "login failed");
          }
        } else if (action != null && action.equals("create")) {
          userName = FeedeoHandler.createAccount(loginOrCreateRequest);
          if (userName != null) {
            // log him
            session.setAttribute("userName", userName);
            jsonResponse.put("success", Boolean.TRUE);
            jsonResponse.put("msg", "Account creation successful");
            // ajouter qq données sur l'user
          } else {
            jsonResponse.put("success", Boolean.FALSE);
            jsonResponse.put("error", "Account creation failed");
          }
        } else {
          jsonResponse.put("success", Boolean.FALSE);
          jsonResponse.put("error", "Request failed. You must login.");
        }
      } catch (Exception e) {
        jsonResponse.put("success", Boolean.FALSE);
        jsonResponse.put("error", e.getMessage());
        JsonExceptionPrinter ePrinter = new JsonExceptionPrinter(e);
        jsonResponse.put("JavaProblem", ePrinter.toMap(true));
      }
    } else {
      FeedeoHandler handler = new FeedeoHandler(userName);
      if (requestType != null && requestType.equals("multiple")) {
        try {
          // Suppress warning because the Feedeo client/server (implicit) API specifies that.
          @SuppressWarnings("unchecked")
          List<Map<String, Object>> requests = (List<Map<String, Object>>) jsonReader
              .read(jsonRequest);
          // handle each request
          for (Map<String, Object> r : requests) {
            // out.println("Req : "+r);
            handler.handle(r, jsonResponse);
          }

          // debug
          // jsonResponse = jsonWriter.write(requests);

        } catch (Exception e) {
          jsonResponse.put("success", Boolean.FALSE);
          jsonResponse.put("error", "bad multiple request");
          JsonExceptionPrinter ePrinter = new JsonExceptionPrinter(e);
          jsonResponse.put("JavaProblem", ePrinter.toMap(true));
        }
      } else if (requestType != null && requestType.equals("simple")) {
        try {
          // Suppress warning because the Feedeo client/server (implicit) API specifies that.
          @SuppressWarnings("unchecked")
          Map<String, Object> simpleRequest = (Map<String, Object>) jsonReader
              .read(jsonRequest);
          handler.handle(simpleRequest, jsonResponse);
        } catch (Exception e) {
          jsonResponse.put("success", Boolean.FALSE);
          jsonResponse.put("error", "bad simple request");
          JsonExceptionPrinter ePrinter = new JsonExceptionPrinter(e);
          jsonResponse.put("JavaProblem", ePrinter.toMap(true));
        }
      } else {
        jsonResponse.put("success", Boolean.FALSE);
        jsonResponse.put("error", "bad request type [multiple, simple]");
      }
    }
    // enlever le error par défaut si succès
    if (((Boolean) jsonResponse.get("success")).booleanValue()) {
      jsonResponse.remove("error");
    }

    JSONWriter jsonWriter = new JSONWriter();

    response.getWriter().write(jsonWriter.write(jsonResponse));
    response.getWriter().flush();
  }

}

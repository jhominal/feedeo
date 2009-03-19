<%@ page import="java.util.*" %>
<%@ page import="org.stringtree.json.JSONWriter" %>
<%
/*
Enumeration pNameList; // contient une liste des params transmises
String pName;          // contient un de ces noms
String pVals [];       // contient l'array des valeurs pour chaque param
String val;            // 1 valeur

out.println("<p>Method used was: " + request.getMethod());

out.println("<p>Liste de parametres et valeurs (pas forcement dans l'ordre): <ol>");

for (pNameList = request.getParameterNames();pNameList.hasMoreElements();){
        pName = (String) pNameList.nextElement();
    out.println("<li>name=" + pName + ": ");
    pVals = request.getParameterValues(pName);

    if (pVals != null) {
                for (int i=0; i<pVals.length; i++)
                        out.print ("val=" + pVals[i] + " ");
    }
}

out.println("</ol>");
*/
/*
String jsonResponse = "";

String userName = (String)session.getAttribute("userName");
String object = request.getParameter("object");
String action = request.getParameter("action");
out.print(request.getQueryString());
// handle login and register request
if(userName == null)
{
        //must be a login request or a register request
        if( action!=null && action.equals("login" ))
        {
                //log user
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                if(login != null && password !=null)
                {
                        //check user

                        //save in session
                        session.setAttribute("userName",userName);
                }
                else
                {
                        jsonResponse = "{ success : false, error : 'You must give login and password.' }";
                }
        }
        else if(action!=null && action.equals("register"))
        {
                //create account
        }
        else
        {
                //illegal request
                jsonResponse = "{ success : false, error : 'You are not logged.' }";
        }
}
// handle other requests
else
{

}


out.print(jsonResponse);
*/

String jsonRequest = request.getParameter("json");
out.println(jsonRequest);
%>

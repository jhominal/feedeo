<%@page import="org.stringtree.json.JSONReader"%>
<%@page import="org.stringtree.json.JSONWriter"%>
<%@page import="java.util.*"%>
<%@page import="serveur.*" %>
<%
HashMap jsonResponse = new HashMap();

JSONReader jsonReader = new JSONReader();
JSONWriter jsonWriter = new JSONWriter();

String jsonRequest = request.getParameter("request");
String requestType = request.getParameter("type");

if(requestType != null && requestType.equals("multiple"))
{
	try
	{
	        ArrayList<HashMap> requests = (ArrayList)jsonReader.read(jsonRequest);
	        //handle each request
	        for(HashMap r:requests)
	        {
	                //out.println("Req : "+r);
	                FeedeoHandler.handle(r,jsonResponse);
	        }
	
	
	//debug
	//jsonResponse = jsonWriter.write(requests);
	
	
	}
	catch(Exception e)
	{
	        jsonResponse.put("success",false);
	        jsonResponse.put("error", "bad multiple request");
	}
}
else if(requestType != null && requestType.equals("simple"))
{
	try
	{
	        HashMap simpleRequest = (HashMap)jsonReader.read(jsonRequest);
			FeedeoHandler.handle(simpleRequest, jsonResponse);

	}
	catch(Exception e)
	{
	        jsonResponse.put("success",false);
	        jsonResponse.put("error", "bad simple request");
	}
}
else 
{
	jsonResponse.put("success",false);
    jsonResponse.put("error", "bad request type [multiple, simple]");

}

out.println(jsonWriter.write(jsonResponse));

%>
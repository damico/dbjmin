package org.jdamico.dbjmin.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdamico.dbjmin.web.action.ActionsFactory;

import db2jmin.pojo.util.Constants;

public class Schemas extends HttpServlet {

	private static final long serialVersionUID = -8232210101653665253L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<String> schemas = ActionsFactory.callJetty(null, Constants.W_SCHEMA_ACTION).getResult();
				PrintWriter out = response.getWriter();
		
		
		
		
		out.println(Constants.HTML_TOP);
		out.println(Constants.HTML_SCHEMA_HEADER);
		out.println(Constants.HTML_TOOLS.replaceAll("@", DbSessionInfo.getInstance().getCurrentDBinfo()));
		out.println("<table border = '1' width = '300'>");
		out.println("<tr bgcolor='#CCCCCC'><td><b>Schemas ("+schemas.size()+")</b></td></tr>\n");
		for(int i=0; i<schemas.size(); i++){
			out.println("<tr><td><a href='/tables?s="+schemas.get(i)+"'>"+schemas.get(i)+"</a></td></tr>\n");
		}
		out.println("</table>");
		if(schemas.size() < 1){
			out.println("<br><br><table bgcolor = 'black' width='300'>" +
			"<tr><td><font color = 'yellow'><b>No Schemas found in this session!</b></font></td></tr></table>");

		}
		
		out.println(Constants.HTML_BOTTON);
		out.close();
	}
		
}

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

public class Tables extends HttpServlet {

	private static final long serialVersionUID = -8232210101653665253L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<String> form_data = new ArrayList<String>();
		form_data.add(request.getParameter("s"));
		ArrayList<String> tables = ActionsFactory.callJetty(form_data, Constants.W_TABLE_ACTION).getResult();
		PrintWriter out = response.getWriter();
		
		out.println(Constants.HTML_TOP);
		out.println(Constants.HTML_TABLE_HEADER);
		out.println(Constants.HTML_TOOLS.replaceAll("@", DbSessionInfo.getInstance().getCurrentDBinfo()));
		out.println("<table border = '1' width = '500'>");
		out.println("<tr bgcolor='#CCCCCC'><td><b>Tables ("+tables.size()+")</b></td>" +
				"<td><b>Sample Data</b></td>" +
				"<td><b>Structure</b></td>" +
				"</tr>\n");
		for(int i=0; i<tables.size(); i++){
			out.println("<tr><td>"+tables.get(i)+"</td>" +
					"<td><a href='/sampledata?s="+request.getParameter("s")+"&t="+tables.get(i)+"'>View</a></td>" +
					"<td><a  href='/structure?s="+request.getParameter("s")+"&t="+tables.get(i)+"'>View</a></td></tr>\n");
		}
		out.println("</table>");
		if(tables.size() < 1){
			out.println("<br><br><table bgcolor = 'black' width='500'>" +
					"<tr><td><font color = 'yellow'><b>No tables for Schema "+request.getParameter("s")+"</b></font></td></tr></table>");
		}
		out.println(Constants.HTML_BOTTON);
		out.close();
	}
		
}

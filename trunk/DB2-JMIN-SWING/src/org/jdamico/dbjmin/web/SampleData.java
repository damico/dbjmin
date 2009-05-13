package org.jdamico.dbjmin.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdamico.dbjmin.web.action.ActionsFactory;

import db2jmin.pojo.util.Constants;

public class SampleData extends HttpServlet {

	private static final long serialVersionUID = -8232210101653665253L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<String> form_data = new ArrayList<String>();
		form_data.add(request.getParameter("s"));
		form_data.add(request.getParameter("t"));
		ArrayList data = ActionsFactory.callJetty(form_data, Constants.W_SAMPLEDATA_ACTION).getResult();
		PrintWriter out = response.getWriter();
		
		
		
		
		

		ArrayList<String> columns_name = new ArrayList<String>();
		ArrayList<String> columns_data = new ArrayList<String>();
		ArrayList set = (ArrayList) data.get(1);
		columns_name = (ArrayList<String>) data.get(0);
		String error = data.get(2).toString();

		out.println(Constants.HTML_TOP);
		out.println(ServletUtils.getInstance().getHTMLhead());
		out.println(Constants.HTML_SAMPLEDATA_HEADER);
		String tools = Constants.HTML_TOOLS.replaceAll("inserLogButton", ServletUtils.getInstance().getLogButton());
		out.println(tools.replaceAll("@", ServletUtils.getInstance().getCurrentDBinfo()));
		out.println("<div id=\"display\"></div><br>\n");
	
		if(set.size()<1) error = error + "Table empty!";
		
		if(error.equals("")){
			out.println("<table border = '1' width = '300'>");
			out.println("<tr bgcolor='#CCCCCC'>\n");
			for (int j = 0; j < columns_name.size(); j++) {
				out.println("<td>"+columns_name.get(j)+"</td>");
			}
			out.println("</tr>\n");
			
			int setSize = set.size();
			for (int i = 0; i < setSize; i++) {
				columns_data.clear();
				columns_data = (ArrayList<String>) set.get(i);
				out.println("<tr>\n");
				for (int j = 0; j < columns_data.size(); j++) {
					try {
						out.println("<td>"+columns_data.get(j)+"</td>");
					} catch (NullPointerException e) {
						// log.AddLogLine("NullPointerException: "+e);
					}

				}
				out.println("</tr>\n");
				columns_data.clear();
			}
			
			out.println("</table>");
		}else{
			out.println("<table bgcolor = 'black' width='500'>" +
					"<tr valign='top'><td><font color = 'red'><b>"+error+"</b></font></td></tr></table>");

		}
		
		out.println("<BR>");
		out.println(Constants.HTML_SQL_FORM.replaceAll("@", ServletUtils.getInstance().getSQLInnerButton()));

		out.println(Constants.HTML_BOTTON);
		out.close();
	}
		
}
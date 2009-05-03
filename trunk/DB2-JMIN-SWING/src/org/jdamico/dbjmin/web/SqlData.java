package org.jdamico.dbjmin.web;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdamico.dbjmin.web.action.ActionsFactory;

import db2jmin.pojo.swing.SwingUtils;
import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.ExecuteUpdateObject;
import db2jmin.pojo.util.InputDataValidation;
import db2jmin.pojo.util.Logger;
import db2jmin.pojo.util.OutputDataValidation;
import db2jmin.pojo.util.SQLParser;

public class SqlData extends HttpServlet {

	private static final long serialVersionUID = -8232210101653665253L;
	private Logger log = new Logger(Constants.LOGNAME);
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> form_data = new ArrayList<String>();
		ArrayList errors = new ArrayList();
		StringBuffer sb = new StringBuffer();
		String query = request.getParameter("s");
		form_data.add(query);
		PrintWriter out = response.getWriter();
		out.println(Constants.HTML_TOP);
		out.println(Constants.HTML_SQL_HEADER);
		out.println(Constants.HTML_TOOLS.replaceAll("@", DbSessionInfo.getInstance().getCurrentDBinfo()));
		
		
		
		
		if(query.contains(";")){
			ArrayList<ExecuteUpdateObject> sqldata = ActionsFactory.callJetty(form_data, Constants.W_BATCH_SQL_ACTION).getResult();
			out.println("<table border = '1' width = '500'>");
			out.println("<tr bgcolor='#CCCCCC'><td><b>#</b></td>" +
					"<td><b>Status</b></td>" +
					"<td><b>Statement</b></td>" +
			"</tr>\n");
			for (int i = 0; i < sqldata.size(); i++) {
				out.println("<tr valign='top'><td>"+String.valueOf(i)+"</td>" +
						"<td>"+sqldata.get(i).getStatus()+"</td>" +
						"<td>"+OutputDataValidation.getInstance().wrapResult(sqldata.get(i).getStatement())+"</td>" +
				"</tr>\n");
			}
			out.println("</table>");
			
		}else{
			ArrayList data = ActionsFactory.callJetty(form_data, Constants.W_SINGLE_SQL_ACTION).getResult();
			ArrayList columns_data = new ArrayList();
			ArrayList columns_name = (ArrayList) data.get(0);
			ArrayList set = (ArrayList) data.get(1);
			errors = (ArrayList) data.get(2);

			
			out.println("<table border = '1' width = '500'>");
			out.println("<tr bgcolor='#CCCCCC'>\n");
			for (int j = 0; j < columns_name.size(); j++) {
				out.println("<td>"+columns_name.get(j)+"</td>");
			}
			out.println("</tr>\n");
			
			int setSize = set.size();
			for (int i = 0; i < setSize; i++) {
				columns_data.clear();
				columns_data = (ArrayList<String>) set.get(i);
				out.println("<tr valign='top'>\n");
				for (int j = 0; j < columns_data.size(); j++) {
					try {
						out.println("<td>"+columns_data.get(j)+"</td>");
					} catch (NullPointerException e) {
					}
				}
				out.println("</tr>\n");
				columns_data.clear();
			}
			
			out.println("</table>");
		}

		
		if (errors.size() > 0) {

			sb.append("ERROR\n");
			String node = null;
			Iterator iter = errors.iterator();
			while (iter.hasNext()) {
				node = iter.next().toString();
				sb.append(node + "\n");
			}
			
			out.println("</table<table bgcolor = 'black' width='500'>" +
			"<tr valign='top'><td><font color = 'red'><b>"+sb.toString()+"</b></font></td></tr></table>");
			
		}
		
		
		out.println("<BR>");
		out.println(Constants.HTML_SQL_FORM);
		out.println(Constants.HTML_BOTTON);
		out.close();
	}

}

package org.jdamico.dbjmin.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdamico.dbjmin.web.action.ActionsFactory;

import db2jmin.pojo.data.Preferences;
import db2jmin.pojo.util.Constants;

public class Structure extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String schema = request.getParameter("s");
		String table = request.getParameter("t");
		Preferences form_data = new Preferences();
		form_data.setSchema(schema);
		form_data.setTable(table);
		ArrayList table_description = ActionsFactory.callJetty(form_data,
				Constants.W_STRUCTURE_ACTION).getResult();
		PrintWriter out = response.getWriter();

		ArrayList<String> colname = (ArrayList<String>) table_description
				.get(0);
		ArrayList<String> typename = (ArrayList<String>) table_description
				.get(1);
		ArrayList<String> length = (ArrayList<String>) table_description.get(2);

		out.println(Constants.HTML_TOP);
		out.println(ServletUtils.getInstance().getHTMLhead());
		out.println(ServletUtils.getInstance().getCommonHeader("Structure",
				schema, table, Constants.SCHEMA_DATA));
		String tools = Constants.HTML_TOOLS.replaceAll("inserLogButton",
				ServletUtils.getInstance().getLogButton());
		out.println(tools.replaceAll("@", ServletUtils.getInstance()
				.getCurrentDBinfo()));
		out.println("<div id=\"display\"></div><br>\n");

		out.println("<table border = '1' width = '400'>");
		out.println("<tr bgcolor='#CCCCCC'><td><b>Column Name</b></td>"
				+ "<td><b>Type Name</b></td>" + "<td><b>Length</b></td>"
				+ "</tr>\n");

		for (int i = 0; i < colname.size(); i++) {
			try {
				out.println("<tr><td>" + colname.get(i) + "</td>" + "<td>"
						+ typename.get(i) + "</td>" + "<td>" + length.get(i)
						+ "</td>" + "</tr>\n");
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		out.println("</table>");

		out.println("<BR>");
		out.println(Constants.HTML_SQL_FORM.replaceAll("@", ServletUtils
				.getInstance().getSQLInnerButton()));

		out.println(Constants.HTML_BOTTON);
		out.close();
	}
}

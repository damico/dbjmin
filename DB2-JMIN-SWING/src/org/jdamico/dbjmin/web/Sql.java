package org.jdamico.dbjmin.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db2jmin.pojo.util.Constants;

public class Sql extends HttpServlet {

	private static final long serialVersionUID = -8232210101653665253L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println(Constants.HTML_TOP);
		out.println(ServletUtils.getInstance().getHTMLhead());
		out.println(Constants.HTML_SQL_HEADER);
		String tools = Constants.HTML_TOOLS.replaceAll("inserLogButton",
				ServletUtils.getInstance().getLogButton());
		out.println(tools.replaceAll("@", ServletUtils.getInstance()
				.getCurrentDBinfo()));
		out.println("<div id=\"display\"></div><br>\n");
		out.println(Constants.HTML_SQL_FORM.replaceAll("@", ServletUtils
				.getInstance().getSQLInnerButton()));
		out.println(Constants.HTML_BOTTON);
		out.close();
	}

}

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println(Constants.HTML_TOP);
		out.println(Constants.HTML_SQL_HEADER);
		out.println(Constants.HTML_TOOLS.replaceAll("@", DbSessionInfo.getInstance().getCurrentDBinfo()));
		
		out.println(Constants.HTML_SQL_FORM);
		out.println(Constants.HTML_BOTTON);
		out.close();
	}
		
}

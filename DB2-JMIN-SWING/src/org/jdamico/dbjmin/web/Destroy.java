package org.jdamico.dbjmin.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdamico.dbjmin.web.action.ActionsFactory;

import db2jmin.pojo.util.Constants;

public class Destroy extends HttpServlet {

	private static final long serialVersionUID = -8231310101653665253L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println(Constants.HTML_TOP);
		out.println(Constants.HTML_DESTROY_HEADER);

		out.println("<table bgcolor = 'black' width='600' cellspacing='10'>\n"
				+ "<tr valign='top'>\n" + "<td><font color = 'red'>\n"
				+ "<h2><center>"
				+ ServletUtils.getInstance().getCurrentDBinfo()
				+ " was closed. <br>"
				+ "DBJMIN instance terminated.</center></h2></font>\n"
				+ "</td></tr></table>");

		out.println(Constants.HTML_BOTTON);
		out.close();
		ActionsFactory.callJetty(null, Constants.W_CLOSE_SESSION).exec();
	}

}

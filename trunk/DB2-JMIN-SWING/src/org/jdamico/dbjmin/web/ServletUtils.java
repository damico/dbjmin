package org.jdamico.dbjmin.web;

import java.util.ArrayList;

import org.jdamico.dbjmin.web.action.ActionsFactory;

import db2jmin.pojo.util.Constants;

public class ServletUtils {
	private static ServletUtils INSTANCE;

	public static ServletUtils getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ServletUtils();
		}
		return INSTANCE;
	}

	public String getCurrentDBinfo() {
		ArrayList<String> dbSession = ActionsFactory.callJetty(null,
				Constants.W_GET_DB_SESSION).getResult();
		String ret = "<font color='green'><b>" + dbSession.get(5) + "://"
				+ dbSession.get(0) + ":" + dbSession.get(1) + "/"
				+ dbSession.get(2) + " [" + dbSession.get(3) + "] </b></font>";
		return ret;
	}

	public String getLogButton() {
		return "<form id=\"myform\" action=\"\" onsubmit=\"doSubmitSerialize(this); return false;\">\n"
				+ "<input id=\"usejson\" name=\"usejson\" type=\"hidden\">"
				+ "<input type=\"hidden\" name=\"f\" value = \"log\">\n"
				+ "<span class=\"button-blue\"><input type=\"submit\" value=\"Log\"  id=\"mysubmit\" name=\"mysubmit\"></span>\n"
				+ "</form>\n";
	}

	public String getSQLInnerButton() {
		return "<form id=\"myform\" action=\"\" onsubmit=\"doSubmitSerialize(this); return false;\">\n"
				+ "<input id=\"usejson\" name=\"usejson\" type=\"hidden\">"
				+ "<input type=\"hidden\" name=\"f\" value = \"sql\">\n"
				+ "<span class=\"button-blue\"><input type=\"submit\" value=\"SQL History\"  id=\"mysubmit\" name=\"mysubmit\"></span>\n"
				+ "</form>\n";
	}

	public String getHTMLhead() {
		String ret = "<script type=\"text/javascript\" src=\"/RenderServlet?f=prototype.js\"></script>"
				+ "<script type=\"text/javascript\" src=\"/RenderServlet?f=deserialize.js\"></script> "
				+

				"\n <script type=\"text/javascript\" language=\"javascript\">"
				+

				"\n function getHtml(strUrl){"
				+ "\n 	    var html;"
				+ "\n 	    new Ajax.Request(strUrl,"
				+ "\n 	    {"
				+ "\n 		method:'get',// tipo de mÃƒÂ©todo"
				+ "\n 		requestHeaders: {Accept: 'text/plain'}, // tipo de consumo"
				+ "\n 		asynchronous: false, // sincrono ou assincrono"
				+ "\n 		onSuccess: function(transport) { // funcao de sucesso"
				+ "\n 		    html = transport.responseText;"
				+ "\n 		},"
				+ "\n 		onFailure: function() { // funcao para mostrar problemas"
				+ "\n 		    alert('Message: Something went wrong...')"
				+ "\n 		}"
				+ "\n 	    });"
				+ "\n 	    return html;"
				+ "\n 	}"
				+

				"\n function display(value) { "
				+ "\n 	var url = value;"
				+ "\n 	var div = $('display');"
				+ "\n		var html = '<input type=\"submit\" value=\"close\"  onclick=\"closedisplay()\"><br><iframe src =\"'+url+'\" width=\"100%\" height=\"400px\"><br><br><br><br>'"
				+ "\n    div.innerHTML = html;"
				+ "\n }"
				+

				"\n function closedisplay() { "
				+ "\n 	var div = $('display');"
				+ "\n		var html = ''"
				+ "\n    div.innerHTML = html;"
				+ "\n }"
				+

				"\n function doSubmitSerialize(form) {  "
				+ "\n 	var value = Form.serialize(form, $('usejson').value); "
				+ "\n 	display('/RenderServlet?'+value); " + "\n 	}" +

				"\n </script>";

		return ret;
	}

	public String getCommonHeader(String title, String schema) {
		title = title + " [ <a href='/tables?s=" + schema + "'>" + schema
				+ "</a> ]";
		String ret = Constants.HTML_COMMON_HEADER.replaceAll("#", title);
		return ret;
	}

	public String getCommonHeader(String title, String schema, String table,
			int type) {

		String action = null;
		if (type == Constants.SCHEMA_STRUCTURE)
			action = "/structure";
		else
			action = "/sampledata";

		title = title + " [ <a href='/tables?s=" + schema + "'>" + schema
				+ "</a> . <a href='" + action + "?s=" + schema + "&t=" + table
				+ "'>" + table + "</a> ]";
		String ret = Constants.HTML_COMMON_HEADER.replaceAll("#", title);
		return ret;
	}
}

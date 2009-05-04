package org.jdamico.dbjmin.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db2jmin.pojo.util.Constants;
import db2jmin.pojo.util.SystemOper;

public class RenderServlet extends HttpServlet {

	private static final long serialVersionUID = -5393876155840395297L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//response.setCharacterEncoding("ISO-8859-1");
		//response.setDateHeader("Expires",System.currentTimeMillis(  ) + 24*60*60*1000);
		String reqFile = request.getParameter("f");
		String filename = null;
		
		if(reqFile.equals("log")){
			filename = SystemOper.singleton().getTempPath()+Constants.LOGNAME+".log"; 
		}else if(reqFile.equals("sql")){
			filename = SystemOper.singleton().getHomePath()+Constants.HISTORY_FILE;
		}else{
			filename = SystemOper.singleton().getLibPath()+reqFile;
		}
		
		Boolean err = false;
		String errMsg = "Incorrect file path or incorrect file name. ("+filename+") ";

		if(reqFile!=null){
			try{
				

					
					   try {
							BufferedReader in = new BufferedReader(new FileReader(filename));
							String str;

							while ((str = in.readLine()) != null) {
								response.getWriter().println(str);
							}
							in.close();
						} catch (IOException ioe) {
							err = true;
							errMsg = errMsg + ioe.getMessage();
						}

					

				
				
			} catch(NullPointerException npe){
				npe.printStackTrace();
				err = true;
				errMsg = errMsg + npe.getMessage();
			}
		}else{
			err = true;
		}

		if(err) response.getWriter().println(errMsg);





	}


}


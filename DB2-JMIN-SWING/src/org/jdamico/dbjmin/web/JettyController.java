package org.jdamico.dbjmin.web;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.servlet.ServletHandler;

public class JettyController {

	private Server server = null;
	
	public JettyController(int port) throws Exception {
		server = new Server();
        Connector connector=new SocketConnector();
        connector.setPort(port);
        server.setConnectors(new Connector[]{connector});
        
        ServletHandler handler=new ServletHandler();
        server.setHandler(handler);
        
        handler.addServletWithMapping("org.jdamico.dbjmin.web.Schemas",		 "/schemas");
        handler.addServletWithMapping("org.jdamico.dbjmin.web.Tables",		 "/tables");
        handler.addServletWithMapping("org.jdamico.dbjmin.web.Sql",			 "/sql");
        handler.addServletWithMapping("org.jdamico.dbjmin.web.SampleData",	 "/sampledata");
        handler.addServletWithMapping("org.jdamico.dbjmin.web.SqlData",		 "/sqldata");
        
        
	}

	public void startServer() throws Exception {
		server.start();
        server.join();
	}

}

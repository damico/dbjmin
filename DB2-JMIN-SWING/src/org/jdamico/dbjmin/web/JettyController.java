package org.jdamico.dbjmin.web;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.servlet.ServletHandler;

public class JettyController implements Runnable {

	private Server server = null;
	private Thread t = null;
	
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


	public void init(){
        t = new Thread(this);
        t.start();
    }

    public int stopServer(){
        t= new Thread(this);
        t.interrupt();
        return 1;
    }
	
	
	public void run() {
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		
	}

}

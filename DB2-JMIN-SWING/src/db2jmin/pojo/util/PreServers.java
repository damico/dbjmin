package db2jmin.pojo.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class allows application to
 * get pre-defined db credentials
 * @author Jose Damico (damico@dcon.com.br)
 * */

public class PreServers {
	
	private static final PreServers getInstance = new PreServers();

	public static PreServers singleton() {
		return getInstance;
	}

	private Logger log = new Logger(Constants.LOGNAME);

	public boolean isPreServer(String xmlFile) {
		boolean ret = false;
		File preServerFile = new File(xmlFile);
		if (preServerFile.exists())
			ret = true;
		return ret;
	}

	public ArrayList<ServersObject> getPreServers(String xmlFile)
			throws Exception {
		String logerror = null;
		ArrayList<ServersObject> srvObjArrLst = new ArrayList<ServersObject>();
		try {
			// Create a factory
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			// Use the factory to create a builder
			DocumentBuilder builder = factory.newDocumentBuilder();
			ByteArrayInputStream xml = new ByteArrayInputStream(xmlFile
					.getBytes());
			File file = new File(xmlFile);
			Document doc = null;
			if (file.exists()) {
				doc = builder.parse(xmlFile);
			} else if (xmlFile.length() > 2) {
				doc = builder.parse(xml);
			} else {
				logerror = "There is no valid XML: No File, No String";
				log.AddLogLine(logerror);
			}
			// Get a list of all elements in the document

			NodeList list = doc.getElementsByTagName("*");

			for (int i = 0; i < list.getLength(); i++) {
				// Get element
				Element element = (Element) list.item(i);
				String nodeName = element.getNodeName();
				if (nodeName.equals("PRESERVERS")) {

					NodeList e_list = element.getElementsByTagName("SERVER");

					for (int j = 0; j < e_list.getLength(); j++) {

						Element e_element = (Element) e_list.item(j);

						/*
						 * Get attributes
						 * <SERVER type="" host="" port="" dbname="" user="db2inst1"/>
						 * */

						if (e_element.getAttribute("dbname") != null
								&& !e_element.getAttribute("dbname").equals("")) {
							ServersObject srvObj = new ServersObject();
							srvObj.setType(e_element.getAttribute("type"));
							srvObj.setHost(e_element.getAttribute("host"));
							srvObj.setPort(e_element.getAttribute("port"));
							srvObj.setDbname(e_element.getAttribute("dbname"));
							srvObj.setUser(e_element.getAttribute("user"));
							srvObjArrLst.add(srvObj);
						} else {
							log.AddLogLine("Invalid PreServers configuration!");
						}

					}
				}
			}
		} catch (Exception e) {
			log.AddLogLine(e.getMessage());
		}
		return srvObjArrLst;
	}
	
	

	public void setPreServers(String type, String tf_server, String tf_port,
			String tf_db, String tf_user) {

		String logerror = null;

		try {
			 RandomAccessFile raf = new RandomAccessFile(Constants.PRESERVER_FILE,"rw");
			 raf.seek(raf.length()-14);
			 raf.writeBytes("\n");
			 raf.writeBytes("<SERVER type=\""+ type + "\" host=\""+ tf_server + "\" port=\"" + tf_port + "\" dbname=\"" + tf_db + "\" user=\"" + tf_user + "\"/>");
			 raf.writeBytes("\n");
			 raf.writeBytes("</PRESERVERS>");
			 raf.close(); 
			
			 

		} catch (IOException e) {

			e.printStackTrace();
		}


	}
}

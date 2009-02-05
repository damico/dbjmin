package db2jmin.pojo.util;

/**
 * This class only defines an object
 * to store information retrieved by
 * PreServers class
 * @author Jose Damico (damico@dcon.com.br)
 * */

public class ServersObject {

	private String type = null;
	private String host = null;
	private String port = null;
	private String dbname = null;
	private String user = null;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}

/*
 * 
 * <SERVER type="db2" host="" port="50000" dbname="" user="db2inst1"/>
 */

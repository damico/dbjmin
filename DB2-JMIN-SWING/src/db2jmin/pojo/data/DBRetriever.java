package db2jmin.pojo.data;

import java.sql.Connection;
import java.util.Map;

public abstract class DBRetriever {
	
	String url = null;
	Connection connection = null;

	public DBRetriever(Connection c) {
		this.setConnection(c);
	}
	
	public DBRetriever(String url) {
		this.setURL(url);
	}
	
	public DBRetriever(Map<String,String> config) {
		
	}
	
	public void setConnection(Connection c) {
		this.connection = c;
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public abstract void setURL(String url);
	
	public void setConfig(Map<String,String> config) {
		
	}
}

package db2jmin.pojo.data;

import java.util.ArrayList;
import java.util.List;

public class Preferences {

	private String host = null; // 0
	private String port = null; // 1
	private String database = null; // 2
	private String user = null; // 3
	private String password = null; // 4
	private String driver = null; // 5
	private String className = null;

	private String schema = null;
	private String table = null;
	private String query = null;

	private boolean modified = false;

	public Preferences() {
		super();
	}

	public void clear() {
		this.host = null;
		this.port = null;
		this.database = null;
		this.user = null;
		this.password = null;
		this.driver = null;

		this.modified = false;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
		this.modified();
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
		this.modified();
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
		this.modified();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
		this.modified();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		this.modified();
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
		this.modified();
	}

	public void setSchema(String schema) {
		this.schema = schema;
		this.modified();
	}

	public String getSchema() {
		return schema;
	}

	public void setTable(String table) {
		this.table = table;
		this.modified();
	}

	public String getTable() {
		return table;
	}

	public void setQuery(String query) {
		this.query = query;
		this.modified();
	}

	public String getQuery() {
		return query;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isModified() {
		return modified;
	}

	public void modified() {
		this.modified = true;
	}

	public List<String> toList() {
		List<String> list = new ArrayList<String>();
		list.add(this.getHost());
		list.add(this.getPort());
		list.add(this.getDatabase());
		list.add(this.getUser());
		list.add(this.getPassword());
		list.add(this.getDriver());
		if (this.getSchema() != null)
			list.add(this.getSchema());
		if (this.getTable() != null)
			list.add(this.getTable());
		if (this.getQuery() != null)
			list.add(this.getQuery());

		return list;
	}

}

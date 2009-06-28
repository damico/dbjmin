package db2jmin.pojo.data;

import java.util.ArrayList;
import java.util.List;

import org.jdamico.dbjmin.crypto.DesEncrypter;

public class Preferences {

	private String remoteDB = null; // 0
	private String portdb = null; // 1
	private String namedb = null; // 2
	private String userdb = null; // 3
	private String pwddb = null; // 4
	private String driver = null; // 5
	private String schema = null;
	private String table = null;
	private String query = null;

	private boolean modified = false;

	public Preferences() {
		super();
	}

	public void clear() {
		this.remoteDB = null;
		this.portdb = null;
		this.namedb = null;
		this.userdb = null;
		this.pwddb = null;
		this.driver = null;

		this.modified = false;
	}

	public String getRemoteDB() {
		return remoteDB;
	}

	public void setRemoteDB(String remoteDB) {
		this.remoteDB = remoteDB;
		this.modified();
	}

	public String getPortdb() {
		return portdb;
	}

	public void setPortdb(String portdb) {
		this.portdb = portdb;
		this.modified();
	}

	public String getNamedb() {
		return namedb;
	}

	public void setNamedb(String namedb) {
		this.namedb = namedb;
		this.modified();
	}

	public String getUserdb() {
		return userdb;
	}

	public void setUserdb(String userdb) {
		this.userdb = userdb;
		this.modified();
	}

	public String getPwddb() {
		return pwddb;
	}

	public void setPwddb(String pwddb) {
		this.pwddb = pwddb;
		this.modified();
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
		this.modified();
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

	public List<String> toList() {
		List<String> list = new ArrayList<String>();
		Preferences p = new DesEncrypter().transformFormData();
		list.add(p.getRemoteDB());
		list.add(p.getPortdb());
		list.add(p.getNamedb());
		list.add(p.getUserdb());
		list.add(p.getPwddb());
		list.add(p.getDriver());
		list.add(p.getSchema());
		list.add(p.getTable());
		list.add(p.getQuery());
		return list;

	}
}

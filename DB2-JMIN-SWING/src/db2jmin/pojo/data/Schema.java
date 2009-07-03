package db2jmin.pojo.data;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class Schema extends DBRetriever {

	String name = null;
	List<Table> tables = null;

	public Schema(Connection c, String name) {
		super(c);
		this.setName(name);
	}

	public Schema(Map<String, String> config, String name) {
		super(config);
		this.setName(name);
	}

	public Schema(String url, String name) {
		super(url);
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Table> getTables() {
		if( tables == null )
			this.tables = retrieveTables();
		return tables;
	}

	private List<Table> retrieveTables() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public void setURL(String url) {
		// TODO Auto-generated method stub
		
	}
}

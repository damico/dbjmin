package db2jmin.pojo.data;

import java.sql.Connection;
import java.util.Map;

public class Table extends DBRetriever {

	public Table(Connection c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	public Table(Map<String, String> config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

	public Table(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setURL(String url) {
		// TODO Auto-generated method stub

	}

}

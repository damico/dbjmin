package db2jmin.pojo.data;

import java.util.ArrayList;
import java.util.List;

public class TablesDO {
	private List<String> tables = null;

	public TablesDO() {
		super();
		setTables(new ArrayList<String>());
	}

	public TablesDO(List<String> tables) {
		super();
		this.setTables(tables);
	}

	public void add(String entry) {
		tables.add(entry);
	}

	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	public List<String> getTables() {
		return tables;
	}
}

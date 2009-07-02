package db2jmin.pojo.data;

import java.util.ArrayList;
import java.util.List;

public class SchemasDO {
	private List<String> schemas = null;

	public SchemasDO() {
		super();
		setSchemas(new ArrayList<String>());
	}

	public SchemasDO(List<String> schema) {
		super();
		this.setSchemas(schema);
	}

	public void add(String entry) {
		schemas.add(entry);
	}

	public void setSchemas(List<String> schema) {
		this.schemas = schema;
	}

	public List<String> getSchemas() {
		return schemas;
	}
}

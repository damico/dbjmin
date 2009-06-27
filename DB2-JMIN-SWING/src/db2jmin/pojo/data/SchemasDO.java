package db2jmin.pojo.data;

import java.util.ArrayList;
import java.util.List;

public class SchemasDO {
	private List<String> schema = null;
	
	public SchemasDO() {
		super();
		setSchema(new ArrayList<String>());
	}
	public SchemasDO(List<String> schema) {
		super();
		this.setSchema(schema);
	}

	public void add(String entry) {
		schema.add(entry);
	}

	public void setSchema(List<String> schema) {
		this.schema = schema;
	}
	
	public List<String> getSchema() {
		return schema;
	}	
}

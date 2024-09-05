package util;

import java.util.HashMap;
import java.util.Map;

/**
 * An enum to represent the different Statuses
 * @author tperez
 *
 */
public enum Status {

	NATURAL("N"),
	TERMINATED("T"),
	RE_APPLIED("A"),
	REVERSED("R"),
	UNDONE("U");

	private String dbText;
	
	private static final Map<String, Status> lookup = new HashMap<>();
	static {
		for(Status status : Status.values()) {
			lookup.put(status.getDbText(), status);
		}
	}
	
	Status(String dbText) {
		this.dbText = dbText;
	}
	
	public String getDbText() {
		return dbText;
	}
	
	public static Status getViaDBText(String dbText) {
		return lookup.get(dbText);
	}
}

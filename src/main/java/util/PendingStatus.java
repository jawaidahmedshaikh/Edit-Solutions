package util;

import java.util.HashMap;
import java.util.Map;

/**
 * An enum to represent the different Pending Statuses
 * @author tperez
 *
 */
public enum PendingStatus {

	HISTORY("H"),
	TERMINATED("T"),
	DELETED("D"),
	PENDING("P");

	private String dbText;
	
	private static final Map<String, PendingStatus> lookup = new HashMap<>();
	static {
		for(PendingStatus pendingStatus : PendingStatus.values()) {
			lookup.put(pendingStatus.getDbText(), pendingStatus);
		}
	}
	
	PendingStatus(String dbText) {
		this.dbText = dbText;
	}
	
	public String getDbText() {
		return dbText;
	}
	
	public static PendingStatus getViaDBText(String dbText) {
		return lookup.get(dbText);
	}
}

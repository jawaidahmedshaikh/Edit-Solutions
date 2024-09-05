package edit.portal.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An enum to represent the different Statuses
 * @author tperez
 *
 */
public enum GroupDeptLocTableColumns {

	COLUMN_DEPT_LOC_CODE("Dept/Loc Code", "DeptLocCode"),
	COLUMN_DEPT_LOC_NAME("Dept/Loc Name", "DeptLocName"),
	COLUMN_EFFECTIVE_DATE("Effective Date", "EffectiveDate"),
	COLUMN_TERMINATION_DATE("Termination Date", "TerminationDate");

	private String displayText;
	private String dbText;
	
	private static final Map<String, GroupDeptLocTableColumns> lookupViaDbText = new HashMap<>();
	static {
		for(GroupDeptLocTableColumns column : GroupDeptLocTableColumns.values()) {
			lookupViaDbText.put(column.getDbText(), column);
		}
	}
	
	private static final Map<String, GroupDeptLocTableColumns> lookupViaDisplayText = new HashMap<>();
	static {
		for(GroupDeptLocTableColumns column : GroupDeptLocTableColumns.values()) {
			lookupViaDisplayText.put(column.getDisplayText(), column);
		}
	}
	
	GroupDeptLocTableColumns(String displayText, String dbText) {
		this.displayText = displayText;
		this.dbText = dbText;
	}
	
	public String getDbText() {
		return dbText;
	}

	public String getDisplayText() {
		return displayText;
	}

	public static GroupDeptLocTableColumns getViaDBText(String dbText) {
		return lookupViaDbText.get(dbText);
	}
	
	public static GroupDeptLocTableColumns getViaDisplayText(String displayText) {
		return lookupViaDisplayText.get(displayText);
	}

	public static String[] getColumnDisplayNames() {
		List<String> displayNamesList = new ArrayList<>();
		for (GroupDeptLocTableColumns column : GroupDeptLocTableColumns.values()) {
			displayNamesList.add(column.getDisplayText());
		}
		return displayNamesList.toArray(new String[displayNamesList.size()]);
	}
}

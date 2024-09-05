package edit.services.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 31, 2003
 * Time: 10:39:11 AM
 * To change this template use Options | File Templates.
 */
public class DBRecord
{
    private final Map columnValues = new HashMap();

    public void add(DBRecordKey dbRecordKey, String colValue)
    {
        colValue = (colValue == null)?"NULL":colValue;

        columnValues.put(dbRecordKey, colValue);
    }

    public DBRecordKey[] getKeys()
    {
        Set dbRecordKeys = columnValues.keySet();

        return (DBRecordKey[]) dbRecordKeys.toArray(new DBRecordKey[dbRecordKeys.size()]);
    }

    public String getColumnValue(DBRecordKey dbRecordKey)
    {
        return columnValues.get(dbRecordKey).toString();
    }

    public boolean isEmpty()
    {
        return columnValues.isEmpty();
    }
}

package edit.services.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 31, 2003
 * Time: 11:14:28 AM
 * To change this template use Options | File Templates.
 */
public class DBRecordSet
{
    private List _dbRecords = new ArrayList();

    public void addDBRecord(DBRecord dbRecord)
    {
        _dbRecords.add(dbRecord);
    }

    public void addDBRecords(DBRecord[] dbRecords)
    {
        _dbRecords.addAll(Arrays.asList(dbRecords));
    }

    public DBRecord[] getDBRecords()
    {
        return (DBRecord[]) _dbRecords.toArray(new DBRecord[_dbRecords.size()]);
    }

    public DBRecord getDBRecord(int index)
    {
        return (DBRecord) _dbRecords.get(index);
    }

    public boolean isEmpty()
    {
        return _dbRecords.isEmpty();
    }

    public int size()
    {
        return _dbRecords.size();
    }
}

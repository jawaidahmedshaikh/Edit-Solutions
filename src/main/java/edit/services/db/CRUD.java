/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 8, 2002
 * Time: 11:56:49 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.services.db;

import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.config.ServicesConfig;
import fission.utility.*;

import java.io.*;
import java.lang.reflect.*;
import java.lang.reflect.Array;
import java.math.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class CRUD implements Cloneable
{
    public static final int RECURSE_TOP_DOWN = 0;
    public static final int RECURSE_BOTTOM_UP = 1;
    private static final int DELETE_FIRST = 0;
    private static final int DELETE_NEXT = 1;
    protected static final String VO_PACKAGE_NAME = "edit.common.vo";
    private static long nextKey;

    // Establish a base primary key.
    static
    {
        nextKey = System.currentTimeMillis();
    }

    private int deletionRecursionLevel = 0;
    private int creationRecursionLevel = 0;
    private int retrievalRecursionLevel = 0;
    private String poolName;
    private Map voObjectModel;

    private boolean debugDeletionsOutFile = true;
    private String deleteFile;

    //    private static Map voClasses;
    //
    private List deletionChain;
    private boolean restoringRealTime;
    private Connection crudConn = null;

    /**********************************
     *    Constructors                *
     **********************************/
    CRUD()
    {
        this.voObjectModel = new HashMap();
        this.deletionChain = new ArrayList();
    	deleteFile = getRecordDeletionFile();

        //        this.voClasses = new HashMap();
    }

    /**
     * Returns this instance to its pool
     */
    public void close()
    {
        CRUDFactory.getSingleton().returnCRUD(this);
    }

    /**
     * Returns the associated Connection's pool name
     * @return
     */
    public String getPoolName()
    {
        return this.poolName;
    }

    /**
     * Associates this instance with an available Connection
     * @param poolName
     */
    protected void initState(String poolName)
    {
        this.poolName = poolName;

        crudConn = ConnectionFactory.getSingleton().getConnection(poolName);
    }

    /**
     * Clears the state and releases the associated Connection
     */
    protected void resetState()
    {
        clearState();

        try
        {
            if (crudConn != null) {
            crudConn.close();

            crudConn = null;
        }
        }
        catch (SQLException e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
    }

    /**
     * Clears accumulated state values
     */
    private void clearState()
    {
        deletionRecursionLevel = 0;
        creationRecursionLevel = 0;
        retrievalRecursionLevel = 0;

        clearVOObjectModel();
        clearDeletionChain();

        //        clearChangeHistory();
    }

    /**********************************
     *    Public Methods             *
     **********************************/
    public static synchronized long getNextAvailableKey()
    {
        return nextKey++;
    }

    public long[] getPrimaryKeysForGeneralQuery(String sql, long thePK)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;

        try
        {
            ps = crudConn.prepareStatement(sql);

            ps.setLong(1, thePK);

            List keys = new ArrayList();

            rs = ps.executeQuery();

            while (rs.next())
            {
                keys.add(new Long(rs.getLong(1)));
            }

            long[] primaryKeys = Util.convertLongToPrim((Long[]) keys.toArray(new Long[keys.size()]));

            if (primaryKeys.length == 0)
            {
                return null;
            }
            else
            {
                return primaryKeys;
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }

                if (ps != null)
                {
                    ps.close();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use Options | File Templates.'

                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Maps a VO Object to the database as either an insert or update.
     *
     * Whether to insert or update is determined by the primary key of the VO.
     * If the primary key = 0, an insert is assumed, otherwise an update is assumed.
     * If a transaction is in progress, the current transactional thread is used,
     * otherwise a new transaction is started.
     *
     * @param   targetVO The VO to insert or update
     * @return  The newly created or updated VO
     */
    public long createOrUpdateVOInDB(Object targetVO)
    {
        long pk = createOrUpdateVOInDB(targetVO, null);

        return pk;
    }

    private long createOrUpdateVOInDB(Object targetVO, Object parentVO)
    {
        if (shouldCreateOrUpdateVO(targetVO))
        {
            PreparedStatement ps1 = null;

            try
            {
                ps1 = getPreparedStatementForVOCreateOrUpdate(targetVO);

                ps1.executeUpdate();

                ((VOObject) targetVO).setVoChanged(false);
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
            finally
            {
                try
                {
                    if (ps1 != null)
                    {
                        ps1.close();
                    }
                }
                catch (SQLException e)
                {
                    System.out.println(e);

                    e.printStackTrace(); //To change body of catch statement use Options | File Templates.

                    throw new RuntimeException(e);
                }
            }
        }

        return getPKValue(targetVO);
    }

    private boolean shouldCreateOrUpdateVO(Object voObject)
    {
        long pkVal = getPKValue(voObject);

        boolean shouldCreateOrUpdate = false;

        if ((pkVal == 0) || restoringRealTime)
        {
            shouldCreateOrUpdate = true;
        }
        else
        {
            //  No longer compare objects to the database to determine if they changed.  Just check the value
            //  of voChanged - if true, the VO has been modified
            shouldCreateOrUpdate = ((VOObject) voObject).getVoChanged();
        }

        return shouldCreateOrUpdate;
    }

    /**
     * Retrieves a non-composite VO from the DB
     *
     * If a transaction is in progress, the current transactional
     * thread is used, otherwise a new transaction is started.
     *
     * @param targetVOClass The target VO to build
     * @param primaryKey The primary key of the target VO
     * @return The non-composite VO
     * @exception Exception General SQL Exception
     */
    public Object retrieveVOFromDB(Class targetVOClass, long primaryKey)
    {
        //        if (primaryKey <= 0)
        //        {
        //            try
        //            {
        //                throw new RuntimeException("Primary Keys Can Not Be <= 0 - Invalid Lookup For [class:" + targetVOClass.getName() + "][pk:" + primaryKey + "]");
        //            }
        //            catch (RuntimeException e)
        //            {
        //                System.out.println(e);
        //
        //                e.printStackTrace();
        //
        //                throw e;
        //            }
        //        }
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            VOClass targetVOMD = VOClass.getVOClassMetaData(targetVOClass);

            Object targetVO = null;

            String sql = getSQLForVORetrieval(targetVOClass, primaryKey);

            ps = crudConn.prepareStatement(sql);

            ps.setLong(1, primaryKey);

            rs = ps.executeQuery();

            if (rs.next())
            {
                targetVO = populateVOFromResultSetRow(rs, targetVOMD);
            }

            if (targetVO != null)
            {
                ((VOObject) targetVO).setVoChanged(false);

                return targetVO;
            }
            else
            {
                return null;
            }
        }

        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }

                if (ps != null)
                {
                    ps.close();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Maps a ResultSet row to a value object
     * @param rs
     * @param targetVOClassMD
     * @return
     */
    public static Object populateVOFromResultSetRow(ResultSet rs, VOClass targetVOClassMD)
    {
        Object targetVO = null;
        String tableName = null;
        String colName = null;

        try
        {
            targetVO = targetVOClassMD.getVOClass().newInstance();

            tableName = targetVOClassMD.getTableName();

            DBDatabase dbDatabase = DBDatabase.getDBDatabaseForTable(tableName);
            DBTable dbTable = dbDatabase.getDBTable(tableName);

            DBColumn[] dbColumns = dbTable.getDBColumns();

            int colCount = dbColumns.length;

            for (int i = 0; i < colCount; i++)
            {
                colName = dbColumns[i].getColumnName();

                Object colVal = getObject(rs, dbColumns[i], targetVOClassMD);

                Method simpleSetter = targetVOClassMD.getSimpleSetter(colName).getMethod();

                if (colVal != null)
                {
                    simpleSetter.invoke(targetVO, new Object[] { colVal });
                }

                //when BigDecimal type and null set the value to zero in the VO
                else
                {
                    Class paramType = simpleSetter.getParameterTypes()[0];

                    if (paramType == BigDecimal.class)
                    {
                        colVal = new BigDecimal("0.0");
                        simpleSetter.invoke(targetVO, new Object[] { colVal });
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(
                "\n******************************* !!! VO/Database Mismatch !!! *******************************");
            System.out.println("voName:" + targetVO.getClass().getName() + " tableName:" + tableName + " colName:" +
                colName);
            System.out.println(
                "********************************************************************************************\n");

            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        return targetVO;
    }

    /**
     * Gets the object from the ResultSet as the appropriate java type.  The appropriate java type is determined by
     * the VO's get method's return type.
     *
     * @param rs                    The ResultSet containing the column value
     * @param targetVOClassMD       The VO containing the column
     *
     * @return  object containing the value from the resultSet and typed to the appropriate java type
     *
     * @throws SQLException
     */
    private static Object getObject(ResultSet rs, DBColumn dbColumn, VOClass targetVOClassMD)
        throws SQLException
    {
        Object columnValue = null;

        String columnName = dbColumn.getColumnName();

        Class columnType = targetVOClassMD.getSimpleGetter(columnName).getTargetFieldType();

        if (columnType == long.class)
        {
            columnValue = new Long(rs.getLong(columnName));
        }
        else if (columnType == int.class)
        {
            columnValue = new Integer(rs.getInt(columnName));
        }
        else if (columnType == float.class)
        {
            columnValue = new Float(rs.getFloat(columnName));
        }
        else if (columnType == double.class)
        {
            columnValue = new Double(rs.getDouble(columnName));
        }
        else if (columnType == BigDecimal.class)
        {
            columnValue = rs.getBigDecimal(columnName);
        }
        else if (columnType == String.class)
        {
            //  The column's java type is a String, check to see if the database type is a date or date and time
            if (dbColumn.isDateOrDateTime())
            {
                if (dbColumn.isDateTime())
                {
                    //  Column contains a date and time, read it in as a timestamp and convert to our internal format
                    columnValue = DBUtil.readAndConvertTimestamp(rs, columnName);
                }
                else
                {
                    //  It is just a date, read it in as such and convert to our internal format
                    columnValue = DBUtil.readAndConvertDate(rs, columnName);
                }
            }
            else
            {
                columnValue = rs.getString(columnName);

                if (columnValue != null)
                {
                    columnValue = columnValue.toString().trim();
                }
            }
        }

        return columnValue;
    }

    /**
     * Deletes a VO from the DB non-recursively.
     *
     * If a transaction is in progress, the current transactional
     * thread is used, otherwise a new transaction is started.
     *
     * @param voClass The target VO to delete
     * @param primaryKey The primary key of the target VO
     * @return The number of rows affected
     * @exception Exception For General SQL exceptions
     */
    public int deleteVOFromDB(Class voClass, long primaryKey)
    {
        int rowsAffected = 0;

        PreparedStatement ps = null;

        try
        {
            if (primaryKey != 0)
            {
                String sql = getSQLForVODelete(voClass, primaryKey);

                ps = crudConn.prepareStatement(sql);

                ps.setLong(1, primaryKey);

                rowsAffected = ps.executeUpdate();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (ps != null)
                {
                    ps.close();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
        }

        return rowsAffected;
    }

    public int deleteVOFromDBRecursively(Class parentVOClass, long primaryKey) throws EDITDeleteException
    {
        int numDeleted = deleteVOFromDBRecursively(parentVOClass, primaryKey, null);

        return numDeleted;
    }

    /**
     * Deletes a target VO and all of its children (recursively)
     *
     * This is a two-phase delete. Phase1 builds a deletion tree (non-transactional).
     * Phase2 then deletes all records transactionally.
     *
     * @param parentVOClass The target VO at the top of the recursion process.
     * @param primaryKey The target primary key
     * @return The number of rows affected
     * @throws EDITDeleteException 
     * @exception Exception General SQL Exception
     */
    public int deleteVOFromDBRecursively(Class parentVOClass, long primaryKey, List voClassExcusionList) throws EDITDeleteException
    {
        VOClass parentVOClassMD = VOClass.getVOClassMetaData(parentVOClass);

        deletionRecursionLevel++;

        int numVOsDeleted = 0;

        if (deletionRecursionLevel == 1)
        {
            clearDeletionChain();

            //            // special case - does this root VO have an entry in a reflexive association table? If so,
            //            // delete its assoc with any parent VOs.
            //            if (parentVOClassMD.isReflexiveVO())
            //            {
            //                long[] relationshipPKs = getPKsForRelationshipTableForRootChildVO(parentVOClass, primaryKey);
            //
            //                if (relationshipPKs != null)
            //                {
            //                    Class relationshipVOClass = getVOClassFromTableName(getRelationshipTableName(parentVOClass));
            //
            //                    for (int i = 0; i < relationshipPKs.length; i++)
            //                    {
            //
            //                        addToDeletionChain(relationshipVOClass, relationshipPKs[i], DELETE_NEXT);
            //                    }
            //                }
            //            }
        }

        // Get all child classes
        Class[] childVOClasses = parentVOClassMD.getChildVOClasses(voClassExcusionList);

        if (childVOClasses != null)
        {
            for (int i = 0; i < childVOClasses.length; i++)
            {
                long[] childVOPKs = getChildVOPKs(parentVOClass, childVOClasses[i], primaryKey);

                if (childVOPKs != null)
                {
                    for (int j = 0; j < childVOPKs.length; j++)
                    {
                        //                        // Special case - reflexive relationship
                        //                        if (parentVOClass.equals(childVOClasses[i]))
                        //                        {
                        //                            Class relationshipVOClass = getVOClassFromTableName(getRelationshipTableName(parentVOClass));
                        //
                        //                            long[] relationshipVOPKs = getPKsForRelationshipTable(primaryKey, childVOPKs[j], childVOClasses[i]);
                        //
                        //                            if (relationshipVOPKs != null)
                        //                            {
                        //                                for (int k = 0; k < relationshipVOPKs.length; k++)
                        //                                {
                        //                                    addToDeletionChain(relationshipVOClass, relationshipVOPKs[k], DELETE_FIRST);
                        //                                }
                        //                            }
                        //                        }
                        deleteVOFromDBRecursively(childVOClasses[i], childVOPKs[j], voClassExcusionList);
                    }
                }
            }
        }

        addToDeletionChain(parentVOClass, primaryKey, DELETE_NEXT);

        deletionRecursionLevel--;

        if (deletionRecursionLevel == 0)
        {
            numVOsDeleted = processDeletionChain();
        }

        return numVOsDeleted;
    }

    private void synchronizeVOModels(Object parentVO, List voExclusionList)
    {
        long parentPK = getPKValue(parentVO);

        // This loads the dB's VOModel into "voObjectModel" until the next create or retrieve recursively
        //        retrieveVOFromDBRecursively(parentVO.getClass(), pkVal, voExclusionList);
        final List userVOPKChain = new ArrayList();
        final List justDeletedDBVOKeys = new ArrayList();

        // Flatten the userVOModel
        recurseVOObjectModel(parentVO, null, CRUD.RECURSE_TOP_DOWN,
            new RecursionListener()
            {
                public void currentNode(Object currentNode, Object parentNode, RecursionContext recursionContext)
                {
                    long currentPK = getPKValue(currentNode);

                    String key = null;

                    if (currentPK != 0)
                    {
                        key = CRUD.this.getObjectKey(currentNode.getClass(), getPKValue(currentNode));

                        if (!userVOPKChain.contains(key))
                        {
                            userVOPKChain.add(key);
                        }
                    }
                }
            }, null, voExclusionList);

        //        Iterator dbModelKeys = voObjectModel.keySet().iterator();
        List dbPKChain = new ArrayList();
        buildPKChainFromDBRecursively(parentVO.getClass(), parentPK, dbPKChain, voExclusionList);

        //        Iterator dbModelKeys = voObjectModel.keySet().iterator();
        Iterator dbModelKeys = dbPKChain.iterator();

        try
        {
            while (dbModelKeys.hasNext())
            {
                String dbModelKey = dbModelKeys.next().toString();

                // If the models are out-of-sync, delete the extraneous VO from the DB
                if (!userVOPKChain.contains(dbModelKey) && !justDeletedDBVOKeys.contains(dbModelKey))
                {
                    //                Object dbModelVO = voObjectModel.get(dbModelKey);
                    String[] keyTokens = Util.fastTokenizer(dbModelKey, "_");

                    deleteVOFromDBRecursively(Class.forName(keyTokens[0]), Long.parseLong(keyTokens[1]));

                    // Maintain the just deleted VO Keys so that we don't try to delete them again.
                    Iterator deletionChainKeys = deletionChain.iterator();

                    while (deletionChainKeys.hasNext())
                    {
                        String deletionChainKey = deletionChainKeys.next().toString();

                        justDeletedDBVOKeys.add(deletionChainKey);
                    }
                }
            }

            clearVOObjectModel();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    public long createOrUpdateVOInDBRecursivelyFromRestore(Object parentVO)
    {
        return 0;

        //        int i = 0;
        //
        //            restoringRealTime = true;
        //
        //            List trxPayeeOverrideVector = null;
        //            TransactionsVO[] transactionsVOs = ((SegmentVO) parentVO).getTransactionsVO();
        //            ClientRelationshipVO[] clientRelVOs = ((SegmentVO) parentVO).getClientRelationshipVO();
        //            for (i = 0; i < transactionsVOs.length; i++)
        //            {
        //                TrxPayeeOverrideVO[] trxPayeeOverrideVOs = transactionsVOs[i].getTrxPayeeOverrideVO();
        //                if (trxPayeeOverrideVOs != null)
        //                {
        //                    if (trxPayeeOverrideVector == null)
        //                    {
        //                        trxPayeeOverrideVector = new ArrayList();
        //                    }
        //
        //                    for (int j = 0; j < trxPayeeOverrideVOs.length; j++)
        //                    {
        //                        trxPayeeOverrideVector.add(trxPayeeOverrideVOs[j]);
        //                    }
        //                }
        //
        //                transactionsVOs[i].removeAllTrxPayeeOverrideVO();
        //            }
        //
        //            for (i = 0; i < clientRelVOs.length; i++)
        //            {
        //                PayeeVO[] payeeVOs = clientRelVOs[i].getPayeeVO();
        //                if (payeeVOs != null && payeeVOs.length > 0)
        //                {
        //                    payeeVOs[0].removeAllTrxPayeeOverrideVO();
        //                }
        //            }
        //
        //            long parentPK = createOrUpdateVOInDBRecursively(parentVO);
        //
        //            if (trxPayeeOverrideVector != null)
        //            {
        //                for (i = 0; i < trxPayeeOverrideVector.size(); i++)
        //                {
        //                    TrxPayeeOverrideVO trxPayeeOverrideVO = (TrxPayeeOverrideVO)
        //                            trxPayeeOverrideVector.get(i);
        //
        //                    createOrUpdateVOInDBRecursively(trxPayeeOverrideVO);
        //                }
        //            }
        //
        //            restoringRealTime = false;
        //
        //            return parentPK;
    }

    public long createOrUpdateVOInDBRecursively(Object targetVO) throws EDITDeleteException
    {
        long parentPK = createOrUpdateVOInDBRecursively(targetVO, null, null, true);

        return parentPK;
    }

    public long createOrUpdateVOInDBRecursively(Object targetVO, List voExclusionList) throws EDITDeleteException
    {
        return createOrUpdateVOInDBRecursively(targetVO, null, voExclusionList, true);
    }

    public long createOrUpdateVOInDBRecursively(Object targetVO, boolean synchronizeWithDB) throws EDITDeleteException
    {
        return createOrUpdateVOInDBRecursively(targetVO, null, null, synchronizeWithDB);
    }

    public long createOrUpdateVOInDBRecursively(Object targetVO, List voExclusionList, boolean synchronizeWithDB) throws EDITDeleteException
    {
        return createOrUpdateVOInDBRecursively(targetVO, null, voExclusionList, synchronizeWithDB);
    }

    private long createOrUpdateVOInDBRecursively(Object targetVO, Object parentVO, List voExclusionList,
        boolean synchronizeWithDB) throws EDITDeleteException
    {
        VOClass parentVOClassMD = VOClass.getVOClassMetaData(targetVO.getClass());

        creationRecursionLevel++;

        if ((creationRecursionLevel == 1) && !restoringRealTime)
        {
            // Don't bother syncrhonizing for a new VO Object
            if ((getPKValue(targetVO) != 0) && synchronizeWithDB)
            {
                synchronizeVOModels(targetVO, voExclusionList);
            }
        }

        long targetPKVal = getPKValue(targetVO);

        // Save or Delete targetVO
        if (((VOObject) targetVO).getVoShouldBeDeleted())
        {
            // Delete and get out of method since children are deleted from DB and no longer valid in VO Model.
            deleteVOFromDBRecursively(targetVO.getClass(), targetPKVal);

            return 0;
        }

        createOrUpdateVOInDB(targetVO, parentVO);

        if (targetPKVal == 0) // If the VO was just created, we need the new PK.
        {
            targetPKVal = getPKValue(targetVO);
        }

        addVOToObjectModel(targetVO);

        Class[] childVOClasses = parentVOClassMD.getChildVOClasses(voExclusionList);

        try
        {
            if (childVOClasses != null)
            {
                for (int i = 0; i < childVOClasses.length; i++)
                {
                    Object[] childVOs = getChildVOsFromParentVO(targetVO, childVOClasses[i]);

                    if (childVOs != null)
                    {
                        for (int j = 0; j < childVOs.length; j++)
                        {
                            // Make sure that the same childVO is obtained so that
                            // all FKs are set.
                            Object childVO = getVOFromObjectModel(childVOs[j].getClass(), getPKValue(childVOs[j]));

                            if (childVO == null)
                            {
                                childVO = childVOs[j];
                            }

                            VOClass childVOClassMD = VOClass.getVOClassMetaData(childVOClasses[i]);

                            //set the foreign key on the child object (if necessary)
                            Method fkGetter = childVOClassMD.getFKGetter(parentVOClassMD.getTableName()).getMethod();

                            if (((Long) fkGetter.invoke(childVO, null)).longValue() == 0)
                            {
                                Method fkSetter = childVOClassMD.getFKSetter(parentVOClassMD.getTableName()).getMethod();

                                fkSetter.invoke(childVO, new Object[] { new Long(targetPKVal) });
                            }

                            createOrUpdateVOInDBRecursively(childVO, targetVO, voExclusionList, synchronizeWithDB);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        creationRecursionLevel--;

        return getPKValue(targetVO);
    }

    public Object[] retrieveVOFromDB(Class childVOClass, Class parentVOClass, long parentPK)
    {
        List results = new ArrayList();

        VOClass parentVOClassMD = VOClass.getVOClassMetaData(parentVOClass);
        VOClass childVOClassMD = VOClass.getVOClassMetaData(childVOClass);

        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            DBTable sqlDBTable = DBTable.getDBTableForTable(childVOClassMD.getTableName());

            String sqlTableName = sqlDBTable.getFullyQualifiedTableName();

            String sqlColumnName = sqlDBTable.getDBColumn(VOClass.getFKColumnName(parentVOClassMD.getTableName()))
                                             .getFullyQualifiedColumnName();

            String sql = "SELECT * FROM " + sqlTableName + " WHERE " + sqlColumnName + " = ?";

            ps = crudConn.prepareStatement(sql);

            ps.setLong(1, parentPK);

            rs = ps.executeQuery();

            while (rs.next())
            {
                results.add(populateVOFromResultSetRow(rs, childVOClassMD));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }

                if (ps != null)
                {
                    ps.close();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
        }

        if (results.size() == 0)
        {
            return null;
        }
        else
        {
            Array.newInstance(childVOClassMD.getVOClass(), results.size());

            return results.toArray((Object[]) Array.newInstance(childVOClassMD.getVOClass(), results.size()));
        }
    }

    public void retrieveVOFromDBRecursively(Object parentVO, List voList, boolean inclusive)
    {
        VOClass parentVOClassMD = VOClass.getVOClassMetaData(parentVO.getClass());

        retrievalRecursionLevel++;

        if (retrievalRecursionLevel == 1)
        {
            clearVOObjectModel();
        }

        // To keep the voModel as "by reference" as opposed to "by value", we need
        // to point to the previous voObject if it exists.
        Object priorVOObject = null;

        if ((priorVOObject = getVOFromObjectModel(parentVO.getClass(), getPKValue(parentVO))) == null)
        {
            addVOToObjectModel(parentVO);
        }
        else
        {
            parentVO = priorVOObject;
        }

        // Add child VOs to current baseVO
        Class[] childVOClasses = parentVOClassMD.getChildVOClasses(null);

        try
        {
            if (childVOClasses != null)
            {
                for (int i = 0; i < childVOClasses.length; i++)
                {
                    boolean shouldGetChild = true;

                    if (inclusive) // voList is a "VOInclusionList"
                    {
                        if ((voList != null) && voList.contains(childVOClasses[i]))
                        {
                            shouldGetChild = true;
                        }
                        else
                        {
                            shouldGetChild = false;
                        }
                    }
                    else // voList is a "VOExclusionList"
                    {
                        if (voList == null)
                        {
                            shouldGetChild = true; // Assumes that the childVO is sought.
                        }
                        else if (voList.contains(childVOClasses[i]))
                        {
                            shouldGetChild = false;
                        }
                        else
                        {
                            shouldGetChild = true;
                        }
                    }

                    if (shouldGetChild)
                    {
                        Object[] childVOs = retrieveVOFromDB(childVOClasses[i], parentVO.getClass(),
                                getPKValue(parentVO));

                        if (childVOs != null)
                        {
                            VOClass childVOClassMD = VOClass.getVOClassMetaData(childVOClasses[i]);
                            Method voSetter = parentVOClassMD.getVOSetter(childVOClassMD.getTableName()).getMethod();

                            voSetter.invoke(parentVO, new Object[] { childVOs });

                            for (int j = 0; j < childVOs.length; j++)
                            {
                                ((VOObject) childVOs[j]).setParentVO(parentVO.getClass(), (VOObject) parentVO);

                                retrieveVOFromDBRecursively(childVOs[j], voList, inclusive);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        retrievalRecursionLevel--;
    }

    public void buildPKChainFromDBRecursively(Class parentVOClass, long parentPK, List pkChain,
        List voClassExclusionList)
    {
        VOClass parentVOClassMD = VOClass.getVOClassMetaData(parentVOClass);

        String key = getObjectKey(parentVOClass, parentPK);

        if (!pkChain.contains(key))
        {
            pkChain.add(key);
        }

        if (parentPK != 0)
        {
            // Add child VOs to current baseVO
            Class[] childVOClasses = parentVOClassMD.getChildVOClasses(voClassExclusionList);

            if (childVOClasses != null)
            {
                for (int i = 0; i < childVOClasses.length; i++)
                {
                    long[] childVOPKs = getChildVOPKs(parentVOClass, childVOClasses[i], parentPK);

                    if (childVOPKs != null)
                    {
                        for (int j = 0; j < childVOPKs.length; j++)
                        {
                            buildPKChainFromDBRecursively(childVOClasses[i], childVOPKs[j], pkChain,
                                voClassExclusionList);
                        }
                    }
                }
            }
        }
    }

    /**
     * A version of Object's equals that performs a deep comparison between two value objects
     * The value objects must be class type and node count equivalent.
     *
     * @param objToTest
     * @param baseObj
     * @param includeChildVOs
     * @return true if the two VO's are [deeply] value equivalent
     * @exception Exception if objects vary by more than value content.
     */
    public static boolean valueObjectsAreEqual(Object objToTest, Object baseObj, boolean includeChildVOs)
    {
        if ((objToTest == null) || (baseObj == null))
        {
            return false;
        }

        // Perform a shallow equals, otherwise use Castor's deep equals
        if (!includeChildVOs)
        {
            VOClass voClassMD = VOClass.getVOClassMetaData(objToTest.getClass());

            VOMethod[] getters = voClassMD.getSimpleAndCTAndFKAndPKGetters(); // or valueObject2's

            try
            {
                for (int i = 0; i < getters.length; i++)
                {
                    Object val1 = getters[i].getMethod().invoke(objToTest, null);
                    Object val2 = getters[i].getMethod().invoke(baseObj, null);

                    if ((val1 != null) && (val2 != null))
                    {
                        String val1Str = val1.toString();
                        String val2Str = val2.toString();

                        if (!val1Str.equals(val2Str))
                        {
                            return false;
                        }
                    }

                    if ((val1 == null) && (val2 != null))
                    {
                        return false;
                    }

                    if ((val1 != null) && (val2 == null))
                    {
                        return false;
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }

            return true;
        }
        else
        {
            return objToTest.equals(baseObj);
        }
    }

    /**********************************
     *    Private Methods             *
     **********************************/
    /**
     * Builds the PreparedStatement for a single VO deletion
     *
     * @param voClass The target VO class to delete
     * @param primaryKey The primary key of the target VO class
     * @return The PreparedStatement
     */
    private String getSQLForVODelete(Class voClass, long primaryKey)
    {
        VOClass voClassMD = VOClass.getVOClassMetaData(voClass);

        String tableName = voClassMD.getTableName();
        String pkColName = VOClass.getPKColumnName(tableName);

        DBTable sqlDBTable = DBTable.getDBTableForTable(tableName);

        String sqlTableName = sqlDBTable.getFullyQualifiedTableName();

        String sqlColName = sqlDBTable.getDBColumn(pkColName).getFullyQualifiedColumnName();

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(sqlTableName);
        sql.append(" WHERE ").append(sqlColName).append(" = ?");

        return sql.toString();
    }

    /**
     * Builds the PreparedStatement to find all child primary keys for the current parent VO class
     *
     * @param parentVOClass The VO class whose children are sought
     * @param childVOClass The child VO whose primary keys are sought
     * @param parentPK The parent VO primary key
     * @return The PreparedStatement
     */
    private long[] getChildPKs(Class parentVOClass, Class childVOClass, long parentPK)
    {
        VOClass parentVOClassMD = VOClass.getVOClassMetaData(parentVOClass);

        VOClass childVOClassMD = VOClass.getVOClassMetaData(childVOClass);
        String childTableName = childVOClassMD.getTableName();
        String fkColName = VOClass.getFKColumnName(parentVOClassMD.getTableName());
        String pkColName = VOClass.getPKColumnName(childTableName);

        DBTable sqlChildDBTable = DBTable.getDBTableForTable(childTableName);

        String sqlChildTableName = sqlChildDBTable.getFullyQualifiedTableName();

        String sqlPKColName = sqlChildDBTable.getDBColumn(pkColName).getFullyQualifiedColumnName();
        String sqlFKColName = sqlChildDBTable.getDBColumn(fkColName).getFullyQualifiedColumnName();

        String sql = "SELECT " + sqlPKColName + " FROM " + sqlChildTableName + " WHERE " + sqlFKColName + " = ?";

        sql = getSQLForPreparedStatement(sqlPKColName, sqlChildTableName, sqlFKColName);

        return getPrimaryKeysForGeneralQuery(sql, parentPK);
    }

    private String getSQLForVORetrieval(Class voClass, long primaryKey)
    {
        VOClass voClassMD = VOClass.getVOClassMetaData(voClass);

        String tableName = voClassMD.getTableName();
        String pkColName = VOClass.getPKColumnName(voClassMD.getTableName());

        DBTable sqlDBTable = DBTable.getDBTableForTable(tableName);

        String sqlTableName = sqlDBTable.getFullyQualifiedTableName();

        String sqlPKColName = sqlDBTable.getDBColumn(pkColName).getFullyQualifiedColumnName();

        String sql = getSQLForPreparedStatement("*", sqlTableName, sqlPKColName);

        return sql;
    }

    //    private long[] getPKsForRelationshipTable(long parentPK, long childPK, Class childVOClass)
    //    {
    //        String relationshipTableName = getRelationshipTableName(childVOClass);
    //
    //        VOClass childVOClassMD = VOClass.getVOClassMetaData(childVOClass);
    //
    //        String parentChildTableName = childVOClassMD.getTableName();
    //
    //        long childRelationshipPK = childPK;
    //
    //        String childFKColName = getChildFKColumnName(parentChildTableName);
    //        String parentFKColName = getParentFKColumnName(parentChildTableName);
    //        String relationshipPKColName = getRelationshipPKColumnName(relationshipTableName);
    //
    //        StringBuilder sql = new StringBuilder();
    //        sql.append("SELECT ").append(relationshipPKColName).append(" FROM ").append(relationshipTableName);
    //        sql.append(" WHERE ").append(parentFKColName).append(" = ").append(parentPK);
    //        sql.append(" AND ").append(childFKColName).append(" = ").append(childRelationshipPK);
    //
    //        return getPrimaryKeysForGeneralQuery(sql.toString());
    //    }
    //    private long[] getPKsForRelationshipTableForRootChildVO(Class rootChildVOClass, long rootChildPK)
    //    {
    //        String relationshipTableName = getRelationshipTableName(rootChildVOClass);
    //
    //        VOClass rootVOClassMD = VOClass.getVOClassMetaData(rootChildVOClass);
    //
    //        String parentChildTableName = rootVOClassMD.getTableName();
    //
    //        String rootChildFKColName = getChildFKColumnName(parentChildTableName);
    //        String relationshipPKColName = getRelationshipPKColumnName(relationshipTableName);
    //
    //        StringBuilder sql = new StringBuilder();
    //        sql.append("SELECT ").append(relationshipPKColName).append(" FROM ").append(relationshipTableName);
    //        sql.append(" WHERE ").append(rootChildFKColName).append(" = ").append(rootChildPK);
    //
    //        return getPrimaryKeysForGeneralQuery(sql.toString());
    //    }
    //    private PreparedStatement getPreparedStatementForInsertingIntoRelationshipTable(Object childVO, Object parentVO)
    //
    //    {
    //        long childRelationshipPK = getPKValue(childVO);
    //        long parentRelationshipPK = getPKValue(parentVO);
    //
    //        long[] segmentRelationshipPKs = getPKsForRelationshipTable(parentRelationshipPK, childRelationshipPK, childVO.getClass());
    //
    //        if (segmentRelationshipPKs == null)
    //        {
    //            StringBuilder sql = new StringBuilder();
    //
    //            String relationshipTableName = getRelationshipTableName(childVO.getClass());
    //
    //            long nextAvailableKey = getNextAvailableKey();
    //
    //            sql.append("INSERT INTO ").append(relationshipTableName).append(" VALUES (?, ?, ?)");
    //
    //            PreparedStatement ps = crudConn.prepareStatement(sql.toString());
    //
    //            ps.setLong(1, nextAvailableKey);
    //            ps.setLong(2, parentRelationshipPK);
    //            ps.setLong(3, childRelationshipPK);
    //
    //            return ps;
    //        }
    //        else
    //        {
    //            return null;
    //        }
    //    }

    /**
     * Builds the PreparedStatement for creating or updating a VO in the database
     *
     * @param voObject The VO to be created or updated in the database
     * @return The PreparedStatement
     * @exception Exception
     */
    private PreparedStatement getPreparedStatementForVOCreateOrUpdate(Object voObject)
    {
        VOClass voClassMD = VOClass.getVOClassMetaData(voObject.getClass());

        String tableName = voClassMD.getTableName();

        DBTable sqlDBTable = DBTable.getDBTableForTable(tableName);

        String sqlTableName = sqlDBTable.getFullyQualifiedTableName();

        long pkVal = getPKValue(voObject);

        DBDatabase dbDatabase = DBDatabase.getDBDatabaseForTable(tableName);
        DBTable dbTable = dbDatabase.getDBTable(tableName);
        DBColumn[] dbColumns = dbTable.getDBColumns();

        StringBuilder sql = new StringBuilder();

        if ((pkVal == 0) || restoringRealTime)
        {
            long primaryKey = 0;

            if (restoringRealTime)
            {
                primaryKey = this.getPKValue(voObject);
            }
            else
            {
                primaryKey = getNextAvailableKey();
            }

            VOMethod pkSetter = voClassMD.getPKSetter();

            try
            {
                pkSetter.getMethod().invoke(voObject, new Long[] { new Long(primaryKey) });
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }

            sql.append("INSERT INTO ").append(sqlTableName);
            sql.append("(");

            for (int i = 0; i < dbColumns.length; i++)
            {
                String sqlColumnName = sqlDBTable.getDBColumn(dbColumns[i].getColumnName()).getFullyQualifiedColumnName();

                sql.append(sqlColumnName);

                if (i < (dbColumns.length - 1))
                {
                    sql.append(",");
                }
            }

            sql.append(")");

            sql.append(" VALUES (");

            for (int i = 0; i < dbColumns.length; i++)
            {
                sql.append("?");

                if (i < (dbColumns.length - 1))
                {
                    sql.append(",");
                }
            }

            sql.append(")");
        }

        // UPDATE
        else
        {
            String pkColName = VOClass.getPKColumnName(tableName);
            String sqlPKColName = sqlDBTable.getDBColumn(pkColName).getFullyQualifiedColumnName();

            sql.append("UPDATE ").append(sqlTableName);
            sql.append(" SET ");

            for (int i = 0; i < dbColumns.length; i++)
            {
                String sqlColumnName = sqlDBTable.getDBColumn(dbColumns[i].getColumnName()).getFullyQualifiedColumnName();

                if (i < (dbColumns.length - 1))
                {
                    sql.append(sqlColumnName).append(" = ?, ");
                }
                else
                {
                    sql.append(sqlColumnName).append(" = ? ");
                }
            }

            sql.append(" WHERE ").append(sqlPKColName).append(" = ").append(pkVal);
        }

        PreparedStatement ps = null;

        try
        {
            ps = crudConn.prepareStatement(sql.toString());

            for (int i = 0; i < dbColumns.length; i++)
            {
                // FK values must be null if there is no value.
                VOMethod voMethod = voClassMD.getSimpleGetter(dbColumns[i].getColumnName());

                Method simpleGetter = voMethod.getMethod();

                int methodCategory = voMethod.getMethodCategory();

                if (methodCategory == VOMethod.FK_GETTER)
                {
                    long fkVal = getFKValue(voObject, simpleGetter);

                    if (fkVal == 0)
                    {
                        ps.setNull(i + 1, dbColumns[i].getColumnSQLType());
                    }
                    else
                    {
                        ps.setLong(i + 1, fkVal);
                    }
                }
                else
                {
                    Object value = simpleGetter.invoke(voObject, null);

                    if (value == null)
                    {
                        if (voMethod.getTargetFieldType() == BigDecimal.class)
                        {
                            //  For all BigDecimal objects that contain null, set to a default value of zero (TEMPORARY, QUICK SOLUTION)
                            value = new EDITBigDecimal("0.0").getBigDecimal();
                        }
                    }

                    if (value != null)
                    {
                        //  Check to see if the database type is a date or date and time
                        if (dbColumns[i].isDateOrDateTime())
                        {
                            //  It's a date or a date and time
                            if (dbColumns[i].isDateTime())
                            {
                                //  It's a date and time, save it as a Timestamp
                                String dateTimeValue = (String) simpleGetter.invoke(voObject, null);

                                java.sql.Timestamp sqlTimeStamp = DBUtil.convertStringToTimestamp(dateTimeValue);

                                ps.setTimestamp(i + 1, sqlTimeStamp);
                            }
                            else
                            {
                                //  It's just a date, save it as a Date
                                String dateValue = (String) simpleGetter.invoke(voObject, null);

//                                if (dateValue.equals(EDITDate.DEFAULT_ZERO_DATE))
//                                {
//                                    // Set to null in db (temporary - this should be handled on the UI side)
//                                    ps.setNull(i + 1, dbColumns[i].getColumnSQLType());
//                                }
//                                else
//                                {
                                    java.sql.Date sqlDate = DBUtil.convertStringToDate(dateValue);

                                    ps.setDate(i + 1, sqlDate);
//                                }
                            }
                        }
                        else
                        {
                            ps.setObject(i + 1, value);
                        }
                    }
                    else
                    {
                        // At this point, the assumption is that it is null, save it as null for its appropriate SQL type
                        ps.setNull(i + 1, dbColumns[i].getColumnSQLType());
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        return ps;
    }

    /**
     * Gets the primary key for a value object
     *
     * @param voObject The target VO
     * @return The primary key
     */
    public long getPKValue(Object voObject)
    {
    
      return ((VOObject) voObject).getPKValue();
//        VOClass voClassMD = VOClass.getVOClassMetaData(voObject.getClass());
//
//        Method pkGetter = voClassMD.getPKGetter().getMethod();
//
//        long pkVal = 0;
//
//        try
//        {
//            pkVal = ((Long) pkGetter.invoke(voObject, null)).longValue();
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//
//            e.printStackTrace(); //To change body of catch statement use Options | File Templates.
//
//            throw new RuntimeException(e);
//        }
//
//        return pkVal;
    }

    /**
     * Gets the foreign key key for a value object
     *
     * @param voObject The target VO
     * @param fkGetter The foreign key getter
     * @return The foreign key
     */
    public static long getFKValue(Object voObject, Method fkGetter)
    {
        long fkVal = 0;

        try
        {
            fkVal = ((Long) fkGetter.invoke(voObject, null)).longValue();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        return fkVal;
    }

    public void startTransaction() throws EDITCRUDException
    {
        try
        {
            crudConn.setAutoCommit(false);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new EDITCRUDException(e.getMessage());
        }
    }

    public void commitTransaction() throws EDITCRUDException
    {
        try
        {
            if ((crudConn != null) && !crudConn.getAutoCommit())
            {
                crudConn.commit();

                crudConn.setAutoCommit(true);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new EDITCRUDException(e.getMessage());
        }
    }

    public void rollbackTransaction() throws EDITCRUDException
    {
        try
        {
            if ((crudConn != null) && !crudConn.getAutoCommit())
            {
                crudConn.rollback();

                crudConn.setAutoCommit(true);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new EDITCRUDException(e.getMessage());
        }
    }

    /**
     * Deletes from the database each entry in the current deletion chain
     *
     * @return The number of records deleted
     */
    private int processDeletionChain()
    {
        int deletedRecords = deletionChain.size();

        try
        {
            for (int i = 0; i < deletedRecords; i++)
            {
                String objectKey = deletionChain.get(i).toString();

                String className = objectKey.substring(0, objectKey.indexOf("_"));
                String primaryKey = objectKey.substring(objectKey.indexOf("_") + 1, objectKey.length());

                deleteVOFromDB(Class.forName(className), Long.parseLong(primaryKey));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        return deletedRecords;
    }

    /**
     * Returns an object key for a target VO Class and its primary key
     *
     * @param voClass The target VO Class
     * @param primaryKey the target primary key
     * @return The object key
     */
    private String getObjectKey(Class voClass, long primaryKey)
    {
        return voClass.getName() + "_" + primaryKey;
    }

    /**
     * Adds a VO targeted for deletion
     *
     * @param voClass The target voClass
     * @param primaryKey The target primary key
     */
    private void addToDeletionChain(Class voClass, long primaryKey, int deletionPosition) throws EDITDeleteException 
    {
	if (!voClass.getSimpleName().equals("CommissionablePremiumHistoryVO")) {
        //throw new EDITDeleteException("Attempt to delete VO: " + voClass.getSimpleName() + " - " + primaryKey);
        String objectKey = getObjectKey(voClass, primaryKey);

        if (!deletionChain.contains(objectKey))
        {
            if (deletionPosition == DELETE_FIRST)
            {
                deletionChain.add(0, objectKey);
            }
            else if (deletionPosition == DELETE_NEXT)
            {
                deletionChain.add(objectKey);
            }
            if (debugDeletionsOutFile)
            {
            	String dateTimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
                try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(deleteFile, true)))) {
                    out.println(dateTimeString + ": " + voClass.getName() + ": " + primaryKey);
                }catch (IOException e) {
                	System.out.println("IOException: " + e.getMessage());
                }
            }
        }
	}
    }

    /**
     * Adds a target VO to the object graph
     *
     * @param voObject
     */
    private void addVOToObjectModel(Object voObject)
    {
        long primaryKey = getPKValue(voObject);

        String objectKey = getObjectKey(voObject.getClass(), primaryKey);

        if (!voObjectModel.containsKey(objectKey))
        {
            voObjectModel.put(objectKey, voObject);
        }
    }

    /**
     * Returns a VO by reference from the object graph
     *
     * @param voClass
     * @param primaryKey
     * @return The target VO if found, null otherwise
     */
    private Object getVOFromObjectModel(Class voClass, long primaryKey)
    {
        String objectKey = getObjectKey(voClass, primaryKey);

        return voObjectModel.get(objectKey);
    }

    /**
     * Clears the object graph
     */
    private void clearVOObjectModel()
    {
        voObjectModel.clear();
    }

    /**
     * Clears the deletion chain
     */
    private void clearDeletionChain()
    {
        deletionChain.clear();
    }

    /**
     * Returns all 1st level VOs of the parentVOObject of the child VO Class type
     *
     * @param parentVOObject The target parent VO object
     * @param childClass The class type of child VOs sought
     * @return All 1st levelt child VOs
     */
    private static Object[] getChildVOsFromParentVO(Object parentVOObject, Class childClass)
    {
        VOClass voParentClassMD = VOClass.getVOClassMetaData(parentVOObject.getClass());
        VOClass voChildClassMD = VOClass.getVOClassMetaData(childClass);

        Method voGetter = voParentClassMD.getVOGetter(voChildClassMD.getTableName()).getMethod();

        Object[] childVOs = null;

        try
        {
            childVOs = (Object[]) voGetter.invoke(parentVOObject, null);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        if (childVOs.length != 0)
        {
            return childVOs;
        }
        else
        {
            return null;
        }
    }

    /**
     * Retrieves all child PKs for the associated parent Class/primary key
     *
     * @param parentVOClass The target parent VO Class
     * @param childVOClass The target child VO Class
     * @param parentPK The target parent primary key
     * @return Any child primary keys, null otherwise
     * @exception Exception
     */
    private long[] getChildVOPKs(Class parentVOClass, Class childVOClass, long parentPK)
    {
        long[] childPKs = null;

        // Segments referring to other Segments is a special case
        //        if (parentVOClass.equals(childVOClass))
        //        {
        //            childPKs = getChildPKsViaRelationshipTable(parentVOClass, parentPK);
        //        }
        //        else
        //        {
        childPKs = getChildPKs(parentVOClass, childVOClass, parentPK);

        //        }
        return childPKs;
    }

    public static void recurseVOObjectModel(Object currentVO, Object parentVO, int methodOfRecursion,
        RecursionListener recursionListener, RecursionContext context, List voExclusionList)
    {
        if (context == null)
        {
            context = new RecursionContext();
        }

        context.incrementRecursionLevel();

        context.updateRecursionPath(currentVO, RecursionContext.ADD_NODE_TO_PATH);

        if (methodOfRecursion == RECURSE_TOP_DOWN)
        {
            recursionListener.currentNode(currentVO, parentVO, context);
        }

        if (!context.shouldContinueRecursion())
        {
            return;
        }

        VOClass currentVOClassMD = VOClass.getVOClassMetaData(currentVO.getClass());

        Class[] childVOClasses = currentVOClassMD.getChildVOClasses(voExclusionList);

        if (childVOClasses != null)
        {
            for (int i = 0; i < childVOClasses.length; i++)
            {
                if (!context.shouldContinueRecursion())
                {
                    return;
                }

                Object[] childVOs = getChildVOsFromParentVO(currentVO, childVOClasses[i]);

                if (childVOs != null)
                {
                    for (int j = 0; j < childVOs.length; j++)
                    {
                        if (!context.shouldContinueRecursion())
                        {
                            return;
                        }

                        recurseVOObjectModel(childVOs[j], currentVO, methodOfRecursion, recursionListener, context,
                            voExclusionList);
                    }
                }
            }
        }

        if (methodOfRecursion == RECURSE_BOTTOM_UP)
        {
            recursionListener.currentNode(currentVO, parentVO, context);
        }

        context.updateRecursionPath(currentVO, RecursionContext.REMOVE_NODE_FROM_PATH);

        context.decrementRecursionLevel();
    }

    public void backupBaseSegmentVOAsBinary(SegmentVO segmentVO)
    {
        PreparedStatement ps = null;

        try
        {
            long segmentPK = segmentVO.getSegmentPK();

            String sql = getSQLForPreparedStatement("SegmentFK", "SegmentBackup", "SegmentFK");

            long[] segmentPKs = getPrimaryKeysForGeneralQuery(sql, segmentPK);

            // If the BaseSegment does not already exist
            if (segmentPKs == null)
            {
                ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream output = new ObjectOutputStream(byteOutputStream);
                output.writeObject(segmentVO);
                output.close();

                byte[] bytes = byteOutputStream.toByteArray();
                byteOutputStream.close();

                InputStream bytesIn = new ByteArrayInputStream(bytes);

                ps = getPreparedStatementForBaseSegmentBackup(segmentPK, bytesIn, bytes);

                ps.executeUpdate();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (ps != null)
                {
                    ps.close();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
        }
    }

    public SegmentVO restoreBaseSegmentFromBinary(long segmentPK)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            SegmentVO segmentVO = null;

            String sql = getSQLForPreparedStatement("BinaryDate", "SegmentBackup", "SegmentFK");

            ps = crudConn.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next())
            {
                InputStream is = rs.getBinaryStream(1);

                ObjectInputStream input = new ObjectInputStream(is);

                segmentVO = (SegmentVO) input.readObject();
            }

            return segmentVO;
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }

                if (ps != null)
                {
                    ps.close();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
        }
    }

    public void deleteSegmentBackup(long segmentPK)
    {
        PreparedStatement ps = null;

        try
        {
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM SegmentBackup WHERE SegmentFK = ").append(segmentPK);

            ps = crudConn.prepareStatement(sql.toString());

            ps.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (ps != null)
                {
                    ps.close();
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
        }
    }

    private PreparedStatement getPreparedStatementForBaseSegmentBackup(long segmentPK, InputStream bytesIn, byte[] bytes)
    {
        PreparedStatement ps = null;

        try
        {
            ps = crudConn.prepareStatement("INSERT INTO SegmentBackup (BinaryData, SegmentFK) VALUES (?,?)");

            ps.setBinaryStream(1, bytesIn, bytes.length);
            ps.setLong(2, segmentPK);
        }
        catch (SQLException e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        return ps;
    }

    public Connection getCrudConn()
    {
        return crudConn;
    }

    /**
     * Simple formatter to create the PreparedStatement sql. Many (most) sql statements in CRUD use this.
     * @param selectExpression
     * @param fromTable
     * @param whereCol
     * @return
     */
    private String getSQLForPreparedStatement(String selectExpression, String fromTable, String whereCol)
    {
        return "SELECT " + selectExpression + " FROM " + fromTable + " WHERE " + whereCol + " = ?";
    }

    public void setRestoringRealTime(boolean realtimeRestore)
    {
        this.restoringRealTime = realtimeRestore;
    }

    public boolean getRestoringRealTime()
    {
        return restoringRealTime;
    }
    
    private String getRecordDeletionFile()
    {
        
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd");
        String dateString = dateTimeFormat.format(new Date().getTime());

        String fileName = "CRUD_Deletes";

        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        String deleteFile = export1.getDirectory() + fileName + "_" + dateString + ".txt";

        return deleteFile;
    }
}



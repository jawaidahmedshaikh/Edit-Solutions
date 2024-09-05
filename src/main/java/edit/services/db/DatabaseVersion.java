/*
 * User: sdorman
 * Date: Sep 27, 2006
 * Time: 1:26:04 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.db;

import edit.common.*;

import java.sql.*;

public class DatabaseVersion
{
    private static final String TABLENAME = "DatabaseVersion";

    private static final String MAJOR_VERSION_FIELDNAME = "MajorVersion";
    private static final String MINOR_VERSION_FIELDNAME = "MinorVersion";
    private static final String UPDATE_DATETIME_FIELDNAME = "UpdateDateTime";

    private String majorVersion;
    private String minorVersion;
    private EDITDateTime updateDateTime;

    public String getMajorVersion()
    {
        return this.majorVersion;
    }

    public void setMajorVersion(String majorVersion)
    {
        this.majorVersion = majorVersion;
    }

    public String getMinorVersion()
    {
        return this.minorVersion;
    }

    public void setMinorVersion(String minorVersion)
    {
        this.minorVersion = minorVersion;
    }

    public EDITDateTime getUpdateDateTime()
    {
        return this.updateDateTime;
    }

    public void setUpdateDateTime(EDITDateTime updateDateTime)
    {
        this.updateDateTime = updateDateTime;
    }

    public static DatabaseVersion findByDatabaseName(String schemaName, String databaseName, String poolName)
    {
        DatabaseVersion databaseVersion = new DatabaseVersion();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String fullyQualifiedTableName = DBUtil.formatTableName(schemaName, databaseName, TABLENAME);

        String sql = " SELECT * FROM " + fullyQualifiedTableName;

        try
        {
//                connection = SessionHelper.getSession(databaseName).connection();
            connection = ConnectionFactory.getSingleton().getConnection(poolName);


            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
                databaseVersion.setMajorVersion(rs.getString(MAJOR_VERSION_FIELDNAME));
                databaseVersion.setMinorVersion(rs.getString(MINOR_VERSION_FIELDNAME));
                databaseVersion.setUpdateDateTime(new EDITDateTime(DBUtil.readAndConvertTimestamp(rs, UPDATE_DATETIME_FIELDNAME)));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return databaseVersion;
    }
}

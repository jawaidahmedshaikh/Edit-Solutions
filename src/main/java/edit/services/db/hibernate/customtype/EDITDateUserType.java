/*
 * User: gfrosti
 * Date: Mar 23, 2005
 * Time: 10:43:32 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.services.db.hibernate.customtype;

import edit.common.*;

import org.hibernate.*;
import org.hibernate.usertype.*;

import java.io.*;
import java.sql.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;



public class EDITDateUserType implements UserType
{
    private static final int[] SQL_TYPES = { Types.DATE };

    /**
     * @see org.hibernate.usertype.UserType#equals(Object, Object)
     * @param o
     * @param o1
     * @return
     * @throws HibernateException
     */
    public boolean equals(Object o, Object o1) throws HibernateException
    {
        boolean equals = false;

        if (o != null && o1 != null)
        {
            equals = o.equals(o1); //To change body of implemented methods use File | Settings | File Templates.
        }
        else if (o == null && o1 == null)
        {
            equals = true;
        }

        return equals;
    }

    /**
     * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, String[], Object)
     * @param resultSet
     * @param strings
     * @param o
     * @return
     * @throws HibernateException
     * @throws SQLException
     */
    public Object nullSafeGet(ResultSet resultSet, String[] strings, Object o) throws HibernateException, SQLException
    {
        EDITDate editDate = null;

        Date date = resultSet.getDate(strings[0]);

        if (!resultSet.wasNull())
        {
            long dateInMillis = date.getTime();

            editDate = new EDITDate(dateInMillis);
        }

        return editDate;
    }

    /**
     * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, Object, int)
     * @param preparedStatement
     * @param value
     * @param index
     * @throws HibernateException
     * @throws SQLException
     */
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException
    {
        if (value == null)
        {
            preparedStatement.setNull(index, Types.TIMESTAMP);
        }
        else
        {
        	// deck: added a debugging statement in the event a bad date causes an exception
            EDITDate editDate = (EDITDate) value;
            try {
                preparedStatement.setTimestamp(index, new Timestamp(editDate.getTimeInMilliseconds()));
            } catch (SQLException e) {
                System.out.println("Date: " + (editDate.getFormattedDate()));
            	throw e;
            }
        }
    }

    /**
     * @see org.hibernate.usertype.UserType#returnedClass()
     * @return
     */
    public Class returnedClass()
    {
        return EDITDate.class; //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @see org.hibernate.usertype.UserType#sqlTypes()
     * @return
     */
    public int[] sqlTypes()
    {
        return SQL_TYPES; //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @see org.hibernate.usertype.UserType#deepCopy(Object)
     * @param o
     * @return
     * @throws HibernateException
     */
    public Object deepCopy(Object o) throws HibernateException
    {
        EDITDate copyOfEditDate = null;

        if (o != null)
        {
            try
            {
                EDITDate editDate = (EDITDate) o;

                copyOfEditDate = editDate.copy();
            }
            catch (Exception e)
            {
              System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return copyOfEditDate;
    }

    /**
     * @see org.hibernate.usertype.UserType#isMutable()
     * @return
     */
    public boolean isMutable()
    {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }

    public Serializable disassemble(Object value) throws HibernateException
    {
        return (Serializable) value;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException
    {
        return cached;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException
    {
        return original;
    }

    public int hashCode(Object x) throws HibernateException
    {
        return x.hashCode();
    }
}

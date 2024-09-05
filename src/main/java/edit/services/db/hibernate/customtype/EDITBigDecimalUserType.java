package edit.services.db.hibernate.customtype;

import edit.common.*;
import org.hibernate.*;
import org.hibernate.usertype.*;

import java.io.*;
import java.math.*;
import java.sql.*;


/*
 * User: sprasad
 * Date: Apr 22, 2005
 * Time: 12:37:59 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
public class EDITBigDecimalUserType implements EnhancedUserType
{
    private static final int[] SQL_TYPES = { Types.DECIMAL };

    /**
     * @see org.hibernate.usertype.UserType#returnedClass()
     * @return
     */
    public Class returnedClass()
    {
        return EDITBigDecimal.class; //To change body of implemented methods use File | Settings | File Templates.
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
     * @see org.hibernate.usertype.UserType#equals(Object, Object)
     * @param o
     * @param o1
     * @return
     * @throws org.hibernate.HibernateException
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
        EDITBigDecimal editBigDecimal = null;

        BigDecimal bigDecimal = resultSet.getBigDecimal(strings[0]);

        if (!resultSet.wasNull())
        {
            editBigDecimal = new EDITBigDecimal(bigDecimal);
        }
        else
        {
            editBigDecimal = new EDITBigDecimal();
        }

        return editBigDecimal;
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
            preparedStatement.setBigDecimal(index, new EDITBigDecimal().getBigDecimal());
        }
        else
        {
            EDITBigDecimal editBigDecimal = (EDITBigDecimal) value;

            preparedStatement.setBigDecimal(index, editBigDecimal.getBigDecimal());
        }
    }

    /**
     * @see org.hibernate.usertype.UserType#deepCopy(Object)
     * @param o
     * @return
     * @throws HibernateException
     */
    public Object deepCopy(Object o) throws HibernateException
    {
        EDITBigDecimal copyOfEDITBigDecimal = null;

        if (o != null)
        {
            try
            {
                EDITBigDecimal editBigDecimal = (EDITBigDecimal) o;

                copyOfEDITBigDecimal = new EDITBigDecimal(editBigDecimal.getBigDecimal());
            }
            catch (Exception e)
            {
              System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return copyOfEDITBigDecimal;
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

  public String objectToSQLString(Object object)
  {
    return toXMLString(object);
  }

  public String toXMLString(Object object)
  {
    return ((EDITBigDecimal)object).toString();
  }

  public Object fromXMLString(String string)
  {
    return new EDITBigDecimal(string);
  }
}

/*
 * User: sprasad
 * Date: Oct 11, 2007
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.db.hibernate.customtype;

import java.io.Serializable;
import java.io.Writer;
import java.io.IOException;

import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import oracle.sql.CLOB;
import oracle.jdbc.OraclePreparedStatement;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;

public class EDITClobUserType implements EnhancedUserType
{
    private static final int[] SQL_TYPES = { Types.CLOB };

    /**
     * @see org.hibernate.usertype.UserType#returnedClass()
     * @return
     */
    public Class returnedClass()
    {
        return String.class; 
    }

    /**
     * @see org.hibernate.usertype.UserType#sqlTypes()
     * @return
     */
    public int[] sqlTypes()
    {
        return SQL_TYPES; 
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
    public Object nullSafeGet(ResultSet resultSet, String[] names, Object o) throws HibernateException, SQLException
    {
        String string = null;

        Clob clob = resultSet.getClob(names[0]);
        
        if (!resultSet.wasNull())
        {
            string = clob.getSubString(1, (int) clob.length());
        }

        return string;
    }

    /**
     * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, Object, int)
     * @param preparedStatement
     * @param value
     * @param index
     * @throws HibernateException
     * @throws SQLException
     */
    public void nullSafeSet(PreparedStatement ps, Object value, int index) throws HibernateException, SQLException
    {
        if (value == null)
        {
            ps.setNull(index, Types.CLOB);            
        }
        else
        {
            if (ps instanceof OraclePreparedStatement)
            {
                oracle.sql.CLOB oracleClob = createOracleCLOB(ps, value);
                
                ps.setClob(index, oracleClob);
            }
            else
            {
                Clob clob = Hibernate.createClob((String) value);
                
                ps.setClob(index, clob);
            }
        }
    }
    
    /**
     * @see org.hibernate.usertype.UserType#deepCopy(Object)
     * @param o
     * @return
     * @throws HibernateException
     */
    public Object deepCopy(Object value)
    {
        String copyOfClob = null;
    
        if (value != null)
        {
            copyOfClob = new String((String) value);
        }

        return copyOfClob;
    }

    /**
     * @see org.hibernate.usertype.UserType#isMutable()
     * @return
     */
    public boolean isMutable()
    {
        return false;
    }
    
    /**
     * @see org.hibernate.usertype.UserType#disassemble()
     * @param value
     * @return
     * @throws HibernateException
     */
    public Serializable disassemble(Object value) throws HibernateException
    {
        return (Serializable) value;
    }

    /**
     * @see org.hibernate.usertype.UserType#assemble
     * @param cached
     * @param owner
     * @return
     * @throws HibernateException
     */
    public Object assemble(Serializable cached, Object owner) throws HibernateException
    {
        return cached;
    }

    /**
     * @see org.hibernate.usertype.UserType#replace
     * @param original
     * @param target
     * @param owner
     * @return
     * @throws HibernateException
     */
    public Object replace(Object original, Object target, Object owner) throws HibernateException
    {
        return original;
    }

    /**
     * @see org.hibernate.usertype.UserType#hashcode
     * @param x
     * @return
     * @throws HibernateException
     */
    public int hashCode(Object x) throws HibernateException
    {
        return x.hashCode();
    }
    
    /**
     * @see org.hibernate.usertype.UserType#objectToSQLString
     * @param object
     * @return
     */
    public String objectToSQLString(Object object)
    {
      return toXMLString(object);
    }

    /**
     * @see org.hibernate.usertype.UserType#toXMLString
     * @param object
     * @return
     */
    public String toXMLString(Object object)
    {
      return object.toString();
    }

    /**
     * @see org.hibernate.usertype.UserType#fromXMLString
     * @param string
     * @return
     */
    public Object fromXMLString(String string)
    {
      return string;
    }
    
    /**
     * Helper method to get oracle version of CLOB.
     * @param ps
     * @param value
     * @return
     * @throws SQLException
     */
    private CLOB createOracleCLOB(PreparedStatement ps, Object value) throws SQLException
    {
        oracle.sql.CLOB temporaryCLOB = null;
        
        try
        {
            temporaryCLOB = CLOB.createTemporary(ps.getConnection(), true, CLOB.DURATION_SESSION);
             
            temporaryCLOB.open(CLOB.MODE_READWRITE);
             
            Writer oracleCLOBWriter = temporaryCLOB.getCharacterOutputStream();
             
            oracleCLOBWriter.write((String) value);
             
            oracleCLOBWriter.flush();
            
            oracleCLOBWriter.close();
            
            temporaryCLOB.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);
        }
        
        return temporaryCLOB;
    }
}

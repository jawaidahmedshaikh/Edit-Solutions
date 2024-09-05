package test.textfixture;

import edit.common.*;

import edit.common.vo.EDITServicesConfig;

import edit.services.config.*;
import edit.services.db.hibernate.*;

import event.GroupSetup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;

import java.net.URISyntaxException;

import org.hibernate.cfg.*;
import org.hibernate.mapping.*;

import java.lang.reflect.*;

import java.math.*;

import java.net.URL;

import java.util.*;
import java.util.List;
import java.util.Set;


/**
 * A convenience class that helps with Unit Testing. Some features include
 * the setting of the path to the configuration file so that SessionHelper
 * may be used outside of application server. Another feature is the ability
 * to auto-populate a Hibernate Entity with dummy data with the use of reflection.
 * This class will grow with useful helpers.
 */
public class TestHelper
{
  public static final String CONFIG_FILE = "test/UnitTest.properties";

  public static final String EDIT_SERVICES_CONFIG = "EDITSERVICESCONFIG";

  private Properties configurationProperties;

  /**
   * Constructor. 
   */
  public TestHelper()
  {
    this.configurationProperties = new Properties();
  }

  /**
   * A convenience method to build and populate a Hibernate entity with dummy default values for easy testing.
   * @param entityClass
   * @return
   */
  public Object buildEntity(Class entityClass, String targetDB)
  {
    Object entity = null;

    try
    {
      entity = SessionHelper.newInstance(entityClass, targetDB);

      Configuration c = SessionHelper.getConfiguration(SessionHelper.EDITSOLUTIONS);

      PersistentClass pc = c.getClassMapping(entityClass.getName());

      if (pc == null)
      {
        throw new Exception("No Mapping Can Be Found For [" + entityClass.getName() + "] - The Class Must Be Added To The Set Of Classes Recognized By DBSessionFactory");
      }
      else
      {
          // I can't get past is it int/Integer or long/Long for setting default values. I will save 
          // this for another day.
        
//        Iterator i = pc.getPropertyIterator();
//
//        while (i.hasNext())
//        {
//          Property property = (Property) i.next();
//
//          String propertyName = property.getName();
//
//          Class propertyType = property.getType().getReturnedClass();//getType(property);
//
//          setDefaultValue(entity, propertyName, propertyType);
//        }
      }
    }
    catch (Exception e)
    {
      System.out.println(e);

      e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
    }

    return entity;
  }

//  /**
//   * The underlying Java type.
//   * @param property
//   * @return
//   */
//  private Class getType(Property property)
//  {
//    Class classType = null;
//
//    classType = property.getType().getReturnedClass();
//
//    if (classType == Integer.class)
//    {
//      classType = int.class;
//    }
//    else if (classType == Long.class)
//    {
//      classType = long.class;
//    }
//
//    return classType;
//  }

  /**
   * For the specified propertyType, a default value will be set on the specified entity. There is no clever
   * defaulting scheme. Strings will be set with 1 character Strings, EDITDates will be set with the current System
   * date, etc.
   * @param entity
   * @param propertyName
   * @param propertyType
   */
  private void setDefaultValue(Object entity, String propertyName, Class propertyType)
  {
    try
    {
      if (!HibernateEntity.class.isAssignableFrom(propertyType))
      {
        Method setter = entity.getClass().getMethod("set" + propertyName, new Class[]
            { propertyType });

        Object defaultValue = getDefaultValue(propertyType);

        try
        {
          setter.invoke(entity, new Object[]
              { defaultValue });
        }
        catch (Exception e)
        {
          // It was a guess to set the defaultValue to long/int instead of Long/Int. Try again with the other type.
          if (propertyType == Long.class)
          {
            defaultValue = new Long(1);
          }
          else if (propertyType == Integer.class)
          {
            defaultValue = new Integer(1);
          }
          setter = entity.getClass().getMethod("set" + propertyName, new Class[]
                     { propertyType });          
          
          setter.invoke(entity, new Object[]
              { defaultValue });          
        }
      }
    }
    catch (Exception e)
    {
      System.out.println(e);

      e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

      throw new RuntimeException(e);
    }
  }

  /**
   * Builds a simple default value for the specified propertyType. Strings will be set to a simple character, EDITDate will
   * be set to the current System date, etc.
   * @param propertyType
   * @return
   */
  private Object getDefaultValue(Class propertyType)
  {
    Object value = null;

    if (propertyType == String.class)
    {
      value = "s";
    }
    else if (propertyType == EDITDate.class)
    {
      value = new EDITDate();
    }
    else if (propertyType == EDITDateTime.class)
    {
      value = new EDITDateTime();
    }
    else if (propertyType == int.class)
    {
      value = 1;
    }
    else if (propertyType == Integer.class)
    {
      value = 1;
    }
    else if (propertyType == BigDecimal.class)
    {
      value = new BigDecimal("1.00");
    }
    else if (propertyType == EDITBigDecimal.class)
    {
      value = new EDITBigDecimal("1.00");
    }
    else if (propertyType == Set.class)
    {
      value = new HashSet();
    }
    else if (propertyType == Long.class)
    {
      value = 1;
    }
    else if (propertyType == long.class)
    {
      value = 1;
    }
    else
    {
      throw new IllegalArgumentException("No valid default object could be built for type [" + propertyType + "]");
    }

    return value;
  }

  /**
   * Respects JUnit convention of having a setUp()/tearDown() lifecycle.
   * Loads the UnitTest.properties and establishes the SessionHelper for
   * use outside the context of an application server.
   * 
   * Additionally, a SessionHelper.beginTransaction() is started with the
   * understanding that the tearDown() will rollback any started transaction.
   * It is believed that Hibernate Entities will be used as "Mock" objects with
   * no intention of permanently persisting the test entities.
   */
  public void setUp() throws FileNotFoundException, IOException
  {
    InputStream in = null;

    try
    {
      in = ClassLoader.getSystemResourceAsStream(CONFIG_FILE);

      if (in == null)
        throw new FileNotFoundException("UnitTest Configuration File not Found [" + CONFIG_FILE + "]");

      getConfigurationProperties().load(in);

      String editServicesConfig = (String) getConfigurationProperties().get(EDIT_SERVICES_CONFIG);

      if (editServicesConfig == null)
        throw new NullPointerException("Unit Test Configuration File Requires a Name/Value for [" + EDIT_SERVICES_CONFIG + "=" + "/path/to/config/file/foo.xml]");

      ServicesConfig.setEditServicesConfig(editServicesConfig);
    }
    finally
    {
      if (in != null)
        in.close();
        
        SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
    }
  }

  /**
   * Respects JUnit convention of having a setUp()/tearDown() lifecycle.
   * The SessionHelper.rollbackTransaction() is invoked to complement the 
   * transaction started in the setUp() method.
   */
  public void tearDown()
  {
    SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);;
  
    SessionHelper.closeSessions();
  }

  public Properties getConfigurationProperties()
  {
    return configurationProperties;
  }
}

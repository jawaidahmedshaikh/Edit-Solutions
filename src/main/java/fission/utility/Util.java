/*
 * User: unknown
 * Date: Jun 4, 2001
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package fission.utility;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.Part;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.vo.*;
import edit.services.db.hibernate.SessionHelper;
import fission.global.AppReqBlock;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.server.UID;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.tree.DefaultElement;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.HibernateProxyHelper;

import engine.*;


/**
 * The Util class contains reusable general purpose
 * methods.
 */
public class Util
{
    private static final Pattern NUMBER_PATTERN = Pattern.compile("(^-?\\d\\d*\\.\\d*$)|(^-?\\d\\d*$)|(^-?\\.\\d\\d*$)");
    public static final int UPPER_CASE = 0;
    public static final int LOWER_CASE = 1;

    /**
     * Often, form data can be directly mapped the the properties in a VO. It becomes the onus of the developer to manually
     * map the form's values to their corresponding VO properties. In such as situation, manual mapping can be avoided.
     * As long as the form's names match the VO's property names, the mapping will work. Form names should follow standard
     * Java naming conventions and begin with lower-case.
     * @param request
     * @param voClass
     * @return
     */
    public static VOObject mapFormDataToVO(HttpServletRequest request, Class voClass, boolean convertDates)
    {
        VOObject voObject = null;

        try
        {
            voObject = (VOObject) voClass.newInstance();

            Method[] methods = voClass.getMethods();

            for (int i = 0; i < methods.length; i++)
            {
                Method method = methods[i];

                if (validVOSetter(method))
                {
                    String setterName = method.getName();

                    String paramName = setterName.substring(3, 4).toLowerCase() + setterName.substring(4);

                    Class paramType = method.getParameterTypes()[0];

                    String paramStringValue = Util.initString((String) request.getParameter(paramName), null);

                    Object paramValue = Util.convertStringToObject(paramStringValue, paramType, convertDates);

                    method.invoke(voObject, new Object[] { paramValue });
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return voObject;
    }

    /**
     * Only setters that set a restricted set of data types are valid. Those types are:
     * String
     * int
     * long
     * BigDecimal
     * @return
     */
    private static boolean validVOSetter(Method method)
    {
        boolean validSetter = false;

        String methodName = method.getName();

        if (methodName.startsWith("set"))
        {
            Class[] params = method.getParameterTypes();

            if (params.length == 1)
            {
                Class paramType = params[0];

                if (paramType == String.class)
                {
                    validSetter = true;
                }
                else if (paramType == int.class)
                {
                    validSetter = true;
                }
                else if (paramType == long.class)
                {
                    validSetter = true;
                }
                else if (paramType == BigDecimal.class)
                {
                    validSetter = true;
                }
            }
        }

        return validSetter;
    }

    public static String formatDecimal(String fstr, BigDecimal num)
    {
        return formatDecimal(fstr, new EDITBigDecimal(num));
    }

    /**
     * This method will return a formatted number as a string.
     * <NUMBER_PATTERN/>
     *
     * @param fstr Format Pattern (i.e. "#########0.00")
     * @param num  Number to be formatted
     * @return Returns formatted number as a string
     */

    //SRAMAM 09/2004 DOUBLE2DECIMAL
    //public static String formatDecimal(String fstr, double num)
    public static String formatDecimal(String fstr, EDITBigDecimal num)
    {
        DecimalFormat df = new DecimalFormat(fstr);

        return df.format(num.doubleValue());
    }

    /**
     * Parses the dataName by the delimiter and
     * returns the results in a List
     */
    public static List parseToVector(String dataName, String delimiter)
    {
        StringTokenizer s = new StringTokenizer(dataName, delimiter);
        List results = null;

        while (s.hasMoreTokens())
        {
            results.add(s.nextToken());
        }

        return results;
    }

    public static String substitute(String s, String targetChars, String substituteChars) throws Exception
    {
        s = java.net.URLDecoder.decode(s);

//        RE re = new RE("[" + targetChars + "']");
//
//        s = re.subst(s, substituteChars);

          Pattern p = Pattern.compile("[" + targetChars + "']");
          
          Matcher m = p.matcher(s);
          
          s = m.replaceAll(substituteChars);          
  
          return s;
    }

    /**
     * Returns the passed in file name with the extension removed
     */
    public static String removeFileExtension(String fileName)
    {
        int extPosition = fileName.indexOf(".");

        if (extPosition > 0)
        {
            String name = fileName.substring(0, extPosition);
            int length = name.length();
            String lastTwoLetters = name.substring(length - 2, length);

            if ((lastTwoLetters.equals("FN")) || (lastTwoLetters.equals("FS")) || (lastTwoLetters.equals("MN")) || (lastTwoLetters.equals("MS")) || (lastTwoLetters.equals("FP")) || (lastTwoLetters.equals("MP")))
            {
                String nameWithOutExtension = name.substring(0, length - 2);

                return nameWithOutExtension;
            }
            else
            {
                return name;
            }
        }
        else
        {
            return fileName;
        }
    }

    /**
     * Returns the sourceString with all occurrences of oldSubstring
     * replaced with newSubString.
     */
    public static String replaceSubstring(String sourceString, String oldSubstring, String newSubstring)
    {
        StringBuilder newString = new StringBuilder();
        int startPosition = 0;
        boolean done = false;

        while (!done)
        {
            int index = sourceString.indexOf(oldSubstring, startPosition);

            if (index == -1)
            {
                newString.append(sourceString.substring(startPosition));
                done = true;
            }
            else
            {
                newString.append(sourceString.substring(startPosition, index));
                newString.append(newSubstring);
                startPosition = index + oldSubstring.length();
            }
        }

        return newString.toString();
    }

    /**
     * Removes spaces from right side of string
     * except when the string is all spaces
     */
    public static String rTrim(String s)
    {
        if (s.trim().length() > 0)
        {
            while (s.endsWith(" "))
            {
                s = s.substring(0, s.length() - 1);
            }
        }

        return s;
    }

    /**
     * Converts the passed in string into a List of Strings
     * Parsing by a line feed
     * <NUMBER_PATTERN/>
     *
     * @param sIn The string to be converted
     * @return Returns a vector of strings
     */
    public static List stringToVector(String sIn)
    {
        StringTokenizer s = new StringTokenizer(sIn, "\n");
        List sList = new ArrayList();

        while (s.hasMoreTokens())
        {
            String sLine = new String(Util.rTrim(s.nextToken().replace('\n', ' ').replace('\r', ' ')));

            sList.add(sLine);
        }

        return sList;
    }

    /**
     * This method will take in a ResultSet and pass back a List of
     * Hashtables.  The Map represents one row and the List represents
     * all the rows in the resultset.
     */
    public static List resultSetToVOH(ResultSet rs) throws Exception
    {
        List vOut = new ArrayList();

        try
        {
            ResultSetMetaData rsm = rs.getMetaData();
            int numCols = rsm.getColumnCount();

            String[] colNames = new String[numCols];

            for (int i = 1; i <= numCols; i++)
            {
                colNames[i - 1] = rsm.getColumnName(i);
            }

            while (rs.next())
            {
                Map hOut = new HashMap();

                for (int i = 1; i <= numCols; i++)
                {
                    /*System.out.println("Column Name = "
                    + rsm.getColumnName(i));
                    System.out.println("Column Type = "
                    + rsm.getColumnTypeName(i));
                    System.out.println("SQL Type = "
                    + rsm.getColumnType(i));
                    */
                    hOut.put(colNames[i - 1], rs.getObject(colNames[i - 1]));
                }

                vOut.add(hOut);
            }

            return vOut;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public static EDITBigDecimal roundToNearestCent(BigDecimal value)
    {
        return roundToNearestCent(new EDITBigDecimal(value));
    }

    //SRAMAM 09/2004 DOUBLE2DECIMAL

    /*    public static double roundDouble(double value, int scale)
        {

            BigDecimal bd1 = new BigDecimal(value);

            return bd1.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
     */

    //SRAMAM 09/2004 DOUBLE2DECIMAL
    //public static double roundToNearestCent(double value)

    /*    public static double roundToNearestCent(double value)
        {

            double newValue = value * 100;
            newValue = Math.round(newValue);
            newValue = newValue / 100;

            return newValue;
        }
    */
    public static EDITBigDecimal roundToNearestCent(EDITBigDecimal value)
    {
        return value.round(2);
    }

    public static EDITBigDecimal roundWithNoCents(EDITBigDecimal value)
    {
        return value.round(0);
    }

    public static EDITBigDecimal roundWithNoCents(BigDecimal value)
    {
        return roundWithNoCents(new EDITBigDecimal(value));
    }

    public static EDITBigDecimal roundUnits(BigDecimal value, int scale)
    {
        return roundUnits(new EDITBigDecimal(value), scale);
    }

    public static EDITBigDecimal roundUnits(EDITBigDecimal value, int scale)
    {
        return value.round(scale);
    }

    public static EDITBigDecimal roundDollars(BigDecimal value)
    {
        return roundDollars(new EDITBigDecimal(value));
    }

    //SRAMAM 09/2004 DOUBLE2DECIMAL

    /*public static double roundDollars(double value)
    {

        StringTokenizer valueToRound = new StringTokenizer(value + "", ".");
        String left = valueToRound.nextToken();
        int leftValue = Integer.parseInt(left);
        String right = valueToRound.nextToken();
        String returnValue = value + "";
        if (right.length() > 2)
        {

            right = right.substring(0, 3);
            int last = Integer.parseInt(right.substring(2));
            int middle = Integer.parseInt(right.substring(1, 2));
            int first = Integer.parseInt(right.substring(0, 1));
            if (last >= 5)
            {
                if (middle == 9)
                {
                    if (first == 9)
                    {
                        leftValue += 1;
                        first = 0;
                        middle = 0;
                    }

                    else
                    {

                        first += 1;
                        middle = 0;
                    }
                }

                else
                {

                    middle += 1;
                }
            }

            returnValue = leftValue + "." + first + middle;

        }
        return (Double.valueOf(returnValue)).doubleValue();
    } */
    public static EDITBigDecimal roundDollars(EDITBigDecimal value)
    {
        return roundToNearestCent(value);
    }

    public static EDITBigDecimal roundAllocation(BigDecimal value)
    {
        return roundAllocation(new EDITBigDecimal(value));
    }
    
    public static EDITBigDecimal roundAllocationToFourDecimals(EDITBigDecimal value)
    {	
    	StringTokenizer valueToRound = new StringTokenizer(value.toString(), ".");
        String left = valueToRound.nextToken();
        int leftValue = Integer.parseInt(left);
        String returnValue = value.toString();

        if (valueToRound.hasMoreTokens())
        {
            String right = valueToRound.nextToken();

            if (right.length() > 4)
            {
                right = right.substring(0, 5);

                int last = Integer.parseInt(right.substring(4));
                int middleC = Integer.parseInt(right.substring(3, 4));
                int middleB = Integer.parseInt(right.substring(2, 3));
                int middleA = Integer.parseInt(right.substring(1, 2));
                int first = Integer.parseInt(right.substring(0, 1));

                if (last >= 5)
                {
                	if (middleC == 9){
                		if (middleB == 9)
                		{
                			if (middleA == 9)
                			{
                				if (first == 9)
                				{
                					leftValue += 1;
                					first = 0;
                					middleA = 0;
                					middleB = 0;
                					middleC = 0;
                				}
                				else
                				{
                					first += 1;
                					middleA = 0;
                					middleB = 0;
                					middleC = 0;
                				}
                			}
                			else
                			{
                				middleA += 1;
                				middleB = 0;
                				middleC = 0;
                			}
                		}
                		else
                		{
                			middleB += 1;
                			middleC = 0;
                		}
                	}
                	else
                	{
                		middleC += 1;
                	}
                }

                returnValue = leftValue + "." + first + middleA + middleB + middleC;
            }
        }

        return new EDITBigDecimal(returnValue);
    }

    //SRAMAM 09/2004 DOUBLE2DECIMAL

    /*  public static double roundAllocation(double value)
      {
          StringTokenizer valueToRound = new StringTokenizer(value + "", ".");
          String left = valueToRound.nextToken();
          int leftValue = Integer.parseInt(left);
          String right = valueToRound.nextToken();
          String returnValue = value + "";
          if (right.length() > 3)
          {
              right = right.substring(0, 4);
              int last = Integer.parseInt(right.substring(3));
              int middleB = Integer.parseInt(right.substring(2, 3));
              int middleA = Integer.parseInt(right.substring(1, 2));
              int first = Integer.parseInt(right.substring(0, 1));
              if (last >= 5)
              {
                  if (middleB == 9)
                  {
                      if (middleA == 9)
                      {
                          if (first == 9)
                          {
                              leftValue += 1;
                              first = 0;
                              middleA = 0;
                              middleB = 0;
                          }
                          else
                          {
                              first += 1;
                              middleA = 0;
                              middleB = 0;
                          }
                      }
                      else
                      {
                          middleA += 1;
                          middleB = 0;
                      }
                  }
                  else
                  {
                      middleB += 1;
                  }
              }

              returnValue = leftValue + "." + first + middleA + middleB;

          }

          return (Double.valueOf(returnValue)).doubleValue();
      }
      */
    public static EDITBigDecimal roundAllocation(EDITBigDecimal value)
    {
        StringTokenizer valueToRound = new StringTokenizer(value.toString(), ".");
        String left = valueToRound.nextToken();
        int leftValue = Integer.parseInt(left);
        String returnValue = value.toString();

        if (valueToRound.hasMoreTokens())
        {
            String right = valueToRound.nextToken();

            if (right.length() > 3)
            {
                right = right.substring(0, 4);

                int last = Integer.parseInt(right.substring(3));
                int middleB = Integer.parseInt(right.substring(2, 3));
                int middleA = Integer.parseInt(right.substring(1, 2));
                int first = Integer.parseInt(right.substring(0, 1));

                if (last >= 5)
                {
                    if (middleB == 9)
                    {
                        if (middleA == 9)
                        {
                            if (first == 9)
                            {
                                leftValue += 1;
                                first = 0;
                                middleA = 0;
                                middleB = 0;
                            }
                            else
                            {
                                first += 1;
                                middleA = 0;
                                middleB = 0;
                            }
                        }
                        else
                        {
                            middleA += 1;
                            middleB = 0;
                        }
                    }
                    else
                    {
                        middleB += 1;
                    }
                }

                returnValue = leftValue + "." + first + middleA + middleB;
            }
        }

        return new EDITBigDecimal(returnValue);
    }

    //SRAMAM 09/2004 DOUBLE2DECIMAL
    //This method is not right and not used so being removed

    /*
    public static EDITBigDecimal roundString(String value)
    {
        return new EDITBigDecimal((Double.valueOf(roundDoubleToString(value))).doubleValue()+"");
    }
    */
    public static String roundDoubleToString(String value)
    {
        return Util.formatDecimal("###,###,##0.00", new EDITBigDecimal(value));
    }

    public static String marshalVO(Object voObject)
    {
        String xml = null;

        if (voObject != null)
        {
            StringWriter writer = null;

            try
            {
                Method marshalMethod = voObject.getClass().getDeclaredMethod("marshal", new Class[] { Writer.class });

                writer = new StringWriter();

                marshalMethod.invoke(voObject, new Object[] { writer });

                xml = writer.toString();
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
            finally
            {
                if (writer != null)
                {
                    try
                    {
                        writer.close();
                    }
                    catch (IOException e)
                    {
                        System.out.println(e);

                        e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return xml;
    }

    public static Object unmarshalVO(Class voClass, String voAsXML) throws Exception
    {
        if (voAsXML != null)
        {
            StringReader reader = new StringReader(voAsXML);

            Method unmarshalMethod = voClass.getDeclaredMethod("unmarshal", new Class[] { Reader.class });

            Object voObject = unmarshalMethod.invoke(voClass, new Object[] { reader });

            reader.close();

            return voObject;
        }
        else
        {
            return null;
        }
    }

    public static String[] marshalVOs(Object[] voObjects) throws Exception
    {
        if (voObjects != null)
        {
            String[] vosAsXML = new String[voObjects.length];

            for (int i = 0; i < vosAsXML.length; i++)
            {
                vosAsXML[i] = marshalVO(voObjects[i]);
            }

            return vosAsXML;
        }
        else
        {
            return null;
        }
    }

    public static Object[] unmarshalVOs(Class voClass, String[] vosAsXML) throws Exception
    {
        if (vosAsXML != null)
        {
            Object voObjects = Array.newInstance(voClass, vosAsXML.length);

            for (int i = 0; i < vosAsXML.length; i++)
            {
                Array.set(voObjects, i, unmarshalVO(voClass, vosAsXML[i]));
            }

            return (Object[]) voObjects;
        }
        else
        {
            return null;
        }
    }

    //    public static boolean isANumber(String val)
    //    {
    //
    //        if (val != null)
    //        {
    //
    //            try
    //            {
    //
    //                BigDecimal bd = new BigDecimal(val.trim());
    //
    //                return true;
    //            }
    //            catch (Exception e)
    //            {
    //
    //                return false;
    //            }
    //        }
    //        else
    //        {
    //
    //            return false;
    //        }
    //    }
    //SRAMAM 09/2004 DOUBLE2DECIMAL
    //This method is not used so commented out

    /*
    public static double convertStringToNumber(String val)
    {

        BigDecimal bd = new BigDecimal(val.trim());

        return bd.doubleValue();
    }
    */

    /**
     * Determines if a string is null or empty
     *
     * @param string            string to be evaluated
     *
     * @return  true if string is null or empty, false otherwise
     */
    public static boolean isStringNullOrEmpty(String string)
    {
        if (string == null)
        {
            return true;
        }
        else
        {
            if (string.length() == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public static Object[] joinArrays(Object[] array1, Object[] array2, Class classType)
    {
        Object[] returnValue = null;

        if ((array1 != null) && (array2 != null))
        {
            Object elements = Array.newInstance(classType, array1.length + array2.length);

            for (int i = 0; i < array1.length; i++)
            {
                Array.set(elements, i, array1[i]);
            }

            for (int i = 0; i < array2.length; i++)
            {
                Array.set(elements, array1.length + i, array2[i]);
            }

            returnValue = (Object[]) elements;
        }
        else if (array1 != null)
        {
            returnValue = array1;
        }
        else if (array2 != null)
        {
            return array2;
        }
        else
        {
            returnValue = null;
        }

        return returnValue;
    }

    public static Object[] invertObjects(Object[] objectsToInvert)
    {
        if ((objectsToInvert != null) && (objectsToInvert.length > 0))
        {
            List invertedObjects;

            invertedObjects = new ArrayList();

            Class elementType = objectsToInvert[0].getClass();

            for (int i = objectsToInvert.length - 1; i >= 0; i--)
            {
                invertedObjects.add(objectsToInvert[i]);
            }

            return (Object[]) invertedObjects.toArray((Object[]) Array.newInstance(elementType, objectsToInvert.length));
        }
        else
        {
            return objectsToInvert;
        }
    }

    public static Object[] sortObjects(Object[] objectsToSort, String[] methodNamesToSortBy)
    {
        if ((objectsToSort != null) && (objectsToSort.length > 0))
        {
            try
            {
                TreeMap map = new TreeMap();

                Class elementClassType = objectsToSort[0].getClass();

                if (objectsToSort[0] instanceof HibernateProxy)
                {
                    elementClassType = HibernateProxyHelper.getClassWithoutInitializingProxy(objectsToSort[0]);
                }

                Object sortedObjects = null;

                //                Method  m   = objectsToSort[0].getClass().getDeclaredMethod(methodNameToSortBy, null);
                for (int i = 0; i < objectsToSort.length; i++)
                {
                    StringBuilder sortKey = new StringBuilder();

                    for (int j = 0; j < methodNamesToSortBy.length; j++)
                    {
                        Object keyElement = objectsToSort[i].getClass().getMethod(methodNamesToSortBy[j], null).invoke(objectsToSort[i], null);

                        String keyElementStr = null;

                        if (keyElement != null)
                        {
                            keyElementStr = keyElement.toString().trim().toUpperCase();

                            if (isANumber(keyElementStr))
                            {
                                String zeros = "";

                                for (int k = 0; k < (20 - keyElementStr.length()); k++)
                                {
                                    zeros += "0";
                                }

                                keyElementStr = zeros + keyElementStr;
                            }

                            sortKey.append(keyElementStr);
                        }
                    }

                    map.put(sortKey.toString() + i, objectsToSort[i]);
                }

                sortedObjects = Array.newInstance(elementClassType, map.size());

                Iterator it = map.values().iterator();
                int i = 0;

                while (it.hasNext())
                {
                    Array.set(sortedObjects, i++, it.next());
                }

                return (Object[]) sortedObjects;
            }
            catch (Exception e)
            {
                System.out.println("Util.sortObjects() " + e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }
        else
        {
            return objectsToSort;
        }
    }

    public static Object[] filterObjects(Object[] objectsToFilter, String methodNameToFilterBy, String filterString)
    {
        if ((objectsToFilter != null) && (objectsToFilter.length > 0))
        {
            try
            {
                TreeMap map = new TreeMap();
                Class elementClassType = objectsToFilter[0].getClass();
                Object filteredObjects = null;

                for (int i = 0; i < objectsToFilter.length; i++)
                {
                    StringBuilder filterKey = new StringBuilder();

                    Object keyElement = objectsToFilter[i].getClass().getMethod(methodNameToFilterBy, null).invoke(objectsToFilter[i], null);

                    String keyElementStr = null;

                    if (keyElement != null)
                    {
                        keyElementStr = keyElement.toString().trim();

                        if (keyElementStr.startsWith(filterString))
                        {
                            filterKey.append(keyElementStr);
                            map.put(filterKey.toString() + i, objectsToFilter[i]);
                        }
                    }
                }

                filteredObjects = Array.newInstance(elementClassType, map.size());

                Iterator it = map.values().iterator();
                int i = 0;

                while (it.hasNext())
                {
                    Array.set(filteredObjects, i++, it.next());
                }

                return (Object[]) filteredObjects;
            }
            catch (Exception e)
            {
                System.out.println("Util.filterObjects() " + e);

                return objectsToFilter;
            }
        }
        else
        {
            return objectsToFilter;
        }
    }

    public static Object deepClone(Object obj) throws Exception
    {
        if (obj instanceof Serializable)
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(baos));

            oos.writeObject(obj);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bais));

            Object deserializedObj = ois.readObject();

            ois.close();

            return deserializedObj;
        }

        else
        {
            throw new Exception("Cannot deep clone - object not serializable");
        }
    }

    public static long[] convertLongToPrim(Long[] longsAsObj)
    {
        long[] longsAsPrim = new long[longsAsObj.length];

        for (int i = 0; i < longsAsPrim.length; i++)
        {
            longsAsPrim[i] = longsAsObj[i].longValue();
        }

        return longsAsPrim;
    }

    public static String[] fastTokenizer(String s, String delimiter)
    {
        return fastTokenizer(s, delimiter, false);
    }
    
    /**
     * Overloaded to allow the caller to specified whether-or-not the values
     * should be trimmed.
     * @param s
     * @param delimiter
     * @param trim
     * @return
     */
    public static String[] fastTokenizer(String s, String delimiter, boolean trim)
    {
        s = s.trim();

        List tokens = new ArrayList();

        String sub = null;
        
        int i = 0;
        
        int j = s.indexOf(delimiter); // First substring

        while (j >= 0)
        {
            sub = s.substring(i, j);
            
            i = j + 1;
            
            j = s.indexOf(delimiter, i); // Rest of substrings

            tokens.add(trim?sub.trim():sub);
        }

        sub = s.substring(i); // Last substring
        
        tokens.add(trim?sub.trim():sub);

        return (String[]) tokens.toArray(new String[tokens.size()]);
    }    

    public static String initString(String value, String defaultValue)
    {
        value = ((value == null) || (value.equals("")) || value.equals("null")) ?
                (defaultValue == null ? null : defaultValue.trim()) : value.trim();

        return value;
    }

    /**
     * Uses reflection to get the string value of the specified property. If the VO or property are null,
     * the value is set to the specified default value.
     *
     * @param vo
     * @param property     name of the VO String property in java-proper case.
     * @param defaultValue
     * @return
     */
    public static String initString(VOObject vo, String property, String defaultValue)
    {
        String stringValue = null;

        try
        {
            if (vo == null)
            {
                stringValue = defaultValue;
            }
            else
            {
                property = convertFirstCharacterCase(property, Util.UPPER_CASE);

                String getterName = "get" + property;

                Method getter = vo.getClass().getMethod(getterName, null);

                stringValue = (String) getter.invoke(vo, null);

                if (stringValue == null)
                {
                    stringValue = defaultValue;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return stringValue;
    }

    public static String mapOptionCodeCTToRuleStructureName(String optionCode)
    {
        String ruleStructureName = null;

        if (optionCode.equalsIgnoreCase("LOA"))
        {
            ruleStructureName = "Life";
        }

        else if (optionCode.equalsIgnoreCase("PCA"))
        {
            ruleStructureName = "PerCert";
        }

        else if (optionCode.equalsIgnoreCase("LPC"))
        {
            ruleStructureName = "LifePerCert";
        }

        else if (optionCode.equalsIgnoreCase("JSA"))
        {
            ruleStructureName = "JTLife";
        }

        else if (optionCode.equalsIgnoreCase("JPC"))
        {
            ruleStructureName = "JTPerCert";
        }

        else if (optionCode.equalsIgnoreCase("LCR"))
        {
            ruleStructureName = "LifeCashRefund";
        }

        else if (optionCode.equalsIgnoreCase("AMC"))
        {
            ruleStructureName = "AmtCert";
        }

        else if (optionCode.equalsIgnoreCase("INR"))
        {
            ruleStructureName = "InstRef";
        }

        else if (optionCode.equalsIgnoreCase("INT"))
        {
            ruleStructureName = "IntOnly";
        }

        else if (optionCode.equalsIgnoreCase("TML"))
        {
            ruleStructureName = "TmpLife";
        }

        else if (optionCode.equalsIgnoreCase("DFA"))
        {
            ruleStructureName = "DefAnn";
        }

        return ruleStructureName;
    }

    /**
     * Checks for the length of the string to be greater than or equal to the specified minimum length
     *
     * @param str
     * @param minLength
     * @return true if length of string is greater than or equal to minLength
     */
    public static boolean isGEMinLength(String str, int minLength)
    {
        if (str.length() >= minLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Checks for the length of the string to be less than or equal to the specified maximum length
     *
     * @param str
     * @param maxLength
     * @return true if length of string is less than or equal to maxLength
     */
    public static boolean isLEMaxLength(String str, int maxLength)
    {
        if (str.length() <= maxLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Checks the specified string for mixed case.  At least one char must be lowercase and one must be uppercase.
     * <NUMBER_PATTERN/>
     * Strips off any non-alphabetic chars before testing.
     *
     * @param str
     * @return boolean - true if str is not all uppercase or is not all lowercase
     */
    public static boolean isMixedCase(String str)
    {
        String alphabeticOnly = str;

        if (!Util.isAlphabetic(str))
        {
            //  There are non-alphabetic characters in the string, strip them off
            alphabeticOnly = Util.removeNonAlphabeticChars(str);
        }

        //  The alphabetic string may not be all uppercase or all lowercase
        boolean allLower = Util.isAllLowerCase(alphabeticOnly);
        boolean allUpper = Util.isAllUpperCase(alphabeticOnly);

        if (allLower || allUpper)
        {
            //  Not mixed case
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Checks to see if all chars in the string are lowercase
     *
     * @param str
     * @return true if all chars are lowercase
     */
    public static boolean isAllLowerCase(String str)
    {
        Pattern p = Pattern.compile("[a-z]*");

        Matcher m = p.matcher(str);

        return m.matches();
    }

    /**
     * Checks to see if all chars in the string are uppercase
     *
     * @param str
     * @return true if all chars are uppercase
     */
    public static boolean isAllUpperCase(String str)
    {
        Pattern p = Pattern.compile("[A-Z]*");

        Matcher m = p.matcher(str);

        return m.matches();
    }

    /**
     * Checks to see if all chars in the string are either letters or numbers
     *
     * @param str
     * @return true if string only contains letters and numbers
     */
    public static boolean isAlphaNumeric(String str)
    {
        Pattern p = Pattern.compile("[a-zA-Z0-9]*");

        Matcher m = p.matcher(str);

        return m.matches();
    }

    /**
     * Checks to see if all chars in the string are letters
     *
     * @param str
     * @return true if string only contains letters
     */
    public static boolean isAlphabetic(String str)
    {
        Pattern p = Pattern.compile("[a-zA-Z]*");

        Matcher m = p.matcher(str);

        return m.matches();
    }

    /**
     * Checks for at least one alphabetic character in the string.
     *
     * @param str - string to be tested
     * @return true if the string contains at least one alphabetic character
     */
    public static boolean hasAlphabeticChar(String str)
    {
        Pattern p = Pattern.compile("[a-zA-Z]");

        Matcher m = p.matcher(str);

        return m.find(); // Match the first sequence without comparing the entire string
    }

    /**
     * Checks for at least one numeric character in the string
     *
     * @param str - string to be tested
     * @return true if the string contains at least one numeric character
     */
    public static boolean hasNumericChar(String str)
    {
        Pattern p = Pattern.compile("[0-9]");

        Matcher m = p.matcher(str);

        return m.find(); // Match the first sequence without comparing the entire string
    }

    /**
     * Checks for any special characters in the string.  Special characters are any characters other than letters or
     * numbers
     *
     * @param str - string to be tested
     * @return true if contains any non-letter or non-number characters
     */
    public static boolean hasSpecialCharacter(String str)
    {
        boolean isAlphabetic = Util.isAlphabetic(str);
        boolean isAlphanumeric = Util.isAlphaNumeric(str);

        if (isAlphabetic || isAlphanumeric)
        {
            // Has just letters or letters and numbers - does not have any special characters
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Checks for at least one space in the string.
     *
     * @param str               string to be checked
     * @return  true if string contains a space, false otherwise
     */
    public static boolean hasSpace(String str)
    {
        Pattern p = Pattern.compile("[ ]");

        Matcher m = p.matcher(str);

        return m.find(); // Match the first sequence without comparing the entire string
    }

    /**
     * Removes any characters that are not letters and returns the modified string
     *
     * @param str
     * @return string with any non-letters removed
     */
    public static String removeNonAlphabeticChars(String str)
    {
        String alphabeticOnly = "";

        Pattern p = Pattern.compile("[^a-z^A-Z]");

        String[] strings = p.split(str);

        for (int i = 0; i < strings.length; i++)
        {
            alphabeticOnly = alphabeticOnly + strings[i];
        }

        return alphabeticOnly;
    }

    /**
     * Removes any characters that are not numbers and returns the modified string
     *
     * @param str
     * @return string with any non-letters removed
     */
    public static String removeNonNumericChars(String str)
    {
        String numberOnly = "";

        Pattern p = Pattern.compile("[^0-9]");

        String[] strings = p.split(str);

        for (int i = 0; i < strings.length; i++)
        {
            numberOnly = numberOnly + strings[i];
        }

        return numberOnly;
    }

    public static String getProductStructure(ProductStructureVO productStructureVO, String delimeter)
    {
        String productStructure = "";
        Company company = Company.findByPK(productStructureVO.getCompanyFK());
        String companyName = company.getCompanyName();
        String marketingPackageName = productStructureVO.getMarketingPackageName();
        String groupProductName = productStructureVO.getGroupProductName();
        String areaName = productStructureVO.getAreaName();
        String businessContractName = productStructureVO.getBusinessContractName();

        productStructure = companyName + delimeter;
        productStructure += (marketingPackageName + delimeter);
        productStructure += (groupProductName + delimeter);
        productStructure += (areaName + delimeter);
        productStructure += businessContractName;

        return productStructure;
    }

    /**
     * Round the values of the specified fields to a dollar currency amount
     * @param voObject - the VO object whose fields and values are effected
     * @param fieldNames - array containing the names of the fields whose values will be rounded
     * @return modified voObject
     * @throws Exception
     */

    //    public static Object roundDollarFields(Object voObject, String[] fieldNames) throws Exception
    //    {
    //        VOClass voClass = VOClass.getVOClassMetaData(voObject.getClass());
    //
    //        VOMethod[] getters = voClass.getSimpleAndCTAndFKAndPKGetters();
    //
    //        List fieldNamesList = Arrays.asList(fieldNames);
    //
    //        if (getters != null)
    //        {
    //            for (int i = 0; i < getters.length; i++)
    //            {
    //                String fieldName = getters[i].getMethod().getName();
    //
    //                fieldName = fieldName.substring(3, fieldName.length());
    //                double roundedField = 0;
    //                if (fieldNamesList.contains(fieldName))
    //                {
    //                    double fieldValue = ((Double)getters[i].getMethod().invoke(voObject, null)).doubleValue() ;
    //                    String value = fieldValue + "";
    //                    if (value.indexOf("E") > 0)
    //                    {
    //                        DecimalFormat twoDecPlcFormatter = new DecimalFormat("0.##");
    //                        roundedField = fieldValue;
    //                        String buffer = new String(twoDecPlcFormatter.format(roundedField));
    //                        System.out.println("number = " + buffer);
    //
    //                    }
    //                    else
    //                    {
    //                        roundedField = roundDollars(fieldValue);
    //                    }
    //                    VOMethod setter = voClass.getSimpleSetter(fieldName);
    //                    setter.getMethod().invoke(voObject, new Object[]{new Double(roundedField)});
    //                }
    //            }
    //        }
    //
    //        return voObject;
    //    }

    /**
     * All xml outputs now use dom to find the fields that need rounding.  The double is rounded using DecimalFormat
     * in order to produce a string output for the xml document.
     */
    public static String roundDollarTextFields(Object voObject, String[] fieldNames)
    {
        Document document = null;

        try
        {
            document = XMLUtil.parse(Util.marshalVO(voObject));

            roundFieldsInList(fieldNames, document);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return document.asXML();
    }

    private static void roundFieldsInList(String[] fieldNames, Document document)
    {
        Element targetElement = null;
        Element rootElement = document.getRootElement();
        String elementName = rootElement.getQualifiedName();

        for (int i = 0; i < fieldNames.length; i++)
        {
            String[] pathTokens = Util.fastTokenizer(fieldNames[i], ".");

            if (elementName.equals(pathTokens[0]))
            {
                List childElements = rootElement.selectNodes(pathTokens[1]);

                if (!childElements.isEmpty())
                {
                    roundTarget((Element) childElements.get(0));
                }
            }
            else
            {
                targetElement = treeWalk(rootElement, pathTokens[0], pathTokens[1]);
            }
        }
    }

    private static Element treeWalk(Element element, String voName, String fieldName)
    {
        Element targetElement = null;

        for (int i = 0, size = element.nodeCount(); i < size; i++)
        {
            Node node = element.node(i);
            String nodeName = node.getName();

            if (node instanceof Element)
            {
                if (nodeName.equals(voName))
                {
                    List childElements = node.selectNodes(fieldName);

                    if (!childElements.isEmpty())
                    {
                        targetElement = (Element) childElements.get(0);
                    }
                }
                else if (nodeName.endsWith("VO"))
                {
                    targetElement = treeWalk((Element) node, voName, fieldName);
                }
            }

            if (targetElement != null)
            {
                roundTarget(targetElement);
            }
        }

        return targetElement;
    }

    private static void roundTarget(Element targetElement)
    {
        DecimalFormat twoDecPlcFormatter = new DecimalFormat("0.##");

        String textValue = targetElement.getText();

        String roundedField = new String(twoDecPlcFormatter.format(Double.parseDouble(textValue)));

        targetElement.setText(roundedField);
    }

    /**
     * Return true if the supplied String can be successfully converted into a number.
     *
     * @param str
     * @return
     */
    public static boolean isANumber(String str)
    {
        Matcher m = NUMBER_PATTERN.matcher(str);

        return m.matches();
    }

    /**
     * Pads an n-digit number with "0".
     * Examples:
     * padWithZero("11", 4) = "0011"
     * padWithZero("11", 3) = "011"
     * padWithZero("11", 2) = "11"
     * padWithZero("11", 1) = "11".  Ignored
     *
     * @param number
     * @param numPlaceValues the final number of whole-number place values
     * @return
     */
    public static String padWithZero(String number, int numPlaceValues)
    {
        int numLength = number.length();

        int numZeros = numPlaceValues - numLength;

        String zeroString = "";

        for (int i = 0; i < numZeros; i++ )
        {
            zeroString += "0";
        }

        number = zeroString + number;

        return number;
    }

    public static String addOnTrailingZero(String value)
    {
        int indexOfDecimal = value.indexOf(".");

        if (value.substring(indexOfDecimal + 1).length() == 1)
        {
            value = value + "0";
        }

        return value;
    }

    public static String addOnTrailingSpaces(String value, int newLength)
    {
        int index = value.length();

        for (int i = index + 1; i < newLength; i++)
        {
              value = value + " ";
        }

        return value;
    }

    /**
     * Uses reflection to get the BigDecimal value of the specified property. If the VO or property is null,
     * the value is set to the specified default value.
     *
     * @param vo
     * @param property     name of the VO BigDecimal property in java-proper case.
     * @param defaultValue
     * @return
     */
    public static BigDecimal initBigDecimal(VOObject vo, String property, BigDecimal defaultValue)
    {
        BigDecimal bigDecimalValue = null;

        try
        {
            if (vo == null)
            {
                bigDecimalValue = defaultValue;
            }
            else
            {
                property = convertFirstCharacterCase(property, Util.UPPER_CASE);

                String getterName = "get" + property;

                Method getter = vo.getClass().getMethod(getterName, null);

                bigDecimalValue = (BigDecimal) getter.invoke(vo, null);

                if (bigDecimalValue == null)
                {
                    bigDecimalValue = defaultValue;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return bigDecimalValue;
    }
    
    /**
     * Convenience method to convert the Long value of the specified non-null String,
     * or default to a specified value which includes null.
     * @param value the String value to convert to Long unless it is null
     * @param defaultValue the value to convert to if the specified value is null
     * @return the Long value, or null is that is the default
     */
    public static Long initLong(String value, Long defaultValue)
    {
        value = initString(value,  null);

        Long theValue = null;
        
        if (value != null)
        {
            theValue = new Long(value);
        }
        else
        {
            if (defaultValue != null)
            {
                theValue = defaultValue;
            }
        }
        
        return theValue;
    }

    /**
     * Uses reflection to get the long value of the specified property. If the VO is null,
     * the value is set to the specified default value.
     *
     * @param vo
     * @param property     name of the VO BigDecimal property in java-proper case.
     * @param defaultValue
     * @return
     */
    public static long initLong(VOObject vo, String property, long defaultValue)
    {
        Long longValue = null;

        try
        {
            if (vo == null)
            {
                longValue = new Long(defaultValue);
            }
            else
            {
                property = convertFirstCharacterCase(property, Util.UPPER_CASE);

                String getterName = "get" + property;

                Method getter = vo.getClass().getMethod(getterName, null);

                longValue = (Long) getter.invoke(vo, null);

                if (longValue == null)
                {
                    longValue = new Long(defaultValue);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return longValue.longValue();
    }

    /**
     * Convenience method to convert the Integer value of the specified non-null String,
     * or default to a specified value which includes null.
     *
     * @param value the String value to convert to Integer unless it is null
     * @param defaultValue the value to convert to if the specified value is null
     *
     * @return the Integer value, or null is that is the default
     */
    public static Integer initInt(String value, Integer defaultValue)
    {
        value = initString(value,  null);

        Integer theValue = null;

        if (value != null)
        {
            theValue = new Integer(value);
        }
        else
        {
            if (defaultValue != null)
            {
                theValue = defaultValue;
            }
        }

        return theValue;
    }


    /**
     * Creates an EDITBigDecimal object if the stringValue is not null or empty.  Otherwise, returns null
     * @param stringValue
     * @return
     */
    public static EDITBigDecimal initEDITBigDecimal(String stringValue, EDITBigDecimal defaultValue)
    {
        EDITBigDecimal value = null;

        if ((stringValue != null) && (!stringValue.equals("")))
        {
            value = new EDITBigDecimal(stringValue);
        }
        else
        {
            if (defaultValue != null)
            {
                value = defaultValue;
            }
        }

        return value;
    }

    /**
     * Returns the specified String with the first character converted to desired case.
     * @return
     */
    public static String convertFirstCharacterCase(String aString, int firstCharacterCase)
    {
        String firstCharacter = aString.substring(0, 1);

        int stringLength = aString.length();

        String remainingCharacters = aString.substring(stringLength - (stringLength - 1), stringLength);

        switch (firstCharacterCase)
        {
        case Util.UPPER_CASE:
            firstCharacter = firstCharacter.toUpperCase();

            break;

        case Util.LOWER_CASE:
            firstCharacter = firstCharacter.toLowerCase();

            break;
        }

        return firstCharacter + remainingCharacters;
    }

    /**
     * Converts the specified String to its specified Object type. For example, a String of "1.11" for an Object type
     * of BigDecimal would return new BigDecimal("1.11").
     * @param stringValue
     * @param classType
     * @return
     */
    public static Object convertStringToObject(String stringValue, Class classType, boolean convertDates)
    {
        Object returnValue = null;

        if (stringValue == null)
        {
            //  If it is a primitive type, return zero (null is not valid)
            if (classType == int.class || classType == long.class)
            {
                returnValue = 0;
            }
            else
            {
                returnValue = null;
            }
        }
        else if (classType == String.class)
        {
            returnValue = stringValue;
        }
        else if ((classType == long.class) || (classType == Long.class))
        {
            returnValue = new Long(Util.initString(stringValue, "0"));
        }
        else if ((classType == int.class) || (classType == Integer.class))
        {
            returnValue = new Integer(Util.initString(stringValue, "0"));
        }
        else if (classType == BigDecimal.class)
        {
            returnValue = new BigDecimal(Util.initString(stringValue, "0"));
        }
        else if (classType == EDITBigDecimal.class)
        {
            returnValue = new EDITBigDecimal(Util.initString(stringValue, "0"));
        }
        else if (classType == EDITDate.class)
        {
            if (convertDates)
            {
                returnValue = new EDITDate(DateTimeUtil.formatMMDDYYYYToYYYYMMDD(stringValue));
            }
            else
            {
            returnValue = new EDITDate(stringValue);
        }
        }
        else if (classType == EDITDateTime.class)
        {
            returnValue = new EDITDateTime(stringValue);
        }

        return returnValue;
    }

    /**
     * Truncates the String to the specified length. The String.length must be >= length, and not null.
     * @param str
     * @param length
     * @return
     */
    public static String truncatateString(String str, int length)
    {
        // deck: corrected method return strings less that param->length

        if (str != null)
        {
            if (str.length() > length)
            {
                str = str.substring(0, length);
            }
        }

        return str;
    }

    /**
     * @see Util#mapFormDataToVO(javax.servlet.http.HttpServletRequest, Class)
     * @param request
     * @param entityClass
     * @return
     */
    public static Object mapFormDataToHibernateEntity(HttpServletRequest request, Class entityClass, String targetDB, boolean convertDates)
    {
        return mapFormDataToHibernateEntity(request, entityClass, targetDB, convertDates, true);
    }
    
    /**
     * @see Util#mapFormDataToVO(javax.servlet.http.HttpServletRequest, Class)
     * @param request
     * @param entityClass
     * @param addNewToHibernateSession if the entity is new (i.e. transient / not yet persisted), then add it to the current Hibernate Session
     * @return
     */
    public static Object mapFormDataToHibernateEntity(HttpServletRequest request, Class entityClass, String targetDB, boolean convertDates, boolean addNewToHibernateSession)
    {
    	
    	Enumeration<String> parameterNames = request.getParameterNames();
    	 
    	        while (parameterNames.hasMoreElements()) {
    	            String paramName = parameterNames.nextElement();
    	            System.out.print(paramName);
    	 
    	            String[] paramValues = request.getParameterValues(paramName);
    	            for (int i = 0; i < paramValues.length; i++) {
    	                String paramValue = paramValues[i];
    	                System.out.println(" - " + paramValue);
    	                System.out.println("");
    	            }
    	 
    	        }

        Object entityObject = null;

        String fullyQualifiedClassName = getFullyQualifiedClassName(entityClass);

        String className = getClassName(fullyQualifiedClassName);

        String pkName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length()) + "PK";

        String pkValue = Util.initString(request.getParameter(pkName), "0");

        try
        {
            if (pkValue.equals("0"))
            {
                if (addNewToHibernateSession)
                {
                entityObject = SessionHelper.newInstance(entityClass, targetDB);
            }
            else
            {
                    entityObject = entityClass.newInstance();   
                }
            }
            else
            {
                entityObject = SessionHelper.get(entityClass, new Long(pkValue), targetDB);
            }

            Method[] methods = entityClass.getMethods();

            for (int i = 0; i < methods.length; i++)
            {
                Method method = methods[i];

                if (validEntitySetter(method))
                {
                    String setterName = method.getName();

                    // with SessionHelper.newInstance() method no need to call setter for PK
                    if (!setterName.equals("set" + className + "PK"))
                    {
                        String paramName = setterName.substring(3, 4).toLowerCase() + setterName.substring(4);
                        
                        // Don't attempt to call a setter if the submitted Form doesn't contain the parameter.
                        if (Collections.list(request.getParameterNames()).contains(paramName))
                        {
                          Class paramType = method.getParameterTypes()[0];
  
                          String paramStringValue = Util.initString((String) request.getParameter(paramName), null);
  
                          Object paramValue = Util.convertStringToObject(paramStringValue, paramType, convertDates);

                          method.invoke(entityObject, new Object[] { paramValue });
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return entityObject;
    }

    /**
     * Returns the class name including the package name.
     * @param theClass
     * @return
     */
    public static String getFullyQualifiedClassName(Class theClass)
    {
        String className = theClass.getName();

        if (className.indexOf("$$EnhancerByCGLIB$$") > 0) // The class is a Hibernate proxy
        {
            int firstIndexOf$$ = className.indexOf("$$");

            className = className.substring(0, firstIndexOf$$);
        }

        return className;
    }

    /**
     * Returns the class name excluding the package name.
     * @param theClass
     * @return
     */
    public static String getClassName(String fullyQualifiedClassName)
    {
        if (fullyQualifiedClassName.indexOf(".") > 0)
        {
            int indexOfLastDot = fullyQualifiedClassName.lastIndexOf(".");

            fullyQualifiedClassName = fullyQualifiedClassName.substring(indexOfLastDot + 1);
        }

        return fullyQualifiedClassName;
    }



    /**
     * Only setters that set a restricted set of data types are valid. Those types are:
     * int
     * long
     * BigDecimal
     * String
     * Integer
     * Long
     * EDITBigDecimal
     * EDITDate
     * EDITDateTime
     * @return
     */
    private static boolean validEntitySetter(Method method)
    {
        boolean validSetter = false;

        String methodName = method.getName();

        if (methodName.startsWith("set"))
        {
            Class[] params = method.getParameterTypes();

            if (params.length == 1)
            {
                Class paramType = params[0];

                if (paramType == String.class)
                {
                    validSetter = true;
                }
                else if (paramType == int.class)
                {
                    validSetter = true;    
                }
                else if (paramType == long.class)
                {
                    validSetter = true;
                }
                else if (paramType == BigDecimal.class)
                {
                    validSetter = true;
                }
                else if (paramType == Integer.class)
                {
                    validSetter = true;
                }
                else if (paramType == Long.class)
                {
                    validSetter = true;
                }
                else if (paramType == EDITBigDecimal.class)
                {
                    validSetter = true;
                }
                else if (paramType == EDITDate.class)
                {
                    validSetter = true;
                }
                else if (paramType == EDITDateTime.class)
                {
                    validSetter = true;
                }
            }
        }

        return validSetter;
    }

    /**
     * Uses reflection to get the long value of the specified property. If the entityObject is null,
     * the value is set to the specified default value.
     *
     * @param entityObject
     * @param property
     * @param defaultValue
     * @return
     */
    public static long initLong(Object entityObject, String property, long defaultValue)
    {
        Long longValue = null;

        try
        {
            if (entityObject == null)
            {
                longValue = new Long(defaultValue);
            }
            else
            {
                property = convertFirstCharacterCase(property, Util.UPPER_CASE);

                String getterName = "get" + property;

                Method getter = entityObject.getClass().getMethod(getterName, null);

                longValue = (Long) getter.invoke(entityObject, null);

                if (longValue == null)
                {
                    longValue = new Long(defaultValue);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return longValue.longValue();
    }

    /**
     * Uses reflection to get the int value of the specified property. If the entityObject is null,
     * the value is set to the specified default value.
     *
     * @param entityObject
     * @param property
     * @param defaultValue
     * @return
     */
    public static int initInt(Object entityObject, String property, int defaultValue)
    {
        Integer intValue = null;

        try
        {
            if (entityObject == null)
            {
                intValue = new Integer(defaultValue);
            }
            else
            {
                property = convertFirstCharacterCase(property, Util.UPPER_CASE);

                String getterName = "get" + property;

                Method getter = entityObject.getClass().getMethod(getterName, null);

                intValue = (Integer) getter.invoke(entityObject, null);

                if (intValue == null)
                {
                    intValue = new Integer(defaultValue);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return intValue.intValue();
    }

    /**
     * Uses reflection to get the int value of the specified property. If the entityObject is null,
     * the value is set to the specified default value.
     *
     * @param entityObject
     * @param property
     * @param defaultValue
     * @return
     */
    public static Object initObject(Object entityObject, String property, Object defaultValue)
    {
        Object objectValue = null;

        try
        {
            if (entityObject == null)
            {
                objectValue = defaultValue;
            }
            else
            {
                property = convertFirstCharacterCase(property, Util.UPPER_CASE);

                String getterName = "get" + property;

                Method getter = entityObject.getClass().getMethod(getterName, null);

                objectValue = getter.invoke(entityObject, null);

                if (objectValue == null)
                {
                    objectValue = defaultValue;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return objectValue;
    }

    /**
     * Checks given String exists in given String array.
     * @param arrayToLook
     * @param stringToLook
     * @return
     */
    public static boolean verifyStringExistsInArray(String[] arrayToLook, String stringToLook)
    {
        boolean isExists = false;

        for (int i = 0; i < arrayToLook.length; i++)
        {
            if (arrayToLook[i].equalsIgnoreCase(stringToLook))
            {
                isExists = true;

                break;
            }
        }

        return isExists;
    }

    /**
     * Returns a key based message from the resource properties file.
     * see edit.common.EDITResources.properties for the listing of name value pairs.
     * @param key
     * @return
     */
    public static String getResourceMessage(String key)
    {
                ResourceBundle resources =  ResourceBundle.getBundle("edit.common.EDITResources");

                String responseMessage = resources.getString(key);

                return responseMessage;
//        return "The Batch Process Is Running - You Can Monitor Its Progress Via The 'Batch Stats' Icon";
    }

    /**
     * An overloaded convenience method that complements the base method. The specified
     * msgTokens are inserted, in their natural order, into the message corresponding to 
     * the supplied key. The key retrieves a resource message with imbedded tokens in the
     * format of {0} {1} ... {n}. The supplied
     * tokens are then substituted. 
     * For example:
     * key=my.message -> "A message with a {0} token and a {1} token and a {2} token." 
     * tokenValues = {"foo1", "foo2", "foo3"}
     * Final message = "A messag with a foo1 token and a foo2 token and a foo3 token."
     * @param key
     * @param tokenValues messages that can be inserted into the core resource message 
     * @return an modified/appended message
     */
    public static String getResourceMessage(String key, String[] tokenValues)
    {
        String resourceMessage = getResourceMessage(key);
       
        int expectedTokenNumber = resourceMessage.split("[{][0-9]+[}]").length - 1;

        int actualTokenNumber = tokenValues.length;
       
        Args.checkForRange(expectedTokenNumber, actualTokenNumber, actualTokenNumber);
       
        for (int i = 0; i < tokenValues.length; i++)
        {
            String token = "[{][" + i + "][}]";
       
            resourceMessage = resourceMessage.replaceFirst(token, tokenValues[i]);  
        }
       
        return resourceMessage;
    }

    /** 
     * Builds a default Element with the specified name and the specified text value.
     *
     * @param elementName
     * @param textValue can be null suggesting that this Element has no value
     * @return a default Element
     */
    public static Element buildElement(String elementName, String textValue)
    {
        Element element = new DefaultElement(elementName);

        if (textValue != null)
        {
            element.setText(textValue);
        }

        return element;
    }

    /**
     * Adds a new Element to the specified Element value with the specified name and specified value.
     * @param element
     * @param textValue
     */
    public static void addValue(Element element, String elementName, String textValue)
    {
        Element newElement = buildElement(elementName, textValue);

        element.add(newElement);
    }

	/**
     * Utility method to concatenate array of strings with seperator
     * @param strings - array of strings to concatenate
     * @param seperator - seperator between fields
     * @return the concatenated string with seperator.
     */
    public static String concatenateStrings(String[] strings, String seperator)
    {
        StringBuilder sb = new StringBuilder();

        if (seperator == null)
        {
            seperator = "";
        }

        for (int i = 0; i < strings.length; i++)
        {
            String string = strings[i];

            sb.append(string);

            if (i != strings.length-1)
            {
                sb.append(seperator);
            }
        }

        return sb.toString();
    }

    /**
     * Calculates and returns the Gain/Loss associated with the specified units and effective/process dates
     * associated with the specified fund
     * @param units
     * @param effectiveDate
     * @param processDate
     * @return
     */ 
    public static EDITBigDecimal calculateGainLoss(EDITBigDecimal units, 
                                                   String effectiveDate,  
                                                   String processDate,
                                                   long filteredFundFK,
                                                   long chargeCodeFK,
                                                   String pricingDirection)
    {
        EDITBigDecimal gainLoss = new EDITBigDecimal();

        EDITDate edEffDate = new EDITDate(effectiveDate);
        EDITDate edProcDate = new EDITDate(processDate);

        if (edEffDate.before(edProcDate) && !units.isEQ(new EDITBigDecimal()))
        {
            UnitValuesVO[] unitValuesVOs =
                    engine.dm.dao.DAOFactory.getUnitValuesDAO().findUnitValuesByFund_ChargeCode_Date(filteredFundFK,
                                                                                                     chargeCodeFK,
                                                                                                     effectiveDate,
                                                                                                     pricingDirection);
            EDITBigDecimal effectiveValue = units.multiplyEditBigDecimal(unitValuesVOs[0].getUnitValue());

            unitValuesVOs =
                    engine.dm.dao.DAOFactory.getUnitValuesDAO().findUnitValuesByFund_ChargeCode_Date(filteredFundFK,
                                                                                                     chargeCodeFK,
                                                                                                     processDate,
                                                                                                     pricingDirection);
            EDITBigDecimal processValue = units.multiplyEditBigDecimal(unitValuesVOs[0].getUnitValue());

            gainLoss = effectiveValue.subtractEditBigDecimal(processValue).round(2);
        }

        return gainLoss;
    }


    /**
     * Get the file name and the contents of the file at that location
     * @param aAppReqBlock
     * @param importInUnitValues
     * @return
     * @throws Exception
     */
    public static String[] uploadTable(AppReqBlock aAppReqBlock, boolean importInUnitValues) throws Exception
    {

        List fileContents = null;

        MultipartParser mp = new MultipartParser(aAppReqBlock.getHttpServletRequest(), 1024 * 10000);

        Part part = null;
        String fileName = null;

        while ((part = mp.readNextPart()) != null)
        {
            if (part instanceof FilePart)
            {
                fileContents = new ArrayList();

                FilePart fp = (FilePart) part;

                if (!importInUnitValues)
                {
                    String tempFileName = fp.getFileName();
                    StringTokenizer st = new StringTokenizer(tempFileName, ".");
                    fileName = st.nextToken();
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(fp.getInputStream()));

                String line = null;

                while ((line = reader.readLine()) != null)
                {
                    fileContents.add(line);
                }

                reader.close();
            }
        }

        aAppReqBlock.getHttpServletRequest().setAttribute("fileName", fileName);

        if (fileContents != null)
        {
            return (String[]) fileContents.toArray(new String[fileContents.size()]);
        }
        else
        {
            return null;
        }
    }

    /**
     * Return <code>true</code> only if <code>aText</code> is not null,
     * and is not empty after trimming. (Trimming removes both
     * leading/trailing whitespace and ASCII control characters.)
     *
     * <P> For checking argument validity, {@link Args#checkForContent} should 
     * be used instead of this method.
     *
     * @param aText possibly-null.
     */
    public static boolean textHasContent(String aText) 
    {
        return (aText != null) && (aText.trim().length() > 0);
    }
        
    /**
     * Return <code>true</code> only if <code>aNumber</code> is in the range 
     * <code>aLow..aHigh</code> (inclusive).
     *
     * <P> For checking argument validity, {@link Args#checkForRange} should 
     * be used instead of this method.
     *
     * @param aLow less than or equal to <code>aHigh</code>.
     */
    public static boolean isInRange( int aNumber, int aLow, int aHigh )
    {
      if (aLow > aHigh) 
      {
        throw new IllegalArgumentException("Low is greater than High.");
      }

      return (aLow <= aNumber && aNumber <= aHigh);
    }

    /**
     * Returns Universally/Globally unique id based on a uniquely generated
     * id for the running JVM(s) and the host machine.
     * e.g.
     * "some-unqique-jvm-id:127.0.0.1"
     * @return the unique id
     */
    public static String getUUID()
    {
      InetAddress inetAddress = null;

      try
      {
        inetAddress = InetAddress.getLocalHost();
      }
      catch (UnknownHostException e)
      {
        System.out.println(e);
  
        e.printStackTrace(); //To change body of catch statement use
  
        throw new RuntimeException(e);
      }

      return inetAddress.getHostAddress() + ":" + new UID().toString();
    }

    /**
     * Returns the host name of the host this application is currently running on.
     *
     * @return host name
     *
     * @throws UnknownHostException
     */
    public static String getCurrentHostName() throws UnknownHostException
    {
        InetAddress ina =  InetAddress.getLocalHost();

        return ina.getHostName();
    }

    /**
     * Strips the contents of removeString from the originalString.
     * Ex.  originalString = "Goodbye, Mr. Chips", removeString = "Mr. ", result = "Goodbye, Chips"
     *
     * @param originalString
     * @param removeString
     *
     * @return  String which contains only the contents of originalString without the removeString text
     */
    public static String stripString(String originalString, String removeString)
    {
        String strippedString = originalString.replaceAll(removeString, "");

        return strippedString;
    }

    /**
     * Returns the VO object as an Element
     *
     * @param voObject              VO to be converted to an Element
     *
     * @return  Element containing the xml equivalent of the voObject
     */
    public static Element getVOAsElement(Object voObject)
    {
        String xmlString = Util.marshalVO(voObject);

        xmlString = XMLUtil.parseOutXMLDeclaration(xmlString);

        return XMLUtil.getElementFromXMLString(xmlString);
    }

    /**
     * Determines whether a value exists for the string argument or not.  Checks to see if the string is null or
     * empty.
     *
     * @param string
     *
     * @return  false if string object is null or empty, true otherwise
     */
    public static boolean valueExists(String string)
    {
        return !Util.isStringNullOrEmpty(string);
    }

    /**
     * Determines whether a value exists for the object argument or not.  Checks to see if the object is null.
     *
     * @param object
     *
     * @return  false if the object is null, true otherwise
     */
    public static boolean valueExists(Object object)
    {
        if (object == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Determines whether a value exists for the EDITBigDecimal object or not.
     *
     * @param editBigDecimal
     *
     * @return  false if the object is null or has a value of zero, true otherwise
     */
    public static boolean valueExists(EDITBigDecimal editBigDecimal)
    {
        if (editBigDecimal == null)
        {
            return false;
        }
        else
        {
            if (editBigDecimal.isEQ(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
            {
                //  It's zero, no value has been set
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    /**
     * Converts an object into a String.  If the object is null, returns a null
     *
     * @param object
     * 
     * @return string value of object or null if object was null
     */
    public static String getStringValueOf(Object object)
    {
        String newString = null;

        if (object == null)
        {
            newString = null;
        }
        else
        {
            newString = String.valueOf(object);
        }

        return newString;
    }
     /*
     * A convenience method to identify the system-dependent line-separator.
     * @return the system-dependent line separator such as "\n" or "\n\r"
     */
    public static String getLineSeparator()
    {
        return System.getProperty("line.separator");
	}
}

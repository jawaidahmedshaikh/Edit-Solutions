package edit.portal.taglib;

import fission.utility.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.*;

import engine.ProductStructure;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 26, 2005
 * Time: 3:16:56 PM
 * To change this template use File | Settings | File Templates.
 *
 * The Apache Jakarta Tablibs project has several useful input tags (as taglibs) (e.g. select, checkbox, text, etc.)
 *
 * The <select> tag can take a list of names/values to display in the <select's> <option> subtag. This class
 * facilitates this by transforming the name/value pairs of a target entity into ordered Lists for the Jakarta tag.
 */
public class InputSelect
{
    /**
     * Specify ascending order of names in <option> list.
     */
    public static final int ASCENDING = 0;

    /**
     * Specify descending order of names in <option> list.
     */
    public static final int DESCENDING = 1;

    /**
     * The ordered option names.
     */
    private List optionNames = new ArrayList();

    /**
     * The ordered option values. Values which are null are converted to an empty String by default.
     */
    private List optionValues = new ArrayList();

    /**
     * <select>s default initial name.
     */
    private static final String DEFAULT_OPTION_NAME = "Please Select";

    /**
     * <select>s default initial value.
     */
    private static final String DEFAULT_OPTION_VALUE = "";

    /**
     * Constructor.
     * @param targetObjects the objects to be rendered as name/value pairs in the <select> element
     * @param targetClass
     * @param optionNameField the field name whose value will be used for the <option> label - e.g. "lastName" (but not "LastName")
     * @param optionValueField the field name whose value will be used for the <option> value - e.g. "townOfBirth" (but not "TownOfBirth")
     * @param order InputSelect.ASCENDING or InputSelect.DESCENDING (for the <option> label names)
     */
    public InputSelect(Object[] targetObjects, Class targetClass, String optionNameField, String optionValueField, int order)
    {
        prepare(targetObjects, targetClass, new String[] { optionNameField }, optionValueField, order);
    }

    /**
     * Constructor.
     * @param targetObjects the objects to be rendered as name/value pairs in the <select> element
     * @param targetClass
     * @param optionNameField the field name whose value will be used for the <option> label - e.g. "lastName" (but not "LastName")
     * @param optionValueField the field name whose value will be used for the <option> value - e.g. "townOfBirth" (but not "TownOfBirth")
     * @param order InputSelect.ASCENDING or InputSelect.DESCENDING (for the <option> label names)
     */
    public InputSelect(Object[] targetObjects, Class targetClass, String[] optionNameFields, String optionValueField, int order)
    {
        prepare(targetObjects, targetClass, optionNameFields, optionValueField, order);
    }

    /**
     * Constructor.
     * @param targetObjects the objects to be rendered as name/value pairs in the <select> element
     * @param targetClass
     * @param optionNameField the field name whose value will be used for the <option> label - e.g. "lastName" (but not "LastName")
     * @param optionValueField the field name whose value will be used for the <option> value - e.g. "townOfBirth" (but not "TownOfBirth")
     * @param order InputSelect.ASCENDING or InputSelect.DESCENDING (for the <option> label names)
     */
    public InputSelect(Object[] targetObjects, Class targetClass, String[] optionNameFields, String[] optionValueFields, int order)
    {
        prepare(targetObjects, targetClass, optionNameFields, optionValueFields, order);
    }

    /**
     * Constructor.
     * @param targetHT the hashtable to be rendered as name/value pairs in the <select> element
     */
    public InputSelect(Hashtable targetHT)
    {
        prepare(targetHT);
    }

    /**
     * Sets the <option> labels and values.
     * @param targetObjects
     * @param targetClass
     * @param optionNameField
     * @param optionValueField
     * @param order
     */
    private void prepare(Object[] targetObjects, Class targetClass, String[] optionNameFields, String optionValueField, int order)
    {
        if (targetObjects != null)
        {
            Object optionName = null;

            Object optionValue = null;

            try
            {
                String[] gettersForOptionNames = getOptionNameGetters(optionNameFields);

                String getterForOptionValue = getOptionValueGetter(optionValueField);

                Object[] sortedObjects = Util.sortObjects(targetObjects, gettersForOptionNames);

                if (order == DESCENDING)
                {
                    sortedObjects = Util.invertObjects(sortedObjects);
                }

                for (int i = 0; i < sortedObjects.length; i++)
                {
                    Object sortedObject = sortedObjects[i];

                    optionName = getOptionName(gettersForOptionNames, sortedObject);

                    optionValue = getOptionValue(getterForOptionValue, sortedObject);

                    if (optionValue == null)
                    {
                        optionValue = "";
                    }

                    optionNames.add(optionName);

                    optionValues.add(optionValue);
                }

                // Regardless, the initial entry must always exists.
                optionNames.add(0, DEFAULT_OPTION_NAME);

                optionValues.add(0, DEFAULT_OPTION_VALUE);
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();
                //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sets the <option> labels and values.  This method is to be used when concatenating more than one optionNameField value
     * as the name - specifically added for concatenating firstName with lastName from the Operator table to correctly
     * populate the repName field on the BillSchedule table.
     * @param targetObjects
     * @param targetClass
     * @param optionNameFields
     * @param optionValueFields
     * @param order
     */
    private void prepare(Object[] targetObjects, Class targetClass, String[] optionNameFields, String[] optionValueFields, int order)
    {
        if (targetObjects != null)
        {
            try
            {
                String[] gettersForOptionNames = getOptionNameGetters(optionNameFields);

                String[] gettersForOptionValues = getOptionValueGetters(optionValueFields);

                Object[] sortedNameObjects = Util.sortObjects(targetObjects, gettersForOptionNames);

                if (order == DESCENDING)
                {
                    sortedNameObjects = Util.invertObjects(sortedNameObjects);
                }

                for (int i = 0; i < sortedNameObjects.length; i++)
                {
                    Object sortedObject = sortedNameObjects[i];

                    Object optionName = null;

                    for (int j = 0; j< gettersForOptionNames.length; j++)
                    {
                        if (optionName == null)
                        {
                            optionName = getOptionName(gettersForOptionNames[j], sortedObject);
                        }
                        else
                        {
                            optionName = optionName + " " + getOptionName(gettersForOptionNames[j], sortedObject);
                        }
                    }

                    Object optionValue = null;

                    for (int j = 0; j < gettersForOptionValues.length; j++)
                    {
                        if (optionValue == null)
                        {
                            optionValue = getOptionValue(gettersForOptionValues[j], sortedObject);
                        }
                        else
                        {
                            optionValue = optionValue + " " + getOptionValue(gettersForOptionValues[j], sortedObject);
                        }
                    }

                    if (optionValue == null)
                    {
                        optionValue = "";
                    }

                    optionNames.add(optionName);

                    optionValues.add(optionValue);
                }

                // Regardless, the initial entry must always exists.
                optionNames.add(0, DEFAULT_OPTION_NAME);

                optionValues.add(0, DEFAULT_OPTION_VALUE);
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();
                //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sets the <option> labels and values.  This method is to be used when populating the "Product Key" dropdown
     * for the product underwriting page.  The targetHT contains the FilteredProductFK as the Key and the ProductStructure
     * entity as its value.  The <option> will be populated with the FilteredProductFK as the name, and the ProductStructure
     * names (including its Company.companyName) concatenated together as its value.  ALWAYS sorted in ascending order.
     * @param targetHT
     */
    private void prepare(Hashtable targetHT)
    {
        Map sortedProducts = sortProductsByName(targetHT);

        Iterator keysIT = sortedProducts.keySet().iterator();

        Iterator valuesIT = sortedProducts.values().iterator();

        while (valuesIT.hasNext())
        {
            String filteredProductFK = (String) valuesIT.next();

            String productKey = (String) keysIT.next();

            optionNames.add(productKey);

            optionValues.add(filteredProductFK);
        }

        // Regardless, the initial entry must always exists.
        optionNames.add(0, DEFAULT_OPTION_NAME);

        optionValues.add(0, DEFAULT_OPTION_VALUE);
    }

    /**
     * Sorts the filtered products by name
     * @param targetHT
     * @return
     */
    private TreeMap sortProductsByName(Hashtable targetHT)
    {
        Enumeration htKeys = targetHT.keys();

        TreeMap sortedProducts = new TreeMap();

        while (htKeys.hasMoreElements())
        {
            String filteredProductFK = (String) htKeys.nextElement();

            ProductStructure productStructure = (ProductStructure) targetHT.get(filteredProductFK);

            String companyName = productStructure.getCompanyName();
            String mpName = productStructure.getMarketingPackageName();
            String gpName = productStructure.getGroupProductName();
            String areaName = productStructure.getAreaName();
            String bcName = productStructure.getBusinessContractName();

            String productKey = companyName + "," + mpName + "," + gpName + "," + areaName + "," + bcName;

            sortedProducts.put(productKey, filteredProductFK);
        }

        return sortedProducts;
    }

    /**
     * Converts the specified field name to a corresponding getter method name.
     * e.g. 'fooFieldName' -> getFooFieldName().
     * @param optionValueField
     * @return
     */
    private String getOptionValueGetter(String optionValueField)
    {
        return "get" + Util.convertFirstCharacterCase(optionValueField, Util.UPPER_CASE);
    }

    /**
     * Builds the corresponding method names for the specified optionValueFields. The method names match the order
     * of the supplied optionNameFields.
     * @param targetClass the class upon which to reflect on
     * @param optionValueFields the fields from which the Methods are generated using reflection
     * @return method names ordered the same as the specified optionNameFields
     */
    private String[] getOptionValueGetters(String[] optionValueFields) throws NoSuchMethodException
    {
        String[] getters = new String[optionValueFields.length];

        for (int i = 0; i < optionValueFields.length; i++)
        {
            String optionValueField = optionValueFields[i];

            getters[i] = "get" + Util.convertFirstCharacterCase(optionValueField, Util.UPPER_CASE);
        }

        return getters;
    }

    /**
     *  The ordered list of <option> label names.
     * @return List
     */
    public List getOptionNames()
    {
        return optionNames;
    }

    /**
     *  The ordered list of <option> label values.
     * @return List
     */
    public List getOptionValues()
    {
        return optionValues;
    }

    /**
     * The <option value="fooValue"> fooName entries represented as a sorted Map. The sort is ordered by 'name' ascending.
     * @return
     */
    public Map getOptions()
    {
        Map optionMap = new LinkedHashMap();

        for (int i = 0; i < optionNames.size(); i++)
        {
            Object optionName = optionNames.get(i);

            Object optionValue = optionValues.get(i);

            optionMap.put(optionName, optionValue);
        }

        return optionMap;
    }

    /**
     * Builds the corresponding method names for the specified optionNameFields. The method names match the order
     * of the supplied optionNameFields.
     * @param targetClass the class upon which to reflect on
     * @param optionNameFields the fields from which the Methods are generated using reflection
     * @return method names ordered the same as the specified optionNameFields
     */
    private String[] getOptionNameGetters(String[] optionNameFields) throws NoSuchMethodException
    {
        String[] getters = new String[optionNameFields.length];

        for (int i = 0; i < optionNameFields.length; i++)
        {
            String optionNameField = optionNameFields[i];

            getters[i] = "get" + Util.convertFirstCharacterCase(optionNameField, Util.UPPER_CASE);
        }

        return getters;
    }

    /**
     * Builds a compound, comma-delited name to represent the <option> name. If there is only one
     * element in the specified array of method names, then the comma is ignored.
     * e.g. 'FooName1, FooName2' (2 element array).
     * e.g. "FooName1' (1 element array).
     * @param optionNameMethods
     * @param targetObject
     * @return
     */
    private Object getOptionName(String[] optionNameMethods, Object targetObject) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        String optionName = "";

        for (int i = 0; i < optionNameMethods.length; i++)
        {
            String methodName = optionNameMethods[i];

            optionName += targetObject.getClass().getMethod(methodName, null).invoke(targetObject, null);

            if ((i + 1) != optionNameMethods.length)
            {
                optionName += ", ";
            }
        }

        return optionName;
    }

    /**
     * Obtains the <option value='fooValue'> from the specified method of the specified object.
     * @param valueNameMethod
     * @param targetObject
     * @return
     */
    private Object getOptionValue(String valueNameMethod, Object targetObject) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return targetObject.getClass().getMethod(valueNameMethod, null).invoke(targetObject, null);
    }

    /**
     * Obtains the <option name='fooValue'> from the specified method of the specified object.
     * This method is to be used when combining more than one name value (see
     * prepare(Object[], Class, String[], String[], int) method
     * @param nameMethod
     * @param targetObject
     * @return
     */
    private Object getOptionName(String nameMethod, Object targetObject) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return targetObject.getClass().getMethod(nameMethod, null).invoke(targetObject, null);
    }
}

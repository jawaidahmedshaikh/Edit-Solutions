/*
 * User: gfrosti
 * Date: Jun 12, 2002
 * Time: 8:37:02 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.services.db;

import edit.common.EDITBigDecimal;

import java.lang.reflect.Method;

public class VOMethod
{

    private Method method;

    private int methodCategory;

    private VOField voField;

    public static final int SIMPLE_SETTER = 0;
    public static final int SIMPLE_GETTER = 1;
    public static final int VO_ADDER = 2;
    public static final int VO_SETTER = 3;
    public static final int VO_GETTER = 4;
    public static final int FK_SETTER = 5;
    public static final int FK_GETTER = 6;
    public static final int PK_SETTER = 7;
    public static final int PK_GETTER = 8;
    public static final int CT_SETTER = 9;
    public static final int CT_GETTER = 10;
    public static final int VO_REMOVER = 11;
    private Class targetFieldType;
//    public static final int PARENT_FK_SETTER = 11;
//    public static final int PARENT_FK_GETTER = 12;
//    public static final int CHILD_FK_SETTER = 13;
//    public static final int CHILD_FK_GETTER = 14;

    public VOMethod(Method method)
    {

        this.method = method;
        this.methodCategory = getMethodCategory(method);
        setTargetFieldType();
    }

    private void setTargetFieldType()
    {
        if (methodCategory == FK_GETTER ||
            methodCategory == FK_SETTER ||
            methodCategory == PK_GETTER ||
            methodCategory == PK_SETTER)
        {
            targetFieldType = long.class;
        }

        else if (methodCategory == CT_GETTER ||
                 methodCategory == CT_SETTER)
        {
            targetFieldType = String.class;
        }

        else if (methodCategory == SIMPLE_GETTER)
        {
            targetFieldType = method.getReturnType();
        }

        else if (methodCategory == SIMPLE_SETTER)
        {
            targetFieldType = method.getParameterTypes()[0];
        }
    }

    public Class getTargetFieldType()
    {
        return targetFieldType;
    }

    public Object getTargetFieldAsObject(String targetFieldValue)
    {
        Object targetFieldValueAsObj = null;

        if (targetFieldType == EDITBigDecimal.class)
        {
            targetFieldValueAsObj = new EDITBigDecimal(targetFieldValue);
        }

       if (targetFieldType ==  double.class)
        {
            targetFieldValueAsObj = new Double(targetFieldValue);
        }
        else if (targetFieldType == int.class)
        {
            targetFieldValueAsObj = new Integer(targetFieldValue);
        }

        else if (targetFieldType == long.class)
        {
            targetFieldValueAsObj = new Long(targetFieldValue);
        }

        else if (targetFieldType == String.class)
        {
            targetFieldValueAsObj = targetFieldValue;
        }

        return targetFieldValueAsObj;
    }

    protected void setVOField(VOField voField)
    {
        this.voField = voField;
    }

    protected VOField getVOField()
    {
        return voField;
    }


    public Method getMethod()
    {

        return method;
    }

    public int getMethodCategory()
    {

        return methodCategory;
    }

    /**
     * Categorizes all necessary methodsto the CRUD process
     *
     * Necessary implies:
     *  Simple getters and setters for column values    (setPhoneNumber, getPhoneNumber)
     *  PK and FK getters and setters                   (setFooPK, getFooPK)
     *  Non-indexed VO Adders                           (addFooVO(fooVO))
     *  Non-indexed VO getters and VO setters           (getFooVO(), setFooVO(fooVo[]))
     *
     * @param method The target method
     * @return The method category     *
     */
    protected static int getMethodCategory(Method method)
    {

        String methodName = method.getName();

        // setters
        if (methodName.startsWith("set"))
        {

            if (methodName.endsWith("VO"))
            {

                Class[] params = method.getParameterTypes();

                if (params.length == 1)
                {

                    return VO_SETTER;
                }
                else
                {

                    // Error - don't track;
                    return -1;
                }
            }
            else if (methodName.endsWith("FK"))
            {

//                if (methodName.startsWith("setParent")){
//
//                    return PARENT_FK_SETTER;
//                }
//                else if (methodName.startsWith("setChild")){
//
//                    return CHILD_FK_SETTER;
//                }
//                else{

                return FK_SETTER;
//                }
            }
            else if (methodName.endsWith("PK"))
            {

                return PK_SETTER;
            }
            else if (methodName.endsWith("CT"))
            {

                return CT_SETTER;
            }
            else
            {

                return SIMPLE_SETTER;
            }
        }
        // getters
        else if (methodName.startsWith("get"))
        {

            if (methodName.endsWith("VO"))
            {

                Class[] params = method.getParameterTypes();

                if (params.length == 0)
                {

                    return VO_GETTER;
                }
                else
                {

                    // Error - don't track
                    return -1;
                }
            }
            else if (methodName.endsWith("FK"))
            {

//                if (methodName.equals("getParentFK")){
//
//                    return PARENT_FK_GETTER;
//                }
//                else if (methodName.equals("getChildFK")){
//
//                    return CHILD_FK_GETTER;
//                }
//                else {

                return FK_GETTER;
//                }
            }
            else if (methodName.endsWith("PK"))
            {

                return PK_GETTER;
            }
            else if (methodName.endsWith("CT"))
            {

                return CT_GETTER;
            }
            else if (methodName.indexOf("VO") > 0)
            {

                // Error - don't track
                return -1;
            }
            else
            {

                return SIMPLE_GETTER;
            }
        }
        // adder
        else if (methodName.startsWith("add"))
        {

            // Only consider the single parameter version
            if (method.getParameterTypes().length == 1)
            {

                return VO_ADDER;
            }
        }
        else if (methodName.startsWith("removeAll") &&
                !methodName.equalsIgnoreCase("removeAllowableTransaction"))
        {
            if (method.getParameterTypes().length == 0)
            {
                return VO_REMOVER;
            }
        }

        // Error - don't track
        return -1;
    }

}

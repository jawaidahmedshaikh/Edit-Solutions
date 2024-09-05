/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */
package fission.dm.valueobject;

import java.io.Serializable;
import java.lang.reflect.Method;


public class ValueObject implements Serializable {


	public static final String DEFAULT_CHAR = "*";
	public static final int EXP_BY_ID       = 0;
	public static final int EXP_BY_NAME     = 1;
	public static final int EXP_FLAT        = 2;
	public static final int EXP_FLUFFY      = 3;
    public static final int EXP_ACCOUNTING  = 4;
    public static final int EXP_FLUFFY_RT   = 5;

	public static final String DEFAULT_STRING    = "";
	public static final int DEFAULT_INT 		 = 0;
	public static final double DEFAULT_DOUBLE 		 = 0;


	public String checkForDefaultString(String value) {

		return (isValidString(value))?value.trim():DEFAULT_STRING;
	}

	public int checkForDefaultInt(String value) {

		return (isValidString(value))?Integer.parseInt(value):DEFAULT_INT;
	}

	public double checkForDefaultDouble(String value) {

		return (isValidString(value))?Double.parseDouble(value):DEFAULT_DOUBLE;
//		return 0;
	}


	public int getExpansionMode() {

		// meaningless unless overridden
		return -1;
	}


	private boolean isValidString(String str) {

		return (str != null && str.trim().length() > 0);
	}
	
    public boolean gettersAreEqual(ValueObject vo){

        try {
            if (vo.getClass().getName().equals(this.getClass().getName())) {

                Method[] methods1 = this.getClass().getDeclaredMethods();
                Method[] methods2 = vo.getClass().getDeclaredMethods();

                for (int i = 0; i < methods1.length; i++){

                    Method method1 = methods1[i];
                    Method method2 = methods2[i];

                    if (method1.getName().startsWith("get")){

                        if (method1.getReturnType().getName().equals("java.lang.String") || method1.getReturnType().isPrimitive()) {

                            Object return1 = method1.invoke(this, new Object[]{});
                            Object return2 = method2.invoke(vo, new Object[]{});

                            if (!return1.equals(return2)){

                                return false;
                            }
                        }
                        else if (method1.getReturnType().isAssignableFrom(ValueObject.class)){

                            ValueObject return1 = (ValueObject) method1.invoke(this, new Object[]{});
                            ValueObject return2 = (ValueObject) method2.invoke(vo, new Object[]{});

                            if (!return1.gettersAreEqual(return2)){

                                return false;
                            }
                        }
                        else if (method1.getReturnType().isArray()){

                            Object[] returns1 = (Object[]) method2.invoke(this, new Object[]{});
                            Object[] returns2 = (Object[]) method1.invoke(vo, new Object[]{});

                            for (int j = 0; j < returns1.length; j++){

                                if (returns1[j] instanceof ValueObject){

                                    if (!(((ValueObject)returns1[j]).gettersAreEqual((ValueObject)returns2[j]))){

                                        return false;
                                    }
                                }
                                else {

                                    if (!returns1[j].equals(returns2[j])){

                                        return false;
                                    }
                                }
                            }
                        }
                        else{

                            throw new RuntimeException("ValueObject getters can return single and arrays of primitives, Strings, and other ValueObjects only.");
                        }
                    }
                }

                return true;
            }
            else{

                return false;
            }
        } catch (Exception e){

            return false;
        }

    }

}
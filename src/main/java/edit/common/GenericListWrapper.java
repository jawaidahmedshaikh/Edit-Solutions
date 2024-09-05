/*
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: May 2, 2002
 * Time: 12:12:35 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.common;

import java.lang.reflect.Method;
import java.util.*;

public class GenericListWrapper {

    private Map ht = new HashMap();

    public GenericListWrapper() {

    }

    public void initializeLists(Object[] VOs, String[] methodList)  throws Exception{

        ht.clear();

        for (int i = 0; i < VOs.length; i++) {

            Class voClass = VOs[i].getClass();

            for (int j = 0; j < methodList.length; j++) {

                Method method = voClass.getDeclaredMethod(methodList[j], null);

                String nameValue = (String) method.invoke(VOs[i], null);

                if (nameValue != null) {

                    setNames(methodList[j], nameValue);
                }
            } //end inner for
        }  //end outer for
    }

    public void setNames(String methodName, String nameValue) throws Exception{

        Set setNameList = null;

        String categoryName = methodName.substring(3);

        if ((setNameList = (TreeSet)ht.get(categoryName)) == null) {

            setNameList = new TreeSet();

            ht.put(categoryName, setNameList);
        }

        setNameList.add(nameValue);
    }

   /* Pass in the column name for the required list
    */
    public String[] getNameList(String categoryName){

        Set nameList = (TreeSet)ht.get(categoryName);

        if (nameList != null) {

            String[] nameListAsArray = new  String[nameList.size()];

            Iterator it = nameList.iterator();

            int i = 0;

            while (it.hasNext()) {

                nameListAsArray[i++] = (String)it.next();
            }

            return nameListAsArray;
        }

        return null;
    }
}

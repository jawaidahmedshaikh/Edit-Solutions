/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Sep 18, 2002
 * Time: 3:26:10 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.db;

import java.util.List;

public class VOBranch implements Cloneable, Comparable {

    private List branch;

    public VOBranch(List branch){

        this.branch =  branch;
    }

    public boolean equals(Object obj){

        if (this == obj){

            return true;
        }

        List objVector = ((VOBranch) obj).getBranch();

        return branch.equals(objVector);
    }

    public List getBranch(){

        return (List) branch;
    }

    public boolean pathElementsInBranch(String[] pathElements){

        if (pathElements.length <= branch.size()) {

            for (int i = 0; i < pathElements.length; i++){

                if (!VOClass.getTableName(branch.get(i).getClass()).equals(pathElements[i])){

                    return false;
                }
            }

            return true;
        }
        else {

            return false;
        }
    }

    public String findValue(String[] tokenElements) throws Exception {

        Object value = null;
        Object targetVO = null;

        for (int i = 0; i < tokenElements.length - 1; i++){

            Object currentVO = branch.get(i);

            if(!VOClass.getTableName(currentVO.getClass()).equals(tokenElements[i])){

                break;
            }
            else{

                targetVO = currentVO;
            }
        }

        if (targetVO != null){

            value = targetVO.getClass().getMethod("get" + tokenElements[tokenElements.length - 1], null).invoke(targetVO, null);
        }

        if (value != null){

            return value.toString();
        }
        else {

            return null;
        }
    }

    public int compareTo(Object obj){

        VOBranch voBranch = (VOBranch) obj;

        int diff = voBranch.getBranch().size() - this.branch.size();

        return diff;
    }
}

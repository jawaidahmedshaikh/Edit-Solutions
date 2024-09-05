/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Sep 19, 2002
 * Time: 2:19:14 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.db;

import fission.utility.Util;

import java.util.*;

public class VOBranchCollection {

    List allBranches;

    public VOBranchCollection(){

        allBranches = new ArrayList();
    }

    public void clear(){

        allBranches.clear();
    }

    public VOBranch[] findAll(String[] tokenPath, String[] criteria) throws Exception
    {

        List matchingBranches = new ArrayList();

        int branchNum = allBranches.size();

        for (int i = 0; i < branchNum; i++){

            VOBranch voBranch = null;

            if ((voBranch = ((VOBranch)allBranches.get(i))).pathElementsInBranch(tokenPath)){

                matchingBranches.add(voBranch);
            }
        }

        if (matchingBranches.size() != 0){

            VOBranch[] unfilteredBranches =  (VOBranch[]) matchingBranches.toArray(new VOBranch[matchingBranches.size()]);

            if (criteria == null){

                return unfilteredBranches;
            }
            else {

                List filteredBranches = new ArrayList(Arrays.asList(unfilteredBranches));

                for (int i = 0; i < criteria.length; i++ ) {

                    List currentFilteredBranches = new ArrayList();

                    String[] tokenizedCriteria = Util.fastTokenizer(criteria[i], "=");
                    String criteriaElements = tokenizedCriteria[0];
                    String criteriaVal      = tokenizedCriteria[1];

                    for (int j = 0; j < filteredBranches.size(); j++){

                        String currentValue = ((VOBranch)(filteredBranches.get(j))).findValue(Util.fastTokenizer(criteriaElements, "."));

                        if (currentValue != null && currentValue.equals(criteriaVal)){

                            currentFilteredBranches.add(unfilteredBranches[j]);
                        }
                    }

                    filteredBranches = currentFilteredBranches;
                }

                if (filteredBranches.size() == 0){

                    return null;
                }
                else{

                    return (VOBranch[]) filteredBranches.toArray(new VOBranch[filteredBranches.size()]);
                }
            }
        }
        else {

            return null;
        }
    }

    public boolean contains(VOBranch voBranch){

        return allBranches.contains(voBranch);
    }

    public void addVOBranch(VOBranch voBranch){

        allBranches.add(voBranch);
    }

    public void setVOBranches(VOBranch[] voBranches){

        for (int i = 0; i < voBranches.length; i++){

            allBranches.add(voBranches[i]);
        }
    }

    public void printSortedBranches(){

        List sortedBranches = new ArrayList();

        Iterator i = allBranches.iterator();

        while (i.hasNext()){

            List currentBranch = ((VOBranch) i.next()).getBranch();

            StringBuilder branchAsString = new StringBuilder();

            for (int j = 0; j < currentBranch.size(); j++){

                Object currentVO = currentBranch.get(j);

                String tableName = VOClass.getTableName(currentVO.getClass());

                branchAsString.append(tableName);

                if (j < currentBranch.size() - 1){

                    branchAsString.append(".");
                }
            }

            sortedBranches.add(branchAsString.toString());
        }

        Collections.sort(sortedBranches);

        i = sortedBranches.iterator();

        int count = 0;

        while (i.hasNext()){

            System.out.println(count++ + ":" + i.next().toString());
        }
    }
}

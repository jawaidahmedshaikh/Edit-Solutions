/*
* Created by IntelliJ IDEA.
* User: gfrosti
* Date: Sep 18, 2002
* Time: 3:22:29 PM
* To change template for new class use
* Code Style | Class Templates options (Tools | IDE Options).
*/
package edit.services.db;

import java.util.List;

public class VOBranchifier implements RecursionListener
{

    private int lastRecursionLevel;
    private List lastBranch;
    private VOBranchCollection voBranchCollection;

    public VOBranchifier()
    {
    }

    public VOBranchCollection branchifyVOs(Object[] voObjects) throws Exception
    {

        voBranchCollection = new VOBranchCollection();

        for (int i = 0; i < voObjects.length; i++)
        {

            RecursionContext recursionContext = new RecursionContext();
            CRUD.recurseVOObjectModel(voObjects[i], null, CRUD.RECURSE_TOP_DOWN, this, recursionContext, null);

            // The last path is, by default, a branch and should be accounted for
            VOBranch lastVOBranch = new VOBranch(lastBranch);
//            if (!voBranchCollection.contains(lastVOBranch)){

            voBranchCollection.addVOBranch(new VOBranch(lastBranch));
//            }
        }

        return voBranchCollection;
    }

    public void currentNode(Object currentNode, Object parentNode, RecursionContext recursionContext)
    {

        int currentRecursionLevel = recursionContext.getRecursionLevel();

        if (currentRecursionLevel == 1) // It's the first node in the tree
        {
            lastRecursionLevel = currentRecursionLevel;
        }
        else if (lastRecursionLevel == currentRecursionLevel) // Moving horizontally across nodes at the same level
        {

//            voBranchCollection.addVOBranch(new VOBranch(recursionContext.getRecursionPath()));
            voBranchCollection.addVOBranch(new VOBranch(lastBranch));
        }
        else if (currentRecursionLevel == (lastRecursionLevel - 1)) // Moving back up the recursion levels looking for the next branch.
        {

//            voBranchCollection.addVOBranch(new VOBranch(recursionContext.getRecursionPath()));
            voBranchCollection.addVOBranch(new VOBranch(lastBranch));
        }

        lastRecursionLevel = currentRecursionLevel;
        lastBranch = recursionContext.getRecursionPath();
    }
}

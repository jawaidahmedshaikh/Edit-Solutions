/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Apr 17, 2002
 * Time: 4:29:58 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class RecursionContext {

    private boolean shouldContinueRecursion = true;
    private Stack recursionPathByName;
    private Stack recursionPath;
    public static final int ADD_NODE_TO_PATH = 0;
    public static final int REMOVE_NODE_FROM_PATH = 1;
    private int recursionLevel;

    private Map memory;

    public RecursionContext(){

        this.memory         = new HashMap();
        this.recursionPathByName = new Stack();
        this.recursionPath = new Stack();
    }

    public void incrementRecursionLevel()
    {
        recursionLevel++;
    }

    public void decrementRecursionLevel()
    {
        recursionLevel--;
    }

    public int getRecursionLevel(){

        return recursionLevel;
    }

    protected void updateRecursionPath(Object voObject, int action){

        if (action == ADD_NODE_TO_PATH){

            recursionPath.push(voObject);
            recursionPathByName.push(VOClass.getTableName(voObject.getClass()));
        }
        else if (action == REMOVE_NODE_FROM_PATH){

            recursionPath.pop();
            recursionPathByName.pop();
        }
        else {

            throw new RuntimeException("Invalid ACTION in RecursionContext.java");
        }
    }

    public void setShouldContinueRecursion(boolean shouldContinueRecursion){

        this.shouldContinueRecursion = shouldContinueRecursion;
    }

    public boolean shouldContinueRecursion(){

        return shouldContinueRecursion;
    }

    public void addToMemory(String key, Object memoryItem){

        memory.put(key, memoryItem);
    }

    public Object getFromMemory(String key){

        return memory.get(key);
    }

    public List getRecursionPathByName(){

        return (List) (recursionPathByName.clone());
    }

    public List getRecursionPath() {

        return (List) recursionPath.clone();
    }
}

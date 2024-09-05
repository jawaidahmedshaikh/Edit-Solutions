/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: May 1, 2002
 * Time: 10:50:03 AM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.db;

public interface RecursionListener {

    public void currentNode(Object currentNode, Object parentNode, RecursionContext recursionContext);
}

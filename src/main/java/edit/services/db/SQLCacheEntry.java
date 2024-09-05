/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 22, 2002
 * Time: 3:06:28 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.db;

import java.util.List;

public class SQLCacheEntry {

    private String sqlCacheKey;
    private Object cacheValue;
    private List tableNames;

    protected SQLCacheEntry(String sqlCacheKey, List tableNames, Object cacheValue){

        this.sqlCacheKey    = sqlCacheKey;
        this.tableNames     = tableNames;
        this.cacheValue     = cacheValue;
      }

    public String getCacheKey(){

        return sqlCacheKey;
    }

    public List getTableNames(){

        return tableNames;
    }

    public Object getCacheValue(){

        return cacheValue;
    }
}

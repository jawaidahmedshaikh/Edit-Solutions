/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jul 26, 2002
 * Time: 12:36:31 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.db;

import edit.common.vo.VOObject;

public final class VOCacheEntry {

    private String poolName;
    private String tableName;
    private long   pkVal;
    private Object targetVO;

    private String voCacheKey;
    private long creationTimeMillis;

    public VOCacheEntry(String poolName, String tableName, long pkVal, Object targetVO) throws Exception{

        this.poolName   = poolName;
        this.tableName  = tableName;
        this.pkVal      = pkVal;
        this.targetVO   = targetVO;

        this.voCacheKey = generateVOCacheKey(poolName, tableName, pkVal);

        this.creationTimeMillis = System.currentTimeMillis();
    }

    public long getCreationTimeMillis(){

        return creationTimeMillis;
    }

    public String getPoolName(){

        return poolName;
    }

    public String getTableName(){

        return tableName;
    }

    public long getPKVal(){

        return pkVal;
    }

    public Object getClonedVO(){

        return ((VOObject) targetVO).cloneVO();
    }

    public String getVOCacheKey(){

        return voCacheKey;
    }

    public void resetCreationTimeMillis(){

        creationTimeMillis = System.currentTimeMillis();
    }

    protected static String generateVOCacheKey(String poolName, String tableName, long pkVal){

        return poolName + "-" + tableName + "-" + pkVal;
    }
}

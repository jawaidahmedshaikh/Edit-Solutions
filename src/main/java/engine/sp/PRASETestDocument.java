package engine.sp;

import edit.common.EDITMap;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import java.util.List;
import java.util.Map;

/**
 * Maps a PRASETest to the set of documents that are needed to 
 * run a PRASETest.
 */
public class PRASETestDocument extends HibernateEntity
{
    public static final String IS_ROOT_YES = "Y";
    
    public static final String IS_ROOT_NO = "N";
    
    /**
     * The associated PRASETest.
     */
    private PRASETest pRASETest;
    
    /**
     * The associated PRASEDocument.
     */
    private PRASEDocument pRASEDocument;
    
    /**
     * 'Y' if this PRASEDocument is the root/initial document
     * in the set of documents used when running ScriptProcessor; 'N'
     * otherwise.
     */
    private String isRoot;
    
    /**
     * PK.
     */
    private Long pRASETestDocumentPK;
    
    /**
     * FK.
     */
    private Long pRASETestFK;
    
    /**
     * FK.
     */
    private Long pRASEDocumentFK;
    
    public PRASETestDocument()
    {
    }

    /**
     * @see PRASETestDocument#pRASETest
     * @param newpraseTest
     */
    public void setPRASETest(PRASETest newpraseTest)
    {
        this.pRASETest = newpraseTest;
    }

    /**
     * @see PRASETestDocument#pRASETest
     * @return
     */
    public PRASETest getPRASETest()
    {
        return pRASETest;
    }

    /**
     * @see #pRASEDocument
     * @param newpraseDocument
     */
    public void setPRASEDocument(PRASEDocument newpraseDocument)
    {
        this.pRASEDocument = newpraseDocument;
    }

    /**
     * @see #pRASEDocument
     * @return
     */
    public PRASEDocument getPRASEDocument()
    {
        return pRASEDocument;
    }
    
    /**
     * @see #isRoot
     * @param newisRoot
     */
    public void setIsRoot(String newisRoot)
    {
        this.isRoot = newisRoot;
    }

    /**
     * @see #isRoot
     * @return
     */
    public String getIsRoot()
    {
        return isRoot;
    }

    /**
     * @see #pRASETestDocumentPK
     * @param newpRASETestDocumentPK
     */
    public void setPRASETestDocumentPK(Long newpRASETestDocumentPK)
    {
        this.pRASETestDocumentPK = newpRASETestDocumentPK;
    }

    /**
     * @see #pRASETestDocumentPK
     */
    public Long getPRASETestDocumentPK()
    {
        return pRASETestDocumentPK;
    }

    /**
     * @see #pRASETestFK
     * @param newpRASETestFK
     */
    public void setPRASETestFK(Long newpRASETestFK)
    {
        this.pRASETestFK = newpRASETestFK;
    }

    /**
     * @see #pRASETestFK
     */
    public Long getPRASETestFK()
    {
        return pRASETestFK;
    }

    /**
     * @see #pRASEDocumentFK
     * @param newpRASEDocumentFK
     */
    public void setPRASEDocumentFK(Long newpRASEDocumentFK)
    {
        this.pRASEDocumentFK = newpRASEDocumentFK;
    }

    /**
     * @see #pRASEDocumentFK
     */
    public Long getPRASEDocumentFK()
    {
        return pRASEDocumentFK;
    }
    
    /**
     * Finder. Includes the associated PRASEDocument.
     * @param praseTestPK
     * @return
     */
    public static PRASETestDocument[] findBy_PRASETestPK_V1(Long praseTestPK)
    {
        String hql = " select praseTestDocument" +
                    " from PRASETestDocument praseTestDocument" +
                    " join fetch praseTestDocument.PRASETest" +
                     " where praseTestDocument.PRASETestFK = :praseTestPK";
        
        Map params = new EDITMap("praseTestPK", praseTestPK);
        
        List<PRASETestDocument> results = SessionHelper.executeHQL(hql, params, SessionHelper.ENGINE);
        
        return results.toArray(new PRASETestDocument[results.size()]);
    }

    public String getDatabase()
    {
        return SessionHelper.ENGINE;
    }

    /**
     * Finder. Finds the list of PRASETestDocuments that should no longer be associated with the specified PRASETest as
     * defined by the specified PRASEDocuments which SHOULD be associated with the specified PRASETest.
     * @param praseTest
     * @param praseDocuments
     * @return
     */
    public static PRASETestDocument[] findBy_PRASETest_PRASEDocuments_NOT_In(PRASETest praseTest, List<PRASEDocument> praseDocuments)
    {
        String hql = " from PRASETestDocument praseTestDocument" +
                     " where praseTestDocument.PRASETest = :praseTest" +
                     " and praseTestDocument.PRASEDocument not in (:praseDocuments)";
        
        Map params = new EDITMap("praseTest", praseTest)
                    .put("praseDocuments", praseDocuments);
        
        List<PRASETestDocument> results = SessionHelper.executeHQL(hql, params, SessionHelper.ENGINE);
        
        return results.toArray(new PRASETestDocument[results.size()]);
    }
}

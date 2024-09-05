/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Dec 22, 2003
 * Time: 12:27:12 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package event;

import edit.common.vo.QuoteVO;
import edit.common.vo.VOObject;
import edit.services.db.CRUDEntity;
import engine.business.*;

public class Quote implements CRUDEntity
{
    private QuoteVO quoteVO;
    private QuoteVO quoteVOOut;

    private QuoteImpl quoteImpl;

    public Quote()
    {
        quoteVO = new QuoteVO();
        quoteImpl = new QuoteImpl();
    }

    /**
     *
     * @param operator
     * @throws Exception
     */
    public void getInforceQuote(String quoteDate, String quoteTypeCT, long segmentPK, String operator) throws Exception
    {
        quoteImpl.getInforceQuote(this, quoteDate, quoteTypeCT, segmentPK, operator);
    }

   public Analyzer analyzeInforceQuote(String quoteDate, String quoteTypeCT, long segmentPK, String operator) throws Exception
    {
        Analyzer analyzer = quoteImpl.analyzeInforceQuote(this, quoteDate, quoteTypeCT, segmentPK, operator);

        return analyzer;
    }

    /**
     *
     * @param quoteVOIn
     * @throws Exception
     */
    public QuoteVO getNewBusinessQuote(QuoteVO quoteVOIn) throws Exception
    {
        quoteVO = quoteVOIn;
        quoteImpl.getNewBusinessQuote(this);

        return quoteVOOut;
    }

    public void save() throws Exception
    {
    }

    public void delete() throws Exception
    {
    }

    public VOObject getVO()
    {
        return quoteVO;
    }

    public long getPK()
    {
        return 0;
    }

    public void setVO(VOObject voObject)
    {
        this.quoteVO = (QuoteVO) voObject;
    }

    public void setVOOut(VOObject voObject)
    {
        this.quoteVOOut = (QuoteVO) voObject;
    }

    public boolean isNew()
    {
        return quoteImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return quoteImpl.cloneCRUDEntity(this);
    }
}

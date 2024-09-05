/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Dec 22, 2003
 * Time: 12:27:12 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package event;

import edit.common.vo.LoanPayoffQuoteVO;
import edit.common.vo.VOObject;
import edit.services.db.CRUDEntity;

public class LoanPayoffQuote implements CRUDEntity
{
    private LoanPayoffQuoteVO loanPayoffQuoteVO;

    private LoanPayoffQuoteImpl loanPayoffQuoteImpl;

    public LoanPayoffQuote()
    {
        loanPayoffQuoteVO = new LoanPayoffQuoteVO();
        loanPayoffQuoteImpl = new LoanPayoffQuoteImpl();
    }

    /**
     *
     * @param quotedate
     * @throws Exception
     */
    public void getLoanPayoffQuote(String quoteDate, long segmentPK) throws Exception
    {
        loanPayoffQuoteImpl.getLoanPayoffQuote(this, quoteDate, segmentPK);
    }

    public void save() throws Exception
    {
    }

    public void delete() throws Exception
    {
    }

    public VOObject getVO()
    {
        return loanPayoffQuoteVO;
    }

    public long getPK()
    {
        return 0;
    }

    public void setVO(VOObject voObject)
    {
        this.loanPayoffQuoteVO = (LoanPayoffQuoteVO) voObject;
    }

    public boolean isNew()
    {
        return loanPayoffQuoteImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return loanPayoffQuoteImpl.cloneCRUDEntity(this);
    }
}

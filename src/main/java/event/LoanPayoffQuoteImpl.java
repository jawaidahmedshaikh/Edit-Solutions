package event;

import edit.common.vo.*;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.RecursionContext;
import edit.services.db.RecursionListener;
import edit.services.db.VOClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import contract.dm.composer.VOComposer;

/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Dec 22, 2003
 * Time: 12:29:55 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */

public class LoanPayoffQuoteImpl extends CRUDEntityImpl implements RecursionListener
{
    protected void getLoanPayoffQuote(LoanPayoffQuote loanPayoffQuote, String quoteDate, long segmentPK) throws Exception
    {
        //compose loanPayoffQuoteVO
        List voInclusionList = new ArrayList();
        voInclusionList.add(InvestmentVO.class);
        voInclusionList.add(BucketVO.class);

        SegmentVO segmentVO = new VOComposer().composeSegmentVO(segmentPK, voInclusionList);

        LoanPayoffQuoteVO loanPayoffQuoteVO = (LoanPayoffQuoteVO) loanPayoffQuote.getVO();

        loanPayoffQuoteVO.setQuoteDate(quoteDate);
        loanPayoffQuoteVO.setSegmentVO(segmentVO);
    }

    public void currentNode(Object currentNode, Object parentNode, RecursionContext recursionContext)
    {
        Map vosByPK = (Map) recursionContext.getFromMemory("vosByPK");

        VOObject voObject = (VOObject) currentNode;

        VOClass voClass = VOClass.getVOClassMetaData(voObject.getClass());

        if (voObject instanceof LoanPayoffQuoteVO)
        {
            if (vosByPK.containsKey("loanPayoffQuoteVO"))
            {
                VOObject voFromOutput = (VOObject) vosByPK.get("loanPayoffQuoteVO");

                voObject.copyFrom(voFromOutput);

                vosByPK.remove("loanPayoffQuoteVO");
            }
        }
        else
        {
            try
            {
                Long pk = (Long) voClass.getPKGetter().getMethod().invoke(voObject, null);

                if (vosByPK.containsKey(pk.toString()))
                {
                    VOObject voFromOutput = (VOObject) vosByPK.get(pk.toString());

                    voObject.copyFrom(voFromOutput);

                    vosByPK.remove(pk.toString());
                }
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        if (vosByPK.isEmpty())
        {
            recursionContext.setShouldContinueRecursion(false);
        }
    }
}

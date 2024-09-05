package event;

import edit.common.vo.SegmentVO;
import edit.common.vo.VOObject;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: May 3, 2004
 * Time: 2:08:46 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PRASEDocument
{
    /**
     * Implementing subclasses are required to implement this method to successfully build a PRASEDocument
     */
    public abstract void buildDocument();

    /**
     * Implementing subclasses are required to implement this method to present a value object reprentation of the PRASEDocument entity
     * @return
     */
    public abstract VOObject getDocumentAsVO();


    public SegmentVO getSegmentVO(long segmentPK)
    {
        return null;
    }

//    public ClientDetailVO getClientDetailVO(long clientDetailPk, booelan inclau, vbooalal,)
}

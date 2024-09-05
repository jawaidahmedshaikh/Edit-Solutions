package contract;

import edit.common.vo.SegmentBackupVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.VOObject;
import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import contract.dm.dao.SegmentBackupDAO;

/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Oct 27, 2003
 * Time: 12:15:53 PM
 * To change this template use Options | File Templates.
 */
public class SegmentBackup implements CRUDEntity
{
    private SegmentBackupVO segmentBackupVO;

    private SegmentBackupImpl segmentBackupImpl;

    public SegmentBackup(SegmentBackupVO segmentBackupVO)
    {
        this();
        this.segmentBackupVO = segmentBackupVO;

    }

    public SegmentBackup(long segmentBackupPK) throws Exception
    {
        this();
        this.segmentBackupImpl.load(this, segmentBackupPK);
    }

    public SegmentBackup()
    {
        this.segmentBackupImpl = new SegmentBackupImpl();
        this.segmentBackupVO = new SegmentBackupVO();
    }

    public void save() throws Exception
    {
        segmentBackupImpl.save(this);
    }

    public void delete()
    {
        segmentBackupImpl.delete(this);
    }

    public long getPK()
    {
        return segmentBackupVO.getSegmentBackupPK();
    }

    public void setVO(VOObject voObject)
    {
        this.segmentBackupVO = (SegmentBackupVO) voObject;
    }

    public boolean isNew()
    {
        return this.segmentBackupImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return this.segmentBackupImpl.cloneCRUDEntity(this);
    }

    public VOObject getVO()
    {
        return segmentBackupVO;
    }

    public void backupContract(long segmentFK, String segmentName, String quoteDate) throws Exception
    {
        this.segmentBackupImpl.backupContract(segmentFK, segmentName, quoteDate);

    }

    public void restoreContract(long segmentFK)  throws Exception
    {
        this.segmentBackupImpl.restoreContract(segmentFK);
    }

    /**
     * Finder.
     * @param segmentPK
     * @return
     */
    public static SegmentBackup[] findBy_SegmentPK(long segmentPK)
    {
        SegmentBackupVO[] segmentBackupVOs = new SegmentBackupDAO().findBySegmentFK(segmentPK);

        return (SegmentBackup[]) CRUDEntityImpl.mapVOToEntity(segmentBackupVOs, SegmentBackup.class);
    }
}

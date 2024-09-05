/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 30, 2003
 * Time: 8:22:54 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package contract.dm.composer;

import edit.common.vo.AgentHierarchyVO;
import edit.common.vo.AgentSnapshotVO;
import edit.common.vo.SegmentVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

public class AgentHierarchyComposer  extends Composer {

    private List voInclusionList;

    private CRUD crud;

    public AgentHierarchyComposer(List voInclusionList) {

        this.voInclusionList = voInclusionList;
    }

    public AgentHierarchyVO compose(long agentHierarchyPK) throws Exception {

        AgentHierarchyVO agentHierarchyVO = null;

        try {

            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            agentHierarchyVO = (AgentHierarchyVO) crud.retrieveVOFromDB(AgentHierarchyVO.class, agentHierarchyPK);

            compose(agentHierarchyVO);
        }
        catch (Exception e) {

            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw e;
        }
        finally {

            if (crud != null) crud.close();

            crud = null;
        }

        return agentHierarchyVO;
    }

    public void compose(AgentHierarchyVO agentHierarchyVO) throws Exception {

        try {

            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(AgentSnapshotVO.class)) appendAgentSnapshotVO(agentHierarchyVO);

            if (voInclusionList.contains(SegmentVO.class)) associateSegmentVO(agentHierarchyVO);
        }
        catch (Exception e) {

            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw e;
        }
        finally {

            if (crud != null) crud.close();

            crud = null;
        }
    }

    private void associateSegmentVO(AgentHierarchyVO agentHierarchyVO) throws Exception {

        voInclusionList.remove(SegmentVO.class);

        SegmentVO segmentVO = new VOComposer().composeSegmentVO(agentHierarchyVO.getSegmentFK(), voInclusionList);

        agentHierarchyVO.setParentVO(SegmentVO.class, segmentVO);

        voInclusionList.add(SegmentVO.class);
    }

    private void appendAgentSnapshotVO(AgentHierarchyVO agentHierarchyVO) throws Exception {

        voInclusionList.remove(AgentSnapshotVO.class);

        AgentSnapshotVO[] agentSnapshotVO = (AgentSnapshotVO[]) crud.retrieveVOFromDB(AgentSnapshotVO.class, AgentHierarchyVO.class, agentHierarchyVO.getAgentHierarchyPK());

        if (agentSnapshotVO != null) agentHierarchyVO.setAgentSnapshotVO(agentSnapshotVO);

        voInclusionList.add(AgentSnapshotVO.class);
    }
}

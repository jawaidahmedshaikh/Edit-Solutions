package role.dm.composer;

import edit.common.vo.ClientDetailVO;
import edit.common.vo.ClientRoleVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Sep 29, 2003
 * Time: 11:40:43 AM
 * To change this template use Options | File Templates.
 */
public class VOComposer
{
    public ClientRoleVO composeClientRoleVO(long clientRolePK) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(ClientDetailVO.class); // Plus Bank, Tax, Pref, etc. VOs are assumed.

        return new ClientRoleComposer(voInclusionList).compose(clientRolePK);
    }
}

package codetable;

import event.financial.client.trx.*;

import org.dom4j.*;
import org.hibernate.*;
import edit.services.db.hibernate.*;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jun 20, 2005
 * Time: 3:01:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class PRASEDocumentHelper
{
    /**
     *
     * @param editTrxPK
     * @return
     */
    public Element getGroupSetup(Long editTrxPK)
    {
        String hql = "from GroupSetup gs join ContractSetup join ClientSetup join EDITTrx et where et.EDITTrxPK = :editTrxPK";

        Map params = new HashMap();

        params.put("editTrxPK", editTrxPK);

        Session hiberateSession = SessionHelper.getSession(SessionHelper.EDITSOLUTIONS);

        Session dom4jSession = hiberateSession.getSession(EntityMode.DOM4J);

        List groupSetups = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        return (Element) groupSetups.get(0);
    }
}

/*
 * User: gfrosti
 * Date: Dec 2, 2004
 * Time: 1:34:38 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package codetable;

import contract.*;

import edit.common.vo.*;

import event.*;

import event.financial.client.trx.*;

import reinsurance.*;


public class ReinsuranceDocument extends PRASEDocument
{
    private ReinsuranceVO reinsuranceVO;
    private ContractTreaty contractTreaty;

    public ReinsuranceDocument(ContractTreaty contractTreaty)
    {
        this.contractTreaty = contractTreaty;

        reinsuranceVO = new ReinsuranceVO();
    }

    /**
     * Builds an instance of NaturalDocVO containing the following VOs only:
     * a) TreatyVO.ContractTreatyVO chain
     * b) TreatyGroupNumber
     * c) ReinsurerNumber
     * Additionally, the TreatyGroup.GroupNumber and Reinsurer.ReinsurerNumber are supplied.
     * @see PRASEDocument#buildDocument()
     */
    public void buildDocument()
    {
        Treaty treaty = contractTreaty.getTreaty();
        TreatyGroup treatyGroup = treaty.getTreatyGroup();
        Reinsurer reinsurer = treaty.getReinsurer();

        TreatyVO treatyVO = null;

        reinsuranceVO.addTreatyVO(treatyVO = (TreatyVO) treaty.getVO());
        treatyVO.addContractTreatyVO((ContractTreatyVO) contractTreaty.getVO());
        reinsuranceVO.setTreatyGroupNumber(treatyGroup.getTreatyGroupNumber());
        reinsuranceVO.setReinsurerNumber(reinsurer.getReinsurerNumber());
    }

    /**
     * Returns the VO representation of this document.
     * @return
     */
    public VOObject getDocumentAsVO()
    {
        return reinsuranceVO;
    }
}

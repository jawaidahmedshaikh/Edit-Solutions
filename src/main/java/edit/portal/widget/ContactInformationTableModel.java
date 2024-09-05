/*
 * User: dlataille
 * Date: July 17, 2007
 * Time: 2:55:25 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import fission.global.*;
import fission.utility.*;

import java.util.*;

import client.ContactInformation;

public class ContactInformationTableModel extends TableModel
{
    public static final String COLUMN_CONTACT_TYPE = "Type";
    public static final String COLUMN_PHONE_EMAIL = "Phone #/Email Address";
    public static final String COLUMN_NAME = "Name";

    private static final String[] COLUMN_NAMES = {COLUMN_CONTACT_TYPE, COLUMN_PHONE_EMAIL, COLUMN_NAME};

    public ContactInformationTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String clientDetailPK = Util.initString((String) this.getAppReqBlock().getUserSession().getParameter("activeCaseClientDetailPK"), null);

        // When adding the same ContactInformation concept to Reinsurance, I decided to simply reuse
        // this TableModel. In this case, I am expected the clientDetailPK to be an HTTP parameter.
        if (clientDetailPK == null)
        {
            clientDetailPK = Util.initString(getAppReqBlock().getReqParm("clientDetailPK"), null);
        }

        if (clientDetailPK != null)
        {
            ContactInformation[] contactInformation = ContactInformation.findBy_ClientDetailFK(new Long(clientDetailPK));
            for (int i = 0; i < contactInformation.length; i++)
            {
                ContactInformationTableRow tableRow = new ContactInformationTableRow(contactInformation[i]);

                super.addRow(tableRow);
            }
        }
    }
}

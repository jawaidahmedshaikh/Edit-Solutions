/*
 * User: dlataille
 * Date: July 17, 2007
 * Time: 2:56:14 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import contract.*;
import group.ContractGroup;
import client.ContactInformation;
import fission.utility.DateTimeUtil;
import fission.utility.Util;


public class ContactInformationTableRow extends TableRow
{

    private ContactInformation contactInformation;


    public ContactInformationTableRow(ContactInformation contactInformation)
    {
        super();

        this.contactInformation = contactInformation;

        populateCellValues();
    }

    /**
     * Maps the values of Master to the TableRow.
     */
    private void populateCellValues()
    {
        String contactType = contactInformation.getContactTypeCT();

        String phoneEmail = Util.initString(contactInformation.getPhoneEmail(), "");

        String name = Util.initString(contactInformation.getName(), "");

        getCellValues().put(ContactInformationTableModel.COLUMN_CONTACT_TYPE, contactType);

        getCellValues().put(ContactInformationTableModel.COLUMN_PHONE_EMAIL, phoneEmail);

        getCellValues().put(ContactInformationTableModel.COLUMN_NAME, name);
    }

    /**
     * @see edit.portal.widgettoolkit.TableRow#getRowId()
     * @return
     */
    public String getRowId()
    {
        return contactInformation.getContactInformationPK().toString();
    }
}

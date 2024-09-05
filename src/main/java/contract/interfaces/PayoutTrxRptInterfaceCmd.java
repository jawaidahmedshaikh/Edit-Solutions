/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Nov 18, 2002
 * Time: 2:07:03 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package contract.interfaces;

import edit.common.*;
import edit.common.vo.ContractClientVO;
import edit.common.vo.SegmentVO;
import edit.services.command.Command;
import edit.services.interfaces.AbstractInterface;
import fission.utility.*;
import event.*;

import java.util.*;

import contract.*;
import client.*;

public class PayoutTrxRptInterfaceCmd extends AbstractInterface implements Command
{
    private EDITTrx editTrx;
    private StringBuffer fileData;
    private final static String oneSpace = " ";

    public void setPayoutRptInformation(EDITTrx editTrx, StringBuffer fileData)
    {
        this.editTrx = editTrx;
        this.fileData = fileData;
    }

    public Object exec()
    {
        EDITDate trxEffDate = editTrx.getEffectiveDate();

        EDITBigDecimal amount = editTrx.getTrxAmount();

        String frequency = editTrx.getClientSetup().getContractSetup().getGroupSetup().getScheduledEvent().getFrequencyCT();

        Segment segment = editTrx.getClientSetup().getContractSetup().getSegment();
        String contractNumber = segment.getContractNumber();

        ClientDetail[] clientDetail = ClientDetail.findBy_SegmentPK_RoleType(segment.getPK(), "PAY");
        Preference preference = Preference.findByClientDetailFK(clientDetail[0].getClientDetailPK());
        String printAs = "";
        String disbSource = "";

        if (preference != null)
        {
            printAs = Util.initString(preference.getPrintAs(), "");

            disbSource = Util.initString(preference.getDisbursementSourceCT(), "");
        }

        if (fileData.length() > 0)
        {

            fileData.append("\n");
        }

        fileData.append(contractNumber);

        int fieldLen = contractNumber.length();
        int numSpaces = 15 - fieldLen;
        for (int s = 1; s <= numSpaces; s++)
        {

            fileData.append(oneSpace);
        }

        fieldLen = printAs.length();
        if (fieldLen > 60)
        {

            int subLength = printAs.length() - 60;
            printAs = printAs.substring(0, printAs.length() - subLength);
            fieldLen = 60;
        }

        fileData.append(printAs);

        numSpaces = 60 - fieldLen;
        for (int s = 1; s <= numSpaces; s++)
        {

            fileData.append(oneSpace);
        }

        fileData.append(frequency);

        String formattedDollars = Util.formatDecimal("###,###,##0.00", amount);
        fieldLen = formattedDollars.length();
        numSpaces = 15 - fieldLen;
        fileData.append(formattedDollars);
        for (int s = 1; s <= numSpaces; s++)
        {

            fileData.append(oneSpace);
        }

        fileData.append(trxEffDate);
        fileData.append(disbSource);

        try
        {
            exportExtract(fileData, "payoutTrxExtract");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return null;
    }

    public void exportExtract(StringBuffer fileData, String fileName) throws Exception {

        super.exportData(fileData.toString(), fileName);
    }
}

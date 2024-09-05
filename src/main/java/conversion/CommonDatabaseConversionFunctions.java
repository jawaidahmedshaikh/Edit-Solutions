/*
 * 
 * User: cgleason
 * Date: Jan 26, 2007
 * Time: 11:07:53 AM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package conversion;

import edit.common.*;
import edit.common.vo.*;
import edit.services.db.*;

import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

import contract.*;
import role.*;
import event.common.*;
import event.*;

public class CommonDatabaseConversionFunctions
{
    /**
     * Input = a created EDITSolutions loaded from a backup with the following tables populated:
     *      CodeTableDef, CodeTable, others??
     * Along with the databases to convert = ACCOUNTING, CLIENT, CONTRACT
     * Output  = EDITSolutions populated with the appropriate data as defined in the mapping document.
     * Conversion :
     *      1.  Convert Accounting DB tables = Account, AccountingDetail, Element, ElementStructure, ElementCompanyRelation
     *      2.  Convert Client DB table = ClientAddress, ClientChangeHistory, ClientDetail
     *      3.  Convert Contract DB tables - contract and history info
     */

    public static final String MIN_DEFAULT_DATE = "0000/00/00";
    public static final String MAX_DEFAULT_DATE = "9999/99/99";


    public CommonDatabaseConversionFunctions()
    {
    }


    public static String getCodeTableValue(Integer codeTableKey)
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        String codeTableValue = null;
        if (codeTableKey != null && codeTableKey != 0)
        {
            CodeTableVO codeTableVO = codeTableWrapper.getCodeTableEntry(codeTableKey);

            if (codeTableVO != null)
            {
                codeTableValue = codeTableVO.getCode();
            }
            else
            {
                System.out.println("Code Table Key missing = " + codeTableKey);
            }
        }

        return codeTableValue;
    }

    public static String convertDate(String dateToConvert)
    {
        String convertedDate = null;

        if (dateToConvert != null)
        {
            if (dateToConvert.equals(MIN_DEFAULT_DATE) || dateToConvert.equals("00/00/00") || dateToConvert.equals("//")
                    || dateToConvert.equals("0000/0/00") || dateToConvert.equals("00/00/0000") || dateToConvert.equals("000/00/00")
                    || dateToConvert.equals(""))
            {
                convertedDate = null;
            }
            else if ((dateToConvert.trim()).equals("//"))
            {
                convertedDate = null;
            }
            else if (dateToConvert.equals((MAX_DEFAULT_DATE)) || dateToConvert.equals("999/99/99"))
            {
                convertedDate = EDITDate.DEFAULT_MAX_DATE;
            }
            else if (dateToConvert.equals("12/31/9999"))
            {
                convertedDate = EDITDate.DEFAULT_MAX_DATE;
            }
            else
            {
                EDITDate validDate = new EDITDate(dateToConvert);
                if (validDate.isAValidDate())
                {
                    convertedDate = dateToConvert;
                }
                else
                {
                    System.out.println("ClientAddress EffectiveDate = " + dateToConvert + " It was set to null");
                    convertedDate = null;
                }
            }
        }

        return convertedDate;
    }

    public static String checkMaintDateTimeFoClient(String maintDateTime)
    {
        if (maintDateTime != null)
        {
            if (maintDateTime.equals("00000000"))
            {
                maintDateTime = null;
            }

            else
            {
                String year = maintDateTime.substring(0,4);
                String month = maintDateTime.substring(4,6);
                String day = maintDateTime.substring(6, 8);
                if (!month.startsWith("/"))
                {

                    maintDateTime = year + EDITDate.DATE_DELIMITER + month + EDITDate.DATE_DELIMITER + day + " " + EDITDateTime.DEFAULT_MIN_TIME;
                }

                if (maintDateTime != null)
                {
                    if (maintDateTime.length() == 10)
                    {
                        maintDateTime = maintDateTime + " " + EDITDateTime.DEFAULT_MIN_TIME;
                    }
                }
            }
        }

        return maintDateTime;
    }

    public static String convertNoteMaintDate(String dateToConvert)
    {

        String date = null;
        if (dateToConvert != null)
        {

            if (dateToConvert.startsWith("Mon") || dateToConvert.startsWith("Tue") || dateToConvert.startsWith("Wed") ||
                dateToConvert.startsWith("Thu") || dateToConvert.startsWith("Fri") || dateToConvert.startsWith("Sat") ||
                dateToConvert.startsWith("Sun"))
            {
                String monthLiteral = dateToConvert.substring(4,7);
                String day = dateToConvert.substring(8, 10);
                String time = dateToConvert.substring(11, 19);
                String year = dateToConvert.substring(24);
                String month = null;

                if (monthLiteral.equalsIgnoreCase("jan"))
                {
                    month = "01";
                }
                else if (monthLiteral.equalsIgnoreCase("feb"))
                {
                    month = "02";
                }
                else if (monthLiteral.equalsIgnoreCase("mar"))
                {
                    month = "03";
                }
                else if (monthLiteral.equalsIgnoreCase("apr"))
                {
                    month = "04";
                }
                else if (monthLiteral.equalsIgnoreCase("may"))
                {
                    month = "05";
                }
                else if (monthLiteral.equalsIgnoreCase("jun"))
                {
                    month = "06";
                }
                else if (monthLiteral.equalsIgnoreCase("jul"))
                {
                    month = "07";
                }
                else if (monthLiteral.equalsIgnoreCase("aug"))
                {
                    month = "08";
                }
                else if (monthLiteral.equalsIgnoreCase("sep"))
                {
                    month = "09";
                }
                else if (monthLiteral.equalsIgnoreCase("oct"))
                {
                    month = "10";
                }
                else if (monthLiteral.equalsIgnoreCase("nov"))
                {
                    month = "11";
                }
                else if (monthLiteral.equalsIgnoreCase("dec"))
                {
                    month = "12";
                }
                else if (month == null)
                {
                    month = "01";
                }

                date = year + EDITDate.DATE_DELIMITER + month + EDITDate.DATE_DELIMITER + day + " " + time;
            }
            else  if (dateToConvert.equals(MIN_DEFAULT_DATE))
            {
                date = null;
            }
            else
            {
                date = dateToConvert + " " + EDITDateTime.DEFAULT_MIN_TIME;
            }
        }

        return date;
    }

    public long[] getActiveClients(long segmentPK, String transactionTypeCT, CRUD crud)
    {
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        String roleTypeCT = null;
        long[] contractClientPKs = null;

        roleTypeCT = getRoleTypeForTrx(transactionTypeCT, segmentPK, crud);

        contractClientPKs = contractLookup.findContractClientPKsBySegmentPKAndRoleType(segmentPK, roleTypeCT);

        if ((contractClientPKs == null) && !(roleTypeCT.equals(Constants.RoleType.OWNER))) // Find the owner if all else fails.
        {
            roleTypeCT = Constants.RoleType.OWNER;

            contractClientPKs = contractLookup.findContractClientPKsBySegmentPKAndRoleType(segmentPK, roleTypeCT);
        }

        return contractClientPKs;
    }

    public String getRoleTypeForTrx(String transactionTypeCT, long segmentPK, CRUD crud)
    {
        String roleTypeCT = null;
        Segment segment = null;
        if (crud == null)
        {
            segment = Segment.findByPK(segmentPK);
        }
        else
        {
            SegmentVO segmentVO = new contract.dm.dao.FastDAO().findSegmentBySegmentPK(segmentPK, crud);
            segment = new Segment(segmentVO);
        }

        if (transactionTypeCT.equalsIgnoreCase("IS") &&
            segment.getSegmentNameCT().equalsIgnoreCase(Segment.SEGMENTNAMECT_PAYOUT))
        {
            roleTypeCT = ClientRole.ROLETYPECT_ANNUITANT;
        }
        else if (transactionTypeCT.equalsIgnoreCase("IS") ||
                 transactionTypeCT.equalsIgnoreCase("MD") ||
                 transactionTypeCT.equalsIgnoreCase("FD"))
        {
            roleTypeCT = Constants.RoleType.INSURED;
        }
        else if (transactionTypeCT.equals("PO") ||
                transactionTypeCT.equals("FS") ||
                transactionTypeCT.equals(EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN) ||
                transactionTypeCT.equals("WI") ||
                transactionTypeCT.equals("DE") ||
                transactionTypeCT.equals("LS") ||
                transactionTypeCT.equals("SW"))
        {
            roleTypeCT = Constants.RoleType.PAYEE;
        }
        else if (transactionTypeCT.equals("BI") ||
                transactionTypeCT.equals("LP") ||
                transactionTypeCT.equals("LA") ||
                transactionTypeCT.equals("PY"))
        {
            roleTypeCT = Constants.RoleType.PAYOR;
        }
        else
        {
            roleTypeCT = Constants.RoleType.OWNER;
        }

        return roleTypeCT;
    }
}

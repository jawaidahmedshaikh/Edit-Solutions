/**

 * User: dlataill

 * Date: Aug 16, 2005

 * Time: 1:42:25 PM

 *

 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved

 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is

 * subject to the license agreement. 

 */
package contract.ui;

import java.util.List;
import java.util.ArrayList;

/**
 * User: dlataill
 * Date: Aug 16, 2005
 * Time: 1:42:25 PM
 * <p/>
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */

public class ImportNewBusinessResponse
{
    private String contractNumber;
    private String message;
    private String insuredSpreadsheetName;
    private String insuredDBName;
    private String ownerSpreadsheetName;
    private String ownerDBName;
    private List clientTypes;

    /**
     * Create an instance of ImportNewBusinessResponse - Is used for NewBusinessImport - will be passed to the
     * importNewBusinessReponseDialog.jsp.  This instance should be instantiated when there is not additional
     * input required from the user (the contract was either added successfully or failed for duplicate contract number)
     * @param contractNumber
     * @param message
     */
    public ImportNewBusinessResponse(String contractNumber,
                                     String message)
    {
        this.contractNumber = contractNumber;
        this.message = message;
        this.clientTypes = new ArrayList();
    }

    /**
     * Create and instance of ImportNewBusinessResponse - Is used for NewBusinessImport - will be passed to the
     * importNewBusinessResponseDialog.jsp.  This instance should be instantiated when there is additional input
     * required from the user(contract was not added successfully due to duplicate client(s))
     * @param contractNumber
     * @param message
     * @param clientTypes
     */
    public ImportNewBusinessResponse(String contractNumber,
                                     String message,
                                     List clientTypes)
    {
        this.contractNumber = contractNumber;
        this.message = message;
        this.clientTypes = clientTypes;
    }

    /**
     * Setter.
     * @param contractNumber    ContractNumber being imported from the spreadsheet.
     */
    public void setContractNumber(String contractNumber)
    {
        this.contractNumber = contractNumber;
    }

    /**
     * Getter.
     * @return contractNumber    Contract Number being imported from the spreadsheet.
     */
    public String getContractNumber()
    {
        return contractNumber;
    }

    /**
     * Setter
     * @param message    import message - denotes whether the contract being imported was successful or if the import errored.
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Getter
     * @return  import message - denotes whether the contract being imported was successful or if the import errored.
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Setter
     * @param name    insured name found on the spreadsheet (only populated if insured being imported was also found on database.)
     */
    public void setInsuredSpreadsheetName(String name)
    {
        insuredSpreadsheetName = name;
    }

    /**
     * Getter
     * @return   insured name found on the spreadsheet (only populated if insured being imported was also found on database.)
     */
    public String getInsuredSpreadsheetName()
    {
        return insuredSpreadsheetName;
    }

    /**
     * Setter
     * @param name   insured name found on the database (only populated if insured being imported was also found on database.)
     */
    public void setInsuredDBName(String name)
    {
        insuredDBName = name;
    }

    /**
     * Getter
     * @return  insured name found on the database (only populated if insured being imported was also found on database.)
     */
    public String getInsuredDBName()
    {
        return insuredDBName;
    }

    /**
     * Setter
     * @param name    owner name found on the spreadsheet (only populated if owner being imported was also found on database.)
     */
    public void setOwnerSpreadsheetName(String name)
    {
        ownerSpreadsheetName = name;
    }

    /**
     * Getter
     * @return   owner name found on the spreadsheet (only populated if owner being imported was also found on database.)
     */
    public String getOwnerSpreadsheetName()
    {
        return ownerSpreadsheetName;
    }

    /**
     * Setter
     * @param name     owner name found on the database (only populated if owner being imported was also found on database.)
     */
    public void setOwnerDBName(String name)
    {
        ownerDBName = name;
    }

    /**
     * Getter
     * @return owner name found on the database (only populated if owner being imported was also found on database.)
     */
    public String getOwnerDBName()
    {
        return ownerDBName;
    }

    /**
     * Setter
     * @param clientTypes  list of client types where the client(s) specified in the spreadsheet were also found
     *                     on the database.
     */
    public void setClientTypes(List clientTypes)
    {
        this.clientTypes = clientTypes;
    }

    /**
     * Getter
     * @return list of clientTypes where client(s) specified in the spreadsheet were also found on the database.
     */
    public List getClientTypes()
    {
        return clientTypes;
    }
}

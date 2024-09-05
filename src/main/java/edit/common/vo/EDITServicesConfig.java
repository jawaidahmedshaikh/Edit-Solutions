/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package edit.common.vo;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class EDITServicesConfig extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _EDITLogList
     */
    private java.util.Vector _EDITLogList;

    /**
     * Field _JAASConfigList
     */
    private java.util.Vector _JAASConfigList;

    /**
     * Field _SOAPConfig
     */
    private edit.common.vo.SOAPConfig _SOAPConfig;

    /**
     * Field _OFACConfig
     */
    private edit.common.vo.OFACConfig _OFACConfig;

    /**
     * Field _EDITIssueProcessList
     */
    private java.util.Vector _EDITIssueProcessList;

    /**
     * Field _EDITDeathProcessList
     */
    private java.util.Vector _EDITDeathProcessList;

    /**
     * Field _spawningTransactionList
     */
    private java.util.Vector _spawningTransactionList;

    /**
     * Field _EDITReportList
     */
    private java.util.Vector _EDITReportList;

    /**
     * Field _dateCalculationsList
     */
    private java.util.Vector _dateCalculationsList;

    /**
     * Field _ignoreAgentLicensingEditsList
     */
    private java.util.Vector _ignoreAgentLicensingEditsList;

    /**
     * Field _reportCompanyName
     */
    private java.lang.String _reportCompanyName;

    /**
     * Field _checkForOverlappingSituationDates
     */
    private java.lang.String _checkForOverlappingSituationDates;

    /**
     * Field _nonFinancialEntityList
     */
    private java.util.Vector _nonFinancialEntityList;

    /**
     * Field _contractHistoryDisplay
     */
    private java.lang.String _contractHistoryDisplay;


      //----------------/
     //- Constructors -/
    //----------------/

    public EDITServicesConfig() {
        super();
        _EDITLogList = new Vector();
        _JAASConfigList = new Vector();
        _EDITIssueProcessList = new Vector();
        _EDITDeathProcessList = new Vector();
        _spawningTransactionList = new Vector();
        _EDITReportList = new Vector();
        _dateCalculationsList = new Vector();
        _ignoreAgentLicensingEditsList = new Vector();
        _nonFinancialEntityList = new Vector();
    } //-- edit.common.vo.EDITServicesConfig()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addDateCalculations
     * 
     * @param vDateCalculations
     */
    public void addDateCalculations(edit.common.vo.DateCalculations vDateCalculations)
        throws java.lang.IndexOutOfBoundsException
    {
        _dateCalculationsList.addElement(vDateCalculations);
    } //-- void addDateCalculations(edit.common.vo.DateCalculations) 

    /**
     * Method addDateCalculations
     * 
     * @param index
     * @param vDateCalculations
     */
    public void addDateCalculations(int index, edit.common.vo.DateCalculations vDateCalculations)
        throws java.lang.IndexOutOfBoundsException
    {
        _dateCalculationsList.insertElementAt(vDateCalculations, index);
    } //-- void addDateCalculations(int, edit.common.vo.DateCalculations) 

    /**
     * Method addEDITDeathProcess
     * 
     * @param vEDITDeathProcess
     */
    public void addEDITDeathProcess(edit.common.vo.EDITDeathProcess vEDITDeathProcess)
        throws java.lang.IndexOutOfBoundsException
    {
        _EDITDeathProcessList.addElement(vEDITDeathProcess);
    } //-- void addEDITDeathProcess(edit.common.vo.EDITDeathProcess) 

    /**
     * Method addEDITDeathProcess
     * 
     * @param index
     * @param vEDITDeathProcess
     */
    public void addEDITDeathProcess(int index, edit.common.vo.EDITDeathProcess vEDITDeathProcess)
        throws java.lang.IndexOutOfBoundsException
    {
        _EDITDeathProcessList.insertElementAt(vEDITDeathProcess, index);
    } //-- void addEDITDeathProcess(int, edit.common.vo.EDITDeathProcess) 

    /**
     * Method addEDITIssueProcess
     * 
     * @param vEDITIssueProcess
     */
    public void addEDITIssueProcess(edit.common.vo.EDITIssueProcess vEDITIssueProcess)
        throws java.lang.IndexOutOfBoundsException
    {
        _EDITIssueProcessList.addElement(vEDITIssueProcess);
    } //-- void addEDITIssueProcess(edit.common.vo.EDITIssueProcess) 

    /**
     * Method addEDITIssueProcess
     * 
     * @param index
     * @param vEDITIssueProcess
     */
    public void addEDITIssueProcess(int index, edit.common.vo.EDITIssueProcess vEDITIssueProcess)
        throws java.lang.IndexOutOfBoundsException
    {
        _EDITIssueProcessList.insertElementAt(vEDITIssueProcess, index);
    } //-- void addEDITIssueProcess(int, edit.common.vo.EDITIssueProcess) 

    /**
     * Method addEDITLog
     * 
     * @param vEDITLog
     */
    public void addEDITLog(edit.common.vo.EDITLog vEDITLog)
        throws java.lang.IndexOutOfBoundsException
    {
        _EDITLogList.addElement(vEDITLog);
    } //-- void addEDITLog(edit.common.vo.EDITLog) 

    /**
     * Method addEDITLog
     * 
     * @param index
     * @param vEDITLog
     */
    public void addEDITLog(int index, edit.common.vo.EDITLog vEDITLog)
        throws java.lang.IndexOutOfBoundsException
    {
        _EDITLogList.insertElementAt(vEDITLog, index);
    } //-- void addEDITLog(int, edit.common.vo.EDITLog) 

    /**
     * Method addEDITReport
     * 
     * @param vEDITReport
     */
    public void addEDITReport(edit.common.vo.EDITReport vEDITReport)
        throws java.lang.IndexOutOfBoundsException
    {
        _EDITReportList.addElement(vEDITReport);
    } //-- void addEDITReport(edit.common.vo.EDITReport) 

    /**
     * Method addEDITReport
     * 
     * @param index
     * @param vEDITReport
     */
    public void addEDITReport(int index, edit.common.vo.EDITReport vEDITReport)
        throws java.lang.IndexOutOfBoundsException
    {
        _EDITReportList.insertElementAt(vEDITReport, index);
    } //-- void addEDITReport(int, edit.common.vo.EDITReport) 

    /**
     * Method addIgnoreAgentLicensingEdits
     * 
     * @param vIgnoreAgentLicensingEdits
     */
    public void addIgnoreAgentLicensingEdits(edit.common.vo.IgnoreAgentLicensingEdits vIgnoreAgentLicensingEdits)
        throws java.lang.IndexOutOfBoundsException
    {
        _ignoreAgentLicensingEditsList.addElement(vIgnoreAgentLicensingEdits);
    } //-- void addIgnoreAgentLicensingEdits(edit.common.vo.IgnoreAgentLicensingEdits) 

    /**
     * Method addIgnoreAgentLicensingEdits
     * 
     * @param index
     * @param vIgnoreAgentLicensingEdits
     */
    public void addIgnoreAgentLicensingEdits(int index, edit.common.vo.IgnoreAgentLicensingEdits vIgnoreAgentLicensingEdits)
        throws java.lang.IndexOutOfBoundsException
    {
        _ignoreAgentLicensingEditsList.insertElementAt(vIgnoreAgentLicensingEdits, index);
    } //-- void addIgnoreAgentLicensingEdits(int, edit.common.vo.IgnoreAgentLicensingEdits) 

    /**
     * Method addJAASConfig
     * 
     * @param vJAASConfig
     */
    public void addJAASConfig(edit.common.vo.JAASConfig vJAASConfig)
        throws java.lang.IndexOutOfBoundsException
    {
        _JAASConfigList.addElement(vJAASConfig);
    } //-- void addJAASConfig(edit.common.vo.JAASConfig) 

    /**
     * Method addJAASConfig
     * 
     * @param index
     * @param vJAASConfig
     */
    public void addJAASConfig(int index, edit.common.vo.JAASConfig vJAASConfig)
        throws java.lang.IndexOutOfBoundsException
    {
        _JAASConfigList.insertElementAt(vJAASConfig, index);
    } //-- void addJAASConfig(int, edit.common.vo.JAASConfig) 

    /**
     * Method addNonFinancialEntity
     * 
     * @param vNonFinancialEntity
     */
    public void addNonFinancialEntity(edit.common.vo.NonFinancialEntity vNonFinancialEntity)
        throws java.lang.IndexOutOfBoundsException
    {
        _nonFinancialEntityList.addElement(vNonFinancialEntity);
    } //-- void addNonFinancialEntity(edit.common.vo.NonFinancialEntity) 

    /**
     * Method addNonFinancialEntity
     * 
     * @param index
     * @param vNonFinancialEntity
     */
    public void addNonFinancialEntity(int index, edit.common.vo.NonFinancialEntity vNonFinancialEntity)
        throws java.lang.IndexOutOfBoundsException
    {
        _nonFinancialEntityList.insertElementAt(vNonFinancialEntity, index);
    } //-- void addNonFinancialEntity(int, edit.common.vo.NonFinancialEntity) 

    /**
     * Method addSpawningTransaction
     * 
     * @param vSpawningTransaction
     */
    public void addSpawningTransaction(edit.common.vo.SpawningTransaction vSpawningTransaction)
        throws java.lang.IndexOutOfBoundsException
    {
        _spawningTransactionList.addElement(vSpawningTransaction);
    } //-- void addSpawningTransaction(edit.common.vo.SpawningTransaction) 

    /**
     * Method addSpawningTransaction
     * 
     * @param index
     * @param vSpawningTransaction
     */
    public void addSpawningTransaction(int index, edit.common.vo.SpawningTransaction vSpawningTransaction)
        throws java.lang.IndexOutOfBoundsException
    {
        _spawningTransactionList.insertElementAt(vSpawningTransaction, index);
    } //-- void addSpawningTransaction(int, edit.common.vo.SpawningTransaction) 

    /**
     * Method enumerateDateCalculations
     */
    public java.util.Enumeration enumerateDateCalculations()
    {
        return _dateCalculationsList.elements();
    } //-- java.util.Enumeration enumerateDateCalculations() 

    /**
     * Method enumerateEDITDeathProcess
     */
    public java.util.Enumeration enumerateEDITDeathProcess()
    {
        return _EDITDeathProcessList.elements();
    } //-- java.util.Enumeration enumerateEDITDeathProcess() 

    /**
     * Method enumerateEDITIssueProcess
     */
    public java.util.Enumeration enumerateEDITIssueProcess()
    {
        return _EDITIssueProcessList.elements();
    } //-- java.util.Enumeration enumerateEDITIssueProcess() 

    /**
     * Method enumerateEDITLog
     */
    public java.util.Enumeration enumerateEDITLog()
    {
        return _EDITLogList.elements();
    } //-- java.util.Enumeration enumerateEDITLog() 

    /**
     * Method enumerateEDITReport
     */
    public java.util.Enumeration enumerateEDITReport()
    {
        return _EDITReportList.elements();
    } //-- java.util.Enumeration enumerateEDITReport() 

    /**
     * Method enumerateIgnoreAgentLicensingEdits
     */
    public java.util.Enumeration enumerateIgnoreAgentLicensingEdits()
    {
        return _ignoreAgentLicensingEditsList.elements();
    } //-- java.util.Enumeration enumerateIgnoreAgentLicensingEdits() 

    /**
     * Method enumerateJAASConfig
     */
    public java.util.Enumeration enumerateJAASConfig()
    {
        return _JAASConfigList.elements();
    } //-- java.util.Enumeration enumerateJAASConfig() 

    /**
     * Method enumerateNonFinancialEntity
     */
    public java.util.Enumeration enumerateNonFinancialEntity()
    {
        return _nonFinancialEntityList.elements();
    } //-- java.util.Enumeration enumerateNonFinancialEntity() 

    /**
     * Method enumerateSpawningTransaction
     */
    public java.util.Enumeration enumerateSpawningTransaction()
    {
        return _spawningTransactionList.elements();
    } //-- java.util.Enumeration enumerateSpawningTransaction() 

    /**
     * Note: hashCode() has not been overriden
     * 
     * @param obj
     */
    public boolean equals(java.lang.Object obj)
    {
        if ( this == obj )
            return true;
        
        if (super.equals(obj)==false)
            return false;
        
        if (obj instanceof EDITServicesConfig) {
        
            EDITServicesConfig temp = (EDITServicesConfig)obj;
            
            if (this._EDITLogList != null) {
                if (temp._EDITLogList == null) return false;
                else if (!(this._EDITLogList.equals(temp._EDITLogList))) 
                    return false;
            }
            else if (temp._EDITLogList != null)
                return false;
            if (this._JAASConfigList != null) {
                if (temp._JAASConfigList == null) return false;
                else if (!(this._JAASConfigList.equals(temp._JAASConfigList))) 
                    return false;
            }
            else if (temp._JAASConfigList != null)
                return false;
            if (this._SOAPConfig != null) {
                if (temp._SOAPConfig == null) return false;
                else if (!(this._SOAPConfig.equals(temp._SOAPConfig))) 
                    return false;
            }
            else if (temp._SOAPConfig != null)
                return false;
            if (this._OFACConfig != null) {
                if (temp._OFACConfig == null) return false;
                else if (!(this._OFACConfig.equals(temp._OFACConfig))) 
                    return false;
            }
            else if (temp._OFACConfig != null)
                return false;
            if (this._EDITIssueProcessList != null) {
                if (temp._EDITIssueProcessList == null) return false;
                else if (!(this._EDITIssueProcessList.equals(temp._EDITIssueProcessList))) 
                    return false;
            }
            else if (temp._EDITIssueProcessList != null)
                return false;
            if (this._EDITDeathProcessList != null) {
                if (temp._EDITDeathProcessList == null) return false;
                else if (!(this._EDITDeathProcessList.equals(temp._EDITDeathProcessList))) 
                    return false;
            }
            else if (temp._EDITDeathProcessList != null)
                return false;
            if (this._spawningTransactionList != null) {
                if (temp._spawningTransactionList == null) return false;
                else if (!(this._spawningTransactionList.equals(temp._spawningTransactionList))) 
                    return false;
            }
            else if (temp._spawningTransactionList != null)
                return false;
            if (this._EDITReportList != null) {
                if (temp._EDITReportList == null) return false;
                else if (!(this._EDITReportList.equals(temp._EDITReportList))) 
                    return false;
            }
            else if (temp._EDITReportList != null)
                return false;
            if (this._dateCalculationsList != null) {
                if (temp._dateCalculationsList == null) return false;
                else if (!(this._dateCalculationsList.equals(temp._dateCalculationsList))) 
                    return false;
            }
            else if (temp._dateCalculationsList != null)
                return false;
            if (this._ignoreAgentLicensingEditsList != null) {
                if (temp._ignoreAgentLicensingEditsList == null) return false;
                else if (!(this._ignoreAgentLicensingEditsList.equals(temp._ignoreAgentLicensingEditsList))) 
                    return false;
            }
            else if (temp._ignoreAgentLicensingEditsList != null)
                return false;
            if (this._reportCompanyName != null) {
                if (temp._reportCompanyName == null) return false;
                else if (!(this._reportCompanyName.equals(temp._reportCompanyName))) 
                    return false;
            }
            else if (temp._reportCompanyName != null)
                return false;
            if (this._checkForOverlappingSituationDates != null) {
                if (temp._checkForOverlappingSituationDates == null) return false;
                else if (!(this._checkForOverlappingSituationDates.equals(temp._checkForOverlappingSituationDates))) 
                    return false;
            }
            else if (temp._checkForOverlappingSituationDates != null)
                return false;
            if (this._nonFinancialEntityList != null) {
                if (temp._nonFinancialEntityList == null) return false;
                else if (!(this._nonFinancialEntityList.equals(temp._nonFinancialEntityList))) 
                    return false;
            }
            else if (temp._nonFinancialEntityList != null)
                return false;
            if (this._contractHistoryDisplay != null) {
                if (temp._contractHistoryDisplay == null) return false;
                else if (!(this._contractHistoryDisplay.equals(temp._contractHistoryDisplay))) 
                    return false;
            }
            else if (temp._contractHistoryDisplay != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCheckForOverlappingSituationDatesReturns the value
     * of field 'checkForOverlappingSituationDates'.
     * 
     * @return the value of field
     * 'checkForOverlappingSituationDates'.
     */
    public java.lang.String getCheckForOverlappingSituationDates()
    {
        return this._checkForOverlappingSituationDates;
    } //-- java.lang.String getCheckForOverlappingSituationDates() 

    /**
     * Method getContractHistoryDisplayReturns the value of field
     * 'contractHistoryDisplay'.
     * 
     * @return the value of field 'contractHistoryDisplay'.
     */
    public java.lang.String getContractHistoryDisplay()
    {
        return this._contractHistoryDisplay;
    } //-- java.lang.String getContractHistoryDisplay() 

    /**
     * Method getDateCalculations
     * 
     * @param index
     */
    public edit.common.vo.DateCalculations getDateCalculations(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _dateCalculationsList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.DateCalculations) _dateCalculationsList.elementAt(index);
    } //-- edit.common.vo.DateCalculations getDateCalculations(int) 

    /**
     * Method getDateCalculations
     */
    public edit.common.vo.DateCalculations[] getDateCalculations()
    {
        int size = _dateCalculationsList.size();
        edit.common.vo.DateCalculations[] mArray = new edit.common.vo.DateCalculations[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.DateCalculations) _dateCalculationsList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.DateCalculations[] getDateCalculations() 

    /**
     * Method getDateCalculationsCount
     */
    public int getDateCalculationsCount()
    {
        return _dateCalculationsList.size();
    } //-- int getDateCalculationsCount() 

    /**
     * Method getEDITDeathProcess
     * 
     * @param index
     */
    public edit.common.vo.EDITDeathProcess getEDITDeathProcess(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITDeathProcessList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.EDITDeathProcess) _EDITDeathProcessList.elementAt(index);
    } //-- edit.common.vo.EDITDeathProcess getEDITDeathProcess(int) 

    /**
     * Method getEDITDeathProcess
     */
    public edit.common.vo.EDITDeathProcess[] getEDITDeathProcess()
    {
        int size = _EDITDeathProcessList.size();
        edit.common.vo.EDITDeathProcess[] mArray = new edit.common.vo.EDITDeathProcess[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.EDITDeathProcess) _EDITDeathProcessList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.EDITDeathProcess[] getEDITDeathProcess() 

    /**
     * Method getEDITDeathProcessCount
     */
    public int getEDITDeathProcessCount()
    {
        return _EDITDeathProcessList.size();
    } //-- int getEDITDeathProcessCount() 

    /**
     * Method getEDITIssueProcess
     * 
     * @param index
     */
    public edit.common.vo.EDITIssueProcess getEDITIssueProcess(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITIssueProcessList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.EDITIssueProcess) _EDITIssueProcessList.elementAt(index);
    } //-- edit.common.vo.EDITIssueProcess getEDITIssueProcess(int) 

    /**
     * Method getEDITIssueProcess
     */
    public edit.common.vo.EDITIssueProcess[] getEDITIssueProcess()
    {
        int size = _EDITIssueProcessList.size();
        edit.common.vo.EDITIssueProcess[] mArray = new edit.common.vo.EDITIssueProcess[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.EDITIssueProcess) _EDITIssueProcessList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.EDITIssueProcess[] getEDITIssueProcess() 

    /**
     * Method getEDITIssueProcessCount
     */
    public int getEDITIssueProcessCount()
    {
        return _EDITIssueProcessList.size();
    } //-- int getEDITIssueProcessCount() 

    /**
     * Method getEDITLog
     * 
     * @param index
     */
    public edit.common.vo.EDITLog getEDITLog(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITLogList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.EDITLog) _EDITLogList.elementAt(index);
    } //-- edit.common.vo.EDITLog getEDITLog(int) 

    /**
     * Method getEDITLog
     */
    public edit.common.vo.EDITLog[] getEDITLog()
    {
        int size = _EDITLogList.size();
        edit.common.vo.EDITLog[] mArray = new edit.common.vo.EDITLog[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.EDITLog) _EDITLogList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.EDITLog[] getEDITLog() 

    /**
     * Method getEDITLogCount
     */
    public int getEDITLogCount()
    {
        return _EDITLogList.size();
    } //-- int getEDITLogCount() 

    /**
     * Method getEDITReport
     * 
     * @param index
     */
    public edit.common.vo.EDITReport getEDITReport(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITReportList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.EDITReport) _EDITReportList.elementAt(index);
    } //-- edit.common.vo.EDITReport getEDITReport(int) 

    /**
     * Method getEDITReport
     */
    public edit.common.vo.EDITReport[] getEDITReport()
    {
        int size = _EDITReportList.size();
        edit.common.vo.EDITReport[] mArray = new edit.common.vo.EDITReport[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.EDITReport) _EDITReportList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.EDITReport[] getEDITReport() 

    /**
     * Method getEDITReportCount
     */
    public int getEDITReportCount()
    {
        return _EDITReportList.size();
    } //-- int getEDITReportCount() 

    /**
     * Method getIgnoreAgentLicensingEdits
     * 
     * @param index
     */
    public edit.common.vo.IgnoreAgentLicensingEdits getIgnoreAgentLicensingEdits(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _ignoreAgentLicensingEditsList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.IgnoreAgentLicensingEdits) _ignoreAgentLicensingEditsList.elementAt(index);
    } //-- edit.common.vo.IgnoreAgentLicensingEdits getIgnoreAgentLicensingEdits(int) 

    /**
     * Method getIgnoreAgentLicensingEdits
     */
    public edit.common.vo.IgnoreAgentLicensingEdits[] getIgnoreAgentLicensingEdits()
    {
        int size = _ignoreAgentLicensingEditsList.size();
        edit.common.vo.IgnoreAgentLicensingEdits[] mArray = new edit.common.vo.IgnoreAgentLicensingEdits[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.IgnoreAgentLicensingEdits) _ignoreAgentLicensingEditsList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.IgnoreAgentLicensingEdits[] getIgnoreAgentLicensingEdits() 

    /**
     * Method getIgnoreAgentLicensingEditsCount
     */
    public int getIgnoreAgentLicensingEditsCount()
    {
        return _ignoreAgentLicensingEditsList.size();
    } //-- int getIgnoreAgentLicensingEditsCount() 

    /**
     * Method getJAASConfig
     * 
     * @param index
     */
    public edit.common.vo.JAASConfig getJAASConfig(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _JAASConfigList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.JAASConfig) _JAASConfigList.elementAt(index);
    } //-- edit.common.vo.JAASConfig getJAASConfig(int) 

    /**
     * Method getJAASConfig
     */
    public edit.common.vo.JAASConfig[] getJAASConfig()
    {
        int size = _JAASConfigList.size();
        edit.common.vo.JAASConfig[] mArray = new edit.common.vo.JAASConfig[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.JAASConfig) _JAASConfigList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.JAASConfig[] getJAASConfig() 

    /**
     * Method getJAASConfigCount
     */
    public int getJAASConfigCount()
    {
        return _JAASConfigList.size();
    } //-- int getJAASConfigCount() 

    /**
     * Method getNonFinancialEntity
     * 
     * @param index
     */
    public edit.common.vo.NonFinancialEntity getNonFinancialEntity(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _nonFinancialEntityList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.NonFinancialEntity) _nonFinancialEntityList.elementAt(index);
    } //-- edit.common.vo.NonFinancialEntity getNonFinancialEntity(int) 

    /**
     * Method getNonFinancialEntity
     */
    public edit.common.vo.NonFinancialEntity[] getNonFinancialEntity()
    {
        int size = _nonFinancialEntityList.size();
        edit.common.vo.NonFinancialEntity[] mArray = new edit.common.vo.NonFinancialEntity[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.NonFinancialEntity) _nonFinancialEntityList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.NonFinancialEntity[] getNonFinancialEntity() 

    /**
     * Method getNonFinancialEntityCount
     */
    public int getNonFinancialEntityCount()
    {
        return _nonFinancialEntityList.size();
    } //-- int getNonFinancialEntityCount() 

    /**
     * Method getOFACConfigReturns the value of field 'OFACConfig'.
     * 
     * @return the value of field 'OFACConfig'.
     */
    public edit.common.vo.OFACConfig getOFACConfig()
    {
        return this._OFACConfig;
    } //-- edit.common.vo.OFACConfig getOFACConfig() 

    /**
     * Method getReportCompanyNameReturns the value of field
     * 'reportCompanyName'.
     * 
     * @return the value of field 'reportCompanyName'.
     */
    public java.lang.String getReportCompanyName()
    {
        return this._reportCompanyName;
    } //-- java.lang.String getReportCompanyName() 

    /**
     * Method getSOAPConfigReturns the value of field 'SOAPConfig'.
     * 
     * @return the value of field 'SOAPConfig'.
     */
    public edit.common.vo.SOAPConfig getSOAPConfig()
    {
        return this._SOAPConfig;
    } //-- edit.common.vo.SOAPConfig getSOAPConfig() 

    /**
     * Method getSpawningTransaction
     * 
     * @param index
     */
    public edit.common.vo.SpawningTransaction getSpawningTransaction(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _spawningTransactionList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SpawningTransaction) _spawningTransactionList.elementAt(index);
    } //-- edit.common.vo.SpawningTransaction getSpawningTransaction(int) 

    /**
     * Method getSpawningTransaction
     */
    public edit.common.vo.SpawningTransaction[] getSpawningTransaction()
    {
        int size = _spawningTransactionList.size();
        edit.common.vo.SpawningTransaction[] mArray = new edit.common.vo.SpawningTransaction[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SpawningTransaction) _spawningTransactionList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SpawningTransaction[] getSpawningTransaction() 

    /**
     * Method getSpawningTransactionCount
     */
    public int getSpawningTransactionCount()
    {
        return _spawningTransactionList.size();
    } //-- int getSpawningTransactionCount() 

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Method removeAllDateCalculations
     */
    public void removeAllDateCalculations()
    {
        _dateCalculationsList.removeAllElements();
    } //-- void removeAllDateCalculations() 

    /**
     * Method removeAllEDITDeathProcess
     */
    public void removeAllEDITDeathProcess()
    {
        _EDITDeathProcessList.removeAllElements();
    } //-- void removeAllEDITDeathProcess() 

    /**
     * Method removeAllEDITIssueProcess
     */
    public void removeAllEDITIssueProcess()
    {
        _EDITIssueProcessList.removeAllElements();
    } //-- void removeAllEDITIssueProcess() 

    /**
     * Method removeAllEDITLog
     */
    public void removeAllEDITLog()
    {
        _EDITLogList.removeAllElements();
    } //-- void removeAllEDITLog() 

    /**
     * Method removeAllEDITReport
     */
    public void removeAllEDITReport()
    {
        _EDITReportList.removeAllElements();
    } //-- void removeAllEDITReport() 

    /**
     * Method removeAllIgnoreAgentLicensingEdits
     */
    public void removeAllIgnoreAgentLicensingEdits()
    {
        _ignoreAgentLicensingEditsList.removeAllElements();
    } //-- void removeAllIgnoreAgentLicensingEdits() 

    /**
     * Method removeAllJAASConfig
     */
    public void removeAllJAASConfig()
    {
        _JAASConfigList.removeAllElements();
    } //-- void removeAllJAASConfig() 

    /**
     * Method removeAllNonFinancialEntity
     */
    public void removeAllNonFinancialEntity()
    {
        _nonFinancialEntityList.removeAllElements();
    } //-- void removeAllNonFinancialEntity() 

    /**
     * Method removeAllSpawningTransaction
     */
    public void removeAllSpawningTransaction()
    {
        _spawningTransactionList.removeAllElements();
    } //-- void removeAllSpawningTransaction() 

    /**
     * Method removeDateCalculations
     * 
     * @param index
     */
    public edit.common.vo.DateCalculations removeDateCalculations(int index)
    {
        java.lang.Object obj = _dateCalculationsList.elementAt(index);
        _dateCalculationsList.removeElementAt(index);
        return (edit.common.vo.DateCalculations) obj;
    } //-- edit.common.vo.DateCalculations removeDateCalculations(int) 

    /**
     * Method removeEDITDeathProcess
     * 
     * @param index
     */
    public edit.common.vo.EDITDeathProcess removeEDITDeathProcess(int index)
    {
        java.lang.Object obj = _EDITDeathProcessList.elementAt(index);
        _EDITDeathProcessList.removeElementAt(index);
        return (edit.common.vo.EDITDeathProcess) obj;
    } //-- edit.common.vo.EDITDeathProcess removeEDITDeathProcess(int) 

    /**
     * Method removeEDITIssueProcess
     * 
     * @param index
     */
    public edit.common.vo.EDITIssueProcess removeEDITIssueProcess(int index)
    {
        java.lang.Object obj = _EDITIssueProcessList.elementAt(index);
        _EDITIssueProcessList.removeElementAt(index);
        return (edit.common.vo.EDITIssueProcess) obj;
    } //-- edit.common.vo.EDITIssueProcess removeEDITIssueProcess(int) 

    /**
     * Method removeEDITLog
     * 
     * @param index
     */
    public edit.common.vo.EDITLog removeEDITLog(int index)
    {
        java.lang.Object obj = _EDITLogList.elementAt(index);
        _EDITLogList.removeElementAt(index);
        return (edit.common.vo.EDITLog) obj;
    } //-- edit.common.vo.EDITLog removeEDITLog(int) 

    /**
     * Method removeEDITReport
     * 
     * @param index
     */
    public edit.common.vo.EDITReport removeEDITReport(int index)
    {
        java.lang.Object obj = _EDITReportList.elementAt(index);
        _EDITReportList.removeElementAt(index);
        return (edit.common.vo.EDITReport) obj;
    } //-- edit.common.vo.EDITReport removeEDITReport(int) 

    /**
     * Method removeIgnoreAgentLicensingEdits
     * 
     * @param index
     */
    public edit.common.vo.IgnoreAgentLicensingEdits removeIgnoreAgentLicensingEdits(int index)
    {
        java.lang.Object obj = _ignoreAgentLicensingEditsList.elementAt(index);
        _ignoreAgentLicensingEditsList.removeElementAt(index);
        return (edit.common.vo.IgnoreAgentLicensingEdits) obj;
    } //-- edit.common.vo.IgnoreAgentLicensingEdits removeIgnoreAgentLicensingEdits(int) 

    /**
     * Method removeJAASConfig
     * 
     * @param index
     */
    public edit.common.vo.JAASConfig removeJAASConfig(int index)
    {
        java.lang.Object obj = _JAASConfigList.elementAt(index);
        _JAASConfigList.removeElementAt(index);
        return (edit.common.vo.JAASConfig) obj;
    } //-- edit.common.vo.JAASConfig removeJAASConfig(int) 

    /**
     * Method removeNonFinancialEntity
     * 
     * @param index
     */
    public edit.common.vo.NonFinancialEntity removeNonFinancialEntity(int index)
    {
        java.lang.Object obj = _nonFinancialEntityList.elementAt(index);
        _nonFinancialEntityList.removeElementAt(index);
        return (edit.common.vo.NonFinancialEntity) obj;
    } //-- edit.common.vo.NonFinancialEntity removeNonFinancialEntity(int) 

    /**
     * Method removeSpawningTransaction
     * 
     * @param index
     */
    public edit.common.vo.SpawningTransaction removeSpawningTransaction(int index)
    {
        java.lang.Object obj = _spawningTransactionList.elementAt(index);
        _spawningTransactionList.removeElementAt(index);
        return (edit.common.vo.SpawningTransaction) obj;
    } //-- edit.common.vo.SpawningTransaction removeSpawningTransaction(int) 

    /**
     * Method setCheckForOverlappingSituationDatesSets the value of
     * field 'checkForOverlappingSituationDates'.
     * 
     * @param checkForOverlappingSituationDates the value of field
     * 'checkForOverlappingSituationDates'.
     */
    public void setCheckForOverlappingSituationDates(java.lang.String checkForOverlappingSituationDates)
    {
        this._checkForOverlappingSituationDates = checkForOverlappingSituationDates;
        
        super.setVoChanged(true);
    } //-- void setCheckForOverlappingSituationDates(java.lang.String) 

    /**
     * Method setContractHistoryDisplaySets the value of field
     * 'contractHistoryDisplay'.
     * 
     * @param contractHistoryDisplay the value of field
     * 'contractHistoryDisplay'.
     */
    public void setContractHistoryDisplay(java.lang.String contractHistoryDisplay)
    {
        this._contractHistoryDisplay = contractHistoryDisplay;
        
        super.setVoChanged(true);
    } //-- void setContractHistoryDisplay(java.lang.String) 

    /**
     * Method setDateCalculations
     * 
     * @param index
     * @param vDateCalculations
     */
    public void setDateCalculations(int index, edit.common.vo.DateCalculations vDateCalculations)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _dateCalculationsList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _dateCalculationsList.setElementAt(vDateCalculations, index);
    } //-- void setDateCalculations(int, edit.common.vo.DateCalculations) 

    /**
     * Method setDateCalculations
     * 
     * @param dateCalculationsArray
     */
    public void setDateCalculations(edit.common.vo.DateCalculations[] dateCalculationsArray)
    {
        //-- copy array
        _dateCalculationsList.removeAllElements();
        for (int i = 0; i < dateCalculationsArray.length; i++) {
            _dateCalculationsList.addElement(dateCalculationsArray[i]);
        }
    } //-- void setDateCalculations(edit.common.vo.DateCalculations) 

    /**
     * Method setEDITDeathProcess
     * 
     * @param index
     * @param vEDITDeathProcess
     */
    public void setEDITDeathProcess(int index, edit.common.vo.EDITDeathProcess vEDITDeathProcess)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITDeathProcessList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _EDITDeathProcessList.setElementAt(vEDITDeathProcess, index);
    } //-- void setEDITDeathProcess(int, edit.common.vo.EDITDeathProcess) 

    /**
     * Method setEDITDeathProcess
     * 
     * @param EDITDeathProcessArray
     */
    public void setEDITDeathProcess(edit.common.vo.EDITDeathProcess[] EDITDeathProcessArray)
    {
        //-- copy array
        _EDITDeathProcessList.removeAllElements();
        for (int i = 0; i < EDITDeathProcessArray.length; i++) {
            _EDITDeathProcessList.addElement(EDITDeathProcessArray[i]);
        }
    } //-- void setEDITDeathProcess(edit.common.vo.EDITDeathProcess) 

    /**
     * Method setEDITIssueProcess
     * 
     * @param index
     * @param vEDITIssueProcess
     */
    public void setEDITIssueProcess(int index, edit.common.vo.EDITIssueProcess vEDITIssueProcess)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITIssueProcessList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _EDITIssueProcessList.setElementAt(vEDITIssueProcess, index);
    } //-- void setEDITIssueProcess(int, edit.common.vo.EDITIssueProcess) 

    /**
     * Method setEDITIssueProcess
     * 
     * @param EDITIssueProcessArray
     */
    public void setEDITIssueProcess(edit.common.vo.EDITIssueProcess[] EDITIssueProcessArray)
    {
        //-- copy array
        _EDITIssueProcessList.removeAllElements();
        for (int i = 0; i < EDITIssueProcessArray.length; i++) {
            _EDITIssueProcessList.addElement(EDITIssueProcessArray[i]);
        }
    } //-- void setEDITIssueProcess(edit.common.vo.EDITIssueProcess) 

    /**
     * Method setEDITLog
     * 
     * @param index
     * @param vEDITLog
     */
    public void setEDITLog(int index, edit.common.vo.EDITLog vEDITLog)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITLogList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _EDITLogList.setElementAt(vEDITLog, index);
    } //-- void setEDITLog(int, edit.common.vo.EDITLog) 

    /**
     * Method setEDITLog
     * 
     * @param EDITLogArray
     */
    public void setEDITLog(edit.common.vo.EDITLog[] EDITLogArray)
    {
        //-- copy array
        _EDITLogList.removeAllElements();
        for (int i = 0; i < EDITLogArray.length; i++) {
            _EDITLogList.addElement(EDITLogArray[i]);
        }
    } //-- void setEDITLog(edit.common.vo.EDITLog) 

    /**
     * Method setEDITReport
     * 
     * @param index
     * @param vEDITReport
     */
    public void setEDITReport(int index, edit.common.vo.EDITReport vEDITReport)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITReportList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _EDITReportList.setElementAt(vEDITReport, index);
    } //-- void setEDITReport(int, edit.common.vo.EDITReport) 

    /**
     * Method setEDITReport
     * 
     * @param EDITReportArray
     */
    public void setEDITReport(edit.common.vo.EDITReport[] EDITReportArray)
    {
        //-- copy array
        _EDITReportList.removeAllElements();
        for (int i = 0; i < EDITReportArray.length; i++) {
            _EDITReportList.addElement(EDITReportArray[i]);
        }
    } //-- void setEDITReport(edit.common.vo.EDITReport) 

    /**
     * Method setIgnoreAgentLicensingEdits
     * 
     * @param index
     * @param vIgnoreAgentLicensingEdits
     */
    public void setIgnoreAgentLicensingEdits(int index, edit.common.vo.IgnoreAgentLicensingEdits vIgnoreAgentLicensingEdits)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _ignoreAgentLicensingEditsList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _ignoreAgentLicensingEditsList.setElementAt(vIgnoreAgentLicensingEdits, index);
    } //-- void setIgnoreAgentLicensingEdits(int, edit.common.vo.IgnoreAgentLicensingEdits) 

    /**
     * Method setIgnoreAgentLicensingEdits
     * 
     * @param ignoreAgentLicensingEditsArray
     */
    public void setIgnoreAgentLicensingEdits(edit.common.vo.IgnoreAgentLicensingEdits[] ignoreAgentLicensingEditsArray)
    {
        //-- copy array
        _ignoreAgentLicensingEditsList.removeAllElements();
        for (int i = 0; i < ignoreAgentLicensingEditsArray.length; i++) {
            _ignoreAgentLicensingEditsList.addElement(ignoreAgentLicensingEditsArray[i]);
        }
    } //-- void setIgnoreAgentLicensingEdits(edit.common.vo.IgnoreAgentLicensingEdits) 

    /**
     * Method setJAASConfig
     * 
     * @param index
     * @param vJAASConfig
     */
    public void setJAASConfig(int index, edit.common.vo.JAASConfig vJAASConfig)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _JAASConfigList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _JAASConfigList.setElementAt(vJAASConfig, index);
    } //-- void setJAASConfig(int, edit.common.vo.JAASConfig) 

    /**
     * Method setJAASConfig
     * 
     * @param JAASConfigArray
     */
    public void setJAASConfig(edit.common.vo.JAASConfig[] JAASConfigArray)
    {
        //-- copy array
        _JAASConfigList.removeAllElements();
        for (int i = 0; i < JAASConfigArray.length; i++) {
            _JAASConfigList.addElement(JAASConfigArray[i]);
        }
    } //-- void setJAASConfig(edit.common.vo.JAASConfig) 

    /**
     * Method setNonFinancialEntity
     * 
     * @param index
     * @param vNonFinancialEntity
     */
    public void setNonFinancialEntity(int index, edit.common.vo.NonFinancialEntity vNonFinancialEntity)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _nonFinancialEntityList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _nonFinancialEntityList.setElementAt(vNonFinancialEntity, index);
    } //-- void setNonFinancialEntity(int, edit.common.vo.NonFinancialEntity) 

    /**
     * Method setNonFinancialEntity
     * 
     * @param nonFinancialEntityArray
     */
    public void setNonFinancialEntity(edit.common.vo.NonFinancialEntity[] nonFinancialEntityArray)
    {
        //-- copy array
        _nonFinancialEntityList.removeAllElements();
        for (int i = 0; i < nonFinancialEntityArray.length; i++) {
            _nonFinancialEntityList.addElement(nonFinancialEntityArray[i]);
        }
    } //-- void setNonFinancialEntity(edit.common.vo.NonFinancialEntity) 

    /**
     * Method setOFACConfigSets the value of field 'OFACConfig'.
     * 
     * @param OFACConfig the value of field 'OFACConfig'.
     */
    public void setOFACConfig(edit.common.vo.OFACConfig OFACConfig)
    {
        this._OFACConfig = OFACConfig;
        
        super.setVoChanged(true);
    } //-- void setOFACConfig(edit.common.vo.OFACConfig) 

    /**
     * Method setReportCompanyNameSets the value of field
     * 'reportCompanyName'.
     * 
     * @param reportCompanyName the value of field
     * 'reportCompanyName'.
     */
    public void setReportCompanyName(java.lang.String reportCompanyName)
    {
        this._reportCompanyName = reportCompanyName;
        
        super.setVoChanged(true);
    } //-- void setReportCompanyName(java.lang.String) 

    /**
     * Method setSOAPConfigSets the value of field 'SOAPConfig'.
     * 
     * @param SOAPConfig the value of field 'SOAPConfig'.
     */
    public void setSOAPConfig(edit.common.vo.SOAPConfig SOAPConfig)
    {
        this._SOAPConfig = SOAPConfig;
        
        super.setVoChanged(true);
    } //-- void setSOAPConfig(edit.common.vo.SOAPConfig) 

    /**
     * Method setSpawningTransaction
     * 
     * @param index
     * @param vSpawningTransaction
     */
    public void setSpawningTransaction(int index, edit.common.vo.SpawningTransaction vSpawningTransaction)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _spawningTransactionList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _spawningTransactionList.setElementAt(vSpawningTransaction, index);
    } //-- void setSpawningTransaction(int, edit.common.vo.SpawningTransaction) 

    /**
     * Method setSpawningTransaction
     * 
     * @param spawningTransactionArray
     */
    public void setSpawningTransaction(edit.common.vo.SpawningTransaction[] spawningTransactionArray)
    {
        //-- copy array
        _spawningTransactionList.removeAllElements();
        for (int i = 0; i < spawningTransactionArray.length; i++) {
            _spawningTransactionList.addElement(spawningTransactionArray[i]);
        }
    } //-- void setSpawningTransaction(edit.common.vo.SpawningTransaction) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EDITServicesConfig unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EDITServicesConfig) Unmarshaller.unmarshal(edit.common.vo.EDITServicesConfig.class, reader);
    } //-- edit.common.vo.EDITServicesConfig unmarshal(java.io.Reader) 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}

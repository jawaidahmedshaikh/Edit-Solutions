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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Comment descrBatch Status
 * 
 * @version $Revision$ $Date$
 */
public class BatchStatusVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _uniqueBatchId
     */
    private java.lang.String _uniqueBatchId;

    /**
     * Field _totalNumberOfContracts
     */
    private long _totalNumberOfContracts;

    /**
     * keeps track of state for field: _totalNumberOfContracts
     */
    private boolean _has_totalNumberOfContracts;

    /**
     * Field _completedContracts
     */
    private long _completedContracts;

    /**
     * keeps track of state for field: _completedContracts
     */
    private boolean _has_completedContracts;

    /**
     * Field _dataBaseUpdated
     */
    private java.lang.String _dataBaseUpdated;

    /**
     * Field _operator
     */
    private java.lang.String _operator;


      //----------------/
     //- Constructors -/
    //----------------/

    public BatchStatusVO() {
        super();
    } //-- edit.common.vo.BatchStatusVO()


      //-----------/
     //- Methods -/
    //-----------/

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
        
        if (obj instanceof BatchStatusVO) {
        
            BatchStatusVO temp = (BatchStatusVO)obj;
            if (this._uniqueBatchId != null) {
                if (temp._uniqueBatchId == null) return false;
                else if (!(this._uniqueBatchId.equals(temp._uniqueBatchId))) 
                    return false;
            }
            else if (temp._uniqueBatchId != null)
                return false;
            if (this._totalNumberOfContracts != temp._totalNumberOfContracts)
                return false;
            if (this._has_totalNumberOfContracts != temp._has_totalNumberOfContracts)
                return false;
            if (this._completedContracts != temp._completedContracts)
                return false;
            if (this._has_completedContracts != temp._has_completedContracts)
                return false;
            if (this._dataBaseUpdated != null) {
                if (temp._dataBaseUpdated == null) return false;
                else if (!(this._dataBaseUpdated.equals(temp._dataBaseUpdated))) 
                    return false;
            }
            else if (temp._dataBaseUpdated != null)
                return false;
            if (this._operator != null) {
                if (temp._operator == null) return false;
                else if (!(this._operator.equals(temp._operator))) 
                    return false;
            }
            else if (temp._operator != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCompletedContractsReturns the value of field
     * 'completedContracts'.
     * 
     * @return the value of field 'completedContracts'.
     */
    public long getCompletedContracts()
    {
        return this._completedContracts;
    } //-- long getCompletedContracts() 

    /**
     * Method getDataBaseUpdatedReturns the value of field
     * 'dataBaseUpdated'.
     * 
     * @return the value of field 'dataBaseUpdated'.
     */
    public java.lang.String getDataBaseUpdated()
    {
        return this._dataBaseUpdated;
    } //-- java.lang.String getDataBaseUpdated() 

    /**
     * Method getOperatorReturns the value of field 'operator'.
     * 
     * @return the value of field 'operator'.
     */
    public java.lang.String getOperator()
    {
        return this._operator;
    } //-- java.lang.String getOperator() 

    /**
     * Method getTotalNumberOfContractsReturns the value of field
     * 'totalNumberOfContracts'.
     * 
     * @return the value of field 'totalNumberOfContracts'.
     */
    public long getTotalNumberOfContracts()
    {
        return this._totalNumberOfContracts;
    } //-- long getTotalNumberOfContracts() 

    /**
     * Method getUniqueBatchIdReturns the value of field
     * 'uniqueBatchId'.
     * 
     * @return the value of field 'uniqueBatchId'.
     */
    public java.lang.String getUniqueBatchId()
    {
        return this._uniqueBatchId;
    } //-- java.lang.String getUniqueBatchId() 

    /**
     * Method hasCompletedContracts
     */
    public boolean hasCompletedContracts()
    {
        return this._has_completedContracts;
    } //-- boolean hasCompletedContracts() 

    /**
     * Method hasTotalNumberOfContracts
     */
    public boolean hasTotalNumberOfContracts()
    {
        return this._has_totalNumberOfContracts;
    } //-- boolean hasTotalNumberOfContracts() 

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
     * Method setCompletedContractsSets the value of field
     * 'completedContracts'.
     * 
     * @param completedContracts the value of field
     * 'completedContracts'.
     */
    public void setCompletedContracts(long completedContracts)
    {
        this._completedContracts = completedContracts;
        
        super.setVoChanged(true);
        this._has_completedContracts = true;
    } //-- void setCompletedContracts(long) 

    /**
     * Method setDataBaseUpdatedSets the value of field
     * 'dataBaseUpdated'.
     * 
     * @param dataBaseUpdated the value of field 'dataBaseUpdated'.
     */
    public void setDataBaseUpdated(java.lang.String dataBaseUpdated)
    {
        this._dataBaseUpdated = dataBaseUpdated;
        
        super.setVoChanged(true);
    } //-- void setDataBaseUpdated(java.lang.String) 

    /**
     * Method setOperatorSets the value of field 'operator'.
     * 
     * @param operator the value of field 'operator'.
     */
    public void setOperator(java.lang.String operator)
    {
        this._operator = operator;
        
        super.setVoChanged(true);
    } //-- void setOperator(java.lang.String) 

    /**
     * Method setTotalNumberOfContractsSets the value of field
     * 'totalNumberOfContracts'.
     * 
     * @param totalNumberOfContracts the value of field
     * 'totalNumberOfContracts'.
     */
    public void setTotalNumberOfContracts(long totalNumberOfContracts)
    {
        this._totalNumberOfContracts = totalNumberOfContracts;
        
        super.setVoChanged(true);
        this._has_totalNumberOfContracts = true;
    } //-- void setTotalNumberOfContracts(long) 

    /**
     * Method setUniqueBatchIdSets the value of field
     * 'uniqueBatchId'.
     * 
     * @param uniqueBatchId the value of field 'uniqueBatchId'.
     */
    public void setUniqueBatchId(java.lang.String uniqueBatchId)
    {
        this._uniqueBatchId = uniqueBatchId;
        
        super.setVoChanged(true);
    } //-- void setUniqueBatchId(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BatchStatusVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BatchStatusVO) Unmarshaller.unmarshal(edit.common.vo.BatchStatusVO.class, reader);
    } //-- edit.common.vo.BatchStatusVO unmarshal(java.io.Reader) 

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

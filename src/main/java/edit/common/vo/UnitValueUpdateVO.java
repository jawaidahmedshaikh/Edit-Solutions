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
 * Used to hold the unit value update import file lines
 * 
 * @version $Revision$ $Date$
 */
public class UnitValueUpdateVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _fundIndicator
     */
    private java.lang.String _fundIndicator;

    /**
     * Field _recordType
     */
    private java.lang.String _recordType;

    /**
     * Field _detailRecordList
     */
    private java.util.Vector _detailRecordList;

    /**
     * Field _rateRecordList
     */
    private java.util.Vector _rateRecordList;


      //----------------/
     //- Constructors -/
    //----------------/

    public UnitValueUpdateVO() {
        super();
        _detailRecordList = new Vector();
        _rateRecordList = new Vector();
    } //-- edit.common.vo.UnitValueUpdateVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addDetailRecord
     * 
     * @param vDetailRecord
     */
    public void addDetailRecord(edit.common.vo.DetailRecord vDetailRecord)
        throws java.lang.IndexOutOfBoundsException
    {
        _detailRecordList.addElement(vDetailRecord);
    } //-- void addDetailRecord(edit.common.vo.DetailRecord) 

    /**
     * Method addDetailRecord
     * 
     * @param index
     * @param vDetailRecord
     */
    public void addDetailRecord(int index, edit.common.vo.DetailRecord vDetailRecord)
        throws java.lang.IndexOutOfBoundsException
    {
        _detailRecordList.insertElementAt(vDetailRecord, index);
    } //-- void addDetailRecord(int, edit.common.vo.DetailRecord) 

    /**
     * Method addRateRecord
     * 
     * @param vRateRecord
     */
    public void addRateRecord(edit.common.vo.RateRecord vRateRecord)
        throws java.lang.IndexOutOfBoundsException
    {
        _rateRecordList.addElement(vRateRecord);
    } //-- void addRateRecord(edit.common.vo.RateRecord) 

    /**
     * Method addRateRecord
     * 
     * @param index
     * @param vRateRecord
     */
    public void addRateRecord(int index, edit.common.vo.RateRecord vRateRecord)
        throws java.lang.IndexOutOfBoundsException
    {
        _rateRecordList.insertElementAt(vRateRecord, index);
    } //-- void addRateRecord(int, edit.common.vo.RateRecord) 

    /**
     * Method enumerateDetailRecord
     */
    public java.util.Enumeration enumerateDetailRecord()
    {
        return _detailRecordList.elements();
    } //-- java.util.Enumeration enumerateDetailRecord() 

    /**
     * Method enumerateRateRecord
     */
    public java.util.Enumeration enumerateRateRecord()
    {
        return _rateRecordList.elements();
    } //-- java.util.Enumeration enumerateRateRecord() 

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
        
        if (obj instanceof UnitValueUpdateVO) {
        
            UnitValueUpdateVO temp = (UnitValueUpdateVO)obj;
            if (this._fundIndicator != null) {
                if (temp._fundIndicator == null) return false;
                else if (!(this._fundIndicator.equals(temp._fundIndicator))) 
                    return false;
            }
            else if (temp._fundIndicator != null)
                return false;
            if (this._recordType != null) {
                if (temp._recordType == null) return false;
                else if (!(this._recordType.equals(temp._recordType))) 
                    return false;
            }
            else if (temp._recordType != null)
                return false;
            if (this._detailRecordList != null) {
                if (temp._detailRecordList == null) return false;
                else if (!(this._detailRecordList.equals(temp._detailRecordList))) 
                    return false;
            }
            else if (temp._detailRecordList != null)
                return false;
            if (this._rateRecordList != null) {
                if (temp._rateRecordList == null) return false;
                else if (!(this._rateRecordList.equals(temp._rateRecordList))) 
                    return false;
            }
            else if (temp._rateRecordList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getDetailRecord
     * 
     * @param index
     */
    public edit.common.vo.DetailRecord getDetailRecord(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _detailRecordList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.DetailRecord) _detailRecordList.elementAt(index);
    } //-- edit.common.vo.DetailRecord getDetailRecord(int) 

    /**
     * Method getDetailRecord
     */
    public edit.common.vo.DetailRecord[] getDetailRecord()
    {
        int size = _detailRecordList.size();
        edit.common.vo.DetailRecord[] mArray = new edit.common.vo.DetailRecord[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.DetailRecord) _detailRecordList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.DetailRecord[] getDetailRecord() 

    /**
     * Method getDetailRecordCount
     */
    public int getDetailRecordCount()
    {
        return _detailRecordList.size();
    } //-- int getDetailRecordCount() 

    /**
     * Method getFundIndicatorReturns the value of field
     * 'fundIndicator'.
     * 
     * @return the value of field 'fundIndicator'.
     */
    public java.lang.String getFundIndicator()
    {
        return this._fundIndicator;
    } //-- java.lang.String getFundIndicator() 

    /**
     * Method getRateRecord
     * 
     * @param index
     */
    public edit.common.vo.RateRecord getRateRecord(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _rateRecordList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.RateRecord) _rateRecordList.elementAt(index);
    } //-- edit.common.vo.RateRecord getRateRecord(int) 

    /**
     * Method getRateRecord
     */
    public edit.common.vo.RateRecord[] getRateRecord()
    {
        int size = _rateRecordList.size();
        edit.common.vo.RateRecord[] mArray = new edit.common.vo.RateRecord[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.RateRecord) _rateRecordList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.RateRecord[] getRateRecord() 

    /**
     * Method getRateRecordCount
     */
    public int getRateRecordCount()
    {
        return _rateRecordList.size();
    } //-- int getRateRecordCount() 

    /**
     * Method getRecordTypeReturns the value of field 'recordType'.
     * 
     * @return the value of field 'recordType'.
     */
    public java.lang.String getRecordType()
    {
        return this._recordType;
    } //-- java.lang.String getRecordType() 

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
     * Method removeAllDetailRecord
     */
    public void removeAllDetailRecord()
    {
        _detailRecordList.removeAllElements();
    } //-- void removeAllDetailRecord() 

    /**
     * Method removeAllRateRecord
     */
    public void removeAllRateRecord()
    {
        _rateRecordList.removeAllElements();
    } //-- void removeAllRateRecord() 

    /**
     * Method removeDetailRecord
     * 
     * @param index
     */
    public edit.common.vo.DetailRecord removeDetailRecord(int index)
    {
        java.lang.Object obj = _detailRecordList.elementAt(index);
        _detailRecordList.removeElementAt(index);
        return (edit.common.vo.DetailRecord) obj;
    } //-- edit.common.vo.DetailRecord removeDetailRecord(int) 

    /**
     * Method removeRateRecord
     * 
     * @param index
     */
    public edit.common.vo.RateRecord removeRateRecord(int index)
    {
        java.lang.Object obj = _rateRecordList.elementAt(index);
        _rateRecordList.removeElementAt(index);
        return (edit.common.vo.RateRecord) obj;
    } //-- edit.common.vo.RateRecord removeRateRecord(int) 

    /**
     * Method setDetailRecord
     * 
     * @param index
     * @param vDetailRecord
     */
    public void setDetailRecord(int index, edit.common.vo.DetailRecord vDetailRecord)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _detailRecordList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _detailRecordList.setElementAt(vDetailRecord, index);
    } //-- void setDetailRecord(int, edit.common.vo.DetailRecord) 

    /**
     * Method setDetailRecord
     * 
     * @param detailRecordArray
     */
    public void setDetailRecord(edit.common.vo.DetailRecord[] detailRecordArray)
    {
        //-- copy array
        _detailRecordList.removeAllElements();
        for (int i = 0; i < detailRecordArray.length; i++) {
            _detailRecordList.addElement(detailRecordArray[i]);
        }
    } //-- void setDetailRecord(edit.common.vo.DetailRecord) 

    /**
     * Method setFundIndicatorSets the value of field
     * 'fundIndicator'.
     * 
     * @param fundIndicator the value of field 'fundIndicator'.
     */
    public void setFundIndicator(java.lang.String fundIndicator)
    {
        this._fundIndicator = fundIndicator;
        
        super.setVoChanged(true);
    } //-- void setFundIndicator(java.lang.String) 

    /**
     * Method setRateRecord
     * 
     * @param index
     * @param vRateRecord
     */
    public void setRateRecord(int index, edit.common.vo.RateRecord vRateRecord)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _rateRecordList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _rateRecordList.setElementAt(vRateRecord, index);
    } //-- void setRateRecord(int, edit.common.vo.RateRecord) 

    /**
     * Method setRateRecord
     * 
     * @param rateRecordArray
     */
    public void setRateRecord(edit.common.vo.RateRecord[] rateRecordArray)
    {
        //-- copy array
        _rateRecordList.removeAllElements();
        for (int i = 0; i < rateRecordArray.length; i++) {
            _rateRecordList.addElement(rateRecordArray[i]);
        }
    } //-- void setRateRecord(edit.common.vo.RateRecord) 

    /**
     * Method setRecordTypeSets the value of field 'recordType'.
     * 
     * @param recordType the value of field 'recordType'.
     */
    public void setRecordType(java.lang.String recordType)
    {
        this._recordType = recordType;
        
        super.setVoChanged(true);
    } //-- void setRecordType(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.UnitValueUpdateVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.UnitValueUpdateVO) Unmarshaller.unmarshal(edit.common.vo.UnitValueUpdateVO.class, reader);
    } //-- edit.common.vo.UnitValueUpdateVO unmarshal(java.io.Reader) 

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

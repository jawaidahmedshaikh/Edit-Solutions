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
 * Class ClientAddressVO.
 * 
 * @version $Revision$ $Date$
 */
public class ClientAddressVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _clientAddressPK
     */
    private long _clientAddressPK;

    /**
     * keeps track of state for field: _clientAddressPK
     */
    private boolean _has_clientAddressPK;

    /**
     * Field _clientDetailFK
     */
    private long _clientDetailFK;

    /**
     * keeps track of state for field: _clientDetailFK
     */
    private boolean _has_clientDetailFK;

    /**
     * Field _sequenceNumber
     */
    private int _sequenceNumber;

    /**
     * keeps track of state for field: _sequenceNumber
     */
    private boolean _has_sequenceNumber;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _terminationDate
     */
    private java.lang.String _terminationDate;

    /**
     * Field _startDate
     */
    private java.lang.String _startDate;

    /**
     * Field _stopDate
     */
    private java.lang.String _stopDate;

    /**
     * Field _zipCode
     */
    private java.lang.String _zipCode;

    /**
     * Field _addressLine1
     */
    private java.lang.String _addressLine1;

    /**
     * Field _addressLine2
     */
    private java.lang.String _addressLine2;

    /**
     * Field _addressLine3
     */
    private java.lang.String _addressLine3;

    /**
     * Field _addressLine4
     */
    private java.lang.String _addressLine4;

    /**
     * Field _county
     */
    private java.lang.String _county;

    /**
     * Field _city
     */
    private java.lang.String _city;

    /**
     * Field _addressTypeCT
     */
    private java.lang.String _addressTypeCT;

    /**
     * Field _zipCodePlacementCT
     */
    private java.lang.String _zipCodePlacementCT;

    /**
     * Field _stateCT
     */
    private java.lang.String _stateCT;

    /**
     * Field _countryCT
     */
    private java.lang.String _countryCT;

    /**
     * Field _overrideStatus
     */
    private java.lang.String _overrideStatus;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _suspenseVOList
     */
    private java.util.Vector _suspenseVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ClientAddressVO() {
        super();
        _suspenseVOList = new Vector();
    } //-- edit.common.vo.ClientAddressVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addSuspenseVO
     * 
     * @param vSuspenseVO
     */
    public void addSuspenseVO(edit.common.vo.SuspenseVO vSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSuspenseVO.setParentVO(this.getClass(), this);
        _suspenseVOList.addElement(vSuspenseVO);
    } //-- void addSuspenseVO(edit.common.vo.SuspenseVO) 

    /**
     * Method addSuspenseVO
     * 
     * @param index
     * @param vSuspenseVO
     */
    public void addSuspenseVO(int index, edit.common.vo.SuspenseVO vSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSuspenseVO.setParentVO(this.getClass(), this);
        _suspenseVOList.insertElementAt(vSuspenseVO, index);
    } //-- void addSuspenseVO(int, edit.common.vo.SuspenseVO) 

    /**
     * Method enumerateSuspenseVO
     */
    public java.util.Enumeration enumerateSuspenseVO()
    {
        return _suspenseVOList.elements();
    } //-- java.util.Enumeration enumerateSuspenseVO() 

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
        
        if (obj instanceof ClientAddressVO) {
        
            ClientAddressVO temp = (ClientAddressVO)obj;
            if (this._clientAddressPK != temp._clientAddressPK)
                return false;
            if (this._has_clientAddressPK != temp._has_clientAddressPK)
                return false;
            if (this._clientDetailFK != temp._clientDetailFK)
                return false;
            if (this._has_clientDetailFK != temp._has_clientDetailFK)
                return false;
            if (this._sequenceNumber != temp._sequenceNumber)
                return false;
            if (this._has_sequenceNumber != temp._has_sequenceNumber)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._terminationDate != null) {
                if (temp._terminationDate == null) return false;
                else if (!(this._terminationDate.equals(temp._terminationDate))) 
                    return false;
            }
            else if (temp._terminationDate != null)
                return false;
            if (this._startDate != null) {
                if (temp._startDate == null) return false;
                else if (!(this._startDate.equals(temp._startDate))) 
                    return false;
            }
            else if (temp._startDate != null)
                return false;
            if (this._stopDate != null) {
                if (temp._stopDate == null) return false;
                else if (!(this._stopDate.equals(temp._stopDate))) 
                    return false;
            }
            else if (temp._stopDate != null)
                return false;
            if (this._zipCode != null) {
                if (temp._zipCode == null) return false;
                else if (!(this._zipCode.equals(temp._zipCode))) 
                    return false;
            }
            else if (temp._zipCode != null)
                return false;
            if (this._addressLine1 != null) {
                if (temp._addressLine1 == null) return false;
                else if (!(this._addressLine1.equals(temp._addressLine1))) 
                    return false;
            }
            else if (temp._addressLine1 != null)
                return false;
            if (this._addressLine2 != null) {
                if (temp._addressLine2 == null) return false;
                else if (!(this._addressLine2.equals(temp._addressLine2))) 
                    return false;
            }
            else if (temp._addressLine2 != null)
                return false;
            if (this._addressLine3 != null) {
                if (temp._addressLine3 == null) return false;
                else if (!(this._addressLine3.equals(temp._addressLine3))) 
                    return false;
            }
            else if (temp._addressLine3 != null)
                return false;
            if (this._addressLine4 != null) {
                if (temp._addressLine4 == null) return false;
                else if (!(this._addressLine4.equals(temp._addressLine4))) 
                    return false;
            }
            else if (temp._addressLine4 != null)
                return false;
            if (this._county != null) {
                if (temp._county == null) return false;
                else if (!(this._county.equals(temp._county))) 
                    return false;
            }
            else if (temp._county != null)
                return false;
            if (this._city != null) {
                if (temp._city == null) return false;
                else if (!(this._city.equals(temp._city))) 
                    return false;
            }
            else if (temp._city != null)
                return false;
            if (this._addressTypeCT != null) {
                if (temp._addressTypeCT == null) return false;
                else if (!(this._addressTypeCT.equals(temp._addressTypeCT))) 
                    return false;
            }
            else if (temp._addressTypeCT != null)
                return false;
            if (this._zipCodePlacementCT != null) {
                if (temp._zipCodePlacementCT == null) return false;
                else if (!(this._zipCodePlacementCT.equals(temp._zipCodePlacementCT))) 
                    return false;
            }
            else if (temp._zipCodePlacementCT != null)
                return false;
            if (this._stateCT != null) {
                if (temp._stateCT == null) return false;
                else if (!(this._stateCT.equals(temp._stateCT))) 
                    return false;
            }
            else if (temp._stateCT != null)
                return false;
            if (this._countryCT != null) {
                if (temp._countryCT == null) return false;
                else if (!(this._countryCT.equals(temp._countryCT))) 
                    return false;
            }
            else if (temp._countryCT != null)
                return false;
            if (this._overrideStatus != null) {
                if (temp._overrideStatus == null) return false;
                else if (!(this._overrideStatus.equals(temp._overrideStatus))) 
                    return false;
            }
            else if (temp._overrideStatus != null)
                return false;
            if (this._operator != null) {
                if (temp._operator == null) return false;
                else if (!(this._operator.equals(temp._operator))) 
                    return false;
            }
            else if (temp._operator != null)
                return false;
            if (this._maintDateTime != null) {
                if (temp._maintDateTime == null) return false;
                else if (!(this._maintDateTime.equals(temp._maintDateTime))) 
                    return false;
            }
            else if (temp._maintDateTime != null)
                return false;
            if (this._suspenseVOList != null) {
                if (temp._suspenseVOList == null) return false;
                else if (!(this._suspenseVOList.equals(temp._suspenseVOList))) 
                    return false;
            }
            else if (temp._suspenseVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAddressLine1Returns the value of field
     * 'addressLine1'.
     * 
     * @return the value of field 'addressLine1'.
     */
    public java.lang.String getAddressLine1()
    {
        return this._addressLine1;
    } //-- java.lang.String getAddressLine1() 

    /**
     * Method getAddressLine2Returns the value of field
     * 'addressLine2'.
     * 
     * @return the value of field 'addressLine2'.
     */
    public java.lang.String getAddressLine2()
    {
        return this._addressLine2;
    } //-- java.lang.String getAddressLine2() 

    /**
     * Method getAddressLine3Returns the value of field
     * 'addressLine3'.
     * 
     * @return the value of field 'addressLine3'.
     */
    public java.lang.String getAddressLine3()
    {
        return this._addressLine3;
    } //-- java.lang.String getAddressLine3() 

    /**
     * Method getAddressLine4Returns the value of field
     * 'addressLine4'.
     * 
     * @return the value of field 'addressLine4'.
     */
    public java.lang.String getAddressLine4()
    {
        return this._addressLine4;
    } //-- java.lang.String getAddressLine4() 

    /**
     * Method getAddressTypeCTReturns the value of field
     * 'addressTypeCT'.
     * 
     * @return the value of field 'addressTypeCT'.
     */
    public java.lang.String getAddressTypeCT()
    {
        return this._addressTypeCT;
    } //-- java.lang.String getAddressTypeCT() 

    /**
     * Method getCityReturns the value of field 'city'.
     * 
     * @return the value of field 'city'.
     */
    public java.lang.String getCity()
    {
        return this._city;
    } //-- java.lang.String getCity() 

    /**
     * Method getClientAddressPKReturns the value of field
     * 'clientAddressPK'.
     * 
     * @return the value of field 'clientAddressPK'.
     */
    public long getClientAddressPK()
    {
        return this._clientAddressPK;
    } //-- long getClientAddressPK() 

    /**
     * Method getClientDetailFKReturns the value of field
     * 'clientDetailFK'.
     * 
     * @return the value of field 'clientDetailFK'.
     */
    public long getClientDetailFK()
    {
        return this._clientDetailFK;
    } //-- long getClientDetailFK() 

    /**
     * Method getCountryCTReturns the value of field 'countryCT'.
     * 
     * @return the value of field 'countryCT'.
     */
    public java.lang.String getCountryCT()
    {
        return this._countryCT;
    } //-- java.lang.String getCountryCT() 

    /**
     * Method getCountyReturns the value of field 'county'.
     * 
     * @return the value of field 'county'.
     */
    public java.lang.String getCounty()
    {
        return this._county;
    } //-- java.lang.String getCounty() 

    /**
     * Method getEffectiveDateReturns the value of field
     * 'effectiveDate'.
     * 
     * @return the value of field 'effectiveDate'.
     */
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } //-- java.lang.String getEffectiveDate() 

    /**
     * Method getMaintDateTimeReturns the value of field
     * 'maintDateTime'.
     * 
     * @return the value of field 'maintDateTime'.
     */
    public java.lang.String getMaintDateTime()
    {
        return this._maintDateTime;
    } //-- java.lang.String getMaintDateTime() 

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
     * Method getOverrideStatusReturns the value of field
     * 'overrideStatus'.
     * 
     * @return the value of field 'overrideStatus'.
     */
    public java.lang.String getOverrideStatus()
    {
        return this._overrideStatus;
    } //-- java.lang.String getOverrideStatus() 

    /**
     * Method getSequenceNumberReturns the value of field
     * 'sequenceNumber'.
     * 
     * @return the value of field 'sequenceNumber'.
     */
    public int getSequenceNumber()
    {
        return this._sequenceNumber;
    } //-- int getSequenceNumber() 

    /**
     * Method getStartDateReturns the value of field 'startDate'.
     * 
     * @return the value of field 'startDate'.
     */
    public java.lang.String getStartDate()
    {
        return this._startDate;
    } //-- java.lang.String getStartDate() 

    /**
     * Method getStateCTReturns the value of field 'stateCT'.
     * 
     * @return the value of field 'stateCT'.
     */
    public java.lang.String getStateCT()
    {
        return this._stateCT;
    } //-- java.lang.String getStateCT() 

    /**
     * Method getStopDateReturns the value of field 'stopDate'.
     * 
     * @return the value of field 'stopDate'.
     */
    public java.lang.String getStopDate()
    {
        return this._stopDate;
    } //-- java.lang.String getStopDate() 

    /**
     * Method getSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.SuspenseVO getSuspenseVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _suspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SuspenseVO) _suspenseVOList.elementAt(index);
    } //-- edit.common.vo.SuspenseVO getSuspenseVO(int) 

    /**
     * Method getSuspenseVO
     */
    public edit.common.vo.SuspenseVO[] getSuspenseVO()
    {
        int size = _suspenseVOList.size();
        edit.common.vo.SuspenseVO[] mArray = new edit.common.vo.SuspenseVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SuspenseVO) _suspenseVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SuspenseVO[] getSuspenseVO() 

    /**
     * Method getSuspenseVOCount
     */
    public int getSuspenseVOCount()
    {
        return _suspenseVOList.size();
    } //-- int getSuspenseVOCount() 

    /**
     * Method getTerminationDateReturns the value of field
     * 'terminationDate'.
     * 
     * @return the value of field 'terminationDate'.
     */
    public java.lang.String getTerminationDate()
    {
        return this._terminationDate;
    } //-- java.lang.String getTerminationDate() 

    /**
     * Method getZipCodeReturns the value of field 'zipCode'.
     * 
     * @return the value of field 'zipCode'.
     */
    public java.lang.String getZipCode()
    {
        return this._zipCode;
    } //-- java.lang.String getZipCode() 

    /**
     * Method getZipCodePlacementCTReturns the value of field
     * 'zipCodePlacementCT'.
     * 
     * @return the value of field 'zipCodePlacementCT'.
     */
    public java.lang.String getZipCodePlacementCT()
    {
        return this._zipCodePlacementCT;
    } //-- java.lang.String getZipCodePlacementCT() 

    /**
     * Method hasClientAddressPK
     */
    public boolean hasClientAddressPK()
    {
        return this._has_clientAddressPK;
    } //-- boolean hasClientAddressPK() 

    /**
     * Method hasClientDetailFK
     */
    public boolean hasClientDetailFK()
    {
        return this._has_clientDetailFK;
    } //-- boolean hasClientDetailFK() 

    /**
     * Method hasSequenceNumber
     */
    public boolean hasSequenceNumber()
    {
        return this._has_sequenceNumber;
    } //-- boolean hasSequenceNumber() 

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
     * Method removeAllSuspenseVO
     */
    public void removeAllSuspenseVO()
    {
        _suspenseVOList.removeAllElements();
    } //-- void removeAllSuspenseVO() 

    /**
     * Method removeSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.SuspenseVO removeSuspenseVO(int index)
    {
        java.lang.Object obj = _suspenseVOList.elementAt(index);
        _suspenseVOList.removeElementAt(index);
        return (edit.common.vo.SuspenseVO) obj;
    } //-- edit.common.vo.SuspenseVO removeSuspenseVO(int) 

    /**
     * Method setAddressLine1Sets the value of field
     * 'addressLine1'.
     * 
     * @param addressLine1 the value of field 'addressLine1'.
     */
    public void setAddressLine1(java.lang.String addressLine1)
    {
        this._addressLine1 = addressLine1;
        
        super.setVoChanged(true);
    } //-- void setAddressLine1(java.lang.String) 

    /**
     * Method setAddressLine2Sets the value of field
     * 'addressLine2'.
     * 
     * @param addressLine2 the value of field 'addressLine2'.
     */
    public void setAddressLine2(java.lang.String addressLine2)
    {
        this._addressLine2 = addressLine2;
        
        super.setVoChanged(true);
    } //-- void setAddressLine2(java.lang.String) 

    /**
     * Method setAddressLine3Sets the value of field
     * 'addressLine3'.
     * 
     * @param addressLine3 the value of field 'addressLine3'.
     */
    public void setAddressLine3(java.lang.String addressLine3)
    {
        this._addressLine3 = addressLine3;
        
        super.setVoChanged(true);
    } //-- void setAddressLine3(java.lang.String) 

    /**
     * Method setAddressLine4Sets the value of field
     * 'addressLine4'.
     * 
     * @param addressLine4 the value of field 'addressLine4'.
     */
    public void setAddressLine4(java.lang.String addressLine4)
    {
        this._addressLine4 = addressLine4;
        
        super.setVoChanged(true);
    } //-- void setAddressLine4(java.lang.String) 

    /**
     * Method setAddressTypeCTSets the value of field
     * 'addressTypeCT'.
     * 
     * @param addressTypeCT the value of field 'addressTypeCT'.
     */
    public void setAddressTypeCT(java.lang.String addressTypeCT)
    {
        this._addressTypeCT = addressTypeCT;
        
        super.setVoChanged(true);
    } //-- void setAddressTypeCT(java.lang.String) 

    /**
     * Method setCitySets the value of field 'city'.
     * 
     * @param city the value of field 'city'.
     */
    public void setCity(java.lang.String city)
    {
        this._city = city;
        
        super.setVoChanged(true);
    } //-- void setCity(java.lang.String) 

    /**
     * Method setClientAddressPKSets the value of field
     * 'clientAddressPK'.
     * 
     * @param clientAddressPK the value of field 'clientAddressPK'.
     */
    public void setClientAddressPK(long clientAddressPK)
    {
        this._clientAddressPK = clientAddressPK;
        
        super.setVoChanged(true);
        this._has_clientAddressPK = true;
    } //-- void setClientAddressPK(long) 

    /**
     * Method setClientDetailFKSets the value of field
     * 'clientDetailFK'.
     * 
     * @param clientDetailFK the value of field 'clientDetailFK'.
     */
    public void setClientDetailFK(long clientDetailFK)
    {
        this._clientDetailFK = clientDetailFK;
        
        super.setVoChanged(true);
        this._has_clientDetailFK = true;
    } //-- void setClientDetailFK(long) 

    /**
     * Method setCountryCTSets the value of field 'countryCT'.
     * 
     * @param countryCT the value of field 'countryCT'.
     */
    public void setCountryCT(java.lang.String countryCT)
    {
        this._countryCT = countryCT;
        
        super.setVoChanged(true);
    } //-- void setCountryCT(java.lang.String) 

    /**
     * Method setCountySets the value of field 'county'.
     * 
     * @param county the value of field 'county'.
     */
    public void setCounty(java.lang.String county)
    {
        this._county = county;
        
        super.setVoChanged(true);
    } //-- void setCounty(java.lang.String) 

    /**
     * Method setEffectiveDateSets the value of field
     * 'effectiveDate'.
     * 
     * @param effectiveDate the value of field 'effectiveDate'.
     */
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } //-- void setEffectiveDate(java.lang.String) 

    /**
     * Method setMaintDateTimeSets the value of field
     * 'maintDateTime'.
     * 
     * @param maintDateTime the value of field 'maintDateTime'.
     */
    public void setMaintDateTime(java.lang.String maintDateTime)
    {
        this._maintDateTime = maintDateTime;
        
        super.setVoChanged(true);
    } //-- void setMaintDateTime(java.lang.String) 

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
     * Method setOverrideStatusSets the value of field
     * 'overrideStatus'.
     * 
     * @param overrideStatus the value of field 'overrideStatus'.
     */
    public void setOverrideStatus(java.lang.String overrideStatus)
    {
        this._overrideStatus = overrideStatus;
        
        super.setVoChanged(true);
    } //-- void setOverrideStatus(java.lang.String) 

    /**
     * Method setSequenceNumberSets the value of field
     * 'sequenceNumber'.
     * 
     * @param sequenceNumber the value of field 'sequenceNumber'.
     */
    public void setSequenceNumber(int sequenceNumber)
    {
        this._sequenceNumber = sequenceNumber;
        
        super.setVoChanged(true);
        this._has_sequenceNumber = true;
    } //-- void setSequenceNumber(int) 

    /**
     * Method setStartDateSets the value of field 'startDate'.
     * 
     * @param startDate the value of field 'startDate'.
     */
    public void setStartDate(java.lang.String startDate)
    {
        this._startDate = startDate;
        
        super.setVoChanged(true);
    } //-- void setStartDate(java.lang.String) 

    /**
     * Method setStateCTSets the value of field 'stateCT'.
     * 
     * @param stateCT the value of field 'stateCT'.
     */
    public void setStateCT(java.lang.String stateCT)
    {
        this._stateCT = stateCT;
        
        super.setVoChanged(true);
    } //-- void setStateCT(java.lang.String) 

    /**
     * Method setStopDateSets the value of field 'stopDate'.
     * 
     * @param stopDate the value of field 'stopDate'.
     */
    public void setStopDate(java.lang.String stopDate)
    {
        this._stopDate = stopDate;
        
        super.setVoChanged(true);
    } //-- void setStopDate(java.lang.String) 

    /**
     * Method setSuspenseVO
     * 
     * @param index
     * @param vSuspenseVO
     */
    public void setSuspenseVO(int index, edit.common.vo.SuspenseVO vSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _suspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSuspenseVO.setParentVO(this.getClass(), this);
        _suspenseVOList.setElementAt(vSuspenseVO, index);
    } //-- void setSuspenseVO(int, edit.common.vo.SuspenseVO) 

    /**
     * Method setSuspenseVO
     * 
     * @param suspenseVOArray
     */
    public void setSuspenseVO(edit.common.vo.SuspenseVO[] suspenseVOArray)
    {
        //-- copy array
        _suspenseVOList.removeAllElements();
        for (int i = 0; i < suspenseVOArray.length; i++) {
            suspenseVOArray[i].setParentVO(this.getClass(), this);
            _suspenseVOList.addElement(suspenseVOArray[i]);
        }
    } //-- void setSuspenseVO(edit.common.vo.SuspenseVO) 

    /**
     * Method setTerminationDateSets the value of field
     * 'terminationDate'.
     * 
     * @param terminationDate the value of field 'terminationDate'.
     */
    public void setTerminationDate(java.lang.String terminationDate)
    {
        this._terminationDate = terminationDate;
        
        super.setVoChanged(true);
    } //-- void setTerminationDate(java.lang.String) 

    /**
     * Method setZipCodeSets the value of field 'zipCode'.
     * 
     * @param zipCode the value of field 'zipCode'.
     */
    public void setZipCode(java.lang.String zipCode)
    {
        this._zipCode = zipCode;
        
        super.setVoChanged(true);
    } //-- void setZipCode(java.lang.String) 

    /**
     * Method setZipCodePlacementCTSets the value of field
     * 'zipCodePlacementCT'.
     * 
     * @param zipCodePlacementCT the value of field
     * 'zipCodePlacementCT'.
     */
    public void setZipCodePlacementCT(java.lang.String zipCodePlacementCT)
    {
        this._zipCodePlacementCT = zipCodePlacementCT;
        
        super.setVoChanged(true);
    } //-- void setZipCodePlacementCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ClientAddressVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ClientAddressVO) Unmarshaller.unmarshal(edit.common.vo.ClientAddressVO.class, reader);
    } //-- edit.common.vo.ClientAddressVO unmarshal(java.io.Reader) 

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

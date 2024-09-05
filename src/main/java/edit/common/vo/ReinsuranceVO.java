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
public class ReinsuranceVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _treatyVOList
     */
    private java.util.Vector _treatyVOList;

    /**
     * Field _treatyGroupNumber
     */
    private java.lang.String _treatyGroupNumber;

    /**
     * Field _reinsurerNumber
     */
    private java.lang.String _reinsurerNumber;


      //----------------/
     //- Constructors -/
    //----------------/

    public ReinsuranceVO() {
        super();
        _treatyVOList = new Vector();
    } //-- edit.common.vo.ReinsuranceVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addTreatyVO
     * 
     * @param vTreatyVO
     */
    public void addTreatyVO(edit.common.vo.TreatyVO vTreatyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTreatyVO.setParentVO(this.getClass(), this);
        _treatyVOList.addElement(vTreatyVO);
    } //-- void addTreatyVO(edit.common.vo.TreatyVO) 

    /**
     * Method addTreatyVO
     * 
     * @param index
     * @param vTreatyVO
     */
    public void addTreatyVO(int index, edit.common.vo.TreatyVO vTreatyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTreatyVO.setParentVO(this.getClass(), this);
        _treatyVOList.insertElementAt(vTreatyVO, index);
    } //-- void addTreatyVO(int, edit.common.vo.TreatyVO) 

    /**
     * Method enumerateTreatyVO
     */
    public java.util.Enumeration enumerateTreatyVO()
    {
        return _treatyVOList.elements();
    } //-- java.util.Enumeration enumerateTreatyVO() 

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
        
        if (obj instanceof ReinsuranceVO) {
        
            ReinsuranceVO temp = (ReinsuranceVO)obj;
            if (this._treatyVOList != null) {
                if (temp._treatyVOList == null) return false;
                else if (!(this._treatyVOList.equals(temp._treatyVOList))) 
                    return false;
            }
            else if (temp._treatyVOList != null)
                return false;
            if (this._treatyGroupNumber != null) {
                if (temp._treatyGroupNumber == null) return false;
                else if (!(this._treatyGroupNumber.equals(temp._treatyGroupNumber))) 
                    return false;
            }
            else if (temp._treatyGroupNumber != null)
                return false;
            if (this._reinsurerNumber != null) {
                if (temp._reinsurerNumber == null) return false;
                else if (!(this._reinsurerNumber.equals(temp._reinsurerNumber))) 
                    return false;
            }
            else if (temp._reinsurerNumber != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getReinsurerNumberReturns the value of field
     * 'reinsurerNumber'.
     * 
     * @return the value of field 'reinsurerNumber'.
     */
    public java.lang.String getReinsurerNumber()
    {
        return this._reinsurerNumber;
    } //-- java.lang.String getReinsurerNumber() 

    /**
     * Method getTreatyGroupNumberReturns the value of field
     * 'treatyGroupNumber'.
     * 
     * @return the value of field 'treatyGroupNumber'.
     */
    public java.lang.String getTreatyGroupNumber()
    {
        return this._treatyGroupNumber;
    } //-- java.lang.String getTreatyGroupNumber() 

    /**
     * Method getTreatyVO
     * 
     * @param index
     */
    public edit.common.vo.TreatyVO getTreatyVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _treatyVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.TreatyVO) _treatyVOList.elementAt(index);
    } //-- edit.common.vo.TreatyVO getTreatyVO(int) 

    /**
     * Method getTreatyVO
     */
    public edit.common.vo.TreatyVO[] getTreatyVO()
    {
        int size = _treatyVOList.size();
        edit.common.vo.TreatyVO[] mArray = new edit.common.vo.TreatyVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.TreatyVO) _treatyVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.TreatyVO[] getTreatyVO() 

    /**
     * Method getTreatyVOCount
     */
    public int getTreatyVOCount()
    {
        return _treatyVOList.size();
    } //-- int getTreatyVOCount() 

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
     * Method removeAllTreatyVO
     */
    public void removeAllTreatyVO()
    {
        _treatyVOList.removeAllElements();
    } //-- void removeAllTreatyVO() 

    /**
     * Method removeTreatyVO
     * 
     * @param index
     */
    public edit.common.vo.TreatyVO removeTreatyVO(int index)
    {
        java.lang.Object obj = _treatyVOList.elementAt(index);
        _treatyVOList.removeElementAt(index);
        return (edit.common.vo.TreatyVO) obj;
    } //-- edit.common.vo.TreatyVO removeTreatyVO(int) 

    /**
     * Method setReinsurerNumberSets the value of field
     * 'reinsurerNumber'.
     * 
     * @param reinsurerNumber the value of field 'reinsurerNumber'.
     */
    public void setReinsurerNumber(java.lang.String reinsurerNumber)
    {
        this._reinsurerNumber = reinsurerNumber;
        
        super.setVoChanged(true);
    } //-- void setReinsurerNumber(java.lang.String) 

    /**
     * Method setTreatyGroupNumberSets the value of field
     * 'treatyGroupNumber'.
     * 
     * @param treatyGroupNumber the value of field
     * 'treatyGroupNumber'.
     */
    public void setTreatyGroupNumber(java.lang.String treatyGroupNumber)
    {
        this._treatyGroupNumber = treatyGroupNumber;
        
        super.setVoChanged(true);
    } //-- void setTreatyGroupNumber(java.lang.String) 

    /**
     * Method setTreatyVO
     * 
     * @param index
     * @param vTreatyVO
     */
    public void setTreatyVO(int index, edit.common.vo.TreatyVO vTreatyVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _treatyVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vTreatyVO.setParentVO(this.getClass(), this);
        _treatyVOList.setElementAt(vTreatyVO, index);
    } //-- void setTreatyVO(int, edit.common.vo.TreatyVO) 

    /**
     * Method setTreatyVO
     * 
     * @param treatyVOArray
     */
    public void setTreatyVO(edit.common.vo.TreatyVO[] treatyVOArray)
    {
        //-- copy array
        _treatyVOList.removeAllElements();
        for (int i = 0; i < treatyVOArray.length; i++) {
            treatyVOArray[i].setParentVO(this.getClass(), this);
            _treatyVOList.addElement(treatyVOArray[i]);
        }
    } //-- void setTreatyVO(edit.common.vo.TreatyVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ReinsuranceVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ReinsuranceVO) Unmarshaller.unmarshal(edit.common.vo.ReinsuranceVO.class, reader);
    } //-- edit.common.vo.ReinsuranceVO unmarshal(java.io.Reader) 

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

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
public class ReinsuranceCheckDocVO extends edit.common.vo.VOObject  
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

    /**
     * Field _segmentVOList
     */
    private java.util.Vector _segmentVOList;

    /**
     * Field _groupSetupVOList
     */
    private java.util.Vector _groupSetupVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ReinsuranceCheckDocVO() {
        super();
        _treatyVOList = new Vector();
        _segmentVOList = new Vector();
        _groupSetupVOList = new Vector();
    } //-- edit.common.vo.ReinsuranceCheckDocVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addGroupSetupVO
     * 
     * @param vGroupSetupVO
     */
    public void addGroupSetupVO(edit.common.vo.GroupSetupVO vGroupSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vGroupSetupVO.setParentVO(this.getClass(), this);
        _groupSetupVOList.addElement(vGroupSetupVO);
    } //-- void addGroupSetupVO(edit.common.vo.GroupSetupVO) 

    /**
     * Method addGroupSetupVO
     * 
     * @param index
     * @param vGroupSetupVO
     */
    public void addGroupSetupVO(int index, edit.common.vo.GroupSetupVO vGroupSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vGroupSetupVO.setParentVO(this.getClass(), this);
        _groupSetupVOList.insertElementAt(vGroupSetupVO, index);
    } //-- void addGroupSetupVO(int, edit.common.vo.GroupSetupVO) 

    /**
     * Method addSegmentVO
     * 
     * @param vSegmentVO
     */
    public void addSegmentVO(edit.common.vo.SegmentVO vSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSegmentVO.setParentVO(this.getClass(), this);
        _segmentVOList.addElement(vSegmentVO);
    } //-- void addSegmentVO(edit.common.vo.SegmentVO) 

    /**
     * Method addSegmentVO
     * 
     * @param index
     * @param vSegmentVO
     */
    public void addSegmentVO(int index, edit.common.vo.SegmentVO vSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSegmentVO.setParentVO(this.getClass(), this);
        _segmentVOList.insertElementAt(vSegmentVO, index);
    } //-- void addSegmentVO(int, edit.common.vo.SegmentVO) 

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
     * Method enumerateGroupSetupVO
     */
    public java.util.Enumeration enumerateGroupSetupVO()
    {
        return _groupSetupVOList.elements();
    } //-- java.util.Enumeration enumerateGroupSetupVO() 

    /**
     * Method enumerateSegmentVO
     */
    public java.util.Enumeration enumerateSegmentVO()
    {
        return _segmentVOList.elements();
    } //-- java.util.Enumeration enumerateSegmentVO() 

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
        
        if (obj instanceof ReinsuranceCheckDocVO) {
        
            ReinsuranceCheckDocVO temp = (ReinsuranceCheckDocVO)obj;
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
            if (this._segmentVOList != null) {
                if (temp._segmentVOList == null) return false;
                else if (!(this._segmentVOList.equals(temp._segmentVOList))) 
                    return false;
            }
            else if (temp._segmentVOList != null)
                return false;
            if (this._groupSetupVOList != null) {
                if (temp._groupSetupVOList == null) return false;
                else if (!(this._groupSetupVOList.equals(temp._groupSetupVOList))) 
                    return false;
            }
            else if (temp._groupSetupVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getGroupSetupVO
     * 
     * @param index
     */
    public edit.common.vo.GroupSetupVO getGroupSetupVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _groupSetupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.GroupSetupVO) _groupSetupVOList.elementAt(index);
    } //-- edit.common.vo.GroupSetupVO getGroupSetupVO(int) 

    /**
     * Method getGroupSetupVO
     */
    public edit.common.vo.GroupSetupVO[] getGroupSetupVO()
    {
        int size = _groupSetupVOList.size();
        edit.common.vo.GroupSetupVO[] mArray = new edit.common.vo.GroupSetupVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.GroupSetupVO) _groupSetupVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.GroupSetupVO[] getGroupSetupVO() 

    /**
     * Method getGroupSetupVOCount
     */
    public int getGroupSetupVOCount()
    {
        return _groupSetupVOList.size();
    } //-- int getGroupSetupVOCount() 

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
     * Method getSegmentVO
     * 
     * @param index
     */
    public edit.common.vo.SegmentVO getSegmentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _segmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SegmentVO) _segmentVOList.elementAt(index);
    } //-- edit.common.vo.SegmentVO getSegmentVO(int) 

    /**
     * Method getSegmentVO
     */
    public edit.common.vo.SegmentVO[] getSegmentVO()
    {
        int size = _segmentVOList.size();
        edit.common.vo.SegmentVO[] mArray = new edit.common.vo.SegmentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SegmentVO) _segmentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SegmentVO[] getSegmentVO() 

    /**
     * Method getSegmentVOCount
     */
    public int getSegmentVOCount()
    {
        return _segmentVOList.size();
    } //-- int getSegmentVOCount() 

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
     * Method removeAllGroupSetupVO
     */
    public void removeAllGroupSetupVO()
    {
        _groupSetupVOList.removeAllElements();
    } //-- void removeAllGroupSetupVO() 

    /**
     * Method removeAllSegmentVO
     */
    public void removeAllSegmentVO()
    {
        _segmentVOList.removeAllElements();
    } //-- void removeAllSegmentVO() 

    /**
     * Method removeAllTreatyVO
     */
    public void removeAllTreatyVO()
    {
        _treatyVOList.removeAllElements();
    } //-- void removeAllTreatyVO() 

    /**
     * Method removeGroupSetupVO
     * 
     * @param index
     */
    public edit.common.vo.GroupSetupVO removeGroupSetupVO(int index)
    {
        java.lang.Object obj = _groupSetupVOList.elementAt(index);
        _groupSetupVOList.removeElementAt(index);
        return (edit.common.vo.GroupSetupVO) obj;
    } //-- edit.common.vo.GroupSetupVO removeGroupSetupVO(int) 

    /**
     * Method removeSegmentVO
     * 
     * @param index
     */
    public edit.common.vo.SegmentVO removeSegmentVO(int index)
    {
        java.lang.Object obj = _segmentVOList.elementAt(index);
        _segmentVOList.removeElementAt(index);
        return (edit.common.vo.SegmentVO) obj;
    } //-- edit.common.vo.SegmentVO removeSegmentVO(int) 

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
     * Method setGroupSetupVO
     * 
     * @param index
     * @param vGroupSetupVO
     */
    public void setGroupSetupVO(int index, edit.common.vo.GroupSetupVO vGroupSetupVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _groupSetupVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vGroupSetupVO.setParentVO(this.getClass(), this);
        _groupSetupVOList.setElementAt(vGroupSetupVO, index);
    } //-- void setGroupSetupVO(int, edit.common.vo.GroupSetupVO) 

    /**
     * Method setGroupSetupVO
     * 
     * @param groupSetupVOArray
     */
    public void setGroupSetupVO(edit.common.vo.GroupSetupVO[] groupSetupVOArray)
    {
        //-- copy array
        _groupSetupVOList.removeAllElements();
        for (int i = 0; i < groupSetupVOArray.length; i++) {
            groupSetupVOArray[i].setParentVO(this.getClass(), this);
            _groupSetupVOList.addElement(groupSetupVOArray[i]);
        }
    } //-- void setGroupSetupVO(edit.common.vo.GroupSetupVO) 

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
     * Method setSegmentVO
     * 
     * @param index
     * @param vSegmentVO
     */
    public void setSegmentVO(int index, edit.common.vo.SegmentVO vSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _segmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSegmentVO.setParentVO(this.getClass(), this);
        _segmentVOList.setElementAt(vSegmentVO, index);
    } //-- void setSegmentVO(int, edit.common.vo.SegmentVO) 

    /**
     * Method setSegmentVO
     * 
     * @param segmentVOArray
     */
    public void setSegmentVO(edit.common.vo.SegmentVO[] segmentVOArray)
    {
        //-- copy array
        _segmentVOList.removeAllElements();
        for (int i = 0; i < segmentVOArray.length; i++) {
            segmentVOArray[i].setParentVO(this.getClass(), this);
            _segmentVOList.addElement(segmentVOArray[i]);
        }
    } //-- void setSegmentVO(edit.common.vo.SegmentVO) 

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
    public static edit.common.vo.ReinsuranceCheckDocVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ReinsuranceCheckDocVO) Unmarshaller.unmarshal(edit.common.vo.ReinsuranceCheckDocVO.class, reader);
    } //-- edit.common.vo.ReinsuranceCheckDocVO unmarshal(java.io.Reader) 

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

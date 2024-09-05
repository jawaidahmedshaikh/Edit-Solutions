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
public class PlacedAgentBranchVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _placedAgentVOList
     */
    private java.util.Vector _placedAgentVOList;

    /**
     * Field _isValidBranch
     */
    private boolean _isValidBranch;

    /**
     * keeps track of state for field: _isValidBranch
     */
    private boolean _has_isValidBranch;


      //----------------/
     //- Constructors -/
    //----------------/

    public PlacedAgentBranchVO() {
        super();
        _placedAgentVOList = new Vector();
    } //-- edit.common.vo.PlacedAgentBranchVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addPlacedAgentVO
     * 
     * @param vPlacedAgentVO
     */
    public void addPlacedAgentVO(edit.common.vo.PlacedAgentVO vPlacedAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPlacedAgentVO.setParentVO(this.getClass(), this);
        _placedAgentVOList.addElement(vPlacedAgentVO);
    } //-- void addPlacedAgentVO(edit.common.vo.PlacedAgentVO) 

    /**
     * Method addPlacedAgentVO
     * 
     * @param index
     * @param vPlacedAgentVO
     */
    public void addPlacedAgentVO(int index, edit.common.vo.PlacedAgentVO vPlacedAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPlacedAgentVO.setParentVO(this.getClass(), this);
        _placedAgentVOList.insertElementAt(vPlacedAgentVO, index);
    } //-- void addPlacedAgentVO(int, edit.common.vo.PlacedAgentVO) 

    /**
     * Method enumeratePlacedAgentVO
     */
    public java.util.Enumeration enumeratePlacedAgentVO()
    {
        return _placedAgentVOList.elements();
    } //-- java.util.Enumeration enumeratePlacedAgentVO() 

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
        
        if (obj instanceof PlacedAgentBranchVO) {
        
            PlacedAgentBranchVO temp = (PlacedAgentBranchVO)obj;
            if (this._placedAgentVOList != null) {
                if (temp._placedAgentVOList == null) return false;
                else if (!(this._placedAgentVOList.equals(temp._placedAgentVOList))) 
                    return false;
            }
            else if (temp._placedAgentVOList != null)
                return false;
            if (this._isValidBranch != temp._isValidBranch)
                return false;
            if (this._has_isValidBranch != temp._has_isValidBranch)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getIsValidBranchReturns the value of field
     * 'isValidBranch'.
     * 
     * @return the value of field 'isValidBranch'.
     */
    public boolean getIsValidBranch()
    {
        return this._isValidBranch;
    } //-- boolean getIsValidBranch() 

    /**
     * Method getPlacedAgentVO
     * 
     * @param index
     */
    public edit.common.vo.PlacedAgentVO getPlacedAgentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _placedAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.PlacedAgentVO) _placedAgentVOList.elementAt(index);
    } //-- edit.common.vo.PlacedAgentVO getPlacedAgentVO(int) 

    /**
     * Method getPlacedAgentVO
     */
    public edit.common.vo.PlacedAgentVO[] getPlacedAgentVO()
    {
        int size = _placedAgentVOList.size();
        edit.common.vo.PlacedAgentVO[] mArray = new edit.common.vo.PlacedAgentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.PlacedAgentVO) _placedAgentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.PlacedAgentVO[] getPlacedAgentVO() 

    /**
     * Method getPlacedAgentVOCount
     */
    public int getPlacedAgentVOCount()
    {
        return _placedAgentVOList.size();
    } //-- int getPlacedAgentVOCount() 

    /**
     * Method hasIsValidBranch
     */
    public boolean hasIsValidBranch()
    {
        return this._has_isValidBranch;
    } //-- boolean hasIsValidBranch() 

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
     * Method removeAllPlacedAgentVO
     */
    public void removeAllPlacedAgentVO()
    {
        _placedAgentVOList.removeAllElements();
    } //-- void removeAllPlacedAgentVO() 

    /**
     * Method removePlacedAgentVO
     * 
     * @param index
     */
    public edit.common.vo.PlacedAgentVO removePlacedAgentVO(int index)
    {
        java.lang.Object obj = _placedAgentVOList.elementAt(index);
        _placedAgentVOList.removeElementAt(index);
        return (edit.common.vo.PlacedAgentVO) obj;
    } //-- edit.common.vo.PlacedAgentVO removePlacedAgentVO(int) 

    /**
     * Method setIsValidBranchSets the value of field
     * 'isValidBranch'.
     * 
     * @param isValidBranch the value of field 'isValidBranch'.
     */
    public void setIsValidBranch(boolean isValidBranch)
    {
        this._isValidBranch = isValidBranch;
        
        super.setVoChanged(true);
        this._has_isValidBranch = true;
    } //-- void setIsValidBranch(boolean) 

    /**
     * Method setPlacedAgentVO
     * 
     * @param index
     * @param vPlacedAgentVO
     */
    public void setPlacedAgentVO(int index, edit.common.vo.PlacedAgentVO vPlacedAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _placedAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vPlacedAgentVO.setParentVO(this.getClass(), this);
        _placedAgentVOList.setElementAt(vPlacedAgentVO, index);
    } //-- void setPlacedAgentVO(int, edit.common.vo.PlacedAgentVO) 

    /**
     * Method setPlacedAgentVO
     * 
     * @param placedAgentVOArray
     */
    public void setPlacedAgentVO(edit.common.vo.PlacedAgentVO[] placedAgentVOArray)
    {
        //-- copy array
        _placedAgentVOList.removeAllElements();
        for (int i = 0; i < placedAgentVOArray.length; i++) {
            placedAgentVOArray[i].setParentVO(this.getClass(), this);
            _placedAgentVOList.addElement(placedAgentVOArray[i]);
        }
    } //-- void setPlacedAgentVO(edit.common.vo.PlacedAgentVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.PlacedAgentBranchVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.PlacedAgentBranchVO) Unmarshaller.unmarshal(edit.common.vo.PlacedAgentBranchVO.class, reader);
    } //-- edit.common.vo.PlacedAgentBranchVO unmarshal(java.io.Reader) 

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

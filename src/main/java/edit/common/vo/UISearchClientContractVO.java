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
public class UISearchClientContractVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _UISearchClientVO
     */
    private edit.common.vo.UISearchClientVO _UISearchClientVO;

    /**
     * Field _UISearchContractVOList
     */
    private java.util.Vector _UISearchContractVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public UISearchClientContractVO() {
        super();
        _UISearchContractVOList = new Vector();
    } //-- edit.common.vo.UISearchClientContractVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addUISearchContractVO
     * 
     * @param vUISearchContractVO
     */
    public void addUISearchContractVO(edit.common.vo.UISearchContractVO vUISearchContractVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vUISearchContractVO.setParentVO(this.getClass(), this);
        _UISearchContractVOList.addElement(vUISearchContractVO);
    } //-- void addUISearchContractVO(edit.common.vo.UISearchContractVO) 

    /**
     * Method addUISearchContractVO
     * 
     * @param index
     * @param vUISearchContractVO
     */
    public void addUISearchContractVO(int index, edit.common.vo.UISearchContractVO vUISearchContractVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vUISearchContractVO.setParentVO(this.getClass(), this);
        _UISearchContractVOList.insertElementAt(vUISearchContractVO, index);
    } //-- void addUISearchContractVO(int, edit.common.vo.UISearchContractVO) 

    /**
     * Method enumerateUISearchContractVO
     */
    public java.util.Enumeration enumerateUISearchContractVO()
    {
        return _UISearchContractVOList.elements();
    } //-- java.util.Enumeration enumerateUISearchContractVO() 

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
        
        if (obj instanceof UISearchClientContractVO) {
        
            UISearchClientContractVO temp = (UISearchClientContractVO)obj;
            if (this._UISearchClientVO != null) {
                if (temp._UISearchClientVO == null) return false;
                else if (!(this._UISearchClientVO.equals(temp._UISearchClientVO))) 
                    return false;
            }
            else if (temp._UISearchClientVO != null)
                return false;
            if (this._UISearchContractVOList != null) {
                if (temp._UISearchContractVOList == null) return false;
                else if (!(this._UISearchContractVOList.equals(temp._UISearchContractVOList))) 
                    return false;
            }
            else if (temp._UISearchContractVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getUISearchClientVOReturns the value of field
     * 'UISearchClientVO'.
     * 
     * @return the value of field 'UISearchClientVO'.
     */
    public edit.common.vo.UISearchClientVO getUISearchClientVO()
    {
        return this._UISearchClientVO;
    } //-- edit.common.vo.UISearchClientVO getUISearchClientVO() 

    /**
     * Method getUISearchContractVO
     * 
     * @param index
     */
    public edit.common.vo.UISearchContractVO getUISearchContractVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _UISearchContractVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.UISearchContractVO) _UISearchContractVOList.elementAt(index);
    } //-- edit.common.vo.UISearchContractVO getUISearchContractVO(int) 

    /**
     * Method getUISearchContractVO
     */
    public edit.common.vo.UISearchContractVO[] getUISearchContractVO()
    {
        int size = _UISearchContractVOList.size();
        edit.common.vo.UISearchContractVO[] mArray = new edit.common.vo.UISearchContractVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.UISearchContractVO) _UISearchContractVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.UISearchContractVO[] getUISearchContractVO() 

    /**
     * Method getUISearchContractVOCount
     */
    public int getUISearchContractVOCount()
    {
        return _UISearchContractVOList.size();
    } //-- int getUISearchContractVOCount() 

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
     * Method removeAllUISearchContractVO
     */
    public void removeAllUISearchContractVO()
    {
        _UISearchContractVOList.removeAllElements();
    } //-- void removeAllUISearchContractVO() 

    /**
     * Method removeUISearchContractVO
     * 
     * @param index
     */
    public edit.common.vo.UISearchContractVO removeUISearchContractVO(int index)
    {
        java.lang.Object obj = _UISearchContractVOList.elementAt(index);
        _UISearchContractVOList.removeElementAt(index);
        return (edit.common.vo.UISearchContractVO) obj;
    } //-- edit.common.vo.UISearchContractVO removeUISearchContractVO(int) 

    /**
     * Method setUISearchClientVOSets the value of field
     * 'UISearchClientVO'.
     * 
     * @param UISearchClientVO the value of field 'UISearchClientVO'
     */
    public void setUISearchClientVO(edit.common.vo.UISearchClientVO UISearchClientVO)
    {
        this._UISearchClientVO = UISearchClientVO;
    } //-- void setUISearchClientVO(edit.common.vo.UISearchClientVO) 

    /**
     * Method setUISearchContractVO
     * 
     * @param index
     * @param vUISearchContractVO
     */
    public void setUISearchContractVO(int index, edit.common.vo.UISearchContractVO vUISearchContractVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _UISearchContractVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vUISearchContractVO.setParentVO(this.getClass(), this);
        _UISearchContractVOList.setElementAt(vUISearchContractVO, index);
    } //-- void setUISearchContractVO(int, edit.common.vo.UISearchContractVO) 

    /**
     * Method setUISearchContractVO
     * 
     * @param UISearchContractVOArray
     */
    public void setUISearchContractVO(edit.common.vo.UISearchContractVO[] UISearchContractVOArray)
    {
        //-- copy array
        _UISearchContractVOList.removeAllElements();
        for (int i = 0; i < UISearchContractVOArray.length; i++) {
            UISearchContractVOArray[i].setParentVO(this.getClass(), this);
            _UISearchContractVOList.addElement(UISearchContractVOArray[i]);
        }
    } //-- void setUISearchContractVO(edit.common.vo.UISearchContractVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.UISearchClientContractVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.UISearchClientContractVO) Unmarshaller.unmarshal(edit.common.vo.UISearchClientContractVO.class, reader);
    } //-- edit.common.vo.UISearchClientContractVO unmarshal(java.io.Reader) 

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

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
public class UISearchClientAgentVO extends edit.common.vo.VOObject  
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
     * Comment describing your root element
     */
    private java.util.Vector _UISearchAgentVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public UISearchClientAgentVO() {
        super();
        _UISearchAgentVOList = new Vector();
    } //-- edit.common.vo.UISearchClientAgentVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addUISearchAgentVO
     * 
     * @param vUISearchAgentVO
     */
    public void addUISearchAgentVO(edit.common.vo.UISearchAgentVO vUISearchAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vUISearchAgentVO.setParentVO(this.getClass(), this);
        _UISearchAgentVOList.addElement(vUISearchAgentVO);
    } //-- void addUISearchAgentVO(edit.common.vo.UISearchAgentVO) 

    /**
     * Method addUISearchAgentVO
     * 
     * @param index
     * @param vUISearchAgentVO
     */
    public void addUISearchAgentVO(int index, edit.common.vo.UISearchAgentVO vUISearchAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vUISearchAgentVO.setParentVO(this.getClass(), this);
        _UISearchAgentVOList.insertElementAt(vUISearchAgentVO, index);
    } //-- void addUISearchAgentVO(int, edit.common.vo.UISearchAgentVO) 

    /**
     * Method enumerateUISearchAgentVO
     */
    public java.util.Enumeration enumerateUISearchAgentVO()
    {
        return _UISearchAgentVOList.elements();
    } //-- java.util.Enumeration enumerateUISearchAgentVO() 

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
        
        if (obj instanceof UISearchClientAgentVO) {
        
            UISearchClientAgentVO temp = (UISearchClientAgentVO)obj;
            if (this._UISearchClientVO != null) {
                if (temp._UISearchClientVO == null) return false;
                else if (!(this._UISearchClientVO.equals(temp._UISearchClientVO))) 
                    return false;
            }
            else if (temp._UISearchClientVO != null)
                return false;
            if (this._UISearchAgentVOList != null) {
                if (temp._UISearchAgentVOList == null) return false;
                else if (!(this._UISearchAgentVOList.equals(temp._UISearchAgentVOList))) 
                    return false;
            }
            else if (temp._UISearchAgentVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getUISearchAgentVO
     * 
     * @param index
     */
    public edit.common.vo.UISearchAgentVO getUISearchAgentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _UISearchAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.UISearchAgentVO) _UISearchAgentVOList.elementAt(index);
    } //-- edit.common.vo.UISearchAgentVO getUISearchAgentVO(int) 

    /**
     * Method getUISearchAgentVO
     */
    public edit.common.vo.UISearchAgentVO[] getUISearchAgentVO()
    {
        int size = _UISearchAgentVOList.size();
        edit.common.vo.UISearchAgentVO[] mArray = new edit.common.vo.UISearchAgentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.UISearchAgentVO) _UISearchAgentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.UISearchAgentVO[] getUISearchAgentVO() 

    /**
     * Method getUISearchAgentVOCount
     */
    public int getUISearchAgentVOCount()
    {
        return _UISearchAgentVOList.size();
    } //-- int getUISearchAgentVOCount() 

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
     * Method removeAllUISearchAgentVO
     */
    public void removeAllUISearchAgentVO()
    {
        _UISearchAgentVOList.removeAllElements();
    } //-- void removeAllUISearchAgentVO() 

    /**
     * Method removeUISearchAgentVO
     * 
     * @param index
     */
    public edit.common.vo.UISearchAgentVO removeUISearchAgentVO(int index)
    {
        java.lang.Object obj = _UISearchAgentVOList.elementAt(index);
        _UISearchAgentVOList.removeElementAt(index);
        return (edit.common.vo.UISearchAgentVO) obj;
    } //-- edit.common.vo.UISearchAgentVO removeUISearchAgentVO(int) 

    /**
     * Method setUISearchAgentVO
     * 
     * @param index
     * @param vUISearchAgentVO
     */
    public void setUISearchAgentVO(int index, edit.common.vo.UISearchAgentVO vUISearchAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _UISearchAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vUISearchAgentVO.setParentVO(this.getClass(), this);
        _UISearchAgentVOList.setElementAt(vUISearchAgentVO, index);
    } //-- void setUISearchAgentVO(int, edit.common.vo.UISearchAgentVO) 

    /**
     * Method setUISearchAgentVO
     * 
     * @param UISearchAgentVOArray
     */
    public void setUISearchAgentVO(edit.common.vo.UISearchAgentVO[] UISearchAgentVOArray)
    {
        //-- copy array
        _UISearchAgentVOList.removeAllElements();
        for (int i = 0; i < UISearchAgentVOArray.length; i++) {
            UISearchAgentVOArray[i].setParentVO(this.getClass(), this);
            _UISearchAgentVOList.addElement(UISearchAgentVOArray[i]);
        }
    } //-- void setUISearchAgentVO(edit.common.vo.UISearchAgentVO) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.UISearchClientAgentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.UISearchClientAgentVO) Unmarshaller.unmarshal(edit.common.vo.UISearchClientAgentVO.class, reader);
    } //-- edit.common.vo.UISearchClientAgentVO unmarshal(java.io.Reader) 

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

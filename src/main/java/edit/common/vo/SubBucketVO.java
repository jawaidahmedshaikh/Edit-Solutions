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
 * Class SubBucketVO.
 * 
 * @version $Revision$ $Date$
 */
public class SubBucketVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _subBucketDetailVOList
     */
    private java.util.Vector _subBucketDetailVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public SubBucketVO() {
        super();
        _subBucketDetailVOList = new Vector();
    } //-- edit.common.vo.SubBucketVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addSubBucketDetailVO
     * 
     * @param vSubBucketDetailVO
     */
    public void addSubBucketDetailVO(edit.common.vo.SubBucketDetailVO vSubBucketDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSubBucketDetailVO.setParentVO(this.getClass(), this);
        _subBucketDetailVOList.addElement(vSubBucketDetailVO);
    } //-- void addSubBucketDetailVO(edit.common.vo.SubBucketDetailVO) 

    /**
     * Method addSubBucketDetailVO
     * 
     * @param index
     * @param vSubBucketDetailVO
     */
    public void addSubBucketDetailVO(int index, edit.common.vo.SubBucketDetailVO vSubBucketDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSubBucketDetailVO.setParentVO(this.getClass(), this);
        _subBucketDetailVOList.insertElementAt(vSubBucketDetailVO, index);
    } //-- void addSubBucketDetailVO(int, edit.common.vo.SubBucketDetailVO) 

    /**
     * Method enumerateSubBucketDetailVO
     */
    public java.util.Enumeration enumerateSubBucketDetailVO()
    {
        return _subBucketDetailVOList.elements();
    } //-- java.util.Enumeration enumerateSubBucketDetailVO() 

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
        
        if (obj instanceof SubBucketVO) {
        
            SubBucketVO temp = (SubBucketVO)obj;
            if (this._subBucketDetailVOList != null) {
                if (temp._subBucketDetailVOList == null) return false;
                else if (!(this._subBucketDetailVOList.equals(temp._subBucketDetailVOList))) 
                    return false;
            }
            else if (temp._subBucketDetailVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getSubBucketDetailVO
     * 
     * @param index
     */
    public edit.common.vo.SubBucketDetailVO getSubBucketDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _subBucketDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SubBucketDetailVO) _subBucketDetailVOList.elementAt(index);
    } //-- edit.common.vo.SubBucketDetailVO getSubBucketDetailVO(int) 

    /**
     * Method getSubBucketDetailVO
     */
    public edit.common.vo.SubBucketDetailVO[] getSubBucketDetailVO()
    {
        int size = _subBucketDetailVOList.size();
        edit.common.vo.SubBucketDetailVO[] mArray = new edit.common.vo.SubBucketDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SubBucketDetailVO) _subBucketDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SubBucketDetailVO[] getSubBucketDetailVO() 

    /**
     * Method getSubBucketDetailVOCount
     */
    public int getSubBucketDetailVOCount()
    {
        return _subBucketDetailVOList.size();
    } //-- int getSubBucketDetailVOCount() 

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
     * Method removeAllSubBucketDetailVO
     */
    public void removeAllSubBucketDetailVO()
    {
        _subBucketDetailVOList.removeAllElements();
    } //-- void removeAllSubBucketDetailVO() 

    /**
     * Method removeSubBucketDetailVO
     * 
     * @param index
     */
    public edit.common.vo.SubBucketDetailVO removeSubBucketDetailVO(int index)
    {
        java.lang.Object obj = _subBucketDetailVOList.elementAt(index);
        _subBucketDetailVOList.removeElementAt(index);
        return (edit.common.vo.SubBucketDetailVO) obj;
    } //-- edit.common.vo.SubBucketDetailVO removeSubBucketDetailVO(int) 

    /**
     * Method setSubBucketDetailVO
     * 
     * @param index
     * @param vSubBucketDetailVO
     */
    public void setSubBucketDetailVO(int index, edit.common.vo.SubBucketDetailVO vSubBucketDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _subBucketDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSubBucketDetailVO.setParentVO(this.getClass(), this);
        _subBucketDetailVOList.setElementAt(vSubBucketDetailVO, index);
    } //-- void setSubBucketDetailVO(int, edit.common.vo.SubBucketDetailVO) 

    /**
     * Method setSubBucketDetailVO
     * 
     * @param subBucketDetailVOArray
     */
    public void setSubBucketDetailVO(edit.common.vo.SubBucketDetailVO[] subBucketDetailVOArray)
    {
        //-- copy array
        _subBucketDetailVOList.removeAllElements();
        for (int i = 0; i < subBucketDetailVOArray.length; i++) {
            subBucketDetailVOArray[i].setParentVO(this.getClass(), this);
            _subBucketDetailVOList.addElement(subBucketDetailVOArray[i]);
        }
    } //-- void setSubBucketDetailVO(edit.common.vo.SubBucketDetailVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SubBucketVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SubBucketVO) Unmarshaller.unmarshal(edit.common.vo.SubBucketVO.class, reader);
    } //-- edit.common.vo.SubBucketVO unmarshal(java.io.Reader) 

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

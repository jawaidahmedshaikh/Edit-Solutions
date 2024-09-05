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
public class CorrespondenceVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Comment describing your root element
     */
    private java.util.Vector _correspondenceDetailVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CorrespondenceVO() {
        super();
        _correspondenceDetailVOList = new Vector();
    } //-- edit.common.vo.CorrespondenceVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addCorrespondenceDetailVO
     * 
     * @param vCorrespondenceDetailVO
     */
    public void addCorrespondenceDetailVO(edit.common.vo.CorrespondenceDetailVO vCorrespondenceDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCorrespondenceDetailVO.setParentVO(this.getClass(), this);
        _correspondenceDetailVOList.addElement(vCorrespondenceDetailVO);
    } //-- void addCorrespondenceDetailVO(edit.common.vo.CorrespondenceDetailVO) 

    /**
     * Method addCorrespondenceDetailVO
     * 
     * @param index
     * @param vCorrespondenceDetailVO
     */
    public void addCorrespondenceDetailVO(int index, edit.common.vo.CorrespondenceDetailVO vCorrespondenceDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCorrespondenceDetailVO.setParentVO(this.getClass(), this);
        _correspondenceDetailVOList.insertElementAt(vCorrespondenceDetailVO, index);
    } //-- void addCorrespondenceDetailVO(int, edit.common.vo.CorrespondenceDetailVO) 

    /**
     * Method enumerateCorrespondenceDetailVO
     */
    public java.util.Enumeration enumerateCorrespondenceDetailVO()
    {
        return _correspondenceDetailVOList.elements();
    } //-- java.util.Enumeration enumerateCorrespondenceDetailVO() 

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
        
        if (obj instanceof CorrespondenceVO) {
        
            CorrespondenceVO temp = (CorrespondenceVO)obj;
            if (this._correspondenceDetailVOList != null) {
                if (temp._correspondenceDetailVOList == null) return false;
                else if (!(this._correspondenceDetailVOList.equals(temp._correspondenceDetailVOList))) 
                    return false;
            }
            else if (temp._correspondenceDetailVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCorrespondenceDetailVO
     * 
     * @param index
     */
    public edit.common.vo.CorrespondenceDetailVO getCorrespondenceDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _correspondenceDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CorrespondenceDetailVO) _correspondenceDetailVOList.elementAt(index);
    } //-- edit.common.vo.CorrespondenceDetailVO getCorrespondenceDetailVO(int) 

    /**
     * Method getCorrespondenceDetailVO
     */
    public edit.common.vo.CorrespondenceDetailVO[] getCorrespondenceDetailVO()
    {
        int size = _correspondenceDetailVOList.size();
        edit.common.vo.CorrespondenceDetailVO[] mArray = new edit.common.vo.CorrespondenceDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CorrespondenceDetailVO) _correspondenceDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CorrespondenceDetailVO[] getCorrespondenceDetailVO() 

    /**
     * Method getCorrespondenceDetailVOCount
     */
    public int getCorrespondenceDetailVOCount()
    {
        return _correspondenceDetailVOList.size();
    } //-- int getCorrespondenceDetailVOCount() 

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
     * Method removeAllCorrespondenceDetailVO
     */
    public void removeAllCorrespondenceDetailVO()
    {
        _correspondenceDetailVOList.removeAllElements();
    } //-- void removeAllCorrespondenceDetailVO() 

    /**
     * Method removeCorrespondenceDetailVO
     * 
     * @param index
     */
    public edit.common.vo.CorrespondenceDetailVO removeCorrespondenceDetailVO(int index)
    {
        java.lang.Object obj = _correspondenceDetailVOList.elementAt(index);
        _correspondenceDetailVOList.removeElementAt(index);
        return (edit.common.vo.CorrespondenceDetailVO) obj;
    } //-- edit.common.vo.CorrespondenceDetailVO removeCorrespondenceDetailVO(int) 

    /**
     * Method setCorrespondenceDetailVO
     * 
     * @param index
     * @param vCorrespondenceDetailVO
     */
    public void setCorrespondenceDetailVO(int index, edit.common.vo.CorrespondenceDetailVO vCorrespondenceDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _correspondenceDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCorrespondenceDetailVO.setParentVO(this.getClass(), this);
        _correspondenceDetailVOList.setElementAt(vCorrespondenceDetailVO, index);
    } //-- void setCorrespondenceDetailVO(int, edit.common.vo.CorrespondenceDetailVO) 

    /**
     * Method setCorrespondenceDetailVO
     * 
     * @param correspondenceDetailVOArray
     */
    public void setCorrespondenceDetailVO(edit.common.vo.CorrespondenceDetailVO[] correspondenceDetailVOArray)
    {
        //-- copy array
        _correspondenceDetailVOList.removeAllElements();
        for (int i = 0; i < correspondenceDetailVOArray.length; i++) {
            correspondenceDetailVOArray[i].setParentVO(this.getClass(), this);
            _correspondenceDetailVOList.addElement(correspondenceDetailVOArray[i]);
        }
    } //-- void setCorrespondenceDetailVO(edit.common.vo.CorrespondenceDetailVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CorrespondenceVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CorrespondenceVO) Unmarshaller.unmarshal(edit.common.vo.CorrespondenceVO.class, reader);
    } //-- edit.common.vo.CorrespondenceVO unmarshal(java.io.Reader) 

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

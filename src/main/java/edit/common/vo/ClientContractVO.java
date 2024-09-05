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
 * Class ClientContractVO.
 * 
 * @version $Revision$ $Date$
 */
public class ClientContractVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _clientDetailVO
     */
    private edit.common.vo.ClientDetailVO _clientDetailVO;

    /**
     * Field _segmentVOList
     */
    private java.util.Vector _segmentVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ClientContractVO() {
        super();
        _segmentVOList = new Vector();
    } //-- edit.common.vo.ClientContractVO()


      //-----------/
     //- Methods -/
    //-----------/

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
     * Method enumerateSegmentVO
     */
    public java.util.Enumeration enumerateSegmentVO()
    {
        return _segmentVOList.elements();
    } //-- java.util.Enumeration enumerateSegmentVO() 

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
        
        if (obj instanceof ClientContractVO) {
        
            ClientContractVO temp = (ClientContractVO)obj;
            if (this._clientDetailVO != null) {
                if (temp._clientDetailVO == null) return false;
                else if (!(this._clientDetailVO.equals(temp._clientDetailVO))) 
                    return false;
            }
            else if (temp._clientDetailVO != null)
                return false;
            if (this._segmentVOList != null) {
                if (temp._segmentVOList == null) return false;
                else if (!(this._segmentVOList.equals(temp._segmentVOList))) 
                    return false;
            }
            else if (temp._segmentVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getClientDetailVOReturns the value of field
     * 'clientDetailVO'.
     * 
     * @return the value of field 'clientDetailVO'.
     */
    public edit.common.vo.ClientDetailVO getClientDetailVO()
    {
        return this._clientDetailVO;
    } //-- edit.common.vo.ClientDetailVO getClientDetailVO() 

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
     * Method removeAllSegmentVO
     */
    public void removeAllSegmentVO()
    {
        _segmentVOList.removeAllElements();
    } //-- void removeAllSegmentVO() 

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
     * Method setClientDetailVOSets the value of field
     * 'clientDetailVO'.
     * 
     * @param clientDetailVO the value of field 'clientDetailVO'.
     */
    public void setClientDetailVO(edit.common.vo.ClientDetailVO clientDetailVO)
    {
        this._clientDetailVO = clientDetailVO;
    } //-- void setClientDetailVO(edit.common.vo.ClientDetailVO) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ClientContractVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ClientContractVO) Unmarshaller.unmarshal(edit.common.vo.ClientContractVO.class, reader);
    } //-- edit.common.vo.ClientContractVO unmarshal(java.io.Reader) 

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

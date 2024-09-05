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
public class ReservesBatchVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _segmentVOList
     */
    private java.util.Vector _segmentVOList;

    /**
     * Field _uniqueBatchId
     */
    private java.lang.String _uniqueBatchId;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Comment describing your root element
     */
    private edit.common.vo.AccumulationVO _accumulationVO;


      //----------------/
     //- Constructors -/
    //----------------/

    public ReservesBatchVO() {
        super();
        _segmentVOList = new Vector();
    } //-- edit.common.vo.ReservesBatchVO()


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
        
        if (obj instanceof ReservesBatchVO) {
        
            ReservesBatchVO temp = (ReservesBatchVO)obj;
            if (this._segmentVOList != null) {
                if (temp._segmentVOList == null) return false;
                else if (!(this._segmentVOList.equals(temp._segmentVOList))) 
                    return false;
            }
            else if (temp._segmentVOList != null)
                return false;
            if (this._uniqueBatchId != null) {
                if (temp._uniqueBatchId == null) return false;
                else if (!(this._uniqueBatchId.equals(temp._uniqueBatchId))) 
                    return false;
            }
            else if (temp._uniqueBatchId != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._accumulationVO != null) {
                if (temp._accumulationVO == null) return false;
                else if (!(this._accumulationVO.equals(temp._accumulationVO))) 
                    return false;
            }
            else if (temp._accumulationVO != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccumulationVOReturns the value of field
     * 'accumulationVO'. The field 'accumulationVO' has the
     * following description: Comment describing your root element
     * 
     * @return the value of field 'accumulationVO'.
     */
    public edit.common.vo.AccumulationVO getAccumulationVO()
    {
        return this._accumulationVO;
    } //-- edit.common.vo.AccumulationVO getAccumulationVO() 

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
     * Method getUniqueBatchIdReturns the value of field
     * 'uniqueBatchId'.
     * 
     * @return the value of field 'uniqueBatchId'.
     */
    public java.lang.String getUniqueBatchId()
    {
        return this._uniqueBatchId;
    } //-- java.lang.String getUniqueBatchId() 

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
     * Method setAccumulationVOSets the value of field
     * 'accumulationVO'. The field 'accumulationVO' has the
     * following description: Comment describing your root element
     * 
     * @param accumulationVO the value of field 'accumulationVO'.
     */
    public void setAccumulationVO(edit.common.vo.AccumulationVO accumulationVO)
    {
        this._accumulationVO = accumulationVO;
    } //-- void setAccumulationVO(edit.common.vo.AccumulationVO) 

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
     * Method setUniqueBatchIdSets the value of field
     * 'uniqueBatchId'.
     * 
     * @param uniqueBatchId the value of field 'uniqueBatchId'.
     */
    public void setUniqueBatchId(java.lang.String uniqueBatchId)
    {
        this._uniqueBatchId = uniqueBatchId;
        
        super.setVoChanged(true);
    } //-- void setUniqueBatchId(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ReservesBatchVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ReservesBatchVO) Unmarshaller.unmarshal(edit.common.vo.ReservesBatchVO.class, reader);
    } //-- edit.common.vo.ReservesBatchVO unmarshal(java.io.Reader) 

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

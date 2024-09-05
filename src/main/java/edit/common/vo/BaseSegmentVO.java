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
public class BaseSegmentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _baseSegmentPK
     */
    private long _baseSegmentPK;

    /**
     * keeps track of state for field: _baseSegmentPK
     */
    private boolean _has_baseSegmentPK;

    /**
     * Field _segmentVO
     */
    private edit.common.vo.SegmentVO _segmentVO;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _clientVOList;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _commissionVOList;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _reinsuranceVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public BaseSegmentVO() {
        super();
        _clientVOList = new Vector();
        _commissionVOList = new Vector();
        _reinsuranceVOList = new Vector();
    } //-- edit.common.vo.BaseSegmentVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addClientVO
     * 
     * @param vClientVO
     */
    public void addClientVO(edit.common.vo.ClientVO vClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientVO.setParentVO(this.getClass(), this);
        _clientVOList.addElement(vClientVO);
    } //-- void addClientVO(edit.common.vo.ClientVO) 

    /**
     * Method addClientVO
     * 
     * @param index
     * @param vClientVO
     */
    public void addClientVO(int index, edit.common.vo.ClientVO vClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientVO.setParentVO(this.getClass(), this);
        _clientVOList.insertElementAt(vClientVO, index);
    } //-- void addClientVO(int, edit.common.vo.ClientVO) 

    /**
     * Method addCommissionVO
     * 
     * @param vCommissionVO
     */
    public void addCommissionVO(edit.common.vo.CommissionVO vCommissionVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionVO.setParentVO(this.getClass(), this);
        _commissionVOList.addElement(vCommissionVO);
    } //-- void addCommissionVO(edit.common.vo.CommissionVO) 

    /**
     * Method addCommissionVO
     * 
     * @param index
     * @param vCommissionVO
     */
    public void addCommissionVO(int index, edit.common.vo.CommissionVO vCommissionVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionVO.setParentVO(this.getClass(), this);
        _commissionVOList.insertElementAt(vCommissionVO, index);
    } //-- void addCommissionVO(int, edit.common.vo.CommissionVO) 

    /**
     * Method addReinsuranceVO
     * 
     * @param vReinsuranceVO
     */
    public void addReinsuranceVO(edit.common.vo.ReinsuranceVO vReinsuranceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vReinsuranceVO.setParentVO(this.getClass(), this);
        _reinsuranceVOList.addElement(vReinsuranceVO);
    } //-- void addReinsuranceVO(edit.common.vo.ReinsuranceVO) 

    /**
     * Method addReinsuranceVO
     * 
     * @param index
     * @param vReinsuranceVO
     */
    public void addReinsuranceVO(int index, edit.common.vo.ReinsuranceVO vReinsuranceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vReinsuranceVO.setParentVO(this.getClass(), this);
        _reinsuranceVOList.insertElementAt(vReinsuranceVO, index);
    } //-- void addReinsuranceVO(int, edit.common.vo.ReinsuranceVO) 

    /**
     * Method enumerateClientVO
     */
    public java.util.Enumeration enumerateClientVO()
    {
        return _clientVOList.elements();
    } //-- java.util.Enumeration enumerateClientVO() 

    /**
     * Method enumerateCommissionVO
     */
    public java.util.Enumeration enumerateCommissionVO()
    {
        return _commissionVOList.elements();
    } //-- java.util.Enumeration enumerateCommissionVO() 

    /**
     * Method enumerateReinsuranceVO
     */
    public java.util.Enumeration enumerateReinsuranceVO()
    {
        return _reinsuranceVOList.elements();
    } //-- java.util.Enumeration enumerateReinsuranceVO() 

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
        
        if (obj instanceof BaseSegmentVO) {
        
            BaseSegmentVO temp = (BaseSegmentVO)obj;
            if (this._baseSegmentPK != temp._baseSegmentPK)
                return false;
            if (this._has_baseSegmentPK != temp._has_baseSegmentPK)
                return false;
            if (this._segmentVO != null) {
                if (temp._segmentVO == null) return false;
                else if (!(this._segmentVO.equals(temp._segmentVO))) 
                    return false;
            }
            else if (temp._segmentVO != null)
                return false;
            if (this._clientVOList != null) {
                if (temp._clientVOList == null) return false;
                else if (!(this._clientVOList.equals(temp._clientVOList))) 
                    return false;
            }
            else if (temp._clientVOList != null)
                return false;
            if (this._commissionVOList != null) {
                if (temp._commissionVOList == null) return false;
                else if (!(this._commissionVOList.equals(temp._commissionVOList))) 
                    return false;
            }
            else if (temp._commissionVOList != null)
                return false;
            if (this._reinsuranceVOList != null) {
                if (temp._reinsuranceVOList == null) return false;
                else if (!(this._reinsuranceVOList.equals(temp._reinsuranceVOList))) 
                    return false;
            }
            else if (temp._reinsuranceVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBaseSegmentPKReturns the value of field
     * 'baseSegmentPK'.
     * 
     * @return the value of field 'baseSegmentPK'.
     */
    public long getBaseSegmentPK()
    {
        return this._baseSegmentPK;
    } //-- long getBaseSegmentPK() 

    /**
     * Method getClientVO
     * 
     * @param index
     */
    public edit.common.vo.ClientVO getClientVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ClientVO) _clientVOList.elementAt(index);
    } //-- edit.common.vo.ClientVO getClientVO(int) 

    /**
     * Method getClientVO
     */
    public edit.common.vo.ClientVO[] getClientVO()
    {
        int size = _clientVOList.size();
        edit.common.vo.ClientVO[] mArray = new edit.common.vo.ClientVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ClientVO) _clientVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ClientVO[] getClientVO() 

    /**
     * Method getClientVOCount
     */
    public int getClientVOCount()
    {
        return _clientVOList.size();
    } //-- int getClientVOCount() 

    /**
     * Method getCommissionVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionVO getCommissionVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CommissionVO) _commissionVOList.elementAt(index);
    } //-- edit.common.vo.CommissionVO getCommissionVO(int) 

    /**
     * Method getCommissionVO
     */
    public edit.common.vo.CommissionVO[] getCommissionVO()
    {
        int size = _commissionVOList.size();
        edit.common.vo.CommissionVO[] mArray = new edit.common.vo.CommissionVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CommissionVO) _commissionVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CommissionVO[] getCommissionVO() 

    /**
     * Method getCommissionVOCount
     */
    public int getCommissionVOCount()
    {
        return _commissionVOList.size();
    } //-- int getCommissionVOCount() 

    /**
     * Method getReinsuranceVO
     * 
     * @param index
     */
    public edit.common.vo.ReinsuranceVO getReinsuranceVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reinsuranceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ReinsuranceVO) _reinsuranceVOList.elementAt(index);
    } //-- edit.common.vo.ReinsuranceVO getReinsuranceVO(int) 

    /**
     * Method getReinsuranceVO
     */
    public edit.common.vo.ReinsuranceVO[] getReinsuranceVO()
    {
        int size = _reinsuranceVOList.size();
        edit.common.vo.ReinsuranceVO[] mArray = new edit.common.vo.ReinsuranceVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ReinsuranceVO) _reinsuranceVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ReinsuranceVO[] getReinsuranceVO() 

    /**
     * Method getReinsuranceVOCount
     */
    public int getReinsuranceVOCount()
    {
        return _reinsuranceVOList.size();
    } //-- int getReinsuranceVOCount() 

    /**
     * Method getSegmentVOReturns the value of field 'segmentVO'.
     * 
     * @return the value of field 'segmentVO'.
     */
    public edit.common.vo.SegmentVO getSegmentVO()
    {
        return this._segmentVO;
    } //-- edit.common.vo.SegmentVO getSegmentVO() 

    /**
     * Method hasBaseSegmentPK
     */
    public boolean hasBaseSegmentPK()
    {
        return this._has_baseSegmentPK;
    } //-- boolean hasBaseSegmentPK() 

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
     * Method removeAllClientVO
     */
    public void removeAllClientVO()
    {
        _clientVOList.removeAllElements();
    } //-- void removeAllClientVO() 

    /**
     * Method removeAllCommissionVO
     */
    public void removeAllCommissionVO()
    {
        _commissionVOList.removeAllElements();
    } //-- void removeAllCommissionVO() 

    /**
     * Method removeAllReinsuranceVO
     */
    public void removeAllReinsuranceVO()
    {
        _reinsuranceVOList.removeAllElements();
    } //-- void removeAllReinsuranceVO() 

    /**
     * Method removeClientVO
     * 
     * @param index
     */
    public edit.common.vo.ClientVO removeClientVO(int index)
    {
        java.lang.Object obj = _clientVOList.elementAt(index);
        _clientVOList.removeElementAt(index);
        return (edit.common.vo.ClientVO) obj;
    } //-- edit.common.vo.ClientVO removeClientVO(int) 

    /**
     * Method removeCommissionVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionVO removeCommissionVO(int index)
    {
        java.lang.Object obj = _commissionVOList.elementAt(index);
        _commissionVOList.removeElementAt(index);
        return (edit.common.vo.CommissionVO) obj;
    } //-- edit.common.vo.CommissionVO removeCommissionVO(int) 

    /**
     * Method removeReinsuranceVO
     * 
     * @param index
     */
    public edit.common.vo.ReinsuranceVO removeReinsuranceVO(int index)
    {
        java.lang.Object obj = _reinsuranceVOList.elementAt(index);
        _reinsuranceVOList.removeElementAt(index);
        return (edit.common.vo.ReinsuranceVO) obj;
    } //-- edit.common.vo.ReinsuranceVO removeReinsuranceVO(int) 

    /**
     * Method setBaseSegmentPKSets the value of field
     * 'baseSegmentPK'.
     * 
     * @param baseSegmentPK the value of field 'baseSegmentPK'.
     */
    public void setBaseSegmentPK(long baseSegmentPK)
    {
        this._baseSegmentPK = baseSegmentPK;
        
        super.setVoChanged(true);
        this._has_baseSegmentPK = true;
    } //-- void setBaseSegmentPK(long) 

    /**
     * Method setClientVO
     * 
     * @param index
     * @param vClientVO
     */
    public void setClientVO(int index, edit.common.vo.ClientVO vClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vClientVO.setParentVO(this.getClass(), this);
        _clientVOList.setElementAt(vClientVO, index);
    } //-- void setClientVO(int, edit.common.vo.ClientVO) 

    /**
     * Method setClientVO
     * 
     * @param clientVOArray
     */
    public void setClientVO(edit.common.vo.ClientVO[] clientVOArray)
    {
        //-- copy array
        _clientVOList.removeAllElements();
        for (int i = 0; i < clientVOArray.length; i++) {
            clientVOArray[i].setParentVO(this.getClass(), this);
            _clientVOList.addElement(clientVOArray[i]);
        }
    } //-- void setClientVO(edit.common.vo.ClientVO) 

    /**
     * Method setCommissionVO
     * 
     * @param index
     * @param vCommissionVO
     */
    public void setCommissionVO(int index, edit.common.vo.CommissionVO vCommissionVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCommissionVO.setParentVO(this.getClass(), this);
        _commissionVOList.setElementAt(vCommissionVO, index);
    } //-- void setCommissionVO(int, edit.common.vo.CommissionVO) 

    /**
     * Method setCommissionVO
     * 
     * @param commissionVOArray
     */
    public void setCommissionVO(edit.common.vo.CommissionVO[] commissionVOArray)
    {
        //-- copy array
        _commissionVOList.removeAllElements();
        for (int i = 0; i < commissionVOArray.length; i++) {
            commissionVOArray[i].setParentVO(this.getClass(), this);
            _commissionVOList.addElement(commissionVOArray[i]);
        }
    } //-- void setCommissionVO(edit.common.vo.CommissionVO) 

    /**
     * Method setReinsuranceVO
     * 
     * @param index
     * @param vReinsuranceVO
     */
    public void setReinsuranceVO(int index, edit.common.vo.ReinsuranceVO vReinsuranceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reinsuranceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vReinsuranceVO.setParentVO(this.getClass(), this);
        _reinsuranceVOList.setElementAt(vReinsuranceVO, index);
    } //-- void setReinsuranceVO(int, edit.common.vo.ReinsuranceVO) 

    /**
     * Method setReinsuranceVO
     * 
     * @param reinsuranceVOArray
     */
    public void setReinsuranceVO(edit.common.vo.ReinsuranceVO[] reinsuranceVOArray)
    {
        //-- copy array
        _reinsuranceVOList.removeAllElements();
        for (int i = 0; i < reinsuranceVOArray.length; i++) {
            reinsuranceVOArray[i].setParentVO(this.getClass(), this);
            _reinsuranceVOList.addElement(reinsuranceVOArray[i]);
        }
    } //-- void setReinsuranceVO(edit.common.vo.ReinsuranceVO) 

    /**
     * Method setSegmentVOSets the value of field 'segmentVO'.
     * 
     * @param segmentVO the value of field 'segmentVO'.
     */
    public void setSegmentVO(edit.common.vo.SegmentVO segmentVO)
    {
        this._segmentVO = segmentVO;
    } //-- void setSegmentVO(edit.common.vo.SegmentVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BaseSegmentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BaseSegmentVO) Unmarshaller.unmarshal(edit.common.vo.BaseSegmentVO.class, reader);
    } //-- edit.common.vo.BaseSegmentVO unmarshal(java.io.Reader) 

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

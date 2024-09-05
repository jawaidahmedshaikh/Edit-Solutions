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
public class TransactionVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _groupSetupVO
     */
    private edit.common.vo.GroupSetupVO _groupSetupVO;

    /**
     * Field _EDITTrxVO
     */
    private edit.common.vo.EDITTrxVO _EDITTrxVO;

    /**
     * Field _segmentVO
     */
    private edit.common.vo.SegmentVO _segmentVO;

    /**
     * Field _clientDetailVO
     */
    private edit.common.vo.ClientDetailVO _clientDetailVO;

    /**
     * Field _numberOfTransfers
     */
    private int _numberOfTransfers;

    /**
     * keeps track of state for field: _numberOfTransfers
     */
    private boolean _has_numberOfTransfers;

    /**
     * Field _depositsVOList
     */
    private java.util.Vector _depositsVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public TransactionVO() {
        super();
        _depositsVOList = new Vector();
    } //-- edit.common.vo.TransactionVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addDepositsVO
     * 
     * @param vDepositsVO
     */
    public void addDepositsVO(edit.common.vo.DepositsVO vDepositsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vDepositsVO.setParentVO(this.getClass(), this);
        _depositsVOList.addElement(vDepositsVO);
    } //-- void addDepositsVO(edit.common.vo.DepositsVO) 

    /**
     * Method addDepositsVO
     * 
     * @param index
     * @param vDepositsVO
     */
    public void addDepositsVO(int index, edit.common.vo.DepositsVO vDepositsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vDepositsVO.setParentVO(this.getClass(), this);
        _depositsVOList.insertElementAt(vDepositsVO, index);
    } //-- void addDepositsVO(int, edit.common.vo.DepositsVO) 

    /**
     * Method deleteNumberOfTransfers
     */
    public void deleteNumberOfTransfers()
    {
        this._has_numberOfTransfers= false;
    } //-- void deleteNumberOfTransfers() 

    /**
     * Method enumerateDepositsVO
     */
    public java.util.Enumeration enumerateDepositsVO()
    {
        return _depositsVOList.elements();
    } //-- java.util.Enumeration enumerateDepositsVO() 

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
        
        if (obj instanceof TransactionVO) {
        
            TransactionVO temp = (TransactionVO)obj;
            if (this._groupSetupVO != null) {
                if (temp._groupSetupVO == null) return false;
                else if (!(this._groupSetupVO.equals(temp._groupSetupVO))) 
                    return false;
            }
            else if (temp._groupSetupVO != null)
                return false;
            if (this._EDITTrxVO != null) {
                if (temp._EDITTrxVO == null) return false;
                else if (!(this._EDITTrxVO.equals(temp._EDITTrxVO))) 
                    return false;
            }
            else if (temp._EDITTrxVO != null)
                return false;
            if (this._segmentVO != null) {
                if (temp._segmentVO == null) return false;
                else if (!(this._segmentVO.equals(temp._segmentVO))) 
                    return false;
            }
            else if (temp._segmentVO != null)
                return false;
            if (this._clientDetailVO != null) {
                if (temp._clientDetailVO == null) return false;
                else if (!(this._clientDetailVO.equals(temp._clientDetailVO))) 
                    return false;
            }
            else if (temp._clientDetailVO != null)
                return false;
            if (this._numberOfTransfers != temp._numberOfTransfers)
                return false;
            if (this._has_numberOfTransfers != temp._has_numberOfTransfers)
                return false;
            if (this._depositsVOList != null) {
                if (temp._depositsVOList == null) return false;
                else if (!(this._depositsVOList.equals(temp._depositsVOList))) 
                    return false;
            }
            else if (temp._depositsVOList != null)
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
     * Method getDepositsVO
     * 
     * @param index
     */
    public edit.common.vo.DepositsVO getDepositsVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _depositsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.DepositsVO) _depositsVOList.elementAt(index);
    } //-- edit.common.vo.DepositsVO getDepositsVO(int) 

    /**
     * Method getDepositsVO
     */
    public edit.common.vo.DepositsVO[] getDepositsVO()
    {
        int size = _depositsVOList.size();
        edit.common.vo.DepositsVO[] mArray = new edit.common.vo.DepositsVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.DepositsVO) _depositsVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.DepositsVO[] getDepositsVO() 

    /**
     * Method getDepositsVOCount
     */
    public int getDepositsVOCount()
    {
        return _depositsVOList.size();
    } //-- int getDepositsVOCount() 

    /**
     * Method getEDITTrxVOReturns the value of field 'EDITTrxVO'.
     * 
     * @return the value of field 'EDITTrxVO'.
     */
    public edit.common.vo.EDITTrxVO getEDITTrxVO()
    {
        return this._EDITTrxVO;
    } //-- edit.common.vo.EDITTrxVO getEDITTrxVO() 

    /**
     * Method getGroupSetupVOReturns the value of field
     * 'groupSetupVO'.
     * 
     * @return the value of field 'groupSetupVO'.
     */
    public edit.common.vo.GroupSetupVO getGroupSetupVO()
    {
        return this._groupSetupVO;
    } //-- edit.common.vo.GroupSetupVO getGroupSetupVO() 

    /**
     * Method getNumberOfTransfersReturns the value of field
     * 'numberOfTransfers'.
     * 
     * @return the value of field 'numberOfTransfers'.
     */
    public int getNumberOfTransfers()
    {
        return this._numberOfTransfers;
    } //-- int getNumberOfTransfers() 

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
     * Method hasNumberOfTransfers
     */
    public boolean hasNumberOfTransfers()
    {
        return this._has_numberOfTransfers;
    } //-- boolean hasNumberOfTransfers() 

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
     * Method removeAllDepositsVO
     */
    public void removeAllDepositsVO()
    {
        _depositsVOList.removeAllElements();
    } //-- void removeAllDepositsVO() 

    /**
     * Method removeDepositsVO
     * 
     * @param index
     */
    public edit.common.vo.DepositsVO removeDepositsVO(int index)
    {
        java.lang.Object obj = _depositsVOList.elementAt(index);
        _depositsVOList.removeElementAt(index);
        return (edit.common.vo.DepositsVO) obj;
    } //-- edit.common.vo.DepositsVO removeDepositsVO(int) 

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
     * Method setDepositsVO
     * 
     * @param index
     * @param vDepositsVO
     */
    public void setDepositsVO(int index, edit.common.vo.DepositsVO vDepositsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _depositsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vDepositsVO.setParentVO(this.getClass(), this);
        _depositsVOList.setElementAt(vDepositsVO, index);
    } //-- void setDepositsVO(int, edit.common.vo.DepositsVO) 

    /**
     * Method setDepositsVO
     * 
     * @param depositsVOArray
     */
    public void setDepositsVO(edit.common.vo.DepositsVO[] depositsVOArray)
    {
        //-- copy array
        _depositsVOList.removeAllElements();
        for (int i = 0; i < depositsVOArray.length; i++) {
            depositsVOArray[i].setParentVO(this.getClass(), this);
            _depositsVOList.addElement(depositsVOArray[i]);
        }
    } //-- void setDepositsVO(edit.common.vo.DepositsVO) 

    /**
     * Method setEDITTrxVOSets the value of field 'EDITTrxVO'.
     * 
     * @param EDITTrxVO the value of field 'EDITTrxVO'.
     */
    public void setEDITTrxVO(edit.common.vo.EDITTrxVO EDITTrxVO)
    {
        this._EDITTrxVO = EDITTrxVO;
    } //-- void setEDITTrxVO(edit.common.vo.EDITTrxVO) 

    /**
     * Method setGroupSetupVOSets the value of field
     * 'groupSetupVO'.
     * 
     * @param groupSetupVO the value of field 'groupSetupVO'.
     */
    public void setGroupSetupVO(edit.common.vo.GroupSetupVO groupSetupVO)
    {
        this._groupSetupVO = groupSetupVO;
    } //-- void setGroupSetupVO(edit.common.vo.GroupSetupVO) 

    /**
     * Method setNumberOfTransfersSets the value of field
     * 'numberOfTransfers'.
     * 
     * @param numberOfTransfers the value of field
     * 'numberOfTransfers'.
     */
    public void setNumberOfTransfers(int numberOfTransfers)
    {
        this._numberOfTransfers = numberOfTransfers;
        
        super.setVoChanged(true);
        this._has_numberOfTransfers = true;
    } //-- void setNumberOfTransfers(int) 

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
    public static edit.common.vo.TransactionVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TransactionVO) Unmarshaller.unmarshal(edit.common.vo.TransactionVO.class, reader);
    } //-- edit.common.vo.TransactionVO unmarshal(java.io.Reader) 

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

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
public class CheckEFTDetailVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _suspenseVOList
     */
    private java.util.Vector _suspenseVOList;

    /**
     * Field _EDITTrxHistoryVOList
     */
    private java.util.Vector _EDITTrxHistoryVOList;

    /**
     * Field _clientDetailVOList
     */
    private java.util.Vector _clientDetailVOList;

    /**
     * Field _segmentVOList
     */
    private java.util.Vector _segmentVOList;

    /**
     * Field _productStructureVOList
     */
    private java.util.Vector _productStructureVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CheckEFTDetailVO() {
        super();
        _suspenseVOList = new Vector();
        _EDITTrxHistoryVOList = new Vector();
        _clientDetailVOList = new Vector();
        _segmentVOList = new Vector();
        _productStructureVOList = new Vector();
    } //-- edit.common.vo.CheckEFTDetailVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addClientDetailVO
     * 
     * @param vClientDetailVO
     */
    public void addClientDetailVO(edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.addElement(vClientDetailVO);
    } //-- void addClientDetailVO(edit.common.vo.ClientDetailVO) 

    /**
     * Method addClientDetailVO
     * 
     * @param index
     * @param vClientDetailVO
     */
    public void addClientDetailVO(int index, edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.insertElementAt(vClientDetailVO, index);
    } //-- void addClientDetailVO(int, edit.common.vo.ClientDetailVO) 

    /**
     * Method addEDITTrxHistoryVO
     * 
     * @param vEDITTrxHistoryVO
     */
    public void addEDITTrxHistoryVO(edit.common.vo.EDITTrxHistoryVO vEDITTrxHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEDITTrxHistoryVO.setParentVO(this.getClass(), this);
        _EDITTrxHistoryVOList.addElement(vEDITTrxHistoryVO);
    } //-- void addEDITTrxHistoryVO(edit.common.vo.EDITTrxHistoryVO) 

    /**
     * Method addEDITTrxHistoryVO
     * 
     * @param index
     * @param vEDITTrxHistoryVO
     */
    public void addEDITTrxHistoryVO(int index, edit.common.vo.EDITTrxHistoryVO vEDITTrxHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEDITTrxHistoryVO.setParentVO(this.getClass(), this);
        _EDITTrxHistoryVOList.insertElementAt(vEDITTrxHistoryVO, index);
    } //-- void addEDITTrxHistoryVO(int, edit.common.vo.EDITTrxHistoryVO) 

    /**
     * Method addProductStructureVO
     * 
     * @param vProductStructureVO
     */
    public void addProductStructureVO(edit.common.vo.ProductStructureVO vProductStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProductStructureVO.setParentVO(this.getClass(), this);
        _productStructureVOList.addElement(vProductStructureVO);
    } //-- void addProductStructureVO(edit.common.vo.ProductStructureVO) 

    /**
     * Method addProductStructureVO
     * 
     * @param index
     * @param vProductStructureVO
     */
    public void addProductStructureVO(int index, edit.common.vo.ProductStructureVO vProductStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProductStructureVO.setParentVO(this.getClass(), this);
        _productStructureVOList.insertElementAt(vProductStructureVO, index);
    } //-- void addProductStructureVO(int, edit.common.vo.ProductStructureVO) 

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
     * Method addSuspenseVO
     * 
     * @param vSuspenseVO
     */
    public void addSuspenseVO(edit.common.vo.SuspenseVO vSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSuspenseVO.setParentVO(this.getClass(), this);
        _suspenseVOList.addElement(vSuspenseVO);
    } //-- void addSuspenseVO(edit.common.vo.SuspenseVO) 

    /**
     * Method addSuspenseVO
     * 
     * @param index
     * @param vSuspenseVO
     */
    public void addSuspenseVO(int index, edit.common.vo.SuspenseVO vSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSuspenseVO.setParentVO(this.getClass(), this);
        _suspenseVOList.insertElementAt(vSuspenseVO, index);
    } //-- void addSuspenseVO(int, edit.common.vo.SuspenseVO) 

    /**
     * Method enumerateClientDetailVO
     */
    public java.util.Enumeration enumerateClientDetailVO()
    {
        return _clientDetailVOList.elements();
    } //-- java.util.Enumeration enumerateClientDetailVO() 

    /**
     * Method enumerateEDITTrxHistoryVO
     */
    public java.util.Enumeration enumerateEDITTrxHistoryVO()
    {
        return _EDITTrxHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateEDITTrxHistoryVO() 

    /**
     * Method enumerateProductStructureVO
     */
    public java.util.Enumeration enumerateProductStructureVO()
    {
        return _productStructureVOList.elements();
    } //-- java.util.Enumeration enumerateProductStructureVO() 

    /**
     * Method enumerateSegmentVO
     */
    public java.util.Enumeration enumerateSegmentVO()
    {
        return _segmentVOList.elements();
    } //-- java.util.Enumeration enumerateSegmentVO() 

    /**
     * Method enumerateSuspenseVO
     */
    public java.util.Enumeration enumerateSuspenseVO()
    {
        return _suspenseVOList.elements();
    } //-- java.util.Enumeration enumerateSuspenseVO() 

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
        
        if (obj instanceof CheckEFTDetailVO) {
        
            CheckEFTDetailVO temp = (CheckEFTDetailVO)obj;
            if (this._suspenseVOList != null) {
                if (temp._suspenseVOList == null) return false;
                else if (!(this._suspenseVOList.equals(temp._suspenseVOList))) 
                    return false;
            }
            else if (temp._suspenseVOList != null)
                return false;
            if (this._EDITTrxHistoryVOList != null) {
                if (temp._EDITTrxHistoryVOList == null) return false;
                else if (!(this._EDITTrxHistoryVOList.equals(temp._EDITTrxHistoryVOList))) 
                    return false;
            }
            else if (temp._EDITTrxHistoryVOList != null)
                return false;
            if (this._clientDetailVOList != null) {
                if (temp._clientDetailVOList == null) return false;
                else if (!(this._clientDetailVOList.equals(temp._clientDetailVOList))) 
                    return false;
            }
            else if (temp._clientDetailVOList != null)
                return false;
            if (this._segmentVOList != null) {
                if (temp._segmentVOList == null) return false;
                else if (!(this._segmentVOList.equals(temp._segmentVOList))) 
                    return false;
            }
            else if (temp._segmentVOList != null)
                return false;
            if (this._productStructureVOList != null) {
                if (temp._productStructureVOList == null) return false;
                else if (!(this._productStructureVOList.equals(temp._productStructureVOList))) 
                    return false;
            }
            else if (temp._productStructureVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getClientDetailVO
     * 
     * @param index
     */
    public edit.common.vo.ClientDetailVO getClientDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ClientDetailVO) _clientDetailVOList.elementAt(index);
    } //-- edit.common.vo.ClientDetailVO getClientDetailVO(int) 

    /**
     * Method getClientDetailVO
     */
    public edit.common.vo.ClientDetailVO[] getClientDetailVO()
    {
        int size = _clientDetailVOList.size();
        edit.common.vo.ClientDetailVO[] mArray = new edit.common.vo.ClientDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ClientDetailVO) _clientDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ClientDetailVO[] getClientDetailVO() 

    /**
     * Method getClientDetailVOCount
     */
    public int getClientDetailVOCount()
    {
        return _clientDetailVOList.size();
    } //-- int getClientDetailVOCount() 

    /**
     * Method getEDITTrxHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.EDITTrxHistoryVO getEDITTrxHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITTrxHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.EDITTrxHistoryVO) _EDITTrxHistoryVOList.elementAt(index);
    } //-- edit.common.vo.EDITTrxHistoryVO getEDITTrxHistoryVO(int) 

    /**
     * Method getEDITTrxHistoryVO
     */
    public edit.common.vo.EDITTrxHistoryVO[] getEDITTrxHistoryVO()
    {
        int size = _EDITTrxHistoryVOList.size();
        edit.common.vo.EDITTrxHistoryVO[] mArray = new edit.common.vo.EDITTrxHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.EDITTrxHistoryVO) _EDITTrxHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.EDITTrxHistoryVO[] getEDITTrxHistoryVO() 

    /**
     * Method getEDITTrxHistoryVOCount
     */
    public int getEDITTrxHistoryVOCount()
    {
        return _EDITTrxHistoryVOList.size();
    } //-- int getEDITTrxHistoryVOCount() 

    /**
     * Method getProductStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ProductStructureVO getProductStructureVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _productStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ProductStructureVO) _productStructureVOList.elementAt(index);
    } //-- edit.common.vo.ProductStructureVO getProductStructureVO(int) 

    /**
     * Method getProductStructureVO
     */
    public edit.common.vo.ProductStructureVO[] getProductStructureVO()
    {
        int size = _productStructureVOList.size();
        edit.common.vo.ProductStructureVO[] mArray = new edit.common.vo.ProductStructureVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ProductStructureVO) _productStructureVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ProductStructureVO[] getProductStructureVO() 

    /**
     * Method getProductStructureVOCount
     */
    public int getProductStructureVOCount()
    {
        return _productStructureVOList.size();
    } //-- int getProductStructureVOCount() 

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
     * Method getSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.SuspenseVO getSuspenseVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _suspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SuspenseVO) _suspenseVOList.elementAt(index);
    } //-- edit.common.vo.SuspenseVO getSuspenseVO(int) 

    /**
     * Method getSuspenseVO
     */
    public edit.common.vo.SuspenseVO[] getSuspenseVO()
    {
        int size = _suspenseVOList.size();
        edit.common.vo.SuspenseVO[] mArray = new edit.common.vo.SuspenseVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SuspenseVO) _suspenseVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SuspenseVO[] getSuspenseVO() 

    /**
     * Method getSuspenseVOCount
     */
    public int getSuspenseVOCount()
    {
        return _suspenseVOList.size();
    } //-- int getSuspenseVOCount() 

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
     * Method removeAllClientDetailVO
     */
    public void removeAllClientDetailVO()
    {
        _clientDetailVOList.removeAllElements();
    } //-- void removeAllClientDetailVO() 

    /**
     * Method removeAllEDITTrxHistoryVO
     */
    public void removeAllEDITTrxHistoryVO()
    {
        _EDITTrxHistoryVOList.removeAllElements();
    } //-- void removeAllEDITTrxHistoryVO() 

    /**
     * Method removeAllProductStructureVO
     */
    public void removeAllProductStructureVO()
    {
        _productStructureVOList.removeAllElements();
    } //-- void removeAllProductStructureVO() 

    /**
     * Method removeAllSegmentVO
     */
    public void removeAllSegmentVO()
    {
        _segmentVOList.removeAllElements();
    } //-- void removeAllSegmentVO() 

    /**
     * Method removeAllSuspenseVO
     */
    public void removeAllSuspenseVO()
    {
        _suspenseVOList.removeAllElements();
    } //-- void removeAllSuspenseVO() 

    /**
     * Method removeClientDetailVO
     * 
     * @param index
     */
    public edit.common.vo.ClientDetailVO removeClientDetailVO(int index)
    {
        java.lang.Object obj = _clientDetailVOList.elementAt(index);
        _clientDetailVOList.removeElementAt(index);
        return (edit.common.vo.ClientDetailVO) obj;
    } //-- edit.common.vo.ClientDetailVO removeClientDetailVO(int) 

    /**
     * Method removeEDITTrxHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.EDITTrxHistoryVO removeEDITTrxHistoryVO(int index)
    {
        java.lang.Object obj = _EDITTrxHistoryVOList.elementAt(index);
        _EDITTrxHistoryVOList.removeElementAt(index);
        return (edit.common.vo.EDITTrxHistoryVO) obj;
    } //-- edit.common.vo.EDITTrxHistoryVO removeEDITTrxHistoryVO(int) 

    /**
     * Method removeProductStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ProductStructureVO removeProductStructureVO(int index)
    {
        java.lang.Object obj = _productStructureVOList.elementAt(index);
        _productStructureVOList.removeElementAt(index);
        return (edit.common.vo.ProductStructureVO) obj;
    } //-- edit.common.vo.ProductStructureVO removeProductStructureVO(int) 

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
     * Method removeSuspenseVO
     * 
     * @param index
     */
    public edit.common.vo.SuspenseVO removeSuspenseVO(int index)
    {
        java.lang.Object obj = _suspenseVOList.elementAt(index);
        _suspenseVOList.removeElementAt(index);
        return (edit.common.vo.SuspenseVO) obj;
    } //-- edit.common.vo.SuspenseVO removeSuspenseVO(int) 

    /**
     * Method setClientDetailVO
     * 
     * @param index
     * @param vClientDetailVO
     */
    public void setClientDetailVO(int index, edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.setElementAt(vClientDetailVO, index);
    } //-- void setClientDetailVO(int, edit.common.vo.ClientDetailVO) 

    /**
     * Method setClientDetailVO
     * 
     * @param clientDetailVOArray
     */
    public void setClientDetailVO(edit.common.vo.ClientDetailVO[] clientDetailVOArray)
    {
        //-- copy array
        _clientDetailVOList.removeAllElements();
        for (int i = 0; i < clientDetailVOArray.length; i++) {
            clientDetailVOArray[i].setParentVO(this.getClass(), this);
            _clientDetailVOList.addElement(clientDetailVOArray[i]);
        }
    } //-- void setClientDetailVO(edit.common.vo.ClientDetailVO) 

    /**
     * Method setEDITTrxHistoryVO
     * 
     * @param index
     * @param vEDITTrxHistoryVO
     */
    public void setEDITTrxHistoryVO(int index, edit.common.vo.EDITTrxHistoryVO vEDITTrxHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _EDITTrxHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vEDITTrxHistoryVO.setParentVO(this.getClass(), this);
        _EDITTrxHistoryVOList.setElementAt(vEDITTrxHistoryVO, index);
    } //-- void setEDITTrxHistoryVO(int, edit.common.vo.EDITTrxHistoryVO) 

    /**
     * Method setEDITTrxHistoryVO
     * 
     * @param EDITTrxHistoryVOArray
     */
    public void setEDITTrxHistoryVO(edit.common.vo.EDITTrxHistoryVO[] EDITTrxHistoryVOArray)
    {
        //-- copy array
        _EDITTrxHistoryVOList.removeAllElements();
        for (int i = 0; i < EDITTrxHistoryVOArray.length; i++) {
            EDITTrxHistoryVOArray[i].setParentVO(this.getClass(), this);
            _EDITTrxHistoryVOList.addElement(EDITTrxHistoryVOArray[i]);
        }
    } //-- void setEDITTrxHistoryVO(edit.common.vo.EDITTrxHistoryVO) 

    /**
     * Method setProductStructureVO
     * 
     * @param index
     * @param vProductStructureVO
     */
    public void setProductStructureVO(int index, edit.common.vo.ProductStructureVO vProductStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _productStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vProductStructureVO.setParentVO(this.getClass(), this);
        _productStructureVOList.setElementAt(vProductStructureVO, index);
    } //-- void setProductStructureVO(int, edit.common.vo.ProductStructureVO) 

    /**
     * Method setProductStructureVO
     * 
     * @param productStructureVOArray
     */
    public void setProductStructureVO(edit.common.vo.ProductStructureVO[] productStructureVOArray)
    {
        //-- copy array
        _productStructureVOList.removeAllElements();
        for (int i = 0; i < productStructureVOArray.length; i++) {
            productStructureVOArray[i].setParentVO(this.getClass(), this);
            _productStructureVOList.addElement(productStructureVOArray[i]);
        }
    } //-- void setProductStructureVO(edit.common.vo.ProductStructureVO) 

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
     * Method setSuspenseVO
     * 
     * @param index
     * @param vSuspenseVO
     */
    public void setSuspenseVO(int index, edit.common.vo.SuspenseVO vSuspenseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _suspenseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSuspenseVO.setParentVO(this.getClass(), this);
        _suspenseVOList.setElementAt(vSuspenseVO, index);
    } //-- void setSuspenseVO(int, edit.common.vo.SuspenseVO) 

    /**
     * Method setSuspenseVO
     * 
     * @param suspenseVOArray
     */
    public void setSuspenseVO(edit.common.vo.SuspenseVO[] suspenseVOArray)
    {
        //-- copy array
        _suspenseVOList.removeAllElements();
        for (int i = 0; i < suspenseVOArray.length; i++) {
            suspenseVOArray[i].setParentVO(this.getClass(), this);
            _suspenseVOList.addElement(suspenseVOArray[i]);
        }
    } //-- void setSuspenseVO(edit.common.vo.SuspenseVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CheckEFTDetailVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CheckEFTDetailVO) Unmarshaller.unmarshal(edit.common.vo.CheckEFTDetailVO.class, reader);
    } //-- edit.common.vo.CheckEFTDetailVO unmarshal(java.io.Reader) 

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

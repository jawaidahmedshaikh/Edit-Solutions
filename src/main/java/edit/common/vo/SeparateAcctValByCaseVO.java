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
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * This VO will be used to create Separate Account Values By Case
 * Reports
 * 
 * @version $Revision$ $Date$
 */
public class SeparateAcctValByCaseVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _separateAcctValDetailByCaseVOList
     */
    private java.util.Vector _separateAcctValDetailByCaseVOList;

    /**
     * Field _advanceTransfers
     */
    private java.math.BigDecimal _advanceTransfers;

    /**
     * Field _reportCompanyName
     */
    private java.lang.String _reportCompanyName;

    /**
     * Field _runDate
     */
    private java.lang.String _runDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public SeparateAcctValByCaseVO() {
        super();
        _separateAcctValDetailByCaseVOList = new Vector();
    } //-- edit.common.vo.SeparateAcctValByCaseVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addSeparateAcctValDetailByCaseVO
     * 
     * @param vSeparateAcctValDetailByCaseVO
     */
    public void addSeparateAcctValDetailByCaseVO(edit.common.vo.SeparateAcctValDetailByCaseVO vSeparateAcctValDetailByCaseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSeparateAcctValDetailByCaseVO.setParentVO(this.getClass(), this);
        _separateAcctValDetailByCaseVOList.addElement(vSeparateAcctValDetailByCaseVO);
    } //-- void addSeparateAcctValDetailByCaseVO(edit.common.vo.SeparateAcctValDetailByCaseVO) 

    /**
     * Method addSeparateAcctValDetailByCaseVO
     * 
     * @param index
     * @param vSeparateAcctValDetailByCaseVO
     */
    public void addSeparateAcctValDetailByCaseVO(int index, edit.common.vo.SeparateAcctValDetailByCaseVO vSeparateAcctValDetailByCaseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSeparateAcctValDetailByCaseVO.setParentVO(this.getClass(), this);
        _separateAcctValDetailByCaseVOList.insertElementAt(vSeparateAcctValDetailByCaseVO, index);
    } //-- void addSeparateAcctValDetailByCaseVO(int, edit.common.vo.SeparateAcctValDetailByCaseVO) 

    /**
     * Method enumerateSeparateAcctValDetailByCaseVO
     */
    public java.util.Enumeration enumerateSeparateAcctValDetailByCaseVO()
    {
        return _separateAcctValDetailByCaseVOList.elements();
    } //-- java.util.Enumeration enumerateSeparateAcctValDetailByCaseVO() 

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
        
        if (obj instanceof SeparateAcctValByCaseVO) {
        
            SeparateAcctValByCaseVO temp = (SeparateAcctValByCaseVO)obj;
            if (this._separateAcctValDetailByCaseVOList != null) {
                if (temp._separateAcctValDetailByCaseVOList == null) return false;
                else if (!(this._separateAcctValDetailByCaseVOList.equals(temp._separateAcctValDetailByCaseVOList))) 
                    return false;
            }
            else if (temp._separateAcctValDetailByCaseVOList != null)
                return false;
            if (this._advanceTransfers != null) {
                if (temp._advanceTransfers == null) return false;
                else if (!(this._advanceTransfers.equals(temp._advanceTransfers))) 
                    return false;
            }
            else if (temp._advanceTransfers != null)
                return false;
            if (this._reportCompanyName != null) {
                if (temp._reportCompanyName == null) return false;
                else if (!(this._reportCompanyName.equals(temp._reportCompanyName))) 
                    return false;
            }
            else if (temp._reportCompanyName != null)
                return false;
            if (this._runDate != null) {
                if (temp._runDate == null) return false;
                else if (!(this._runDate.equals(temp._runDate))) 
                    return false;
            }
            else if (temp._runDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAdvanceTransfersReturns the value of field
     * 'advanceTransfers'.
     * 
     * @return the value of field 'advanceTransfers'.
     */
    public java.math.BigDecimal getAdvanceTransfers()
    {
        return this._advanceTransfers;
    } //-- java.math.BigDecimal getAdvanceTransfers() 

    /**
     * Method getReportCompanyNameReturns the value of field
     * 'reportCompanyName'.
     * 
     * @return the value of field 'reportCompanyName'.
     */
    public java.lang.String getReportCompanyName()
    {
        return this._reportCompanyName;
    } //-- java.lang.String getReportCompanyName() 

    /**
     * Method getRunDateReturns the value of field 'runDate'.
     * 
     * @return the value of field 'runDate'.
     */
    public java.lang.String getRunDate()
    {
        return this._runDate;
    } //-- java.lang.String getRunDate() 

    /**
     * Method getSeparateAcctValDetailByCaseVO
     * 
     * @param index
     */
    public edit.common.vo.SeparateAcctValDetailByCaseVO getSeparateAcctValDetailByCaseVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _separateAcctValDetailByCaseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SeparateAcctValDetailByCaseVO) _separateAcctValDetailByCaseVOList.elementAt(index);
    } //-- edit.common.vo.SeparateAcctValDetailByCaseVO getSeparateAcctValDetailByCaseVO(int) 

    /**
     * Method getSeparateAcctValDetailByCaseVO
     */
    public edit.common.vo.SeparateAcctValDetailByCaseVO[] getSeparateAcctValDetailByCaseVO()
    {
        int size = _separateAcctValDetailByCaseVOList.size();
        edit.common.vo.SeparateAcctValDetailByCaseVO[] mArray = new edit.common.vo.SeparateAcctValDetailByCaseVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SeparateAcctValDetailByCaseVO) _separateAcctValDetailByCaseVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SeparateAcctValDetailByCaseVO[] getSeparateAcctValDetailByCaseVO() 

    /**
     * Method getSeparateAcctValDetailByCaseVOCount
     */
    public int getSeparateAcctValDetailByCaseVOCount()
    {
        return _separateAcctValDetailByCaseVOList.size();
    } //-- int getSeparateAcctValDetailByCaseVOCount() 

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
     * Method removeAllSeparateAcctValDetailByCaseVO
     */
    public void removeAllSeparateAcctValDetailByCaseVO()
    {
        _separateAcctValDetailByCaseVOList.removeAllElements();
    } //-- void removeAllSeparateAcctValDetailByCaseVO() 

    /**
     * Method removeSeparateAcctValDetailByCaseVO
     * 
     * @param index
     */
    public edit.common.vo.SeparateAcctValDetailByCaseVO removeSeparateAcctValDetailByCaseVO(int index)
    {
        java.lang.Object obj = _separateAcctValDetailByCaseVOList.elementAt(index);
        _separateAcctValDetailByCaseVOList.removeElementAt(index);
        return (edit.common.vo.SeparateAcctValDetailByCaseVO) obj;
    } //-- edit.common.vo.SeparateAcctValDetailByCaseVO removeSeparateAcctValDetailByCaseVO(int) 

    /**
     * Method setAdvanceTransfersSets the value of field
     * 'advanceTransfers'.
     * 
     * @param advanceTransfers the value of field 'advanceTransfers'
     */
    public void setAdvanceTransfers(java.math.BigDecimal advanceTransfers)
    {
        this._advanceTransfers = advanceTransfers;
        
        super.setVoChanged(true);
    } //-- void setAdvanceTransfers(java.math.BigDecimal) 

    /**
     * Method setReportCompanyNameSets the value of field
     * 'reportCompanyName'.
     * 
     * @param reportCompanyName the value of field
     * 'reportCompanyName'.
     */
    public void setReportCompanyName(java.lang.String reportCompanyName)
    {
        this._reportCompanyName = reportCompanyName;
        
        super.setVoChanged(true);
    } //-- void setReportCompanyName(java.lang.String) 

    /**
     * Method setRunDateSets the value of field 'runDate'.
     * 
     * @param runDate the value of field 'runDate'.
     */
    public void setRunDate(java.lang.String runDate)
    {
        this._runDate = runDate;
        
        super.setVoChanged(true);
    } //-- void setRunDate(java.lang.String) 

    /**
     * Method setSeparateAcctValDetailByCaseVO
     * 
     * @param index
     * @param vSeparateAcctValDetailByCaseVO
     */
    public void setSeparateAcctValDetailByCaseVO(int index, edit.common.vo.SeparateAcctValDetailByCaseVO vSeparateAcctValDetailByCaseVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _separateAcctValDetailByCaseVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSeparateAcctValDetailByCaseVO.setParentVO(this.getClass(), this);
        _separateAcctValDetailByCaseVOList.setElementAt(vSeparateAcctValDetailByCaseVO, index);
    } //-- void setSeparateAcctValDetailByCaseVO(int, edit.common.vo.SeparateAcctValDetailByCaseVO) 

    /**
     * Method setSeparateAcctValDetailByCaseVO
     * 
     * @param separateAcctValDetailByCaseVOArray
     */
    public void setSeparateAcctValDetailByCaseVO(edit.common.vo.SeparateAcctValDetailByCaseVO[] separateAcctValDetailByCaseVOArray)
    {
        //-- copy array
        _separateAcctValDetailByCaseVOList.removeAllElements();
        for (int i = 0; i < separateAcctValDetailByCaseVOArray.length; i++) {
            separateAcctValDetailByCaseVOArray[i].setParentVO(this.getClass(), this);
            _separateAcctValDetailByCaseVOList.addElement(separateAcctValDetailByCaseVOArray[i]);
        }
    } //-- void setSeparateAcctValDetailByCaseVO(edit.common.vo.SeparateAcctValDetailByCaseVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SeparateAcctValByCaseVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SeparateAcctValByCaseVO) Unmarshaller.unmarshal(edit.common.vo.SeparateAcctValByCaseVO.class, reader);
    } //-- edit.common.vo.SeparateAcctValByCaseVO unmarshal(java.io.Reader) 

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

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
 * Class SeparateAcctValDetailByCaseVO.
 * 
 * @version $Revision$ $Date$
 */
public class SeparateAcctValDetailByCaseVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contractNumber
     */
    private java.lang.String _contractNumber;

    /**
     * Field _clientDetailVOList
     */
    private java.util.Vector _clientDetailVOList;

    /**
     * Field _investmentInformationVOList
     */
    private java.util.Vector _investmentInformationVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public SeparateAcctValDetailByCaseVO() {
        super();
        _clientDetailVOList = new Vector();
        _investmentInformationVOList = new Vector();
    } //-- edit.common.vo.SeparateAcctValDetailByCaseVO()


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
     * Method addInvestmentInformationVO
     * 
     * @param vInvestmentInformationVO
     */
    public void addInvestmentInformationVO(edit.common.vo.InvestmentInformationVO vInvestmentInformationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentInformationVO.setParentVO(this.getClass(), this);
        _investmentInformationVOList.addElement(vInvestmentInformationVO);
    } //-- void addInvestmentInformationVO(edit.common.vo.InvestmentInformationVO) 

    /**
     * Method addInvestmentInformationVO
     * 
     * @param index
     * @param vInvestmentInformationVO
     */
    public void addInvestmentInformationVO(int index, edit.common.vo.InvestmentInformationVO vInvestmentInformationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentInformationVO.setParentVO(this.getClass(), this);
        _investmentInformationVOList.insertElementAt(vInvestmentInformationVO, index);
    } //-- void addInvestmentInformationVO(int, edit.common.vo.InvestmentInformationVO) 

    /**
     * Method enumerateClientDetailVO
     */
    public java.util.Enumeration enumerateClientDetailVO()
    {
        return _clientDetailVOList.elements();
    } //-- java.util.Enumeration enumerateClientDetailVO() 

    /**
     * Method enumerateInvestmentInformationVO
     */
    public java.util.Enumeration enumerateInvestmentInformationVO()
    {
        return _investmentInformationVOList.elements();
    } //-- java.util.Enumeration enumerateInvestmentInformationVO() 

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
        
        if (obj instanceof SeparateAcctValDetailByCaseVO) {
        
            SeparateAcctValDetailByCaseVO temp = (SeparateAcctValDetailByCaseVO)obj;
            if (this._contractNumber != null) {
                if (temp._contractNumber == null) return false;
                else if (!(this._contractNumber.equals(temp._contractNumber))) 
                    return false;
            }
            else if (temp._contractNumber != null)
                return false;
            if (this._clientDetailVOList != null) {
                if (temp._clientDetailVOList == null) return false;
                else if (!(this._clientDetailVOList.equals(temp._clientDetailVOList))) 
                    return false;
            }
            else if (temp._clientDetailVOList != null)
                return false;
            if (this._investmentInformationVOList != null) {
                if (temp._investmentInformationVOList == null) return false;
                else if (!(this._investmentInformationVOList.equals(temp._investmentInformationVOList))) 
                    return false;
            }
            else if (temp._investmentInformationVOList != null)
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
     * Method getContractNumberReturns the value of field
     * 'contractNumber'.
     * 
     * @return the value of field 'contractNumber'.
     */
    public java.lang.String getContractNumber()
    {
        return this._contractNumber;
    } //-- java.lang.String getContractNumber() 

    /**
     * Method getInvestmentInformationVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentInformationVO getInvestmentInformationVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentInformationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InvestmentInformationVO) _investmentInformationVOList.elementAt(index);
    } //-- edit.common.vo.InvestmentInformationVO getInvestmentInformationVO(int) 

    /**
     * Method getInvestmentInformationVO
     */
    public edit.common.vo.InvestmentInformationVO[] getInvestmentInformationVO()
    {
        int size = _investmentInformationVOList.size();
        edit.common.vo.InvestmentInformationVO[] mArray = new edit.common.vo.InvestmentInformationVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InvestmentInformationVO) _investmentInformationVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InvestmentInformationVO[] getInvestmentInformationVO() 

    /**
     * Method getInvestmentInformationVOCount
     */
    public int getInvestmentInformationVOCount()
    {
        return _investmentInformationVOList.size();
    } //-- int getInvestmentInformationVOCount() 

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
     * Method removeAllInvestmentInformationVO
     */
    public void removeAllInvestmentInformationVO()
    {
        _investmentInformationVOList.removeAllElements();
    } //-- void removeAllInvestmentInformationVO() 

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
     * Method removeInvestmentInformationVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentInformationVO removeInvestmentInformationVO(int index)
    {
        java.lang.Object obj = _investmentInformationVOList.elementAt(index);
        _investmentInformationVOList.removeElementAt(index);
        return (edit.common.vo.InvestmentInformationVO) obj;
    } //-- edit.common.vo.InvestmentInformationVO removeInvestmentInformationVO(int) 

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
     * Method setContractNumberSets the value of field
     * 'contractNumber'.
     * 
     * @param contractNumber the value of field 'contractNumber'.
     */
    public void setContractNumber(java.lang.String contractNumber)
    {
        this._contractNumber = contractNumber;
        
        super.setVoChanged(true);
    } //-- void setContractNumber(java.lang.String) 

    /**
     * Method setInvestmentInformationVO
     * 
     * @param index
     * @param vInvestmentInformationVO
     */
    public void setInvestmentInformationVO(int index, edit.common.vo.InvestmentInformationVO vInvestmentInformationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentInformationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInvestmentInformationVO.setParentVO(this.getClass(), this);
        _investmentInformationVOList.setElementAt(vInvestmentInformationVO, index);
    } //-- void setInvestmentInformationVO(int, edit.common.vo.InvestmentInformationVO) 

    /**
     * Method setInvestmentInformationVO
     * 
     * @param investmentInformationVOArray
     */
    public void setInvestmentInformationVO(edit.common.vo.InvestmentInformationVO[] investmentInformationVOArray)
    {
        //-- copy array
        _investmentInformationVOList.removeAllElements();
        for (int i = 0; i < investmentInformationVOArray.length; i++) {
            investmentInformationVOArray[i].setParentVO(this.getClass(), this);
            _investmentInformationVOList.addElement(investmentInformationVOArray[i]);
        }
    } //-- void setInvestmentInformationVO(edit.common.vo.InvestmentInformationVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SeparateAcctValDetailByCaseVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SeparateAcctValDetailByCaseVO) Unmarshaller.unmarshal(edit.common.vo.SeparateAcctValDetailByCaseVO.class, reader);
    } //-- edit.common.vo.SeparateAcctValDetailByCaseVO unmarshal(java.io.Reader) 

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

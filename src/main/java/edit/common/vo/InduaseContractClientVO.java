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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class InduaseContractClientVO.
 * 
 * @version $Revision$ $Date$
 */
public class InduaseContractClientVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _induaseContractClientPK
     */
    private long _induaseContractClientPK;

    /**
     * keeps track of state for field: _induaseContractClientPK
     */
    private boolean _has_induaseContractClientPK;

    /**
     * Field _contractClientVO
     */
    private edit.common.vo.ContractClientVO _contractClientVO;


      //----------------/
     //- Constructors -/
    //----------------/

    public InduaseContractClientVO() {
        super();
    } //-- edit.common.vo.InduaseContractClientVO()


      //-----------/
     //- Methods -/
    //-----------/

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
        
        if (obj instanceof InduaseContractClientVO) {
        
            InduaseContractClientVO temp = (InduaseContractClientVO)obj;
            if (this._induaseContractClientPK != temp._induaseContractClientPK)
                return false;
            if (this._has_induaseContractClientPK != temp._has_induaseContractClientPK)
                return false;
            if (this._contractClientVO != null) {
                if (temp._contractClientVO == null) return false;
                else if (!(this._contractClientVO.equals(temp._contractClientVO))) 
                    return false;
            }
            else if (temp._contractClientVO != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getContractClientVOReturns the value of field
     * 'contractClientVO'.
     * 
     * @return the value of field 'contractClientVO'.
     */
    public edit.common.vo.ContractClientVO getContractClientVO()
    {
        return this._contractClientVO;
    } //-- edit.common.vo.ContractClientVO getContractClientVO() 

    /**
     * Method getInduaseContractClientPKReturns the value of field
     * 'induaseContractClientPK'.
     * 
     * @return the value of field 'induaseContractClientPK'.
     */
    public long getInduaseContractClientPK()
    {
        return this._induaseContractClientPK;
    } //-- long getInduaseContractClientPK() 

    /**
     * Method hasInduaseContractClientPK
     */
    public boolean hasInduaseContractClientPK()
    {
        return this._has_induaseContractClientPK;
    } //-- boolean hasInduaseContractClientPK() 

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
     * Method setContractClientVOSets the value of field
     * 'contractClientVO'.
     * 
     * @param contractClientVO the value of field 'contractClientVO'
     */
    public void setContractClientVO(edit.common.vo.ContractClientVO contractClientVO)
    {
        this._contractClientVO = contractClientVO;
    } //-- void setContractClientVO(edit.common.vo.ContractClientVO) 

    /**
     * Method setInduaseContractClientPKSets the value of field
     * 'induaseContractClientPK'.
     * 
     * @param induaseContractClientPK the value of field
     * 'induaseContractClientPK'.
     */
    public void setInduaseContractClientPK(long induaseContractClientPK)
    {
        this._induaseContractClientPK = induaseContractClientPK;
        
        super.setVoChanged(true);
        this._has_induaseContractClientPK = true;
    } //-- void setInduaseContractClientPK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.InduaseContractClientVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.InduaseContractClientVO) Unmarshaller.unmarshal(edit.common.vo.InduaseContractClientVO.class, reader);
    } //-- edit.common.vo.InduaseContractClientVO unmarshal(java.io.Reader) 

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

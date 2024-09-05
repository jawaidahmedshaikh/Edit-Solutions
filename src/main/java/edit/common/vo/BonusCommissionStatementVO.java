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
 * Class BonusCommissionStatementVO.
 * 
 * @version $Revision$ $Date$
 */
public class BonusCommissionStatementVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _bonusStatementVOList
     */
    private java.util.Vector _bonusStatementVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public BonusCommissionStatementVO() {
        super();
        _bonusStatementVOList = new Vector();
    } //-- edit.common.vo.BonusCommissionStatementVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addBonusStatementVO
     * 
     * @param vBonusStatementVO
     */
    public void addBonusStatementVO(edit.common.vo.BonusStatementVO vBonusStatementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusStatementVO.setParentVO(this.getClass(), this);
        _bonusStatementVOList.addElement(vBonusStatementVO);
    } //-- void addBonusStatementVO(edit.common.vo.BonusStatementVO) 

    /**
     * Method addBonusStatementVO
     * 
     * @param index
     * @param vBonusStatementVO
     */
    public void addBonusStatementVO(int index, edit.common.vo.BonusStatementVO vBonusStatementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusStatementVO.setParentVO(this.getClass(), this);
        _bonusStatementVOList.insertElementAt(vBonusStatementVO, index);
    } //-- void addBonusStatementVO(int, edit.common.vo.BonusStatementVO) 

    /**
     * Method enumerateBonusStatementVO
     */
    public java.util.Enumeration enumerateBonusStatementVO()
    {
        return _bonusStatementVOList.elements();
    } //-- java.util.Enumeration enumerateBonusStatementVO() 

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
        
        if (obj instanceof BonusCommissionStatementVO) {
        
            BonusCommissionStatementVO temp = (BonusCommissionStatementVO)obj;
            if (this._bonusStatementVOList != null) {
                if (temp._bonusStatementVOList == null) return false;
                else if (!(this._bonusStatementVOList.equals(temp._bonusStatementVOList))) 
                    return false;
            }
            else if (temp._bonusStatementVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBonusStatementVO
     * 
     * @param index
     */
    public edit.common.vo.BonusStatementVO getBonusStatementVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusStatementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BonusStatementVO) _bonusStatementVOList.elementAt(index);
    } //-- edit.common.vo.BonusStatementVO getBonusStatementVO(int) 

    /**
     * Method getBonusStatementVO
     */
    public edit.common.vo.BonusStatementVO[] getBonusStatementVO()
    {
        int size = _bonusStatementVOList.size();
        edit.common.vo.BonusStatementVO[] mArray = new edit.common.vo.BonusStatementVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BonusStatementVO) _bonusStatementVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BonusStatementVO[] getBonusStatementVO() 

    /**
     * Method getBonusStatementVOCount
     */
    public int getBonusStatementVOCount()
    {
        return _bonusStatementVOList.size();
    } //-- int getBonusStatementVOCount() 

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
     * Method removeAllBonusStatementVO
     */
    public void removeAllBonusStatementVO()
    {
        _bonusStatementVOList.removeAllElements();
    } //-- void removeAllBonusStatementVO() 

    /**
     * Method removeBonusStatementVO
     * 
     * @param index
     */
    public edit.common.vo.BonusStatementVO removeBonusStatementVO(int index)
    {
        java.lang.Object obj = _bonusStatementVOList.elementAt(index);
        _bonusStatementVOList.removeElementAt(index);
        return (edit.common.vo.BonusStatementVO) obj;
    } //-- edit.common.vo.BonusStatementVO removeBonusStatementVO(int) 

    /**
     * Method setBonusStatementVO
     * 
     * @param index
     * @param vBonusStatementVO
     */
    public void setBonusStatementVO(int index, edit.common.vo.BonusStatementVO vBonusStatementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusStatementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBonusStatementVO.setParentVO(this.getClass(), this);
        _bonusStatementVOList.setElementAt(vBonusStatementVO, index);
    } //-- void setBonusStatementVO(int, edit.common.vo.BonusStatementVO) 

    /**
     * Method setBonusStatementVO
     * 
     * @param bonusStatementVOArray
     */
    public void setBonusStatementVO(edit.common.vo.BonusStatementVO[] bonusStatementVOArray)
    {
        //-- copy array
        _bonusStatementVOList.removeAllElements();
        for (int i = 0; i < bonusStatementVOArray.length; i++) {
            bonusStatementVOArray[i].setParentVO(this.getClass(), this);
            _bonusStatementVOList.addElement(bonusStatementVOArray[i]);
        }
    } //-- void setBonusStatementVO(edit.common.vo.BonusStatementVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BonusCommissionStatementVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BonusCommissionStatementVO) Unmarshaller.unmarshal(edit.common.vo.BonusCommissionStatementVO.class, reader);
    } //-- edit.common.vo.BonusCommissionStatementVO unmarshal(java.io.Reader) 

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

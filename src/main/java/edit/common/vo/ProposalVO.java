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
 * Schema to be used for reprojection
 * 
 * @version $Revision$ $Date$
 */
public class ProposalVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _naturalDocVO
     */
    private edit.common.vo.NaturalDocVO _naturalDocVO;

    /**
     * Field _proposalDate
     */
    private java.lang.String _proposalDate;

    /**
     * Field _proposalProjectionVOList
     */
    private java.util.Vector _proposalProjectionVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ProposalVO() {
        super();
        _proposalProjectionVOList = new Vector();
    } //-- edit.common.vo.ProposalVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addProposalProjectionVO
     * 
     * @param vProposalProjectionVO
     */
    public void addProposalProjectionVO(edit.common.vo.ProposalProjectionVO vProposalProjectionVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProposalProjectionVO.setParentVO(this.getClass(), this);
        _proposalProjectionVOList.addElement(vProposalProjectionVO);
    } //-- void addProposalProjectionVO(edit.common.vo.ProposalProjectionVO) 

    /**
     * Method addProposalProjectionVO
     * 
     * @param index
     * @param vProposalProjectionVO
     */
    public void addProposalProjectionVO(int index, edit.common.vo.ProposalProjectionVO vProposalProjectionVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProposalProjectionVO.setParentVO(this.getClass(), this);
        _proposalProjectionVOList.insertElementAt(vProposalProjectionVO, index);
    } //-- void addProposalProjectionVO(int, edit.common.vo.ProposalProjectionVO) 

    /**
     * Method enumerateProposalProjectionVO
     */
    public java.util.Enumeration enumerateProposalProjectionVO()
    {
        return _proposalProjectionVOList.elements();
    } //-- java.util.Enumeration enumerateProposalProjectionVO() 

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
        
        if (obj instanceof ProposalVO) {
        
            ProposalVO temp = (ProposalVO)obj;
            if (this._naturalDocVO != null) {
                if (temp._naturalDocVO == null) return false;
                else if (!(this._naturalDocVO.equals(temp._naturalDocVO))) 
                    return false;
            }
            else if (temp._naturalDocVO != null)
                return false;
            if (this._proposalDate != null) {
                if (temp._proposalDate == null) return false;
                else if (!(this._proposalDate.equals(temp._proposalDate))) 
                    return false;
            }
            else if (temp._proposalDate != null)
                return false;
            if (this._proposalProjectionVOList != null) {
                if (temp._proposalProjectionVOList == null) return false;
                else if (!(this._proposalProjectionVOList.equals(temp._proposalProjectionVOList))) 
                    return false;
            }
            else if (temp._proposalProjectionVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getNaturalDocVOReturns the value of field
     * 'naturalDocVO'.
     * 
     * @return the value of field 'naturalDocVO'.
     */
    public edit.common.vo.NaturalDocVO getNaturalDocVO()
    {
        return this._naturalDocVO;
    } //-- edit.common.vo.NaturalDocVO getNaturalDocVO() 

    /**
     * Method getProposalDateReturns the value of field
     * 'proposalDate'.
     * 
     * @return the value of field 'proposalDate'.
     */
    public java.lang.String getProposalDate()
    {
        return this._proposalDate;
    } //-- java.lang.String getProposalDate() 

    /**
     * Method getProposalProjectionVO
     * 
     * @param index
     */
    public edit.common.vo.ProposalProjectionVO getProposalProjectionVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _proposalProjectionVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ProposalProjectionVO) _proposalProjectionVOList.elementAt(index);
    } //-- edit.common.vo.ProposalProjectionVO getProposalProjectionVO(int) 

    /**
     * Method getProposalProjectionVO
     */
    public edit.common.vo.ProposalProjectionVO[] getProposalProjectionVO()
    {
        int size = _proposalProjectionVOList.size();
        edit.common.vo.ProposalProjectionVO[] mArray = new edit.common.vo.ProposalProjectionVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ProposalProjectionVO) _proposalProjectionVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ProposalProjectionVO[] getProposalProjectionVO() 

    /**
     * Method getProposalProjectionVOCount
     */
    public int getProposalProjectionVOCount()
    {
        return _proposalProjectionVOList.size();
    } //-- int getProposalProjectionVOCount() 

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
     * Method removeAllProposalProjectionVO
     */
    public void removeAllProposalProjectionVO()
    {
        _proposalProjectionVOList.removeAllElements();
    } //-- void removeAllProposalProjectionVO() 

    /**
     * Method removeProposalProjectionVO
     * 
     * @param index
     */
    public edit.common.vo.ProposalProjectionVO removeProposalProjectionVO(int index)
    {
        java.lang.Object obj = _proposalProjectionVOList.elementAt(index);
        _proposalProjectionVOList.removeElementAt(index);
        return (edit.common.vo.ProposalProjectionVO) obj;
    } //-- edit.common.vo.ProposalProjectionVO removeProposalProjectionVO(int) 

    /**
     * Method setNaturalDocVOSets the value of field
     * 'naturalDocVO'.
     * 
     * @param naturalDocVO the value of field 'naturalDocVO'.
     */
    public void setNaturalDocVO(edit.common.vo.NaturalDocVO naturalDocVO)
    {
        this._naturalDocVO = naturalDocVO;
    } //-- void setNaturalDocVO(edit.common.vo.NaturalDocVO) 

    /**
     * Method setProposalDateSets the value of field
     * 'proposalDate'.
     * 
     * @param proposalDate the value of field 'proposalDate'.
     */
    public void setProposalDate(java.lang.String proposalDate)
    {
        this._proposalDate = proposalDate;
        
        super.setVoChanged(true);
    } //-- void setProposalDate(java.lang.String) 

    /**
     * Method setProposalProjectionVO
     * 
     * @param index
     * @param vProposalProjectionVO
     */
    public void setProposalProjectionVO(int index, edit.common.vo.ProposalProjectionVO vProposalProjectionVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _proposalProjectionVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vProposalProjectionVO.setParentVO(this.getClass(), this);
        _proposalProjectionVOList.setElementAt(vProposalProjectionVO, index);
    } //-- void setProposalProjectionVO(int, edit.common.vo.ProposalProjectionVO) 

    /**
     * Method setProposalProjectionVO
     * 
     * @param proposalProjectionVOArray
     */
    public void setProposalProjectionVO(edit.common.vo.ProposalProjectionVO[] proposalProjectionVOArray)
    {
        //-- copy array
        _proposalProjectionVOList.removeAllElements();
        for (int i = 0; i < proposalProjectionVOArray.length; i++) {
            proposalProjectionVOArray[i].setParentVO(this.getClass(), this);
            _proposalProjectionVOList.addElement(proposalProjectionVOArray[i]);
        }
    } //-- void setProposalProjectionVO(edit.common.vo.ProposalProjectionVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ProposalVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ProposalVO) Unmarshaller.unmarshal(edit.common.vo.ProposalVO.class, reader);
    } //-- edit.common.vo.ProposalVO unmarshal(java.io.Reader) 

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

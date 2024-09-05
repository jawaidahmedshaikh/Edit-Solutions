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
 * Class InterestRateParametersVO.
 * 
 * @version $Revision$ $Date$
 */
public class InterestRateParametersVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _interestRateParametersPK
     */
    private long _interestRateParametersPK;

    /**
     * keeps track of state for field: _interestRateParametersPK
     */
    private boolean _has_interestRateParametersPK;

    /**
     * Field _filteredFundFK
     */
    private long _filteredFundFK;

    /**
     * keeps track of state for field: _filteredFundFK
     */
    private boolean _has_filteredFundFK;

    /**
     * Field _originalDate
     */
    private java.lang.String _originalDate;

    /**
     * Field _optionCT
     */
    private java.lang.String _optionCT;

    /**
     * Field _stopDate
     */
    private java.lang.String _stopDate;

    /**
     * Field _interestRateVOList
     */
    private java.util.Vector _interestRateVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public InterestRateParametersVO() {
        super();
        _interestRateVOList = new Vector();
    } //-- edit.common.vo.InterestRateParametersVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addInterestRateVO
     * 
     * @param vInterestRateVO
     */
    public void addInterestRateVO(edit.common.vo.InterestRateVO vInterestRateVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInterestRateVO.setParentVO(this.getClass(), this);
        _interestRateVOList.addElement(vInterestRateVO);
    } //-- void addInterestRateVO(edit.common.vo.InterestRateVO) 

    /**
     * Method addInterestRateVO
     * 
     * @param index
     * @param vInterestRateVO
     */
    public void addInterestRateVO(int index, edit.common.vo.InterestRateVO vInterestRateVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInterestRateVO.setParentVO(this.getClass(), this);
        _interestRateVOList.insertElementAt(vInterestRateVO, index);
    } //-- void addInterestRateVO(int, edit.common.vo.InterestRateVO) 

    /**
     * Method enumerateInterestRateVO
     */
    public java.util.Enumeration enumerateInterestRateVO()
    {
        return _interestRateVOList.elements();
    } //-- java.util.Enumeration enumerateInterestRateVO() 

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
        
        if (obj instanceof InterestRateParametersVO) {
        
            InterestRateParametersVO temp = (InterestRateParametersVO)obj;
            if (this._interestRateParametersPK != temp._interestRateParametersPK)
                return false;
            if (this._has_interestRateParametersPK != temp._has_interestRateParametersPK)
                return false;
            if (this._filteredFundFK != temp._filteredFundFK)
                return false;
            if (this._has_filteredFundFK != temp._has_filteredFundFK)
                return false;
            if (this._originalDate != null) {
                if (temp._originalDate == null) return false;
                else if (!(this._originalDate.equals(temp._originalDate))) 
                    return false;
            }
            else if (temp._originalDate != null)
                return false;
            if (this._optionCT != null) {
                if (temp._optionCT == null) return false;
                else if (!(this._optionCT.equals(temp._optionCT))) 
                    return false;
            }
            else if (temp._optionCT != null)
                return false;
            if (this._stopDate != null) {
                if (temp._stopDate == null) return false;
                else if (!(this._stopDate.equals(temp._stopDate))) 
                    return false;
            }
            else if (temp._stopDate != null)
                return false;
            if (this._interestRateVOList != null) {
                if (temp._interestRateVOList == null) return false;
                else if (!(this._interestRateVOList.equals(temp._interestRateVOList))) 
                    return false;
            }
            else if (temp._interestRateVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFilteredFundFKReturns the value of field
     * 'filteredFundFK'.
     * 
     * @return the value of field 'filteredFundFK'.
     */
    public long getFilteredFundFK()
    {
        return this._filteredFundFK;
    } //-- long getFilteredFundFK() 

    /**
     * Method getInterestRateParametersPKReturns the value of field
     * 'interestRateParametersPK'.
     * 
     * @return the value of field 'interestRateParametersPK'.
     */
    public long getInterestRateParametersPK()
    {
        return this._interestRateParametersPK;
    } //-- long getInterestRateParametersPK() 

    /**
     * Method getInterestRateVO
     * 
     * @param index
     */
    public edit.common.vo.InterestRateVO getInterestRateVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _interestRateVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InterestRateVO) _interestRateVOList.elementAt(index);
    } //-- edit.common.vo.InterestRateVO getInterestRateVO(int) 

    /**
     * Method getInterestRateVO
     */
    public edit.common.vo.InterestRateVO[] getInterestRateVO()
    {
        int size = _interestRateVOList.size();
        edit.common.vo.InterestRateVO[] mArray = new edit.common.vo.InterestRateVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InterestRateVO) _interestRateVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InterestRateVO[] getInterestRateVO() 

    /**
     * Method getInterestRateVOCount
     */
    public int getInterestRateVOCount()
    {
        return _interestRateVOList.size();
    } //-- int getInterestRateVOCount() 

    /**
     * Method getOptionCTReturns the value of field 'optionCT'.
     * 
     * @return the value of field 'optionCT'.
     */
    public java.lang.String getOptionCT()
    {
        return this._optionCT;
    } //-- java.lang.String getOptionCT() 

    /**
     * Method getOriginalDateReturns the value of field
     * 'originalDate'.
     * 
     * @return the value of field 'originalDate'.
     */
    public java.lang.String getOriginalDate()
    {
        return this._originalDate;
    } //-- java.lang.String getOriginalDate() 

    /**
     * Method getStopDateReturns the value of field 'stopDate'.
     * 
     * @return the value of field 'stopDate'.
     */
    public java.lang.String getStopDate()
    {
        return this._stopDate;
    } //-- java.lang.String getStopDate() 

    /**
     * Method hasFilteredFundFK
     */
    public boolean hasFilteredFundFK()
    {
        return this._has_filteredFundFK;
    } //-- boolean hasFilteredFundFK() 

    /**
     * Method hasInterestRateParametersPK
     */
    public boolean hasInterestRateParametersPK()
    {
        return this._has_interestRateParametersPK;
    } //-- boolean hasInterestRateParametersPK() 

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
     * Method removeAllInterestRateVO
     */
    public void removeAllInterestRateVO()
    {
        _interestRateVOList.removeAllElements();
    } //-- void removeAllInterestRateVO() 

    /**
     * Method removeInterestRateVO
     * 
     * @param index
     */
    public edit.common.vo.InterestRateVO removeInterestRateVO(int index)
    {
        java.lang.Object obj = _interestRateVOList.elementAt(index);
        _interestRateVOList.removeElementAt(index);
        return (edit.common.vo.InterestRateVO) obj;
    } //-- edit.common.vo.InterestRateVO removeInterestRateVO(int) 

    /**
     * Method setFilteredFundFKSets the value of field
     * 'filteredFundFK'.
     * 
     * @param filteredFundFK the value of field 'filteredFundFK'.
     */
    public void setFilteredFundFK(long filteredFundFK)
    {
        this._filteredFundFK = filteredFundFK;
        
        super.setVoChanged(true);
        this._has_filteredFundFK = true;
    } //-- void setFilteredFundFK(long) 

    /**
     * Method setInterestRateParametersPKSets the value of field
     * 'interestRateParametersPK'.
     * 
     * @param interestRateParametersPK the value of field
     * 'interestRateParametersPK'.
     */
    public void setInterestRateParametersPK(long interestRateParametersPK)
    {
        this._interestRateParametersPK = interestRateParametersPK;
        
        super.setVoChanged(true);
        this._has_interestRateParametersPK = true;
    } //-- void setInterestRateParametersPK(long) 

    /**
     * Method setInterestRateVO
     * 
     * @param index
     * @param vInterestRateVO
     */
    public void setInterestRateVO(int index, edit.common.vo.InterestRateVO vInterestRateVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _interestRateVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInterestRateVO.setParentVO(this.getClass(), this);
        _interestRateVOList.setElementAt(vInterestRateVO, index);
    } //-- void setInterestRateVO(int, edit.common.vo.InterestRateVO) 

    /**
     * Method setInterestRateVO
     * 
     * @param interestRateVOArray
     */
    public void setInterestRateVO(edit.common.vo.InterestRateVO[] interestRateVOArray)
    {
        //-- copy array
        _interestRateVOList.removeAllElements();
        for (int i = 0; i < interestRateVOArray.length; i++) {
            interestRateVOArray[i].setParentVO(this.getClass(), this);
            _interestRateVOList.addElement(interestRateVOArray[i]);
        }
    } //-- void setInterestRateVO(edit.common.vo.InterestRateVO) 

    /**
     * Method setOptionCTSets the value of field 'optionCT'.
     * 
     * @param optionCT the value of field 'optionCT'.
     */
    public void setOptionCT(java.lang.String optionCT)
    {
        this._optionCT = optionCT;
        
        super.setVoChanged(true);
    } //-- void setOptionCT(java.lang.String) 

    /**
     * Method setOriginalDateSets the value of field
     * 'originalDate'.
     * 
     * @param originalDate the value of field 'originalDate'.
     */
    public void setOriginalDate(java.lang.String originalDate)
    {
        this._originalDate = originalDate;
        
        super.setVoChanged(true);
    } //-- void setOriginalDate(java.lang.String) 

    /**
     * Method setStopDateSets the value of field 'stopDate'.
     * 
     * @param stopDate the value of field 'stopDate'.
     */
    public void setStopDate(java.lang.String stopDate)
    {
        this._stopDate = stopDate;
        
        super.setVoChanged(true);
    } //-- void setStopDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.InterestRateParametersVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.InterestRateParametersVO) Unmarshaller.unmarshal(edit.common.vo.InterestRateParametersVO.class, reader);
    } //-- edit.common.vo.InterestRateParametersVO unmarshal(java.io.Reader) 

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

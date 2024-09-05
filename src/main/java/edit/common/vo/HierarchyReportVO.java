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
public class HierarchyReportVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _hierarchyReportEntryVOList
     */
    private java.util.Vector _hierarchyReportEntryVOList;

    /**
     * Field _contractCode
     */
    private java.lang.String _contractCode;


      //----------------/
     //- Constructors -/
    //----------------/

    public HierarchyReportVO() {
        super();
        _hierarchyReportEntryVOList = new Vector();
    } //-- edit.common.vo.HierarchyReportVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addHierarchyReportEntryVO
     * 
     * @param vHierarchyReportEntryVO
     */
    public void addHierarchyReportEntryVO(edit.common.vo.HierarchyReportEntryVO vHierarchyReportEntryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vHierarchyReportEntryVO.setParentVO(this.getClass(), this);
        _hierarchyReportEntryVOList.addElement(vHierarchyReportEntryVO);
    } //-- void addHierarchyReportEntryVO(edit.common.vo.HierarchyReportEntryVO) 

    /**
     * Method addHierarchyReportEntryVO
     * 
     * @param index
     * @param vHierarchyReportEntryVO
     */
    public void addHierarchyReportEntryVO(int index, edit.common.vo.HierarchyReportEntryVO vHierarchyReportEntryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vHierarchyReportEntryVO.setParentVO(this.getClass(), this);
        _hierarchyReportEntryVOList.insertElementAt(vHierarchyReportEntryVO, index);
    } //-- void addHierarchyReportEntryVO(int, edit.common.vo.HierarchyReportEntryVO) 

    /**
     * Method enumerateHierarchyReportEntryVO
     */
    public java.util.Enumeration enumerateHierarchyReportEntryVO()
    {
        return _hierarchyReportEntryVOList.elements();
    } //-- java.util.Enumeration enumerateHierarchyReportEntryVO() 

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
        
        if (obj instanceof HierarchyReportVO) {
        
            HierarchyReportVO temp = (HierarchyReportVO)obj;
            if (this._hierarchyReportEntryVOList != null) {
                if (temp._hierarchyReportEntryVOList == null) return false;
                else if (!(this._hierarchyReportEntryVOList.equals(temp._hierarchyReportEntryVOList))) 
                    return false;
            }
            else if (temp._hierarchyReportEntryVOList != null)
                return false;
            if (this._contractCode != null) {
                if (temp._contractCode == null) return false;
                else if (!(this._contractCode.equals(temp._contractCode))) 
                    return false;
            }
            else if (temp._contractCode != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getContractCodeReturns the value of field
     * 'contractCode'.
     * 
     * @return the value of field 'contractCode'.
     */
    public java.lang.String getContractCode()
    {
        return this._contractCode;
    } //-- java.lang.String getContractCode() 

    /**
     * Method getHierarchyReportEntryVO
     * 
     * @param index
     */
    public edit.common.vo.HierarchyReportEntryVO getHierarchyReportEntryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _hierarchyReportEntryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.HierarchyReportEntryVO) _hierarchyReportEntryVOList.elementAt(index);
    } //-- edit.common.vo.HierarchyReportEntryVO getHierarchyReportEntryVO(int) 

    /**
     * Method getHierarchyReportEntryVO
     */
    public edit.common.vo.HierarchyReportEntryVO[] getHierarchyReportEntryVO()
    {
        int size = _hierarchyReportEntryVOList.size();
        edit.common.vo.HierarchyReportEntryVO[] mArray = new edit.common.vo.HierarchyReportEntryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.HierarchyReportEntryVO) _hierarchyReportEntryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.HierarchyReportEntryVO[] getHierarchyReportEntryVO() 

    /**
     * Method getHierarchyReportEntryVOCount
     */
    public int getHierarchyReportEntryVOCount()
    {
        return _hierarchyReportEntryVOList.size();
    } //-- int getHierarchyReportEntryVOCount() 

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
     * Method removeAllHierarchyReportEntryVO
     */
    public void removeAllHierarchyReportEntryVO()
    {
        _hierarchyReportEntryVOList.removeAllElements();
    } //-- void removeAllHierarchyReportEntryVO() 

    /**
     * Method removeHierarchyReportEntryVO
     * 
     * @param index
     */
    public edit.common.vo.HierarchyReportEntryVO removeHierarchyReportEntryVO(int index)
    {
        java.lang.Object obj = _hierarchyReportEntryVOList.elementAt(index);
        _hierarchyReportEntryVOList.removeElementAt(index);
        return (edit.common.vo.HierarchyReportEntryVO) obj;
    } //-- edit.common.vo.HierarchyReportEntryVO removeHierarchyReportEntryVO(int) 

    /**
     * Method setContractCodeSets the value of field
     * 'contractCode'.
     * 
     * @param contractCode the value of field 'contractCode'.
     */
    public void setContractCode(java.lang.String contractCode)
    {
        this._contractCode = contractCode;
        
        super.setVoChanged(true);
    } //-- void setContractCode(java.lang.String) 

    /**
     * Method setHierarchyReportEntryVO
     * 
     * @param index
     * @param vHierarchyReportEntryVO
     */
    public void setHierarchyReportEntryVO(int index, edit.common.vo.HierarchyReportEntryVO vHierarchyReportEntryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _hierarchyReportEntryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vHierarchyReportEntryVO.setParentVO(this.getClass(), this);
        _hierarchyReportEntryVOList.setElementAt(vHierarchyReportEntryVO, index);
    } //-- void setHierarchyReportEntryVO(int, edit.common.vo.HierarchyReportEntryVO) 

    /**
     * Method setHierarchyReportEntryVO
     * 
     * @param hierarchyReportEntryVOArray
     */
    public void setHierarchyReportEntryVO(edit.common.vo.HierarchyReportEntryVO[] hierarchyReportEntryVOArray)
    {
        //-- copy array
        _hierarchyReportEntryVOList.removeAllElements();
        for (int i = 0; i < hierarchyReportEntryVOArray.length; i++) {
            hierarchyReportEntryVOArray[i].setParentVO(this.getClass(), this);
            _hierarchyReportEntryVOList.addElement(hierarchyReportEntryVOArray[i]);
        }
    } //-- void setHierarchyReportEntryVO(edit.common.vo.HierarchyReportEntryVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.HierarchyReportVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.HierarchyReportVO) Unmarshaller.unmarshal(edit.common.vo.HierarchyReportVO.class, reader);
    } //-- edit.common.vo.HierarchyReportVO unmarshal(java.io.Reader) 

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

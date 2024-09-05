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
 * Class HierarchyReportEntryVO.
 * 
 * @version $Revision$ $Date$
 */
public class HierarchyReportEntryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentName
     */
    private java.lang.String _agentName;

    /**
     * Field _agentId
     */
    private java.lang.String _agentId;

    /**
     * Field _hierarchyReportEntryVOList
     */
    private java.util.Vector _hierarchyReportEntryVOList;

    /**
     * Field _placedAgentVO
     */
    private edit.common.vo.PlacedAgentVO _placedAgentVO;

    /**
     * Field _hasError
     */
    private boolean _hasError;

    /**
     * keeps track of state for field: _hasError
     */
    private boolean _has_hasError;


      //----------------/
     //- Constructors -/
    //----------------/

    public HierarchyReportEntryVO() {
        super();
        _hierarchyReportEntryVOList = new Vector();
    } //-- edit.common.vo.HierarchyReportEntryVO()


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
        
        if (obj instanceof HierarchyReportEntryVO) {
        
            HierarchyReportEntryVO temp = (HierarchyReportEntryVO)obj;
            if (this._agentName != null) {
                if (temp._agentName == null) return false;
                else if (!(this._agentName.equals(temp._agentName))) 
                    return false;
            }
            else if (temp._agentName != null)
                return false;
            if (this._agentId != null) {
                if (temp._agentId == null) return false;
                else if (!(this._agentId.equals(temp._agentId))) 
                    return false;
            }
            else if (temp._agentId != null)
                return false;
            if (this._hierarchyReportEntryVOList != null) {
                if (temp._hierarchyReportEntryVOList == null) return false;
                else if (!(this._hierarchyReportEntryVOList.equals(temp._hierarchyReportEntryVOList))) 
                    return false;
            }
            else if (temp._hierarchyReportEntryVOList != null)
                return false;
            if (this._placedAgentVO != null) {
                if (temp._placedAgentVO == null) return false;
                else if (!(this._placedAgentVO.equals(temp._placedAgentVO))) 
                    return false;
            }
            else if (temp._placedAgentVO != null)
                return false;
            if (this._hasError != temp._hasError)
                return false;
            if (this._has_hasError != temp._has_hasError)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentIdReturns the value of field 'agentId'.
     * 
     * @return the value of field 'agentId'.
     */
    public java.lang.String getAgentId()
    {
        return this._agentId;
    } //-- java.lang.String getAgentId() 

    /**
     * Method getAgentNameReturns the value of field 'agentName'.
     * 
     * @return the value of field 'agentName'.
     */
    public java.lang.String getAgentName()
    {
        return this._agentName;
    } //-- java.lang.String getAgentName() 

    /**
     * Method getHasErrorReturns the value of field 'hasError'.
     * 
     * @return the value of field 'hasError'.
     */
    public boolean getHasError()
    {
        return this._hasError;
    } //-- boolean getHasError() 

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
     * Method getPlacedAgentVOReturns the value of field
     * 'placedAgentVO'.
     * 
     * @return the value of field 'placedAgentVO'.
     */
    public edit.common.vo.PlacedAgentVO getPlacedAgentVO()
    {
        return this._placedAgentVO;
    } //-- edit.common.vo.PlacedAgentVO getPlacedAgentVO() 

    /**
     * Method hasHasError
     */
    public boolean hasHasError()
    {
        return this._has_hasError;
    } //-- boolean hasHasError() 

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
     * Method setAgentIdSets the value of field 'agentId'.
     * 
     * @param agentId the value of field 'agentId'.
     */
    public void setAgentId(java.lang.String agentId)
    {
        this._agentId = agentId;
        
        super.setVoChanged(true);
    } //-- void setAgentId(java.lang.String) 

    /**
     * Method setAgentNameSets the value of field 'agentName'.
     * 
     * @param agentName the value of field 'agentName'.
     */
    public void setAgentName(java.lang.String agentName)
    {
        this._agentName = agentName;
        
        super.setVoChanged(true);
    } //-- void setAgentName(java.lang.String) 

    /**
     * Method setHasErrorSets the value of field 'hasError'.
     * 
     * @param hasError the value of field 'hasError'.
     */
    public void setHasError(boolean hasError)
    {
        this._hasError = hasError;
        
        super.setVoChanged(true);
        this._has_hasError = true;
    } //-- void setHasError(boolean) 

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
     * Method setPlacedAgentVOSets the value of field
     * 'placedAgentVO'.
     * 
     * @param placedAgentVO the value of field 'placedAgentVO'.
     */
    public void setPlacedAgentVO(edit.common.vo.PlacedAgentVO placedAgentVO)
    {
        this._placedAgentVO = placedAgentVO;
    } //-- void setPlacedAgentVO(edit.common.vo.PlacedAgentVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.HierarchyReportEntryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.HierarchyReportEntryVO) Unmarshaller.unmarshal(edit.common.vo.HierarchyReportEntryVO.class, reader);
    } //-- edit.common.vo.HierarchyReportEntryVO unmarshal(java.io.Reader) 

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

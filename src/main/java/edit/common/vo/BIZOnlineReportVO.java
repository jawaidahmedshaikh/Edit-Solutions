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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class BIZOnlineReportVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _isOnlineReportAttached
     */
    private boolean _isOnlineReportAttached;

    /**
     * keeps track of state for field: _isOnlineReportAttached
     */
    private boolean _has_isOnlineReportAttached;

    /**
     * Field _filteredOnlineReportPK
     */
    private long _filteredOnlineReportPK;

    /**
     * keeps track of state for field: _filteredOnlineReportPK
     */
    private boolean _has_filteredOnlineReportPK;

    /**
     * Field _onlineReportVO
     */
    private edit.common.vo.OnlineReportVO _onlineReportVO;


      //----------------/
     //- Constructors -/
    //----------------/

    public BIZOnlineReportVO() {
        super();
    } //-- edit.common.vo.BIZOnlineReportVO()


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
        
        if (obj instanceof BIZOnlineReportVO) {
        
            BIZOnlineReportVO temp = (BIZOnlineReportVO)obj;
            if (this._isOnlineReportAttached != temp._isOnlineReportAttached)
                return false;
            if (this._has_isOnlineReportAttached != temp._has_isOnlineReportAttached)
                return false;
            if (this._filteredOnlineReportPK != temp._filteredOnlineReportPK)
                return false;
            if (this._has_filteredOnlineReportPK != temp._has_filteredOnlineReportPK)
                return false;
            if (this._onlineReportVO != null) {
                if (temp._onlineReportVO == null) return false;
                else if (!(this._onlineReportVO.equals(temp._onlineReportVO))) 
                    return false;
            }
            else if (temp._onlineReportVO != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFilteredOnlineReportPKReturns the value of field
     * 'filteredOnlineReportPK'.
     * 
     * @return the value of field 'filteredOnlineReportPK'.
     */
    public long getFilteredOnlineReportPK()
    {
        return this._filteredOnlineReportPK;
    } //-- long getFilteredOnlineReportPK() 

    /**
     * Method getIsOnlineReportAttachedReturns the value of field
     * 'isOnlineReportAttached'.
     * 
     * @return the value of field 'isOnlineReportAttached'.
     */
    public boolean getIsOnlineReportAttached()
    {
        return this._isOnlineReportAttached;
    } //-- boolean getIsOnlineReportAttached() 

    /**
     * Method getOnlineReportVOReturns the value of field
     * 'onlineReportVO'.
     * 
     * @return the value of field 'onlineReportVO'.
     */
    public edit.common.vo.OnlineReportVO getOnlineReportVO()
    {
        return this._onlineReportVO;
    } //-- edit.common.vo.OnlineReportVO getOnlineReportVO() 

    /**
     * Method hasFilteredOnlineReportPK
     */
    public boolean hasFilteredOnlineReportPK()
    {
        return this._has_filteredOnlineReportPK;
    } //-- boolean hasFilteredOnlineReportPK() 

    /**
     * Method hasIsOnlineReportAttached
     */
    public boolean hasIsOnlineReportAttached()
    {
        return this._has_isOnlineReportAttached;
    } //-- boolean hasIsOnlineReportAttached() 

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
     * Method setFilteredOnlineReportPKSets the value of field
     * 'filteredOnlineReportPK'.
     * 
     * @param filteredOnlineReportPK the value of field
     * 'filteredOnlineReportPK'.
     */
    public void setFilteredOnlineReportPK(long filteredOnlineReportPK)
    {
        this._filteredOnlineReportPK = filteredOnlineReportPK;
        
        super.setVoChanged(true);
        this._has_filteredOnlineReportPK = true;
    } //-- void setFilteredOnlineReportPK(long) 

    /**
     * Method setIsOnlineReportAttachedSets the value of field
     * 'isOnlineReportAttached'.
     * 
     * @param isOnlineReportAttached the value of field
     * 'isOnlineReportAttached'.
     */
    public void setIsOnlineReportAttached(boolean isOnlineReportAttached)
    {
        this._isOnlineReportAttached = isOnlineReportAttached;
        
        super.setVoChanged(true);
        this._has_isOnlineReportAttached = true;
    } //-- void setIsOnlineReportAttached(boolean) 

    /**
     * Method setOnlineReportVOSets the value of field
     * 'onlineReportVO'.
     * 
     * @param onlineReportVO the value of field 'onlineReportVO'.
     */
    public void setOnlineReportVO(edit.common.vo.OnlineReportVO onlineReportVO)
    {
        this._onlineReportVO = onlineReportVO;
    } //-- void setOnlineReportVO(edit.common.vo.OnlineReportVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BIZOnlineReportVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BIZOnlineReportVO) Unmarshaller.unmarshal(edit.common.vo.BIZOnlineReportVO.class, reader);
    } //-- edit.common.vo.BIZOnlineReportVO unmarshal(java.io.Reader) 

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

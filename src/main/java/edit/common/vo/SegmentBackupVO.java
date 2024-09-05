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
 * Class SegmentBackupVO.
 * 
 * @version $Revision$ $Date$
 */
public class SegmentBackupVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _segmentBackupPK
     */
    private long _segmentBackupPK;

    /**
     * keeps track of state for field: _segmentBackupPK
     */
    private boolean _has_segmentBackupPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _lineNumber
     */
    private int _lineNumber;

    /**
     * keeps track of state for field: _lineNumber
     */
    private boolean _has_lineNumber;

    /**
     * Field _lineText
     */
    private java.lang.String _lineText;


      //----------------/
     //- Constructors -/
    //----------------/

    public SegmentBackupVO() {
        super();
    } //-- edit.common.vo.SegmentBackupVO()


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
        
        if (obj instanceof SegmentBackupVO) {
        
            SegmentBackupVO temp = (SegmentBackupVO)obj;
            if (this._segmentBackupPK != temp._segmentBackupPK)
                return false;
            if (this._has_segmentBackupPK != temp._has_segmentBackupPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._lineNumber != temp._lineNumber)
                return false;
            if (this._has_lineNumber != temp._has_lineNumber)
                return false;
            if (this._lineText != null) {
                if (temp._lineText == null) return false;
                else if (!(this._lineText.equals(temp._lineText))) 
                    return false;
            }
            else if (temp._lineText != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getLineNumberReturns the value of field 'lineNumber'.
     * 
     * @return the value of field 'lineNumber'.
     */
    public int getLineNumber()
    {
        return this._lineNumber;
    } //-- int getLineNumber() 

    /**
     * Method getLineTextReturns the value of field 'lineText'.
     * 
     * @return the value of field 'lineText'.
     */
    public java.lang.String getLineText()
    {
        return this._lineText;
    } //-- java.lang.String getLineText() 

    /**
     * Method getSegmentBackupPKReturns the value of field
     * 'segmentBackupPK'.
     * 
     * @return the value of field 'segmentBackupPK'.
     */
    public long getSegmentBackupPK()
    {
        return this._segmentBackupPK;
    } //-- long getSegmentBackupPK() 

    /**
     * Method getSegmentFKReturns the value of field 'segmentFK'.
     * 
     * @return the value of field 'segmentFK'.
     */
    public long getSegmentFK()
    {
        return this._segmentFK;
    } //-- long getSegmentFK() 

    /**
     * Method hasLineNumber
     */
    public boolean hasLineNumber()
    {
        return this._has_lineNumber;
    } //-- boolean hasLineNumber() 

    /**
     * Method hasSegmentBackupPK
     */
    public boolean hasSegmentBackupPK()
    {
        return this._has_segmentBackupPK;
    } //-- boolean hasSegmentBackupPK() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

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
     * Method setLineNumberSets the value of field 'lineNumber'.
     * 
     * @param lineNumber the value of field 'lineNumber'.
     */
    public void setLineNumber(int lineNumber)
    {
        this._lineNumber = lineNumber;
        
        super.setVoChanged(true);
        this._has_lineNumber = true;
    } //-- void setLineNumber(int) 

    /**
     * Method setLineTextSets the value of field 'lineText'.
     * 
     * @param lineText the value of field 'lineText'.
     */
    public void setLineText(java.lang.String lineText)
    {
        this._lineText = lineText;
        
        super.setVoChanged(true);
    } //-- void setLineText(java.lang.String) 

    /**
     * Method setSegmentBackupPKSets the value of field
     * 'segmentBackupPK'.
     * 
     * @param segmentBackupPK the value of field 'segmentBackupPK'.
     */
    public void setSegmentBackupPK(long segmentBackupPK)
    {
        this._segmentBackupPK = segmentBackupPK;
        
        super.setVoChanged(true);
        this._has_segmentBackupPK = true;
    } //-- void setSegmentBackupPK(long) 

    /**
     * Method setSegmentFKSets the value of field 'segmentFK'.
     * 
     * @param segmentFK the value of field 'segmentFK'.
     */
    public void setSegmentFK(long segmentFK)
    {
        this._segmentFK = segmentFK;
        
        super.setVoChanged(true);
        this._has_segmentFK = true;
    } //-- void setSegmentFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SegmentBackupVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SegmentBackupVO) Unmarshaller.unmarshal(edit.common.vo.SegmentBackupVO.class, reader);
    } //-- edit.common.vo.SegmentBackupVO unmarshal(java.io.Reader) 

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

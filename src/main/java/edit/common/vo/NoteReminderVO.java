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
 * Class NoteReminderVO.
 * 
 * @version $Revision$ $Date$
 */
public class NoteReminderVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _noteReminderPK
     */
    private long _noteReminderPK;

    /**
     * keeps track of state for field: _noteReminderPK
     */
    private boolean _has_noteReminderPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _sequence
     */
    private int _sequence;

    /**
     * keeps track of state for field: _sequence
     */
    private boolean _has_sequence;

    /**
     * Field _note
     */
    private java.lang.String _note;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _noteTypeCT
     */
    private java.lang.String _noteTypeCT;

    /**
     * Field _noteQualifierCT
     */
    private java.lang.String _noteQualifierCT;


      //----------------/
     //- Constructors -/
    //----------------/

    public NoteReminderVO() {
        super();
    } //-- edit.common.vo.NoteReminderVO()


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
        
        if (obj instanceof NoteReminderVO) {
        
            NoteReminderVO temp = (NoteReminderVO)obj;
            if (this._noteReminderPK != temp._noteReminderPK)
                return false;
            if (this._has_noteReminderPK != temp._has_noteReminderPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._sequence != temp._sequence)
                return false;
            if (this._has_sequence != temp._has_sequence)
                return false;
            if (this._note != null) {
                if (temp._note == null) return false;
                else if (!(this._note.equals(temp._note))) 
                    return false;
            }
            else if (temp._note != null)
                return false;
            if (this._maintDateTime != null) {
                if (temp._maintDateTime == null) return false;
                else if (!(this._maintDateTime.equals(temp._maintDateTime))) 
                    return false;
            }
            else if (temp._maintDateTime != null)
                return false;
            if (this._operator != null) {
                if (temp._operator == null) return false;
                else if (!(this._operator.equals(temp._operator))) 
                    return false;
            }
            else if (temp._operator != null)
                return false;
            if (this._noteTypeCT != null) {
                if (temp._noteTypeCT == null) return false;
                else if (!(this._noteTypeCT.equals(temp._noteTypeCT))) 
                    return false;
            }
            else if (temp._noteTypeCT != null)
                return false;
            if (this._noteQualifierCT != null) {
                if (temp._noteQualifierCT == null) return false;
                else if (!(this._noteQualifierCT.equals(temp._noteQualifierCT))) 
                    return false;
            }
            else if (temp._noteQualifierCT != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getMaintDateTimeReturns the value of field
     * 'maintDateTime'.
     * 
     * @return the value of field 'maintDateTime'.
     */
    public java.lang.String getMaintDateTime()
    {
        return this._maintDateTime;
    } //-- java.lang.String getMaintDateTime() 

    /**
     * Method getNoteReturns the value of field 'note'.
     * 
     * @return the value of field 'note'.
     */
    public java.lang.String getNote()
    {
        return this._note;
    } //-- java.lang.String getNote() 

    /**
     * Method getNoteQualifierCTReturns the value of field
     * 'noteQualifierCT'.
     * 
     * @return the value of field 'noteQualifierCT'.
     */
    public java.lang.String getNoteQualifierCT()
    {
        return this._noteQualifierCT;
    } //-- java.lang.String getNoteQualifierCT() 

    /**
     * Method getNoteReminderPKReturns the value of field
     * 'noteReminderPK'.
     * 
     * @return the value of field 'noteReminderPK'.
     */
    public long getNoteReminderPK()
    {
        return this._noteReminderPK;
    } //-- long getNoteReminderPK() 

    /**
     * Method getNoteTypeCTReturns the value of field 'noteTypeCT'.
     * 
     * @return the value of field 'noteTypeCT'.
     */
    public java.lang.String getNoteTypeCT()
    {
        return this._noteTypeCT;
    } //-- java.lang.String getNoteTypeCT() 

    /**
     * Method getOperatorReturns the value of field 'operator'.
     * 
     * @return the value of field 'operator'.
     */
    public java.lang.String getOperator()
    {
        return this._operator;
    } //-- java.lang.String getOperator() 

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
     * Method getSequenceReturns the value of field 'sequence'.
     * 
     * @return the value of field 'sequence'.
     */
    public int getSequence()
    {
        return this._sequence;
    } //-- int getSequence() 

    /**
     * Method hasNoteReminderPK
     */
    public boolean hasNoteReminderPK()
    {
        return this._has_noteReminderPK;
    } //-- boolean hasNoteReminderPK() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

    /**
     * Method hasSequence
     */
    public boolean hasSequence()
    {
        return this._has_sequence;
    } //-- boolean hasSequence() 

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
     * Method setMaintDateTimeSets the value of field
     * 'maintDateTime'.
     * 
     * @param maintDateTime the value of field 'maintDateTime'.
     */
    public void setMaintDateTime(java.lang.String maintDateTime)
    {
        this._maintDateTime = maintDateTime;
        
        super.setVoChanged(true);
    } //-- void setMaintDateTime(java.lang.String) 

    /**
     * Method setNoteSets the value of field 'note'.
     * 
     * @param note the value of field 'note'.
     */
    public void setNote(java.lang.String note)
    {
        this._note = note;
        
        super.setVoChanged(true);
    } //-- void setNote(java.lang.String) 

    /**
     * Method setNoteQualifierCTSets the value of field
     * 'noteQualifierCT'.
     * 
     * @param noteQualifierCT the value of field 'noteQualifierCT'.
     */
    public void setNoteQualifierCT(java.lang.String noteQualifierCT)
    {
        this._noteQualifierCT = noteQualifierCT;
        
        super.setVoChanged(true);
    } //-- void setNoteQualifierCT(java.lang.String) 

    /**
     * Method setNoteReminderPKSets the value of field
     * 'noteReminderPK'.
     * 
     * @param noteReminderPK the value of field 'noteReminderPK'.
     */
    public void setNoteReminderPK(long noteReminderPK)
    {
        this._noteReminderPK = noteReminderPK;
        
        super.setVoChanged(true);
        this._has_noteReminderPK = true;
    } //-- void setNoteReminderPK(long) 

    /**
     * Method setNoteTypeCTSets the value of field 'noteTypeCT'.
     * 
     * @param noteTypeCT the value of field 'noteTypeCT'.
     */
    public void setNoteTypeCT(java.lang.String noteTypeCT)
    {
        this._noteTypeCT = noteTypeCT;
        
        super.setVoChanged(true);
    } //-- void setNoteTypeCT(java.lang.String) 

    /**
     * Method setOperatorSets the value of field 'operator'.
     * 
     * @param operator the value of field 'operator'.
     */
    public void setOperator(java.lang.String operator)
    {
        this._operator = operator;
        
        super.setVoChanged(true);
    } //-- void setOperator(java.lang.String) 

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
     * Method setSequenceSets the value of field 'sequence'.
     * 
     * @param sequence the value of field 'sequence'.
     */
    public void setSequence(int sequence)
    {
        this._sequence = sequence;
        
        super.setVoChanged(true);
        this._has_sequence = true;
    } //-- void setSequence(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.NoteReminderVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.NoteReminderVO) Unmarshaller.unmarshal(edit.common.vo.NoteReminderVO.class, reader);
    } //-- edit.common.vo.NoteReminderVO unmarshal(java.io.Reader) 

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
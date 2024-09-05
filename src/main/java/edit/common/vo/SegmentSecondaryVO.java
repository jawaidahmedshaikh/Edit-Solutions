package edit.common.vo;

import java.math.BigDecimal;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

public class SegmentSecondaryVO extends edit.common.vo.VOObject implements java.io.Serializable {

	private long _segmentSecondaryPK;
	private long _segmentParentFK;
	private long _segmentChildFK;
	private BigDecimal _annualPremium;
	private String _location;
	private String _sequence;
	
	private boolean _has_segmentSecondaryPK;
	private boolean _has_segmentParentFK;
	private boolean _has_segmentChildFK;
	
	public SegmentSecondaryVO() {
        super();
    }
	
	/**
     * Method setSegmentSecondaryPK
     * Sets the value of field 'segmentSecondaryPK'.
     * 
     * @param segmentSecondaryPK the value of field 'segmentSecondaryPK'
     */
    public void setSegmentSecondaryPK(long segmentSecondaryPK)
    {
        this._segmentSecondaryPK = segmentSecondaryPK;
        
        super.setVoChanged(true);
        this._has_segmentSecondaryPK = true;
    }
    
    /**
     * Method getSegmentSecondaryPK
     * Returns the value of field 'segmentSecondaryPK'.
     * 
     * @return the value of field 'segmentSecondaryPK'.
     */
    public long getSegmentSecondaryPK()
    {
        return this._segmentSecondaryPK;
    }

    /**
     * Method setSegmentParentFK
     * Sets the value of field 'segmentParentFK'.
     * 
     * @param segmentParentFK the value of field 'segmentParentFK'
     */
    public void setSegmentParentFK(long segmentParentFK)
    {
        this._segmentParentFK = segmentParentFK;
        
        super.setVoChanged(true);
        this._has_segmentParentFK = true;
    }
    
    /**
     * Method getSegmentParentFK
     * Returns the value of field 'segmentParentFK'.
     * 
     * @return the value of field 'segmentParentFK'.
     */
    public long getSegmentParentFK()
    {
        return this._segmentParentFK;
    }
	
    /**   
     * Method setSegmentChildFK
     * Sets the value of field 'segmentChildFK'.
     * 
     * @param segmentChildFK the value of field 'segmentChildFK'
     */
    public void setSegmentChildFK(long segmentChildFK)
    {
        this._segmentChildFK = segmentChildFK;
        
        super.setVoChanged(true);
        this._has_segmentChildFK = true;
    }
    
    /**
     * Method getSegmentChildFK
     * Returns the value of field 'segmentChildFK'.
     * 
     * @return the value of field 'segmentChildFK'.
     */
    public long getSegmentChildFK()
    {
        return this._segmentChildFK;
    }
	
    /**   
     * Method setAnnualPremium
     * Sets the value of field 'annualPremium'.
     * 
     * @param annualPremium the value of field 'annualPremium'
     */
    public void setAnnualPremium(BigDecimal annualPremium)
    {
        this._annualPremium = annualPremium;
        
        super.setVoChanged(true);
    }
    
    /**
     * Method getAnnualPremium
     * Returns the value of field 'annualPremium'.
     * 
     * @return the value of field 'annualPremium'.
     */
    public BigDecimal getAnnualPremium()
    {
        return this._annualPremium;
    }
	
    /**   
     * Method setLocation
     * Sets the value of field 'location'.
     * 
     * @param location the value of field 'location'
     */
    public void setLocation(String location)
    {
        this._location = location;
        
        super.setVoChanged(true);
    }
    
    /**
     * Method getLocation
     * Returns the value of field 'location'.
     * 
     * @return the value of field 'location'.
     */
    public String getLocation()
    {
        return this._location;
    }
    
    /**   
     * Method setSequence
     * Sets the value of field 'sequence'.
     * 
     * @param location the value of field 'sequence'
     */
    public void setSequence(String sequence)
    {
        this._sequence = sequence;
        
        super.setVoChanged(true);
    }
    
    /**
     * Method getSequence
     * Returns the value of field 'sequence'.
     * 
     * @return the value of field 'sequence'.
     */
    public String getSequence()
    {
        return this._sequence;
    }
    
    public boolean hasSegmentSecondaryPK() 
    {	
    	return _has_segmentSecondaryPK;
    }
    
    public boolean hasSegmentParentFK() 
    {	
    	return _has_segmentParentFK;
    }
    
    public boolean hasSegmentChildFK() 
    {	
    	return _has_segmentChildFK;
    }
    
    /**
     * Compare two instances of SegmentSecondaryVO
     * 
     * @param obj
     */
    public boolean equals(java.lang.Object obj)
    {
        if ( this == obj )
            return true;
        
        if (super.equals(obj)==false)
            return false;
        
        if (obj instanceof SegmentSecondaryVO) {
        
        	SegmentSecondaryVO temp = (SegmentSecondaryVO)obj;
            if (this._segmentSecondaryPK != temp._segmentSecondaryPK)
                return false;
            if (this._has_segmentSecondaryPK != temp._has_segmentSecondaryPK)
                return false;
            if (this._segmentParentFK != temp._segmentParentFK)
                return false;
            if (this._has_segmentParentFK != temp._has_segmentParentFK)
                return false;
            if (this._segmentChildFK != temp._segmentChildFK)
                return false;
            if (this._has_segmentChildFK != temp._has_segmentChildFK)
                return false;

            if (this._annualPremium != null) {
                if (temp._annualPremium == null) return false;
                else if (!(this._annualPremium.equals(temp._annualPremium))) 
                    return false;
            }
            else if (temp._annualPremium != null)
                return false;
            
            if (this._location != null) {
                if (temp._location == null) return false;
                else if (!(this._location.equals(temp._location))) 
                    return false;
            }
            else if (temp._location != null)
                return false;

            if (this._sequence != null) {
                if (temp._sequence == null) return false;
                else if (!(this._sequence.equals(temp._sequence))) 
                    return false;
            }
            else if (temp._sequence != null)
                return false;
            
            return true;
        }
        return false;
    }

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
    }
    
    /**
     * Method marshal
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    }
    
    /**
     * Method marshal
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    }
    
    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SegmentSecondaryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SegmentSecondaryVO) Unmarshaller.unmarshal(edit.common.vo.SegmentSecondaryVO.class, reader);
    } 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } 
}

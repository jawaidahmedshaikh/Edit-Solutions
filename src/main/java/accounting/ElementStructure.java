package accounting;

public class ElementStructure {

	private Long elementStructurePK;
    
    private Long elementFK;
    
    private Long fundFK;

    private Long chargeCodeFK;

    private String memoCode;
    
    private String certainPeriod;

    private String qualNonQualCT;

    private String switchEffectInd;

    private String suppressAccountingInd;
    
    private Element element;

    public void setElementStructurePK(Long elementStructurePK)
    {
        this.elementStructurePK = elementStructurePK;
    }

    public Long getElementStructurePK()
    {
        return elementStructurePK;
    }
    
    public void setElementFK(Long elementFK)
    {
        this.elementFK = elementFK;
    }

    public Long getElementFK()
    {
        return elementFK;
    }
    
    public void setFundFK(Long fundFK)
    {
        this.fundFK = fundFK;
    }

    public Long getFundFK()
    {
        return fundFK;
    }
    
    public void setChargeCodeFK(Long chargeCodeFK)
    {
        this.chargeCodeFK = chargeCodeFK;
    }

    public Long getChargeCodeFK()
    {
        return chargeCodeFK;
    }
	
    public void setMemoCode(String memoCode)
    {
        this.memoCode = memoCode;
    }

    public String getMemoCode()
    {
        return memoCode;
    }
    
    public void setCertainPeriod(String certainPeriod)
    {
        this.certainPeriod = certainPeriod;
    }

    public String getCertainPeriod()
    {
        return certainPeriod;
    }
    
    public void setQualNonQualCT(String qualNonQualCT)
    {
        this.qualNonQualCT = qualNonQualCT;
    }

    public String getQualNonQualCT()
    {
        return qualNonQualCT;
    }
    
    public void setSwitchEffectInd(String switchEffectInd)
    {
        this.switchEffectInd = switchEffectInd;
    }

    public String getSwitchEffectInd()
    {
        return switchEffectInd;
    }
    
    public void setSuppressAccountingInd(String suppressAccountingInd)
    {
        this.suppressAccountingInd = suppressAccountingInd;
    }

    public String getSuppressAccountingInd()
    {
        return suppressAccountingInd;
    }
    
    public void setElement(Element element)
    {
        this.element = element;
    }

    public Element getElement()
    {
        return element;
    }
}

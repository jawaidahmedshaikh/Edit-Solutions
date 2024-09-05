package accounting;

public class ElementCompanyRelation {

	private Long elementCompanyRelationPK;
    
    private Long productStructureFK;
    
    private Long elementFK;
    
    private Element element;

    public void setElementCompanyRelationPK(Long elementCompanyRelationPK)
    {
        this.elementCompanyRelationPK = elementCompanyRelationPK;
    }

    public Long getElementCompanyRelationPK()
    {
        return elementCompanyRelationPK;
    }
    
    public void setElementFK(Long elementFK)
    {
        this.elementFK = elementFK;
    }

    public Long getElementFK()
    {
        return elementFK;
    }
    
    public void setProductStructureFK(Long productStructureFK)
    {
        this.productStructureFK = productStructureFK;
    }

    public Long getProductStructureFK()
    {
        return productStructureFK;
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

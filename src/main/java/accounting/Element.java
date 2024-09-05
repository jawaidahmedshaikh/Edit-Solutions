package accounting;

import java.util.Set;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

public class Element extends HibernateEntity
{
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;
    
    private Long elementPK;
        
    private String effectiveDate;
    
    private String sequenceNumber;
    
    private String process;
    
    private String event;
    
    private String eventType;

    private String elementName;

    private String operator;

    private String maintDateTime;
    
    private Set<ElementCompanyRelation> elementCompanyRelations;
    
    private Set<ElementStructure> elementStructures;
    
    public Element()
    {
    }

    public String getDatabase()
    {
        return DATABASE;
    }

    public void setElementPK(Long elementPK)
    {
        this.elementPK = elementPK;
    }

    public Long getElementPK()
    {
        return elementPK;
    }

    public void setEffectiveDate(String effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    public String getEffectiveDate()
    {
        return effectiveDate;
    }

    public void setSequenceNumber(String sequenceNumber)
    {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSequenceNumber()
    {
        return sequenceNumber;
    }

    public void setProcess(String process)
    {
        this.process = process;
    }

    public String getProcess()
    {
        return process;
    }

    public void setEvent(String event)
    {
        this.event = event;
    }

    public String getEvent()
    {
        return event;
    }
    
    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public String getEventType()
    {
        return eventType;
    }
    
    public void setElementName(String elementName)
    {
        this.elementName = elementName;
    }

    public String getElementName()
    {
        return elementName;
    }
    
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getOperator()
    {
        return operator;
    }
    
    public void setMaintDateTime(String maintDateTime)
    {
        this.maintDateTime = maintDateTime;
    }

    public String getMaintDateTime()
    {
        return maintDateTime;
    }
    
    public void setElementCompanyRelations(Set<ElementCompanyRelation> elementCompanyRelations)
    {
        this.elementCompanyRelations = elementCompanyRelations;
    }

    public Set<ElementCompanyRelation> getElementCompanyRelations()
    {
        return elementCompanyRelations;
    }
    
    public void setElementStructures(Set<ElementStructure> elementStructures)
    {
        this.elementStructures = elementStructures;
    }

    public Set<ElementStructure> getElementStructures()
    {
        return elementStructures;
    }
}

package engine.sp;

import edit.common.EDITDate;

import edit.common.EDITDateTime;

import edit.common.EDITMap;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import fission.utility.XMLUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;

/**
 * Represents a XML document that was originally represented by 
 * a PRASEDocBuilder. PRASEDocuments are really just XML documents, but
 * contain additional information such as a user-supplied description.
 * 
 * PRASEDocument(s) will participate in PRASETests as the input documents
 * for the test.
 */
public class PRASEDocument extends HibernateEntity
{
    /**
     * PK.
     */
    private Long pRASEDocumentPK;
    
    /**
     * A name that represents the original PRASEDocBuilder used to 
     * represent this PRASEDocument within prase. Valid values would be
     * 'SegmentDocument' representing the SegmentDocument.java builder, or
     * 'ClientDocument' representing the ClientDocument.java builder.
     */
    private String rootElementName;
    
    /**
     * A free form description.
     */
    private String description;
    
    /**
     * The content of the document as XML.
     */
    private String documentContentXML;
    
    /**
     * The date/time this document was created.
     */
    private EDITDateTime creationDateTime;
    
    /**
     * The formatted String representing the original Driver 
     * responsible for created this Document (if any).
     * 
     * Format:
     * 
     * Effective Date, Process, Event, Event Type.
     */
    private String creationDriverKey;
    
    /**
     * The set of PRASETests associated to thie PRASEDocument mapped
     * via PRASETestDocument.
     */
    private Set<PRASETestDocument> pRASETestDocuments;
    
    /**
     * The building parameters originally used when building this PRASEDocument.
     */
    private String buildingParameterXML;
    
    public PRASEDocument()
    {
        setCreationDateTime(new EDITDateTime());
    }

    /**
     * @see PRASEDocument#pRASEDocumentPK
     * @param newpraseDocumentPK
     */
    public void setPRASEDocumentPK(Long newpraseDocumentPK)
    {
        this.pRASEDocumentPK = newpraseDocumentPK;
    }

    /**
     * @see PRASEDocument#pRASEDocumentPK
     * @return
     */
    public Long getPRASEDocumentPK()
    {
        return pRASEDocumentPK;
    }

    /**
     * @see PRASEDocument#rootElementName
     * @param newdocumentType
     */
    public void setRootElementName(String newdocumentType)
    {
        this.rootElementName = newdocumentType;
    }

    /**
     * @see PRASEDocument#rootElementName
     * @return
     */
    public String getRootElementName()
    {
        return rootElementName;
    }

    /**
     * @see #description
     * @param newdescription
     */
    public void setDescription(String newdescription)
    {
        this.description = newdescription;
    }

    /**
     * @see #description
     * @return
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @see PRASEDocument#documentContentXML
     * @param newdocumentContent
     */
    public void setDocumentContentXML(String newdocumentContent)
    {
        this.documentContentXML = newdocumentContent;
    }

    /**
     * @see PRASEDocument#documentContentXML
     * @return
     */
    public String getDocumentContentXML()
    {
        return documentContentXML;
    }
    
    /**
     * Gets the DocumentContentXML as a DOM4J Document.
     * @return
     */
    public Document getDocument()
    {
        Document document = null;

        try
        {
            document = XMLUtil.parse(getDocumentContentXML());
        }
        catch (DocumentException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);
        }
        return document;
    }

    /**
     * @seee #pRASETestDocuments
     * @param newpRASETestDocuments
     */
    public void setPRASETestDocuments(Set<PRASETestDocument> newpRASETestDocuments)
    {
        this.pRASETestDocuments = newpRASETestDocuments;
    }

    /**
     * @seee #pRASETestDocuments
     */
    public Set<PRASETestDocument> getPRASETestDocuments()
    {
        return pRASETestDocuments;
    }
    
    /**
     * Finder.
     * @return
     */
    public static PRASEDocument[] findAll()
    {
        String hql = "from PRASEDocument";
        
        List<PRASEDocument> results = SessionHelper.executeHQL(hql, null, SessionHelper.ENGINE);
        
        return results.toArray(new PRASEDocument[results.size()]);
    }

    @Override
    public String getDatabase()
    {
        return SessionHelper.ENGINE;
    }

    /**
     * @see #creationDate
     * @param newcreationDate
     */
    public void setCreationDateTime(EDITDateTime newcreationDateTime)
    {
        this.creationDateTime = newcreationDateTime;
    }

    /**
     * @see #creationDate
     * @return
     */
    public EDITDateTime getCreationDateTime()
    {
        return creationDateTime;
    }

    /**
     * @see PRASEDocument#creationDriverKey
     * @param newdriverkey
     */
    public void setCreationDriverKey(String newdriverkey)
    {
        this.creationDriverKey = newdriverkey;
    }

    /**
     * @see PRASEDocument#creationDriverKey
     * @return
     */
    public String getCreationDriverKey()
    {
        return creationDriverKey;
    }
    
    /**
     * Finder. Finds all PRASEDocuments not associated with the specified PRASETestPK.
     * @param praseTestPK
     * @return
     */
    public static PRASEDocument[] findBy_PRASETestPK_Not_Associated(Long praseTestPK)
    {
        String hql = " from PRASEDocument praseDocument" +
                     " where praseDocument.PRASEDocumentPK not in" +
                     " (" +
                     " select praseTestDocument.PRASEDocumentFK" +
                     " from PRASETestDocument praseTestDocument" +
                     " where praseTestDocument.PRASETestFK = :praseTestPK" +
                     " )";
        
        Map params = new EDITMap("praseTestPK", praseTestPK);
        
        List<PRASEDocument> results = SessionHelper.executeHQL(hql, params, SessionHelper.ENGINE);
        
        return results.toArray(new PRASEDocument[results.size()]);
    }

    /**
     * @see #buildingParameterXML
     * @param newbuildingParameterXML
     */
    public void setBuildingParameterXML(String newbuildingParameterXML)
    {
        this.buildingParameterXML = newbuildingParameterXML;
    }

    /**
     * @see #buildingParameterXML
     * @return
     */
    public String getBuildingParameterXML()
    {
        return buildingParameterXML;
    }
    
    /**
     * Converts the existing building parameters stored as
     * an XML Document into their equivalent Map of name/value pairs.
     * The root element of the original XML Document is ignored.
     * 
     * e.g.
     * 
     * <BuildingParameterXML>
     *   <Name1>Value1</Name1>
     *   <Name2>Value2</Name2>
     * </BuildingParameterXML> 
     * 
     * Equates to:
     * 
     * Map map = new HashMap();
     * 
     * map.put(Name1, Value1);
     * map.put(Name2, Value2);.
     * 
     * @return the Map equivalent of the current BuildingParameterXML
     */
    public Map<String, String> getBuilderingParameters()
    {
        Map<String, String> buildingParametersMap = new HashMap<String, String>();
        
        Document buildingParametersXMLDocument = null;

        try
        {
            buildingParametersXMLDocument = XMLUtil.parse(getBuildingParameterXML());

            List<Element> buildingParameterElements = buildingParametersXMLDocument.getRootElement().elements();

            for (Element buildingParameterElement: buildingParameterElements)
            {
                String buildingParameterName = buildingParameterElement.getName();

                String buildingParameterValue = buildingParameterElement.getText();

                buildingParametersMap.put(buildingParameterName, buildingParameterValue);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            throw new RuntimeException(e);
        }
        
        return buildingParametersMap;
    }
    
    /**
     * It is possible that the DocumentContentXML has been set with
     * the formatting characters (/t /n etc) still in place. This
     * removes all such characters.
     */
    public void normalizeDocumentContentXML() throws DocumentException
    {
        Document normalizedDocument = XMLUtil.parse(getDocumentContentXML());
        
        String normalizedXML = normalizedDocument.getRootElement().asXML();
        
        setDocumentContentXML(normalizedXML);
    }
    
    /**
     * It is possible that the BuildingParameterXML has been set with
     * the formatting characters (/t /n etc) still in place. This
     * removes all such characters.
     */
    public void normalizeBuildingParameterXML() throws DocumentException
    {
        Document normalizedDocument = XMLUtil.parse(getBuildingParameterXML());
        
        String normalizedXML = normalizedDocument.getRootElement().asXML();
        
        setBuildingParameterXML(normalizedXML);
    }    
}

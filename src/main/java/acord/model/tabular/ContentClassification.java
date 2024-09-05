/*
 * User: gfrosti
 * Date: Jun 8, 2006
 * Time: 9:14:00 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package acord.model.tabular;

import org.dom4j.*;
import org.dom4j.tree.*;
import fission.utility.*;

/**
 * Contains information about the content of the table data including name, supplier, and identification.
 *
 * @author gfrosti
 */
public class ContentClassification
{
    private Element element = new DefaultElement("ContentClassification");

    Element comments                 = new DefaultElement("Comments");
    Element complexContentInd        = new DefaultElement("ComplexContentInd");
    Element contentType              = new DefaultElement("ContentType");                   // Required
    Element contentClassificationKey = new DefaultElement("ContentClassificationKey");
    Element contentSubType           = new DefaultElement("ContentSubType");
    Element providerName             = new DefaultElement("ProviderName");
    Element tableDescription         = new DefaultElement("TableDescription");
    Element tableName                = new DefaultElement("TableName");                     // Required
    Element tableReference           = new DefaultElement("TableReference");



    public ContentClassification(String tableName, int contentType)
    {
        this.setTableName(tableName);
        this.setContentType(contentType);
    }

    /**
     * Returns the XML element for this object
     * @return
     */
    public Element getElement()
    {
        return this.element;
    }

    /**
     * Returns the miscellaneous information regarding the information that is within the file itself.
     * @return
     */
    public String getComments()
    {
        return this.comments.getText();
    }

    /**
     * Sets the miscellaneous information regarding the information that is within the file itself.
     * @param comments
     */
    public void setComments(String comments)
    {
        if (this.comments.content() != null)
        {
            this.comments.clearContent();
        }

        this.element.add(this.comments);

        this.comments.add(new DefaultText(comments));
    }

    /**
     * Returns the Complex Content Indicator which is used to indicate that the physical representation of the
     * content is heterogeneous data.
     * <P>
     * ACORD NOTE: On ContentClassification, ComplexContentInd may only be set to true when all of the keys and axes
     * are the same for all table values. There must be at least two correlated table values. It must only be used
     * when the table values fall within the same ContentType. The correlated values have related business usage. This
     * should only be used when the relationship of the data needs to be provided and there is no alternative method.
     * Default value false.
     *
     * @return
     */
    public int getComplexContentInd()
    {
        return Integer.parseInt(this.complexContentInd.getText());
    }

    /**
     * Sets the Complex Content Indicator which is used to indicate that the physical representation of the
     * content is heterogeneous data.
     * <P>
     * ACORD NOTE: On ContentClassification, ComplexContentInd may only be set to true when all of the keys and axes
     * are the same for all table values. There must be at least two correlated table values. It must only be used
     * when the table values fall within the same ContentType. The correlated values have related business usage. This
     * should only be used when the relationship of the data needs to be provided and there is no alternative method.
     * Default value false.
     *
     * @param complexContentInd
     */
    public void setComplexContentInd(int complexContentInd)
    {
        if (this.complexContentInd.content() != null)
        {
            this.complexContentInd.clearContent();
        }

        this.element.add(this.complexContentInd);

        String text = Util.getResourceMessage("oliBoolean." + complexContentInd);

        this.complexContentInd.add(new DefaultText(text));
        this.complexContentInd.addAttribute("tc", complexContentInd + "");
    }

    /**
     * Returns the constant that identifies the type of information in the table.
     * @return
     */
    public int getContentType()
    {
        return Integer.parseInt(this.contentType.getText());
    }

    /**
     * Sets the constant that identifies the type of information in the table.
     * @param contentType
     */
    public void setContentType(int contentType)
    {
        if (this.contentType.content() != null)
        {
            this.contentType.clearContent();
        }

        this.element.add(this.contentType);

        String text = Util.getResourceMessage("contentType." + contentType);

        this.contentType.add(new DefaultText(text));
        this.contentType.addAttribute("tc", contentType + "");
    }

    /**
     * Returns the Content Classification Key.  This parameter is not defined in the ACORD
     * @return
     */
    public Element getContentClassificationKey()
    {
        return this.contentClassificationKey;
    }

    /**
     * Sets the Content Classification Key.  This parameter is not defined in the ACORD
     * @param contentClassificationKey
     */
    public void setContentClassificationKey(String contentClassificationKey)
    {
        if (this.contentClassificationKey.content() != null)
        {
            this.contentClassificationKey.clearContent();
        }

        this.element.add(this.contentClassificationKey);

        this.contentClassificationKey.add(new DefaultText(contentClassificationKey));
    }

    /**
     * Returns the content sub type which further identifies the type of value information in the table. ContentType
     * provides the general classification.
     *
     * @return
     */
    public int getContentSubType()
    {
        return Integer.parseInt(this.contentSubType.getText());
    }

    /**
     * Sets the content sub type which further identifies the type of value information in the table. ContentType
     * provides the general classification.
     *
     * @param contentSubType
     */
    public void setContentSubType(int contentSubType)
    {
        if (this.contentSubType.content() != null)
        {
            this.contentSubType.clearContent();
        }

        this.element.add(this.contentSubType);

        String text = Util.getResourceMessage("contentSubType." + contentSubType);

        this.contentSubType.add(new DefaultText(text));
        this.contentSubType.addAttribute("tc", contentSubType + "");
    }

    /**
     * Returns the name of the provider of the table.
     * @return
     */
    public String getProviderName()
    {
        return this.providerName.getText();
    }

    /**
     * Sets the name of the provider of the table.
     * @param providerName
     */
    public void setProviderName(String providerName)
    {
        if (this.providerName.content() != null)
        {
            this.providerName.clearContent();
        }

        this.element.add(this.providerName);

        this.providerName.add(new DefaultText(providerName));
    }

    /**
     * Returns the freeform description of the contents of the table or sub table being included.
     * @return
     */
    public String getTableDescription()
    {
        return this.tableDescription.getText();
    }

    /**
     * Sets the freeform description of the contents of the table or sub table being included.
     * @param tableDescription
     */
    public void setTableDescription(String tableDescription)
    {
        if (this.tableDescription.content() != null)
        {
            this.tableDescription.clearContent();
        }

        this.element.add(this.tableDescription);

        this.tableDescription.add(new DefaultText(tableDescription));
    }

    /**
     * Returns the name as defined by the table supplier.
     * @return
     */
    public String getTableName()
    {
        return this.tableName.getText();
    }

    /**
     * Sets the name as defined by the table supplier.
     * @param tableName
     */
    public void setTableName(String tableName)
    {
        if (this.tableName.content() != null)
        {
            this.tableName.clearContent();
        }

        this.element.add(this.tableName);

        this.tableName.add(new DefaultText(tableName));
    }

    /**
     * Returns the reference to the published document which contains the table.
     * @return
     */
    public String getTableReference()
    {
        return this.tableReference.getText();
    }

    /**
     * Sets the reference to the published document which contains the table.
     * @param tableReference
     */
    public void setTableReference(String tableReference)
    {
        if (this.tableReference.content() != null)
        {
            this.tableReference.clearContent();
        }

        this.element.add(this.tableReference);

        this.tableReference.add(new DefaultText(tableReference));
    }
}
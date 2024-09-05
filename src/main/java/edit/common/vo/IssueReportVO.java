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
public class IssueReportVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Document to be fed into PRASE for the generation of Issue
     * Reports
     */
    private edit.common.vo.IssueDocumentVO _issueDocumentVO;

    /**
     * Field _fileName
     */
    private java.lang.String _fileName;


      //----------------/
     //- Constructors -/
    //----------------/

    public IssueReportVO() {
        super();
    } //-- edit.common.vo.IssueReportVO()


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
        
        if (obj instanceof IssueReportVO) {
        
            IssueReportVO temp = (IssueReportVO)obj;
            if (this._issueDocumentVO != null) {
                if (temp._issueDocumentVO == null) return false;
                else if (!(this._issueDocumentVO.equals(temp._issueDocumentVO))) 
                    return false;
            }
            else if (temp._issueDocumentVO != null)
                return false;
            if (this._fileName != null) {
                if (temp._fileName == null) return false;
                else if (!(this._fileName.equals(temp._fileName))) 
                    return false;
            }
            else if (temp._fileName != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFileNameReturns the value of field 'fileName'.
     * 
     * @return the value of field 'fileName'.
     */
    public java.lang.String getFileName()
    {
        return this._fileName;
    } //-- java.lang.String getFileName() 

    /**
     * Method getIssueDocumentVOReturns the value of field
     * 'issueDocumentVO'. The field 'issueDocumentVO' has the
     * following description: Document to be fed into PRASE for the
     * generation of Issue Reports
     * 
     * @return the value of field 'issueDocumentVO'.
     */
    public edit.common.vo.IssueDocumentVO getIssueDocumentVO()
    {
        return this._issueDocumentVO;
    } //-- edit.common.vo.IssueDocumentVO getIssueDocumentVO() 

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
     * Method setFileNameSets the value of field 'fileName'.
     * 
     * @param fileName the value of field 'fileName'.
     */
    public void setFileName(java.lang.String fileName)
    {
        this._fileName = fileName;
        
        super.setVoChanged(true);
    } //-- void setFileName(java.lang.String) 

    /**
     * Method setIssueDocumentVOSets the value of field
     * 'issueDocumentVO'. The field 'issueDocumentVO' has the
     * following description: Document to be fed into PRASE for the
     * generation of Issue Reports
     * 
     * @param issueDocumentVO the value of field 'issueDocumentVO'.
     */
    public void setIssueDocumentVO(edit.common.vo.IssueDocumentVO issueDocumentVO)
    {
        this._issueDocumentVO = issueDocumentVO;
    } //-- void setIssueDocumentVO(edit.common.vo.IssueDocumentVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.IssueReportVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.IssueReportVO) Unmarshaller.unmarshal(edit.common.vo.IssueReportVO.class, reader);
    } //-- edit.common.vo.IssueReportVO unmarshal(java.io.Reader) 

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

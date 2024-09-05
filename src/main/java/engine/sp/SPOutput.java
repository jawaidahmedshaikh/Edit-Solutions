/*
 * User: gfrosti
 * Date: Oct 12, 2004
 * Time: 10:34:33 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.sp;

import edit.common.vo.SPOutputVO;
import edit.common.vo.VOObject;
import edit.common.vo.ValidationVO;

import engine.sp.custom.document.PRASEDocBuilder;

import fission.utility.DOMUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;
import org.dom4j.tree.DefaultElement;


public class SPOutput
{
    private List<Element> calculationOutputs;
    private boolean processedWithErrors;
    private SPOutputVO spOutputVO;
    private List<Element> validationOutputs;
    
    /**
     * The set of documents built during the SP session.
     */
    private Map<String, Document> documents = new HashMap<String, Document>();
    

    public SPOutput()
    {
        this.calculationOutputs = new ArrayList<Element>();

        this.validationOutputs = new ArrayList<Element>();
    }

    /**
   * Convenience method to get the desired Document by DocumentType.
   * @param rootElementName
   * @return
   */
    public Document getDocument(String rootElementName)
    {
      return getDocuments().get(rootElementName);
    }

    /**
     * Getter.
     * @return
     */
    public Map<String, Document> getDocuments()
    {
        return documents;
    }

    /**
     * Setter.
     * @param documents
     */
    public void setDocuments(Map<String, Document> documents)
    {
        this.documents = documents;
    }

    /**
     * Adds the output of a calculation to the set of such outputs. Duplicate entries are prevented.
     * @param element
     */
    public void addCalculationOutput(Element element)
    {
        if (!calculationOutputs.contains(element))
        {
            calculationOutputs.add(element);
        }
    }

    /**
     * Adds the output of a validation to the set of such outputs. Duplicate entries are prevented.
     * @param element
     */
    public void addValidationOutput(Element element)
    {
        if (!validationOutputs.contains(element))
        {
            validationOutputs.add(element);
        }
    }

    /**
     * Empties all current outputs.
     */
    public void clear()
    {
        this.calculationOutputs.clear();

        this.validationOutputs.clear();

        this.processedWithErrors = false;
    }

    /**
     * Returns the VO representation of this entity.
     * @return
     */
    public SPOutputVO getSPOutputVO()
    {
        if (spOutputVO == null)
        {
            spOutputVO = new SPOutputVO();
        }

        List calculationVOs = mapElementsToVOs(calculationOutputs);

        List validationVOs = mapElementsToVOs(validationOutputs);

        if (calculationVOs != null)
        {
            spOutputVO.setVOObject((VOObject[]) calculationVOs.toArray(new VOObject[calculationVOs.size()]));
        }

        if (validationVOs != null)
        {
            spOutputVO.setValidationVO((ValidationVO[]) validationVOs.toArray(new ValidationVO[validationVOs.size()]));
        }

        return spOutputVO;
    }
    
    /**
     * Normally, our VOs can readily convert themselves to XML. In the case of SPOutputVO, however,
     * the XML Schema uses "polymorphism" in that the backing schema was created using
     * a "VOObject" Element since the outputs are not known at runtime, yet every VO
     * inherits from VOObject. 
     * 
     * This "polymorphism" creates some funky XML. The XML is fine for Castor, but
     * not for downstream systems that can't make sense of Castor's mapping.
     * 
     * This is convenience method to manually build the SPOutout as XML so that
     * is is more human/system friendly.
     * 
     * WARNING: This Document is built by disassociating the existing calculator
     * and validation output Elements from their current Document.
     * 
     * @return
     */
    public Document getSPOutputVODocument()
    {
        Document spOutputVODocument = new DefaultDocument();  
        
        Element spOutputVORootElement = new DefaultElement("SPOutputVO");
        
        spOutputVODocument.setRootElement(spOutputVORootElement);
        
        for (Element calculationOutput: getCalculationOutputs())
        {
            spOutputVORootElement.add(DOMUtil.stripFromDocument(calculationOutput, true));            
        }
        
        for (Element validationOutput:getValidationOutputs())
        {
            spOutputVORootElement.add(DOMUtil.stripFromDocument(validationOutput, true));
        }
        
        Element processedWithErrorsElement = new DefaultElement("ProcessedWithErrors");
        
        processedWithErrorsElement.setText(Boolean.toString(processedWithErrors));
        
        spOutputVORootElement.add(processedWithErrorsElement);
        
        return spOutputVODocument;
    }
    
    /**
     * The list of ValidationVOs, or an empty array if there aren't any.
     * @return
     */
    public List<ValidationVO> getValidationVOs() 
    {
        return mapElementsToVOs(validationOutputs);    
    }

    /**
     * Getter.
     * @return
     */
    public boolean getProcessedWithErrors()
    {
        return processedWithErrors;
    }

    /**
     * Setter.
     * @param processedWithErrors
     */
    public void setProcessedWithErrors(boolean processedWithErrors)
    {
        this.processedWithErrors = processedWithErrors;
    }

    /**
     * Maps the set of DOM elements to their corresponding VO representation.
     * @param elements
     * @return
     */
    private List<ValidationVO> mapElementsToVOs(List elements)
    {
        List outputAsVOs = null;

        outputAsVOs = new ArrayList();

        for (int i = 0; i < elements.size(); i++)
        {
            Element currentElement = (Element) elements.get(i);

            VOObject currentVOObject = DOMUtil.mapElementToVO(currentElement);

            outputAsVOs.add(currentVOObject);
        }

        return outputAsVOs;
    }

    /**
     * True if there are Calculation Outputs.
     * @return
     */
    public boolean hasCalculationOutputs()
    {
        return !calculationOutputs.isEmpty();
    }

    /**
     * True if there are Validation Outputs.
     * @return
     */
    public boolean hasValidationOutputs()
    {
        return !validationOutputs.isEmpty();
    }
    
    /**
     * The list of calculation outputs as Elements, if any.
     * Typically these are the DOM4J version of a VO, but this
     * is not always the case.
     * @return
     */
    public List<Element> getCalculationOutputs()
    {
        return calculationOutputs;
    }
    
    /**
     * Finds the list of ValidationVOs of type "H" (hard) if any.
     */
    public List<ValidationVO> getHardEdits()
    {
      List<ValidationVO> hardEdits = new ArrayList<ValidationVO>();
      
      if (hasValidationOutputs())
      {
        ValidationVO[] validationVOs = getSPOutputVO().getValidationVO();
        
        for (ValidationVO validationVO:validationVOs)
        {
          if (validationVO.getSeverity().equals(ValidateInst.SEVERITY_HARD))
          {
            hardEdits.add(validationVO);
          }
        }
      }
      
      return hardEdits;
    }   
    
    /**
     * Finds the list of ValidationVOs of type "W" (hard) if any.
     */
    public List<ValidationVO> getWarningEdits()
    {
      List<ValidationVO> warningEdits = new ArrayList<ValidationVO>();
      
      if (hasValidationOutputs())
      {
        ValidationVO[] validationVOs = getSPOutputVO().getValidationVO();
        
        for (ValidationVO validationVO:validationVOs)
        {
          if (validationVO.getSeverity().equals(ValidateInst.SEVERITY_WARNING))
          {
            warningEdits.add(validationVO);
          }
        }
      }
      
      return warningEdits;
    }       
    
    /**
     * True if within the set of Validations, there is
     * at least one hard edit.
     * @return
     */
    public boolean hasHardEdits()
    {
        return !getHardEdits().isEmpty();        
    }
    
    /**
     * True if within the set of Validations, there is
     * at least one warning edit.
     * @return
     */    
    public boolean hasWarningEdits()
    {
        return !getWarningEdits().isEmpty();        
    }

    /**
     * @see #validationOutputs
     * @return
     */
    public List<Element> getValidationOutputs()
    {
        return validationOutputs;
    }
}

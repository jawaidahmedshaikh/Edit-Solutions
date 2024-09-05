package edit.common.exceptions;

import edit.common.vo.ValidationVO;

import fission.utility.Util;

import java.util.List;

import org.hibernate.HibernateException;

/**
 * Thrown due to the Non-Financial (NF) framework.
 * Occures when the NF Framework has notified PRASE of field-level changes
 * that PRASE objects to (via the NF Validation Scripts).
 * 
 * The message within the exception is captured as an XML document in the form of:
 * 
 * <NonFinancialValidationDocVO>
 *      <ValidationVO></ValidationVO> // repeated for every one generate by PRASE
 * </NonFinancialValidationDocVO>
 * 
 * NOTE: Extending Error is unusual since this exception does not represent some fatal error.
 * The dilemna is that there are too many try/catch(Exception) to compete with threatening
 * to eat this exception.
 */
public class EDITNonFinancialException extends Error
{
    public EDITNonFinancialException(List<ValidationVO> hardEdits)
    {
        super(mapToXML(hardEdits));
    }

    /**
     * Maps the specified ValidationVOs to an XML equivalent placed
     * with the root document of <NonFinancialValidationDocVO/>
     * @param hardEdits
     * @return
     */
    private static String mapToXML(List<ValidationVO> hardEdits) 
    {
        String nonFinancialValidationDocVO = "<NonFinancialValidationDocVO>";

        for (ValidationVO validationVO: hardEdits) 
        {
            nonFinancialValidationDocVO += Util.marshalVO(validationVO);    
        }
        
        nonFinancialValidationDocVO += "</NonFinancialValidationDocVO>";
        
        return nonFinancialValidationDocVO;
    }
}

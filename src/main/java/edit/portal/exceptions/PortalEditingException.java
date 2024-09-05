package edit.portal.exceptions;

import edit.common.exceptions.EDITException;
import edit.common.vo.EditErrorVO;
import edit.common.vo.ValidationVO;


public class PortalEditingException extends EDITException {

    private EditErrorVO[]   editErrorVOs;
    private String          returnPage;
    private String          transaction;
    private String          action;

    private ValidationVO[] validationVOs;

    public PortalEditingException()
    {

    }

    public PortalEditingException(String message)
    {
        super(message);
    }

    public void setEditErrorVOs(EditErrorVO[] editErrorVOs)
    {

        this.editErrorVOs = editErrorVOs;
    }

    public EditErrorVO[] getEditErrorVOs()
    {

        return editErrorVOs;
    }

    public void setValidationVOs(ValidationVO[] validationVOs)
    {

        this.validationVOs = validationVOs;
    }

    public ValidationVO[] getValidationVOs()
    {

        return validationVOs;
    }

    public void setReturnPage(String returnPage)
    {

        this.returnPage = returnPage;
    }

    public String getReturnPage()
    {

        return returnPage;
    }

    public void setTransaction(String transaction)
    {

        this.transaction = transaction;
    }

    public String getTransaction()
    {

        return transaction;
    }

    public void setAction(String action)
    {

        this.action = action;
    }

    public String getAction()
    {

        return action;
    }
}

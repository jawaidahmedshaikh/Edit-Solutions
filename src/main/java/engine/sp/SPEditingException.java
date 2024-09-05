package engine.sp;

import edit.common.vo.EditErrorVO;


public class SPEditingException extends Exception {

    private EditErrorVO[]   editErrorVOs;
    private String          returnPage;
    private String          transaction;
    private String          action;
    private String          message;

    public SPEditingException(){}

    public SPEditingException(String message)
    {
        this.message = message;
    }

    public void setEditErrorVOs(EditErrorVO[] editErrorVOs){

        this.editErrorVOs = editErrorVOs;
    }

    public EditErrorVO[] getEditErrorVOs(){

        return editErrorVOs;
    }
}

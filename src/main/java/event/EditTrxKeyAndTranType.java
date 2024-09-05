/*
 * Copyright (c) 2004 Your Corporation. All Rights Reserved.
 */
package event;
public class EditTrxKeyAndTranType
{
    long  editTrxPK;
    String tranType;

    public EditTrxKeyAndTranType(long editTrxPk, String tranType)
    {
        this.editTrxPK  = editTrxPk;
        this.tranType   = tranType;
    }

    public long getEditTrxPK()
    {
        return editTrxPK;
    }
    
    public String getTranType()
    {
        return tranType;
    }
}

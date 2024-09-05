package accounting;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

public class Account extends HibernateEntity
{
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;
    
    private Long accountPK;
    
    private Long elementStructureFK;
    
    private String accountNumber;
    
    private String accountName;
    
    private String effect;
    
    private String accountDescription;
    
    public Account()
    {
    }

    public String getDatabase()
    {
        return DATABASE;
    }

    public void setAccountPK(Long accountPK)
    {
        this.accountPK = accountPK;
    }

    public Long getAccountPK()
    {
        return accountPK;
    }

    public void setElementStructureFK(Long elementStructureFK)
    {
        this.elementStructureFK = elementStructureFK;
    }

    public Long getElementStructureFK()
    {
        return elementStructureFK;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setEffect(String effect)
    {
        this.effect = effect;
    }

    public String getEffect()
    {
        return effect;
    }

    public void setAccountDescription(String accountDescription)
    {
        this.accountDescription = accountDescription;
    }

    public String getAccountDescription()
    {
        return accountDescription;
    }
}

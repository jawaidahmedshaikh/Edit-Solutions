package accounting;

import java.util.List;
import java.util.Objects;

import edit.common.EDITMap;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

@SuppressWarnings("serial")
public class JournalAdjustmentLookup extends HibernateEntity
{
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;
    
    private String companyName;
    
    private String accountNumber;
    
    private String accountName;
    
    private String agentCodeRequiredInd;
    
    private String stateCodeRequiredInd;
    
	public JournalAdjustmentLookup()
    {
    }

    public String getDatabase()
    {
        return DATABASE;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
    public String getAgentCodeRequiredInd() 
    {
		return agentCodeRequiredInd;
	}

	public void setAgentCodeRequiredInd(String agentCodeRequiredInd) 
	{
		this.agentCodeRequiredInd = agentCodeRequiredInd;
	}

	public String getStateCodeRequiredInd() 
	{
		return stateCodeRequiredInd;
	}

	public void setStateCodeRequiredInd(String stateCodeRequiredInd) 
	{
		this.stateCodeRequiredInd = stateCodeRequiredInd;
	}
	
    /**
     * Finds all JournalAdjustmentLookup's
     * @return 
     */
    public static JournalAdjustmentLookup[] findAll()
    {
        String hql = " from JournalAdjustmentLookup journalAdjustmentLookup";

        EDITMap params = new EDITMap();

        List<JournalAdjustmentLookup> results = SessionHelper.executeHQL(hql, params, JournalAdjustmentLookup.DATABASE);

        return results.toArray(new JournalAdjustmentLookup[results.size()]);
    }
    
    /**
     * Required for hibernate composite ID's
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ( !(obj instanceof JournalAdjustmentLookup) ) return false;

        final JournalAdjustmentLookup otherJal = (JournalAdjustmentLookup) obj;

        if ( !otherJal.getAccountNumber().equals( getAccountNumber() ) ) return false;
        if ( !otherJal.getCompanyName().equals( getCompanyName() ) ) return false;

        return true;
    }

    /**
     * Required for hibernate composite ID's
     */
    @Override
    public int hashCode() {
    	return Objects.hash(getAccountNumber(), getCompanyName());
    }
}
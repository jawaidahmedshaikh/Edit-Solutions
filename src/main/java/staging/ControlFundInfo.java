/*
 * User: sprasad
 * Date: Apr 17, 2008
 * Time: 11:34:12 AM
 * 
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */

package staging;

import edit.common.EDITBigDecimal;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

public class ControlFundInfo extends HibernateEntity
{
    private Long controlFundInfoPK;
    private Long stagingFK;
    private String fundNumber;
    private EDITBigDecimal  totalCurrentAccountValue;

    private Staging staging;
    
    public static final String DATABASE = SessionHelper.STAGING;
    
    /**
     * Setter.
     * @param controlFundInfoPK
     */
    public void setControlFundInfoPK(Long controlFundInfoPK)
    {
        this.controlFundInfoPK = controlFundInfoPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getControlFundInfoPK()
    {
        return controlFundInfoPK;
    }

    /**
     * Setter.
     * @param stagingFK
     */
    public void setStagingFK(Long stagingFK)
    {
        this.stagingFK = stagingFK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getStagingFK()
    {
        return stagingFK;
    }

    /**
     * Setter.
     * @param fundNumber
     */
    public void setFundNumber(String fundNumber)
    {
        this.fundNumber = fundNumber;
    }

    /**
     * Getter.
     * @return
     */
    public String getFundNumber()
    {
        return fundNumber;
    }

    /**
     * Setter.
     * @param totalCurrentAccountValue
     */
    public void setTotalCurrentAccountValue(EDITBigDecimal totalCurrentAccountValue)
    {
        this.totalCurrentAccountValue = totalCurrentAccountValue;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getTotalCurrentAccountValue()
    {
        return totalCurrentAccountValue;
    }
    
    /**
     * Setter.
     * @param staging
     */
    public void setStaging(Staging staging)
    {
        this.staging = staging;
    }

    /**
     * Getter.
     * @return
     */
    public Staging getStaging()
    {
        return staging;
    }

    /**
     * Getter.
     * @return
     */
    public String getDatabase()
    {
        return this.DATABASE;
    }
}

/*
 * User: dlataille
 * Date: Mar 14, 2008
 * Time: 10:26:59 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package staging;

import edit.common.*;
import edit.services.db.hibernate.*;

public class ErrorLog extends HibernateEntity
{
    private Long errorLogPK;
    private Long segmentBaseFK;
    private EDITDateTime creationDateTime;
    private String severity;
    private String logMessage;

    private SegmentBase segmentBase;

    public static final String DATABASE = SessionHelper.STAGING;

    public ErrorLog()
    {                                                              
    }

    /**
     * Getter.
     * @return
     */
    public Long getErrorLogPK()
    {
        return errorLogPK;
    }

    /**
     * Setter.
     * @param errorLogPK
     */
    public void setErrorLogPK(Long errorLogPK)
    {
        this.errorLogPK = errorLogPK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getSegmentBaseFK()
    {
        return segmentBaseFK;
    }

    /**
     * Setter.
     * @param segmentBaseFK
     */
    public void setSegmentBaseFK(Long segmentBaseFK)
    {
        this.segmentBaseFK = segmentBaseFK;
    }

    public SegmentBase getSegmentBase()
    {
        return segmentBase;
    }

    public void setSegmentBase(SegmentBase segmentBase)
    {
        this.segmentBase = segmentBase;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDateTime getCreationDateTime()
    {
        return creationDateTime;
    }

    /**
     * Setter.
     * @param creationDateTime
     */
    public void setCreationDateTime(EDITDateTime creationDateTime)
    {
        this.creationDateTime = creationDateTime;
    }

    /**
     * Getter.
     * @return
     */
    public String getSeverity()
    {
        return severity;
    }

    /**
     * Setter.
     * @param severity
     */
    public void setSeverity(String severity)
    {
        this.severity = severity;
    }

    /**
     * Getter.
     * @return
     */
    public String getLogMessage()
    {
        return logMessage;
    }

    /**
     * Setter.
     * @param logMessage
     */
    public void setLogMessage(String logMessage)
    {
        this.logMessage = logMessage;
    }

    public String getDatabase()
    {
        return ErrorLog.DATABASE;
    }
}

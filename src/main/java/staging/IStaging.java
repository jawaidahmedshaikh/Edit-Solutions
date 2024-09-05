/**
 * User: dlataill
 * Date: Oct 3, 2007
 * Time: 8:38:54 AM
 * <p/>
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package staging;

public interface IStaging
{
    public StagingContext stage(StagingContext stagingContext, String database);
}

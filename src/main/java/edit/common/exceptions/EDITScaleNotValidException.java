package edit.common.exceptions;

/*
 * User: sramamurthy
 * Date: Sep 22, 2004
 * Time: 3:06:05 PM
 *
 * 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

public class EDITScaleNotValidException extends EDITRuntimeException{
    /**
     *
     * @param msg
     */
    public  EDITScaleNotValidException(String msg){
        super(msg);
    }
}
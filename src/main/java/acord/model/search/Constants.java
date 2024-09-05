/*
 * User: sdorman
 * Date: Aug 29, 2006
 * Time: 9:21:28 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package acord.model.search;

public interface Constants
{
    /**
     * Maintains the set of constants for the required CriteriaOperator for CriteriaExpression
     * @author sdorman
     */
    public interface CriteriaOperator
    {
        // "XOR" (exclusive or) logical operator
        public int LOGICAL_OPERATOR_XOR = 4;

        // AND - Logical And
        public int LOGICAL_OPERATOR_AND = 2;

        // NOT - Logical Not
        public int LOGICAL_OPERATOR_NOT = 3;

        // OR Logical OR
        public int LOGICAL_OPERATOR_OR = 1;
    }
}

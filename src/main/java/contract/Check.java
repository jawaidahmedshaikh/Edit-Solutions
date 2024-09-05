/*
 * User: gfrosti
 * Date: Apr 6, 2005
 * Time: 9:51:49 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import edit.common.*;

import agent.Agent;

import role.ClientRoleFinancial;

import client.Preference;

import event.financial.client.strategy.*;


public class Check
{
    /**
     * Generates the next check date according the rules of ClientRoleFinancial and its Commission.
     * @param clientRoleFinancial
     * @return
     */
    public EDITDate generateNextCheckDate(ClientRoleFinancial clientRoleFinancial)
    {
        EDITDate nextCheckDate = null;

        EDITDateTime lastCheckDateTime = clientRoleFinancial.getLastCheckDateTime();

        EDITDate lastCheckDate = null;

        if (lastCheckDateTime != null)
        {
            lastCheckDate = lastCheckDateTime.getEDITDate();
        }

        Preference preference = Preference.findByClientRoleFinancialPK_OverrideStatus(clientRoleFinancial.getPK(), Preference.PRIMARY);

        Agent agent = Agent.findByClientRoleFinancialPK(clientRoleFinancial.getPK());

        if (preference.getPaymentModeCT().equals(Preference.AUTO))
        {
            nextCheckDate = agent.getHireDate();
        }
        else if (lastCheckDate == null)
        {
            nextCheckDate = addMode(agent.getHireDate(), preference.getPaymentModeCT());
        }
        else
        {
            nextCheckDate = addMode(lastCheckDate, preference.getPaymentModeCT());
        }

        return nextCheckDate;
    }

    /**
     * Adds the specified mode to the specified date to create a new EDITDate instance.
     * @param date
     * @param mode
     * @return
     */
    private EDITDate addMode(EDITDate date, String mode)
    {
        // The date logic need to be extracted to a more general class. Make do for now.
        EDITDate newDate = new Reschedule().getNextModalDate(date, mode, "N", "FooTrx");

        return newDate;
    }
}

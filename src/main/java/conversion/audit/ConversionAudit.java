/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package conversion.audit;

/**
 *
 * @author sprasad
 */
public abstract class ConversionAudit
{
    protected static final String DEMARCATOR = "|";

    protected abstract void generate() throws Exception;
}

package engine.sp.custom.entity;

import acord.model.Constants;
import edit.services.db.hibernate.HibernateEntity;

import edit.services.db.hibernate.SessionHelper;
import edit.common.EDITDate;
import edit.common.EDITDateTime;

import engine.sp.InstOneOperand;
import engine.sp.SPException;
import engine.sp.ScriptProcessor;

import fission.utility.Util;

import java.lang.reflect.Method;
import java.util.List;

import org.dom4j.Element;

import org.dom4j.tree.DefaultElement;


/**
 * Event [after] the scripter has created a HibernateEntity from an existing
 * Element in the DOM, the scripter may make further updates to the
 * Element. These changes will need to be mapped to the HibernateEntity as
 * an update to it.
 * 
 * The syntax is as follows:
 * 
 * Activateentity(Update, a.b.c.FooVO)
 * 
 * In the above case, the HibernateEntity corresponding to the
 * last active a.b.c.Foo will be located, and then the Foo.Values will
 * be moved over to the HibernateEntity.values.
 * 
 * Is is also possible that the scripter wishes to map a subset of the of 
 * the FooVO's values to the backing Hibernate entity. This is likely when
 * the scripter is working with a number of Elements whose values must
 * be mapped to the backing Hibernate. In such a case there are three scenarios:
 * 
 * 1. 
 * Activateentity(Update, a.b.c.FooVO, {VarA, VarB, VarC});
 * - This maps a subset of values from a.b.c.FooVO to the backing HibernateEntity Foo.
 * 
 * 2. 
 * Activateentity(Update, a.b.c.FooVO, x.y.z.RecordVO);
 * - This maps all of the values of x.y.z.RecordVO to the backing HibernateEntity Foo.
 * 
 * 3.
 * Activateentity(Update, a.b.c.FooVO, x.y.z.RecordVO, {VarA, VarB, VarC});
 * - This maps a subset of the values from x.y.z.RecordVO to the backing HibernateEntity Foo.
 * 
 * Note: In all cases, the names must match across all VOs/Entities involved. This is not
 * unreasonable in that the scripter should have total control of the naming process.
 */
public class UpdateEntity extends ActivateEntityCommand
{

    /**
     * As of this writing, there are four possible cases for using
     * this instruction. 
     * 
     * FULL_MAPPING_ONE_VO      - would represent Activateentity (Update, a.b.c.FooVO)
     * FULL_MAPPING_TWO_VOS     - would represent Activateentity (Update, a.b.c.FooVO, x.y.z.RecordVO)
     * PARTIAL_MAPPING_ONE_VO   - would represent Activateentity (Update, a.b.c.FooVO, {VarA, VarB, VarC})
     * PARTIAL_MAPPING_TWO_VOS  - would represent ActivateEntity (Update, a.b.c.FooVO, x.y.z.RecordVO, {VarA, VarB, VarC}).
     * @see #UpdateEntity
     */
    private enum MappingCase
    {

        FULL_MAPPING_ONE_VO,
        FULL_MAPPING_TWO_VOS,
        PARTIAL_MAPPING_ONE_VO,
        PARTIAL_MAPPING_TWO_VOS;
    }

    public UpdateEntity()
    {
    }

    public void execute(ScriptProcessor sp, String[] parameterTokens) throws SPException
    {
        Element lastActiveElement = null;

        Element sourceActiveElement = null;

        try
        {
            lastActiveElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias(parameterTokens[1], sp));

            MappingCase mappingCase = getMappingCase(parameterTokens);

            HibernateEntity hibernateEntity = SessionHelper.getHibernateEntity(lastActiveElement, sp.getHibernateSession());

            switch (mappingCase)
            {
                case FULL_MAPPING_ONE_VO:
                {
                    sourceActiveElement = lastActiveElement;

                    break;
                }
                case FULL_MAPPING_TWO_VOS:
                {
                    sourceActiveElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias(parameterTokens[2], sp));

                    break;
                }
                case PARTIAL_MAPPING_ONE_VO:
                {
                    String[] valueNames = extractValueNames(parameterTokens[2]);

                    sourceActiveElement = buildSourceElement(lastActiveElement, valueNames, sp);

                    break;
                }
                case PARTIAL_MAPPING_TWO_VOS:
                {
                    String[] valueNames = extractValueNames(parameterTokens[3]);

                    sourceActiveElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias(parameterTokens[2], sp));

                    sourceActiveElement = buildSourceElement(sourceActiveElement, valueNames, sp);

                    break;
                }
            }
            
            updateHibernateEntity(hibernateEntity, sourceActiveElement);
        }
        catch (Exception e)
        {
            String lastActiveElementName = "N/A";

            if (lastActiveElement != null)
            {
                lastActiveElementName = lastActiveElement.getName();
            }

            String sourceActiveElementXML = "N/A";

            if (sourceActiveElement != null)
            {
                sourceActiveElementXML = sourceActiveElement.asXML();
            }

            throw new SPException("Unable to update Hibernate Entity [" + lastActiveElementName + "] with values from [" + sourceActiveElementXML + "].", SPException.INSTRUCTION_PROCESSING_ERROR, e);
        }
    }

    /**
     * From the specified parameterTokens
     * @param parameterTokens
     * @return
     */
    private UpdateEntity.MappingCase getMappingCase(String[] parameterTokens)
    {
        MappingCase mappingCase = null;

        if (parameterTokens.length == 2)
        {
            mappingCase = MappingCase.FULL_MAPPING_ONE_VO; // has to be
        }
        else if (parameterTokens.length == 4)
        {
            mappingCase = MappingCase.PARTIAL_MAPPING_TWO_VOS; // has to be
        }
        else // there are 2 possibilities left - let's examine...
        {
            if (parameterTokens[2].startsWith("{"))
            {
                mappingCase = MappingCase.PARTIAL_MAPPING_ONE_VO;
            }
            else
            {
                mappingCase = MappingCase.FULL_MAPPING_TWO_VOS;
            }
        }

        return mappingCase;
    }

    /**
     * Builds a new Element from the specified element using the specified named values.
     * 
     * @param sourceElement
     * @param valueNames
     * @sp
     * @return an Element using the same name as the specified element, but containing a subset of the values
     */
    private Element buildSourceElement(Element element, String[] valueNames, ScriptProcessor sp)
    {
        Element subsetElement = new DefaultElement(element.getName());

        for (String valueName : valueNames)
        {
            Element valueNamedElement = null;

            String value = null;

            if (valueName.startsWith(":"))
            {
                valueName = valueName.substring(1, valueName.length());

                valueNamedElement = (Element) element.element(valueName).clone();

                value = sp.getWSEntry(valueName);

                valueNamedElement.setText(value);
            }
            else
            {
                valueNamedElement = (Element) element.element(valueName).clone();
            }

            subsetElement.add(valueNamedElement);
        }

        return subsetElement;
    }

    /**
     * Tokenizes the specified String into its name tokens. For example,
     * the String "{A, B, C}" would return A, B, C (the '}' and '{' stripped-off.
     * @param valueNamesString supplies as "{A, B, C}"
     * @return
     */
    private String[] extractValueNames(String valueNamesString)
    {
        int openingParenthesis = valueNamesString.indexOf("{");

        int closingParenthesis = valueNamesString.lastIndexOf("}");

        valueNamesString = valueNamesString.substring(openingParenthesis + 1, closingParenthesis);

        String[] valueNameTokens = Util.fastTokenizer(valueNamesString, ",", true);

        return valueNameTokens;
    }

    /**
     * Updates the specified hibernateEntity with the corresponding named values of the specified
     * source Element. This is a value-only mapping - all PKs and FKs are ignored.
     * @param hibernateEntity
     * @param sourceElement
     */
    private void updateHibernateEntity(HibernateEntity hibernateEntity, Element sourceElement) throws Exception
    {
        List<Element> nameValueElements = sourceElement.elements();

        for (Element nameValueElement : nameValueElements)
        {
            String name = nameValueElement.getName();

            // Ignore PKs and FKs - we don't associate this way with Hibernate - must use Associate instruction.
            if (!name.endsWith("PK") && !name.endsWith("FK") ||
                name.equalsIgnoreCase("OriginatingTrxFK") ||
                name.equalsIgnoreCase("TerminationTrxFK"))
            {
                Object value = nameValueElement.getText();

                Method getter = hibernateEntity.getClass().getMethod("get" + name, new Class[]
                        {
                        });

                Class returnType = getter.getReturnType();

                Method setter = hibernateEntity.getClass().getMethod("set" + name, new Class[]
                        {
                            returnType
                        });

                // Empty Strings or the reserved word #NULL are treated as a null reference.
                if ((value.toString().length()) == 0 || (value.toString().equals(engine.common.Constants.ScriptKeyword.NULL)))
                {
                    value = null;
                }
                else
                {
                    if (name.equalsIgnoreCase("LastStatementDateTime") || name.equalsIgnoreCase("LastCheckDateTime"))
                    {
                        EDITDate ed = new EDITDate(value.toString());

                        EDITDateTime edt = new EDITDateTime(ed, EDITDateTime.DEFAULT_MIN_TIME);

                        value = edt;
                    }

                    value = Util.convertStringToObject(value.toString(), returnType, false);
                }

                setter.invoke(hibernateEntity, value);
            }
        }
    }
}

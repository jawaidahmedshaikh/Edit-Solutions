package engine.sp;

import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.InvalidXPathException;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultDocument;
import org.dom4j.tree.DefaultElement;

/**
 * This instruction was written after the introduction of the
 * RetrieveEntity instruction which allows a scripter to retrieve
 * entities from the actual DB and place the results within the ResultDocVO (a custom instruction).
 * 
 * It later became apparent that a similar feature would be useful for scripters when working
 * with the Documents. Currently, scripters have to navigate their way to the desired element, and
 * then once there, grab or update the information sought.
 * 
 * This instruction will allow a script to search for (retrieve) the Element(s) sought from
 * within a Document currently loaded in ScriptProcessor.
 * 
 * The syntax used isn't unique - we simply borrowed the XPath syntax available within the DOM4J API.
 * 
 * Example 1:
 * 
 * retrieve (/AAA/CCC)
 * 
 * This would retrieve all CCC Elements currently under the root AAA document. Syntax must always
 * start with / since there could be multiple root Documents currently loaded so we need to know
 * which one to use.
 * 
 * Example 2:
 * 
 * retrieve (/AAA/CCC[Foo>5 and Goo<10]) 
 * 
 * This would retrieve all CCC elements under AAA that have a Foo Element with a value > 5 and a Goo Element with a value < 10.
 * 
 * i.e. AAA.CCC.Foo > 5.
 * 
 * Example 3:
 * 
 * retrieve (/AAA/CCC[firstName = "Jesus"])
 * 
 * This would retrieve all CCC elements under AAA that have a firstName element with a text value of "Tom" (case sensitive).
 *
 * Example 4:
 * 
 * retrieve (/AAA/BBB/CCC[contains(firstName,"P")])
 * 
 * This would retrieve all CCC elements under AAA that have a firstName element with a text value that contains the letter "P".
 *
 * Scripters will often need to substitute value tokens into the expression for dynamic evaluations.
 * 
 * Example 5:
 * 
 * retrieve (/AAA/CCC[firstName = ":fooFirstName"])
 * 
 * This would evaluate as in example # 3, but WS would be expected to contain a value for the name fooFirstName.
 * 
 * Note: Any results that are placed in the ResultDoc are placed by copy (the original Elements are deep-copied).
 * This is necessary since an Element can't have two parents at the same time.
 * 
 */
public class Retrieve extends Inst
{
    /**
     * Extracts the named paramters in an hql expression.
     * 
     * e.g.
     * 
     * String hql = "from foo Foo where foo.A = :aValue and foo.B = :bValue";
     * 
     * The pattern would extract the named parameters of 'aValue' and 'bValue'.
     */
    private static Pattern p1 = Pattern.compile("(:.+?\\b)");
    
    /**
     * Extracts the "target" root or relative element name from the XPath expression. 
     *
     * e.g. /A/B[C="foo"] - would extract "A"
     * 
     * e.g. A[B="foo"] - would extract "A"
     * 
     */
    private static Pattern p2 = Pattern.compile("(\\w+)");
    
    public Retrieve()
    {
    }

    protected void compile(ScriptProcessor theProcessor)
    {
        super.sp = theProcessor;
    }

    protected void exec(ScriptProcessor execSP) throws SPException
    {
        super.sp = execSP;

        try
        {
            String xpath = buildXPathExpression();
            
            List<Element> results = executeXPath(xpath);

            for (Element result: results)
            {
                Element resultCopy = result.createCopy();

                sp.getResultDocVO().getRootElement().add(resultCopy);
            } 
        }
        finally
        {
            sp.incrementInstPtr();
        }
    }

    /**
     * Executes the specified xpath expression against the
     * currently loaded Document identified by the root Element name.
     * 
     * e.g.
     * 
     * /RootElement/A/B[Name="foo"]
     * 
     * The above would find all B Elements with a Name of "foo" within
     * the Document with root Element "RootElement".
     *
     * @param xpath
     * @return
     */
    public List<Element> executeXPath(String xpath) throws SPException
    {
        Element targetElement = getTargetElement(xpath);

        XPath xpathSelector = DocumentHelper.createXPath(xpath);

        List<Element> results = xpathSelector.selectNodes(targetElement);
        
        return results;
    }
    
    /**
     * Parses the general instruction syntax of:
     * retrieve (/AAA/BBB/CCC[code = ":fooCode"]) to identify:
     * 
     * 1. The XPath expression of /AAA/BBB/CCC[code = ":fooCode"]
     * 2. The value token of fooCode with which the WS value will be substituded to
     * give you:
     * 
     * /AAA/BBB/CCC[code = "fooCodeValue"].
     * 
     * @return the document name
     */    
    private String buildXPathExpression()
    {
        String xPathExpression = getRawXPathExpression();
        
        if (xPathExpression.indexOf(":") >= 0)
        {
            xPathExpression = substituteWSValues(xPathExpression);            
        }
        
        return xPathExpression;
    }
    
    /**
     * Parses the general instruction syntax of:
     * retrieve (/AAA/BBB/CCC[code = ":fooCode"]) to identify
     * the XPath expression of /AAA/BBB/CCC[code = ":fooCode"].
     * 
     * @return the XPath expression (may still continue value tokens of :fooValue) i.e. "Raw"
     */
    private String getRawXPathExpression()
    {
      String line = super.getInstAsEntered();

      int openingParenthesis = line.indexOf("(");
      
      int closingParenthesis = line.lastIndexOf(")");

      String xpathExpression = line.substring(openingParenthesis + 1, closingParenthesis);

      return xpathExpression;
    }

    /**
     * All xpath expression may start with the "/" or "" (nothing).
     * 
     * If the expression starts with "/", then we know the document sought
     * is relative to the root Document. 
     * 
     * If the expression starts with "", then we know that the document/
     * @param xpathExpression
     * @return
     */
    private Element getTargetElement(String xpathExpression) throws SPException
    {
        Element targetElement = null;

        String targetElementName = getTargetElementName(xpathExpression);
        
        if (xpathExpression.startsWith("/"))
        {
            targetElement = sp.getSPParams().getDocumentByName(targetElementName).getRootElement();         
        }
        else
        {
            targetElement = sp.getSPParams().getLastActivePurportedParent(targetElementName);
        }
        
        return targetElement;
    }

    /**
     * Uses a previously defined regular expression to extract the
     * target Element name. 
     * 
     * e.g.
     * 
     * 
     * @param xPathExpression
     * @return
     */
    private String getTargetElementName(String xPathExpression)
    {
        String targetElementName = null;
        
        Matcher m = p2.matcher(xPathExpression);

        if (m.find())
        {
            targetElementName = m.group(); // we only care about the first element in the path
        }
        
        return targetElementName;
    }
    
    /**
     * Extracts AAA from "AAA/BBB/CCC...".
     * @param xpath
     * @return
     */
    private String getRelativeElementName(String xpath)
    {
        int indexOfFirstSlash = 1;
        
        int indexOfSecondSlash = xpath.indexOf("/", 1);
        
        String rootElementName = xpath.substring(indexOfFirstSlash, indexOfSecondSlash);
        
        return rootElementName;
    }    

    /**
     * Parses the specified xPath expression and replaces any
     * :fooToken with the equivalent working storage fooToken entry.
     * @param xPathExpression
     * @return
     */
    private String substituteWSValues(String xPathExpression)
    {
        Matcher m = p1.matcher(xPathExpression);
        
        while(m.find())
        {
            String valueToken = m.group();
            
            String wsValue = sp.getWSEntry(valueToken.substring(1));
        
            xPathExpression = xPathExpression.replaceFirst(valueToken, wsValue);
        }
        
        return xPathExpression;
    }
    
    /**
     * This is a cheat - I want to re-use this instruction from another instruction - I can't
     * find a proper abstraction for this XPath stuff.
     * @param sp
     */
    public void setScriptProcessor(ScriptProcessor sp)
    {
        this.sp = sp;
    }
}

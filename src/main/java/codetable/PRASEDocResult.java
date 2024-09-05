package codetable;

import org.dom4j.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jul 11, 2005
 * Time: 1:10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class PRASEDocResult
{
    private Element rootElement;

    private Object rootEntity;

    public PRASEDocResult(Object rootEntity, Element rootElement)
    {
        this.rootEntity = rootEntity;

        this.rootElement = rootElement;
    }

    /**
     * The root Hibernate entity mapped as a DOM4J element.
     * @return
     */
    public Element getElement()
    {
        return rootElement;
    }

    /**
     * The root Hibernate entity.
     * @return
     */
    public Object getEntity()
    {
        return rootEntity;
    }
}

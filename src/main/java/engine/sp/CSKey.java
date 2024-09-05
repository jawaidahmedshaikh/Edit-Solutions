package engine.sp;

import java.io.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jul 25, 2005
 * Time: 2:18:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSKey implements Serializable
{
    public static final int BY_SCRIPT_NAME = 0;
    public static final int BY_SCRIPT_PK = 1;
    public static final int BY_COMPANY_STRUCTURE = 2;
    private long scriptPK;
    private String scriptName;
    private long productStructurePK;
    private String process;
    private String event;
    private String eventType;
    private int keyType;

    /**
     * Constructor when the compiled script is to be uniquely identified by the scriptPK. It is assumed that the
     * target compiled script is an elemental script (not fully exploded).
     * @param scriptPK
     */
    public CSKey(long scriptPK)
    {
        this(scriptPK, null, 0, null, null, null);

        keyType = BY_SCRIPT_PK;
    }

    /**
     * Constructor when the compiled script is to be uniquely identified by the script name. It is assumed that the
     * target compiled script is an elemental script (not fully exploded).
     * @param scriptName
     */
    public CSKey(String scriptName)
    {
        this(0, scriptName, 0, null, null, null);

        keyType = BY_SCRIPT_NAME;
    }

    /**
     * Constructor for when the fully exploded compiled script is targeted since we have the compound key that would
     * uniquely identify the compiled script.
     * @param productStructurePK
     * @param process
     * @param event
     * @param eventType
     */
    public CSKey(long productStructurePK, String process, String event, String eventType)
    {
        this(0, null, productStructurePK, process, event, eventType);

        keyType = BY_COMPANY_STRUCTURE;
    }

    /**
     * A convenience constructor to help default initial values.
     * @param scriptPK
     * @param scriptName
     * @param productStructurePK
     * @param process
     * @param event
     * @param eventType
     */
    private CSKey(long scriptPK, String scriptName, long productStructurePK, String process, String event, String eventType)
    {
        this.scriptPK = scriptPK;
        this.scriptName = (scriptName == null)?"":scriptName;
        this.productStructurePK = productStructurePK;
        this.process = (process == null)?"":process;
        this.event = (event == null)?"":event;
        this.eventType = (eventType == null)?"":eventType;
    }

    /**
     * Returns the type of the key as:
     * BY_SCRIPT_NAME
     * BY_SCRIPT_PK
     * BY_COMPANY_STRUCTURE
     * @return
     */
    public int getKeyType()
    {
        return keyType;
    }

    public long getScriptPK()
    {
        return scriptPK;
    }

    public String getScriptName()
    {
        return scriptName;
    }

    public long getProductStructurePK()
    {
        return productStructurePK;
    }

    public String getProcess()
    {
        return process;
    }

    public String getEvent()
    {
        return event;
    }

    public String getEventType()
    {
        return eventType;
    }

    public boolean equals(Object o)
    {
        boolean keyIdentified = false;

        if (this == o) return true;

        if (!(o instanceof CSKey)) return false;

        final CSKey csKey = (CSKey) o;

        if (keyType == CSKey.BY_SCRIPT_NAME)
        {
            return scriptName.equals(csKey.getScriptName());
        }

        if (keyType == CSKey.BY_SCRIPT_PK)
        {
            return (scriptPK == csKey.getScriptPK());
        }

        if (keyType == CSKey.BY_COMPANY_STRUCTURE)
        {
            return  (productStructurePK == csKey.getProductStructurePK()) && (process.equals(csKey.getProcess())) && (event.equals(csKey.getEvent())) && (eventType.equals(csKey.getEventType()));
        }

        if (!keyIdentified)
        {
            throw new RuntimeException("Unable To Properly Evaluate CSKey.equals()");
        }

        return true;
    }

    public int hashCode()
    {
        int result;

        if (getKeyType() == CSKey.BY_SCRIPT_PK)
        {
            result = (int) (scriptPK ^ (scriptPK >>> 32));
        }
        else if (getKeyType() == CSKey.BY_SCRIPT_NAME)
        {
            result = 29 * scriptName.hashCode();
        }
        else
        {
            result = 29 * (int) (productStructurePK ^ (productStructurePK >>> 32));
            result = 29 * result + process.hashCode();
            result = 29 * result + event.hashCode();
            result = 29 * result + eventType.hashCode();
            result = 29 * result + keyType;
        }

        return result;
    }

    public String toString()
    {
        String result = "[ScriptPK: " +  scriptPK + "] ";
        result += "[ScriptName: " +  scriptName + "] ";
        result += "[ProductStructurePK: " + productStructurePK + "] ";
        result += "[Process: " + process + "] ";
        result += "[Event" + event + "] ";
        result += "[EventType: " + eventType + "] ";
        result += "[KeyType: " + keyType + "]";

        return result;
    }
}

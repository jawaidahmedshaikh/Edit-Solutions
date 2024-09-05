/*
 * CompiledScript.java       Version 1.0   03/21/2003
 *
 * Copyright (c) 2003 Systems engineering Group, LLC. All Rights Reserved
 *
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC anbd may not be copied in whole or  in
 * part without the written permission of Systems engineering group, LLC.
 */
package engine.sp;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CompiledScript implements Serializable
{
  /**
   * The set of compiled instructions (children of Inst).
   */
  private List script;

  /**
   * The gets/calls to other scripts.
   */
  private TreeMap<String, String> labels;

  /**
   * The compiledScriptKey which serves as the unique identifier for the script.
   */
  private String compiledScriptKey;

  /**
   * True if this CompiledScript is being used be a currently active Script.
   */
  private boolean inUse;

  public CompiledScript(String compiledScriptKey, List script, TreeMap<String, String> labels)
  {
    super();

    this.script = script;

    this.labels = labels;

    this.compiledScriptKey = compiledScriptKey;
    
    setInUse(false); // just being explicit
  }

  /**
   * Getter.
   * @see #script
   * @return
   */
  public List getScript()
  {
    return script;
  }

  /**
   * Getter.
   * @see #labels
   * @return
   */
  public TreeMap getLabels()
  {
    return labels;
  }

  /**
   * Performs a deep clone of this CompiledScript.
   * @return
   */
  public CompiledScript clone()
  {
    CompiledScript clonedCompiledScript = null;

    try
    {
      // Clone the instruction list
      List clonedInstructions = new ArrayList();

      int scriptSize = getScript().size();

      for (int i = 0; i < scriptSize; i++)
      {
        Inst inst = (Inst) getScript().get(i);

        Inst clonedInst = (Inst) inst.clone();

        clonedInstructions.add(clonedInst);
      }

      TreeMap<String, String> clonedLabels = (TreeMap<String, String>) getLabels().clone(); // They are just Strings.

      clonedCompiledScript = new CompiledScript(getCompiledScriptKey(), clonedInstructions, clonedLabels);
    }
    catch (Exception e)
    {
      System.out.println(e);

      e.printStackTrace();
    }

    return clonedCompiledScript;
  }

  /**
   * Getter.
   * @see #compiledScriptKey
   * @return
   */
  public String getCompiledScriptKey()
  {
    return compiledScriptKey;
  }

  /**
   * Likely to be set when the CompiledScript has been borrowed and is now in use.
   * @param inUse
   */
  public final void setInUse(boolean inUse)
  {
    this.inUse = inUse;
  }

  /**
   * True if this CompiledScript is in use. This flag will likely be set to false
   * when the CompiledScript is return to the provide (pool) and made availabe for
   * a later running Script.
   * @return
   */
  public boolean isInUse()
  {
    return inUse;
  }
}

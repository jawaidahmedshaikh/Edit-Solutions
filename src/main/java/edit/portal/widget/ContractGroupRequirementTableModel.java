package edit.portal.widget;

import casetracking.CaseRequirement;

import casetracking.usecase.CasetrackingUseCase;
import casetracking.usecase.CasetrackingUseCaseImpl;

import client.ClientDetail;

import contract.FilteredRequirement;
import contract.Requirement;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;

import engine.ProductStructure;

import fission.global.AppReqBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * As with other Requirements in the system, the ContractGroup entity
 * can be associated with Requirements filtered by FilteredRequirement (associated
 * with a system level key of Case ****).
 */
public class ContractGroupRequirementTableModel extends TableModel
{
  public static final String COLUMN_REQUIREMENT_ID    = "Requirement Id";
  
  public static final String COLUMN_REQUIREMNT_DESC   = "Requirement Description";
  
  public static final String COLUMN_STATUS            = "Status";
  
  public static final String COLUMN_EFFECTIVE_DATE    = "Effective Date";

  private static final String[] COLUMN_NAMES = {COLUMN_REQUIREMENT_ID, COLUMN_REQUIREMNT_DESC, COLUMN_STATUS, COLUMN_EFFECTIVE_DATE};


  public ContractGroupRequirementTableModel(AppReqBlock appReqBlock)
  {
      super(appReqBlock);

      getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
  }

  /**
   * Renders the associated Requirements via ContractGroupRequirement (this table may change).
   * Note: 
   */
  protected void buildTableRows()
  {
  }
}

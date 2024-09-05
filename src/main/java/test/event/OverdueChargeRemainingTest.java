package test.event;

import contract.Segment;

import edit.common.EDITBigDecimal;

import edit.common.EDITDate;

import edit.services.db.hibernate.SessionHelper;

import event.ClientSetup;
import event.ContractSetup;
import event.EDITTrx;
import event.OverdueCharge;
import event.OverdueChargeRemaining;

import event.OverdueChargeSettled;

import java.util.Set;

import junit.framework.TestCase;

import test.textfixture.TestHelper;

public class OverdueChargeRemainingTest extends TestCase
{
  TestHelper th = new TestHelper();
  
  EDITTrx editTrxPY;

  OverdueChargeSettled overdueChargeSettled1a;

  OverdueChargeSettled overdueChargeSettled1b;

  OverdueChargeSettled overdueChargeSettled2a;
  
  OverdueChargeSettled overdueChargeSettled2b;

  OverdueCharge overdueCharge1;

  OverdueCharge overdueCharge2;

  EDITTrx editTrxMD1;

  EDITTrx editTrxMD2;  

  public OverdueChargeRemainingTest(String sTestName)
  {
    super(sTestName);
  }
  
 /**
  * EDITTrxPY
  * EDITTrx.OverdueCharge.OverdueChargeSettled (X4 - two for each OverdueCharge)
  * EDITTrx.OverdueCharge (X2)
  * EDITTrx.OverdueCharge.OverdueChargeSettled.EDITTrxMD (X2 - one shared by 2 (each) OverdueCharge) 
  */
  protected void setUp() throws Exception
  {
    super.setUp();

    th.setUp();
    
    // Build the test model
    editTrxPY = (EDITTrx) th.buildEntity(EDITTrx.class, SessionHelper.EDITSOLUTIONS); // root

    overdueChargeSettled1a = (OverdueChargeSettled) th.buildEntity(OverdueChargeSettled.class, SessionHelper.EDITSOLUTIONS);

    overdueChargeSettled1b = (OverdueChargeSettled) th.buildEntity(OverdueChargeSettled.class, SessionHelper.EDITSOLUTIONS);

    overdueChargeSettled2a = (OverdueChargeSettled) th.buildEntity(OverdueChargeSettled.class, SessionHelper.EDITSOLUTIONS);

    overdueChargeSettled2b = (OverdueChargeSettled) th.buildEntity(OverdueChargeSettled.class, SessionHelper.EDITSOLUTIONS);

    overdueCharge1 = (OverdueCharge) th.buildEntity(OverdueCharge.class, SessionHelper.EDITSOLUTIONS);

    overdueCharge2 = (OverdueCharge) th.buildEntity(OverdueCharge.class, SessionHelper.EDITSOLUTIONS);

    editTrxMD1 = (EDITTrx) th.buildEntity(EDITTrx.class, SessionHelper.EDITSOLUTIONS); // root

    editTrxMD2 = (EDITTrx) th.buildEntity(EDITTrx.class, SessionHelper.EDITSOLUTIONS); // root

    // Set the test values.
    overdueCharge1.setOverdueAdmin(new EDITBigDecimal("100.00"));
    overdueCharge1.setOverdueCoi(new EDITBigDecimal("0.00"));
    overdueCharge1.setOverdueExpense(new EDITBigDecimal("0.00"));

    overdueCharge2.setOverdueAdmin(new EDITBigDecimal("200.00"));
    overdueCharge2.setOverdueCoi(new EDITBigDecimal("0.00"));
    overdueCharge2.setOverdueExpense(new EDITBigDecimal("0.00"));

    overdueChargeSettled1a.setSettledAdmin(new EDITBigDecimal("1.00"));
    overdueChargeSettled1a.setSettledCoi(new EDITBigDecimal("0.00"));
    overdueChargeSettled1a.setSettledExpense(new EDITBigDecimal("0.00"));

    overdueChargeSettled1b.setSettledAdmin(new EDITBigDecimal("1.00"));
    overdueChargeSettled1b.setSettledCoi(new EDITBigDecimal("0.00"));
    overdueChargeSettled1b.setSettledExpense(new EDITBigDecimal("0.00"));

    overdueChargeSettled2a.setSettledAdmin(new EDITBigDecimal("2.00"));
    overdueChargeSettled2a.setSettledCoi(new EDITBigDecimal("0.00"));
    overdueChargeSettled2a.setSettledExpense(new EDITBigDecimal("0.00"));

    overdueChargeSettled2b.setSettledAdmin(new EDITBigDecimal("2.00"));
    overdueChargeSettled2b.setSettledCoi(new EDITBigDecimal("0.00"));
    overdueChargeSettled2b.setSettledExpense(new EDITBigDecimal("0.00"));

    editTrxPY.setEffectiveDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));

    editTrxPY.setTransactionTypeCT("PY");

    editTrxMD1.setEffectiveDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));

    editTrxMD2.setEffectiveDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));

    editTrxMD1.setTransactionTypeCT("MD");

    editTrxMD2.setTransactionTypeCT("MD");

    editTrxMD1.setStatus("N");

    editTrxMD2.setStatus("A");

    // Do the associations.
    editTrxPY.add(overdueChargeSettled1a);

    editTrxPY.add(overdueChargeSettled1b);

    editTrxPY.add(overdueChargeSettled2a);

    editTrxPY.add(overdueChargeSettled2b);

    overdueCharge1.add(overdueChargeSettled1a);

    overdueCharge1.add(overdueChargeSettled1b);

    overdueCharge2.add(overdueChargeSettled2a);

    overdueCharge2.add(overdueChargeSettled2b);

    editTrxMD1.add(overdueCharge1);

    editTrxMD2.add(overdueCharge2);

    // Save/Flush for root entities for Hibernate
    SessionHelper.saveOrUpdate(editTrxPY, SessionHelper.EDITSOLUTIONS);

    SessionHelper.saveOrUpdate(editTrxMD1, SessionHelper.EDITSOLUTIONS);

    SessionHelper.saveOrUpdate(editTrxMD2, SessionHelper.EDITSOLUTIONS);

    SessionHelper.flushSessions();    
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();

    th.tearDown();
    
    editTrxPY = null;

    overdueChargeSettled1a = null;

    overdueChargeSettled1b = null;

    overdueChargeSettled2a = null;

    overdueChargeSettled2b = null;

    overdueCharge1 = null;

    overdueCharge2 = null;

    editTrxMD1 = null;

    editTrxMD2 = null;
  }

  /**
   * At the EDITTrx level, OverdueCharge(s) are to be calculated and summed
   * as a total. The final result is the OverdueCharge.Amounts subtract the
   * effect of each OverdueCharge.OverdueChargeSettled.
   * 
   * 1. Build the model to test (done in setup)
   * 
   * 2. Generate the calculation
   * 
   * 3. Test for expected results
   * 
   * @see OverdueChargeRemaining#calculate()
   */
  public void testCalculate_EDITTrxLevel()
  {
    // 2. Generate the calculation.
    OverdueChargeRemaining overdueChargeRemaining = new OverdueChargeRemaining(editTrxPY, OverdueChargeRemaining.SCOPE_EDITTRX_LEVEL);

    overdueChargeRemaining.calculate();

    OverdueChargeRemaining.OverdueChargeRemainingAmount amount = overdueChargeRemaining.getOverdueChargeRemainingAmount();

    // 3. Test for expected results
    assertEquals("Calculating RemainingAdmin at EDITTrx Level", 294.00, amount.getRemainingAdmin().doubleValue());
  }
  
  /**
   * At the EDITTrx level, OverdueCharge(s) are to be calculated and summed
   * as a total. The final result is the OverdueCharge.Amounts subtract the
   * effect of each OverdueCharge.OverdueChargeSettled.
   * 
   * 1. Build the model to test (done in setup but added to in this test)
   * Added the following entities to the test model:
   * 
   * ClientSetup (X3 - 1 for each for the 2 editTrxMD and the 1 editTrxPY)
   * ClientSetup.ContractSetup (X3 - 1 each for the 2 ClientSetup and the 1 editTrxPY)
   * Segment (X1)
   * 
   * 2. Generate the calculation.
   * 
   * 3. Test for expected results.
   * 
   * We should see 2 OverdueChargeRemainingAmounts - 1 for each of the OverdueCharges.
   * 
   * @see OverdueChargeRemaining#calculate()
   */
  public void testCalculate_SegmentLevel()
  {
    // 1. Add to the existing test model. We need ClientSetup, ContractSetup, and Segment entities
    ClientSetup clientSetup1 = (ClientSetup) th.buildEntity(ClientSetup.class, SessionHelper.EDITSOLUTIONS);
    
    ClientSetup clientSetup2 = (ClientSetup) th.buildEntity(ClientSetup.class, SessionHelper.EDITSOLUTIONS);

    ClientSetup clientSetupPY = (ClientSetup) th.buildEntity(ClientSetup.class, SessionHelper.EDITSOLUTIONS);
    
    ContractSetup contractSetup1 = (ContractSetup) th.buildEntity(ContractSetup.class, SessionHelper.EDITSOLUTIONS);
    
    ContractSetup contractSetup2 = (ContractSetup) th.buildEntity(ContractSetup.class, SessionHelper.EDITSOLUTIONS);

    ContractSetup contractSetupPY = (ContractSetup) th.buildEntity(ContractSetup.class, SessionHelper.EDITSOLUTIONS);
     
    Segment segment = (Segment) th.buildEntity(Segment.class, SessionHelper.EDITSOLUTIONS);
    
    // Associate
    segment.addContractSetup(contractSetup1);
    
    segment.addContractSetup(contractSetup2);

    segment.addContractSetup(contractSetupPY);
    
    contractSetup1.addClientSetup(clientSetup1);
    
    contractSetup2.addClientSetup(clientSetup2);

    contractSetupPY.addClientSetup(clientSetupPY);
    
    clientSetup1.addEDITTrx(editTrxMD1);
    
    clientSetup2.addEDITTrx(editTrxMD2);

    clientSetupPY.addEDITTrx(editTrxPY);
    
    SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS);
    
    SessionHelper.flushSessions();
  
    // 2. Generate the calculation.
    OverdueChargeRemaining overdueChargeRemaining = new OverdueChargeRemaining(editTrxPY, OverdueChargeRemaining.SCOPE_SEGMENT_LEVEL);

    overdueChargeRemaining.calculate();

    Set<OverdueChargeRemaining.OverdueChargeRemainingAmount> amounts = overdueChargeRemaining.getOverdueChargeRemainingAmounts();

    // 3. Test for expected results
    assertEquals("Calculating the number of OverdueChargeRemainingAmounts", 2, amounts.size());
  }  
}

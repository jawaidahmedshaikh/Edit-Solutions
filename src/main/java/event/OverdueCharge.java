/*
 * User: dlataille
 * Date: June 14, 2006
 * Time: 12:20:00 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package event;

import edit.common.*;
import edit.services.db.hibernate.*;

import java.util.*;

import contract.*;


public class OverdueCharge extends HibernateEntity
{
    private Long overdueChargePK;

    private EDITTrx editTrx;
    private Long editTrxFK;

    private EDITBigDecimal overdueCoi;
    private EDITBigDecimal overdueAdmin;
    private EDITBigDecimal overdueExpense;
  
    /**
     * Constant.
     * @see #calculateOverdueChargeAmounts
     */
    public static final String OVERDUE_COI = "overdueCoi";
    
    /**
     * Constant.
     * @see #calculateOverdueChargeAmounts
     */
    public static final String OVERDUE_ADMIN = "overdueAdmin";

    /**
     * Constant.
     * @see #calculateOverdueChargeAmounts
     */
    public static final String OVERDUE_EXPENSE = "overdueExpense";

    private Set<OverdueChargeSettled>  overdueChargesSettled = new HashSet<OverdueChargeSettled>();

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;



    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, OverdueCharge.DATABASE);
    }

    public void hDelete()
    {
        SessionHelper.delete(this, OverdueCharge.DATABASE);
    }

    /**
     * Getter.
     * @return
     */
    public Long getOverdueChargePK()
    {
        return this.overdueChargePK;
    }

    /**
     * Setter.
     * @param overdueChargePK
     */
    public void setOverdueChargePK(Long overdueChargePK)
    {
        this.overdueChargePK = overdueChargePK;
    }

    public EDITTrx getEDITTrx()
    {
        return editTrx;
    }

    public void setEDITTrx(EDITTrx editTrx)
    {
        this.editTrx = editTrx;
    }

    public Long getEDITTrxFK()
    {
        return this.editTrxFK;
    }

    /**
     * Setter.
     * @param editTrxFK
     */
    public void setEDITTrxFK(Long editTrxFK)
    {
        this.editTrxFK = editTrxFK;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getOverdueCoi()
    {
        return overdueCoi;
    }

    /**
     * Setter.
     * @param overdueCoi
     */
    public void setOverdueCoi(EDITBigDecimal overdueCoi)
    {
        this.overdueCoi = overdueCoi;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getOverdueAdmin()
    {
        return overdueAdmin;
    }

    /**
     * Setter.
     * @param overdueAdmin
     */
    public void setOverdueAdmin(EDITBigDecimal overdueAdmin)
    {
        this.overdueAdmin = overdueAdmin;
    }

    /**
     * Getter.
     * @return
     */
    public EDITBigDecimal getOverdueExpense()
    {
        return overdueExpense;
    }

    /**
     * Setter.
     * @param overdueExpense
     */
    public void setOverdueExpense(EDITBigDecimal overdueExpense)
    {
        this.overdueExpense = overdueExpense;
    }

    /**
     * Getter.
     * @return
     */
    public Set<OverdueChargeSettled> getOverdueChargesSettled()
    {
        return overdueChargesSettled;
    }

    /**
     * Setter.
     * @param overdueChargesSettled
     */
    public void setOverdueChargesSettled(Set<OverdueChargeSettled> overdueChargesSettled)
    {
        this.overdueChargesSettled = overdueChargesSettled;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return OverdueCharge.DATABASE;
    }

    /**
     * Finder by Segment and TransactionTypes.
     * @param segment
     * @param transactionTypes
     * @return
     */
    public static OverdueCharge[] findBySegment_AND_TransactionTypes(Segment segment, String[] transactionTypes)
    {
        String hql = " select overdueCharge" +
                     " from OverdueCharge overdueCharge" +
                     " join overdueCharge.EDITTrx editTrx" +
                     " join editTrx.ClientSetup clientSetup" +
                     " join clientSetup.ContractSetup contractSetup" +
                     " join contractSetup.Segment segment" +
                     " where segment = :segment" +
                     " and editTrx.TransactionTypeCT " + " in (:transactionTypes)";

        Map params = new HashMap();

        params.put("segment", segment);
        params.put("transactionTypes", transactionTypes);

        List results = SessionHelper.executeHQL(hql, params, OverdueCharge.DATABASE);

        return (OverdueCharge[]) results.toArray(new OverdueCharge[results.size()]);
    }
   
  /**
   * Finds the set of OverdueCharges at the <b>Segment</b> level or <b>EDITTrx</b> level
   * of the specified editTrxPK where:
   * 
   * EDITTrx.EffectiveDate <= effectiveDate of the specified editTrx
   * ContractSetup.SegmentFK = segmentFK associated with the specified editTrx
   * EDITTrx.TransactionTypeCT = 'MD'
   * EDITTrx.Status in ('N', 'A')
   *  
   * @param editTrxPY a Premium EDITTrx
   * @param segmentLevel if true then ALL OverdueCharges for the related Segment are retrieved, otherwise just those for the specified EDITTrx
   * @return
   */
    public static OverdueCharge[] findBy_EDITTrxPK_V1(EDITTrx editTrxPY, boolean segmentLevel)
    {
      String hql = null;
      
      EDITMap params = null;
      
      if (segmentLevel)
      {
        hql = " select overdueCharge" +
              " from OverdueCharge overdueCharge" +       
              " left join fetch overdueCharge.OverdueChargesSettled" +
              " join overdueCharge.EDITTrx editTrxMD" +
              " join editTrxMD.ClientSetup clientSetup" +
              " join clientSetup.ContractSetup contractSetup" +
             
              " where editTrxMD.EffectiveDate <= :effectiveDate" +
              " and editTrxMD.Status in ('N', 'A')" +
              " and contractSetup.SegmentFK = :segmentPK";    
                     
          params = new EDITMap("effectiveDate", editTrxPY.getEffectiveDate())
                          .put("segmentPK", editTrxPY.getClientSetup().getContractSetup().getSegmentFK());
                     
      }
      else if (!segmentLevel) // EDITTrx level
      {
        hql = " select overdueCharge" +
              " from OverdueCharge overdueCharge" +
              " left join fetch overdueCharge.OverdueChargesSettled overdueChargeSettled" +
              " join fetch overdueCharge.EDITTrx editTrxMD" +
                     
              " where editTrxMD.EffectiveDate <= :effectiveDate" +
              " and editTrxMD.Status in ('N', 'A')" +
              " and overdueChargeSettled.EDITTrx = :editTrxPY";
                     
          params = new EDITMap("effectiveDate", editTrxPY.getEffectiveDate())
                          .put("editTrxPY", editTrxPY);        
      }
        
      List<OverdueCharge> results = SessionHelper.makeUnique(SessionHelper.executeHQL(hql, params, OverdueCharge.DATABASE));
      
      return results.toArray(new OverdueCharge[results.size()]);
    }

  /**
   * A convenience method in that OverdueCharges may be calculated at the EDITTrx level
   * or the Segment level. The performance goal to retrieve/sum all applicable
   * OverdueCharges at once motivates this method which will likely be used by
   * Segment and EDITTrx entities.
   * @param overdueCharges from either a single applicable EDITTrx, or ALL the applicable EDITTrx(s) for a Segment
   * @return OverdueChargeRemaining contains the three overdue amounts.
   * @see #OVERDUE_ADMIN
   * @see #OVERDUE_COI
   * @see #OVERDUE_EXPENSE
   */
  //    public static Map calculateOverdueChargeAmounts(OverdueCharge[] overdueCharges)
  //    {
  //      OverdueChargeRemaining overdueChargeRemaining = new OverdueChargeRemaining()
  //
  //      EDITBigDecimal overdueAdmin = new EDITBigDecimal();
  //      EDITBigDecimal overdueCoi = new EDITBigDecimal();
  //      EDITBigDecimal overdueExpense = new EDITBigDecimal();
  //
  //      for (OverdueCharge overdueCharge: overdueCharges)
  //      {
  //          overdueAdmin = overdueAdmin.addEditBigDecimal(overdueCharge.getOverdueAdmin());
  //          overdueCoi = overdueCoi.addEditBigDecimal(overdueCharge.getOverdueCoi());
  //          overdueExpense = overdueExpense.addEditBigDecimal(overdueCharge.getOverdueExpense());
  //
  //          Set overdueChargesSettled = overdueCharge.getOverdueChargesSettled();
  //
  //          for (OverdueChargeSettled overdueChargeSettled: overdueChargesSettled)
  //          {
  //            overdueAdmin = overdueAdmin.subtractEditBigDecimal(overdueChargeSettled.getSettledAdmin());
  //            overdueCoi = overdueCoi.subtractEditBigDecimal(overdueChargeSettled.getSettledCoi());
  //            overdueExpense = overdueExpense.subtractEditBigDecimal(overdueChargeSettled.getSettledExpense());
  //          }
  //      }
  //
  //      if (overdueAdmin.isGT("0") || overdueCoi.isGT("0") || overdueExpense.isGT("0"))
  //      {
  //          overdueHT.put("overdueAdmin", OVERDUE_ADMIN);
  //          overdueHT.put("overdueCoi", OVERDUE_COI);
  //          overdueHT.put("overdueExpense", OVERDUE_EXPENSE);
  //      }
  //      
  //      return overdueHT;
  //    }

  /**
   * Adder.
   * @param overdueChargeSettled
   */
  public void add(OverdueChargeSettled overdueChargeSettled)
  {
    getOverdueChargesSettled().add(overdueChargeSettled);
    
    overdueChargeSettled.setOverdueCharge(this);
  }
}

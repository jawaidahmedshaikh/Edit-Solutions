/*
 * RateTable.java      Version 2.00  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Systens Engineering Group, LLC ("Confidential Information").
 */

 package engine.dm;

import edit.common.vo.RateTableVO;
import edit.common.EDITBigDecimal;
import engine.dm.dao.FastDAO;
import engine.sp.*;
import fission.dm.SMException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

 /**
  * This class will be used to store Rate Tables
  */

// public class RateTable extends DBObject {
 public class RateTable  {

     //Member variables


	 //Constants
	 public static final String RATE_TABLE = "RATE_TABLE";
	 public static final String TABLEKEY_ID = "TABLEKEY_ID";
	 public static final String AGE = "AGE";
	 public static final String DURATION = "DURATION";
	 public static final String RATE = "RATE";

     public static final String SEX = "sex";
	 public static final String ISSUE_AGE = "issueage";
	 public static final String CLASS = "class";
     public static final String AREA = "area";
	 public static final String COVERAGE_AMOUNT = "coverageamount";
     public static final String USER_KEY = "userkey";
	 public static String nValue = "N";
	 public static String cValue = "C";
	 public static String gValue = "G";

     // Constructors
     public RateTable() {

         super();
     }

     // Accessor methods

  	/**
      *  Retrieves the table data using the passed in parameters,
      *  expands the table data and returns the result
      */
     public static List getExpandedTable(String accessType,
                                            long tableKeyId,
                                             int issueAge,
                                              String interpolateInd) throws SPException {

 	    // Get and expand table based on Access Type
         RateTable aRateTable = new RateTable();
         List tableData = null;

         if (accessType.equalsIgnoreCase("Attain"))  {

 			RateTableVO[] rateTableVOs = null;

 			try {

                 rateTableVOs = new FastDAO().findAttainRates(tableKeyId, issueAge);

                 if (interpolateInd.equalsIgnoreCase("N"))
                 {
                     tableData = aRateTable.expandTableAttain(issueAge, rateTableVOs);
                 }
                 else
                 {
                     tableData = aRateTable.rateInterpolation(rateTableVOs);
                 }
             }

 			catch  (Exception e) {
     			System.out.println("A-RateTable.getExpandedTable(): Rates access problem: " + e);
 			}

         } else if (accessType.equalsIgnoreCase("Issue"))  {

 			RateTableVO[] rateTableVOs = null;

 			try
             {

                 rateTableVOs = CSCache.getCSCache().getIssueRatesBy_TableKeyPK_Age(tableKeyId, issueAge);

                 if (interpolateInd.equalsIgnoreCase("N"))
                 {
                     tableData = aRateTable.expandTableIssue(1, rateTableVOs);
                 }
                else
                 {
                     tableData = aRateTable.rateInterpolation(rateTableVOs);
                 }
             }

 			catch  (Exception e) {

     			System.out.println("B-RateTable.getExpandedTable(): Rates access problem: " + e);
 			}


         } else  {
             throw new SPException("Invalid AccessType : "
 				+ accessType
 				+ " for table " + (tableKeyId + ""), SPException.TABLE_ACCESS_ERROR);
         }

         return tableData;
     }
     
 	/**
     *  Retrieves the table data using the passed in parameters,
     *  expands the table data and returns the result
     */
    public static List getExpandedTable(String accessType,
                                           long tableKeyId,
                                            int issueAge,
                                             String interpolateInd,
                                             int duration) throws SPException {

	    // Get and expand table based on Access Type
        RateTable aRateTable = new RateTable();
        List tableData = null;

        if (accessType.equalsIgnoreCase("Attain"))  {

			RateTableVO[] rateTableVOs = null;

			try {

                rateTableVOs = new FastDAO().findAttainRatesWithDuration(tableKeyId, issueAge, duration);

                if (interpolateInd.equalsIgnoreCase("N"))
                {
                    tableData = aRateTable.expandTableAttain(issueAge, rateTableVOs);
                }
                else
                {
                    tableData = aRateTable.rateInterpolation(rateTableVOs);
                }
            }

			catch  (Exception e) {
    			System.out.println("A-RateTable.getExpandedTable(): Rates access problem: " + e);
			}

        } else if (accessType.equalsIgnoreCase("Issue"))  {

			RateTableVO[] rateTableVOs = null;

			try
            {

                rateTableVOs = CSCache.getCSCache().getIssueRatesBy_TableKeyPK_Age(tableKeyId, issueAge);

                if (interpolateInd.equalsIgnoreCase("N"))
                {
                    tableData = aRateTable.expandTableIssue(1, rateTableVOs);
                }
               else
                {
                    tableData = aRateTable.rateInterpolation(rateTableVOs);
                }
            }

			catch  (Exception e) {

    			System.out.println("B-RateTable.getExpandedTable(): Rates access problem: " + e);
			}


        } else  {
            throw new SPException("Invalid AccessType : "
				+ accessType
				+ " for table " + (tableKeyId + ""), SPException.TABLE_ACCESS_ERROR);
        }

        return tableData;
    }

     private static List expandTableAttain(int startAge, RateTableVO[] rateTableVOs)
            throws SMException {

        List tableData = new ArrayList();
        int i = startAge;
		for (int j = 0; j < rateTableVOs.length; j++) {
            RateTableVO row = rateTableVOs[j];
            EDITBigDecimal rate = new EDITBigDecimal(row.getRate());
            int age = row.getAge();
            if (age == i)  {
                tableData.add(rate);
                //System.out.println("Age = " + age + " Rate = " + rate
                //    + " i = " + i);
                i++;

            } else if (age > i)  {
                // Expand table
                while (age >= i)  {
                    tableData.add(rate);
                    //System.out.println("Age = " + age + " Rate = " + rate
                    //    + " i = " + i);
                    i++;
                }

            } else  {
                throw new SMException(
                    "There Is A Rate Table Problem With The Age Column", SPException.TABLE_ACCESS_ERROR);
            }

        }

        return tableData;
    }



    private static List expandTableIssue(int startAge, RateTableVO[] rateTableVOs)
            							   throws SMException {


        List tableData = new ArrayList();
        int i = startAge;
		for (int j = 0; j < rateTableVOs.length; j++) {

            RateTableVO row = rateTableVOs[j];
            EDITBigDecimal rate = new EDITBigDecimal(row.getRate());
            int duration = row.getDuration();
            if (duration == i)  {
                tableData.add(rate);
                //System.out.println("Duration = " + duration + " Rate = " +
                //rate + " i = " + i);
                i++;

            } else if (duration > i)  {
                // Expand table
                while (duration >= i)  {
                    tableData.add(rate);
                    //System.out.println("Duration = " + duration + " Rate = "
                    //    + rate + " i = " + i);
                    i++;
                }

            } else  {
                throw new SMException(
                    "There is a Rate Table problem with the duration column");
            }

        }

        return tableData;
    }

    private static List rateInterpolation(RateTableVO[] rateTableVOs)
    {

        /**  formula to apply to rates to fill in gap: highrate - lowrate/ highDuration - lowDuration = increamentValue
         *   incrementvalue is added to the prior duration rate and become the rate at the next duration
        **/
         List tableData = new ArrayList();

         for (int i = 0; i < rateTableVOs.length; i++)
         {
             RateTableVO rateTableVO = (RateTableVO)rateTableVOs[i];
             int lowDuration = rateTableVO.getDuration();
             EDITBigDecimal lowRate = new EDITBigDecimal(rateTableVO.getRate());
             tableData.add(lowRate);

             i++;
             if (i < rateTableVOs.length)
             {
                 rateTableVO = (RateTableVO)rateTableVOs[i];
                 int highDuration = rateTableVO.getDuration();
                 EDITBigDecimal highRate = new EDITBigDecimal(rateTableVO.getRate());

                 int durationDiff = (highDuration - lowDuration);

                 if (durationDiff > 1)
                 {
                     EDITBigDecimal incrementalValue = (highRate.subtractEditBigDecimal(lowRate)).divideEditBigDecimal(durationDiff + "");
                     EDITBigDecimal nextRate = new EDITBigDecimal(lowRate.getBigDecimal());

                     for (int j = lowDuration + 1; j < highDuration; j++)
                     {
                        nextRate = nextRate.addEditBigDecimal(incrementalValue);
                        tableData.add(nextRate);
                     }
                 }

                 i--;
             }
         }

         return tableData;
    }
 }
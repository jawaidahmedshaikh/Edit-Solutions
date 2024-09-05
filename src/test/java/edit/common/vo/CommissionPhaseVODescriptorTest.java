package edit.common.vo;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.exolab.castor.mapping.FieldDescriptor;
import org.junit.Before;
import org.junit.Test;


public class CommissionPhaseVODescriptorTest {
	CommissionPhaseVODescriptor desc;
	
	@Before
	public void setUp() throws Exception {
		desc = new CommissionPhaseVODescriptor();
	}

	@Test
	public void testCommissionPhaseVODescriptor() throws Exception {
	    long commissionPhasePK = 1l;
	    long premiumDueFK = 3l;
	    int commissionPhaseID = 4;
	    java.math.BigDecimal expectedMonthlyPremium = new BigDecimal(5);
	    java.math.BigDecimal prevCumExpectedMonthlyPrem = new BigDecimal(6); 
	    java.math.BigDecimal priorExpectedMonthlyPremium = new BigDecimal(7);
	    java.lang.String effectiveDate = "2020/03/22";
	    
		CommissionPhaseVO vo = new CommissionPhaseVO();
		
		FieldDescriptor[] fields = desc.getFields();
		List<String> handlerValueList = new ArrayList();

		for (FieldDescriptor field : fields) {
			if (field.getFieldName().contains("effectiveDate"))
			{
				field.getHandler().setValue(vo, effectiveDate);
				handlerValueList.add(field.getHandler().getValue(vo).toString());
			}
			if (field.getFieldName().contains("commissionPhasePK"))
			{
				field.getHandler().setValue(vo, commissionPhasePK);
				handlerValueList.add(field.getHandler().getValue(vo).toString());
			}
			if (field.getFieldName().contains("premiumDueFK"))
			{
				field.getHandler().setValue(vo, premiumDueFK);
				handlerValueList.add(field.getHandler().getValue(vo).toString());
			}
			if (field.getFieldName().contains("commissionPhaseID"))
			{
				field.getHandler().setValue(vo, commissionPhaseID);
				handlerValueList.add(field.getHandler().getValue(vo).toString());
			}
			if (field.getFieldName().contains("expectedMonthlyPremium"))
			{
				field.getHandler().setValue(vo, expectedMonthlyPremium);
				handlerValueList.add(field.getHandler().getValue(vo).toString());
			}
			if (field.getFieldName().contains("prevCumExpectedMonthlyPrem"))
			{
				field.getHandler().setValue(vo, prevCumExpectedMonthlyPrem);
				handlerValueList.add(field.getHandler().getValue(vo).toString());
			}
			if (field.getFieldName().contains("priorExpectedMonthlyPremium"))
			{
				field.getHandler().setValue(vo, priorExpectedMonthlyPremium);
				handlerValueList.add(field.getHandler().getValue(vo).toString());
			}

		}
		assertEquals(handlerValueList.toString(), "[1, 3, 4, 5, 6, 7, 2020/03/22]");
	}

}

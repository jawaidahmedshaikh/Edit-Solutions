package edit.common.vo;

// import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.exolab.castor.mapping.FieldDescriptor;
// import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;


public class FinancialHistoryVODescriptorTest {
	FinancialHistoryVODescriptor desc;
	
	@Before
	public void setUp() throws Exception {
		desc = new FinancialHistoryVODescriptor();
	}

	@Test
	public void testFinancialHistoryVODescriptor() throws Exception {
		FinancialHistoryVO vo = new FinancialHistoryVO();
		BigDecimal currentDeathBenefit = new BigDecimal("1");
		BigDecimal currentCorridorPercent = new BigDecimal("2");
		BigDecimal surrenderCharge = new BigDecimal("3");
		
	    for (FieldDescriptor field : desc.getFields()) {
			if (field.getFieldName().equals("_currentDeathBenefit")) {
				field.getHandler().setValue(vo, currentDeathBenefit);
				assertEquals(field.getHandler().getValue(vo), new BigDecimal("1"));
			} else if (field.getFieldName().equals("_currentCorridorPercent")) {
				field.getHandler().setValue(vo, currentCorridorPercent);
				assertEquals(field.getHandler().getValue(vo), new BigDecimal("2"));
			} else if (field.getFieldName().equals("_surrenderCharge")) {
				field.getHandler().setValue(vo, surrenderCharge);
				assertEquals(field.getHandler().getValue(vo), new BigDecimal("3"));
			}
	    }
	}
	
	@Test
	public void testFinancialHistoryVODescriptor_newInstance() throws Exception {
	    for (FieldDescriptor field : desc.getFields()) {
			if (field.getFieldName().equals("_currentDeathBenefit")) {
				// MatcherAssert.assertThat(field.getHandler().newInstance(null), instanceOf(BigDecimal.class));
			}
	    }

	}
}

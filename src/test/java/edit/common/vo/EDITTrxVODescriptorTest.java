package edit.common.vo;

import static org.junit.Assert.assertEquals;

import org.exolab.castor.mapping.FieldDescriptor;
import org.junit.Before;
import org.junit.Test;

public class EDITTrxVODescriptorTest {
	EDITTrxVODescriptor desc;
	
	@Before
	public void setUp() throws Exception {
		desc = new EDITTrxVODescriptor();
	}

	@Test
	public void testEDITTrxVODescriptor() throws Exception {
		EDITTrxVO vo = new EDITTrxVO();
		String noCommissionInd = "N";
		String zeroLoadInd = "N";

	    for (FieldDescriptor field : desc.getFields()) {
	    	if (field.getFieldName().equals("_noCommissionInd")) {
	    	    field.getHandler().setValue(vo, noCommissionInd);
	    	    assertEquals(field.getHandler().getValue(vo), "N");
	    	} else if ("_zeroLoadInd".equals(field.getFieldName())) {
	    	    field.getHandler().setValue(vo, zeroLoadInd);
	    	    assertEquals(field.getHandler().getValue(vo), "N");
	    	}
	    }
	}
}

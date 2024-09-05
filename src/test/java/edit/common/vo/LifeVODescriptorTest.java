package edit.common.vo;
import static org.junit.Assert.assertEquals;

import org.exolab.castor.mapping.FieldDescriptor;
import org.junit.Before;
import org.junit.Test;

public class LifeVODescriptorTest {
	LifeVODescriptor desc;
	
	@Before
	public void setUp() throws Exception {
		desc = new LifeVODescriptor();
	}

	@Test
	public void testLifeVODescriptor() throws Exception {
		LifeVO vo = new LifeVO();
	    String tamraStartDate = "2020/04/08";
	    String MAPEndDate = "2020/04/08";

	    for (FieldDescriptor field : desc.getFields()) {
	    	if (field.getFieldName().equals("_tamraStartDate")) {
	    	    field.getHandler().setValue(vo, tamraStartDate);
	    	    assertEquals(field.getHandler().getValue(vo), "2020/04/08");
	    	} else if ("_MAPEndDate".equals(field.getFieldName())) {
	    	    field.getHandler().setValue(vo, MAPEndDate);
	    	    assertEquals(field.getHandler().getValue(vo), "2020/04/08");
	    	}
	    }
	}

}

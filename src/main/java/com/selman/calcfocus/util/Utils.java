package com.selman.calcfocus.util;

import java.util.List;

import edit.common.vo.SegmentVO;

public class Utils {
	
	public static SegmentVO getDrivingSegmentVO(List<SegmentVO> segmentVOs, long drivingSegmentPk) {
		for (SegmentVO segmentVO : segmentVOs) {
		    if (segmentVO.getSegmentPK() == drivingSegmentPk) {
		    	return segmentVO;
		    }
		}
	    return null;
	}


}

package com.editsolutions.prd.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import electric.util.holder.booleanInOut;

public class PRDFileUtils {

	
	public static void createTriggerFile(File file, String message) throws IOException {
	    FileUtils.writeStringToFile(file, message, "UTF-8");	
	}

	public static boolean directoryExists(String path) {
        File dir = new File(path);
        // Tests whether the directory denoted by this abstract pathname exists.
        return dir.exists();
	}
}

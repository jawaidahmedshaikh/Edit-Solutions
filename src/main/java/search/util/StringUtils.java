package search.util;

public class StringUtils {
	public static String addZeroesToContract(String contractNumber) {
		String s1 = contractNumber.substring(0, 2);
		String s2 = contractNumber.substring(2);
		try {
		    return s1 + String.format("%08d", Integer.parseInt(s2));
		} catch (NumberFormatException e) {
			return contractNumber;
		}
	}

}

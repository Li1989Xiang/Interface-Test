package testBase;

import org.apache.commons.lang3.StringUtils;

public class StringCompartor {
	/**
	 * 比对字符串，并在不同处通过指定字符串进行标记
	 * @param str 原始字符串
	 * @param strToCompare 要比对的字符串
	 * @param flag 用于标记的字符串
	 * @return 若不同则返回带有标记的字符串，否则返回原字符串
	 */
	public static String differenceWithArrow(String str, String strToCompare, String flag) {
		String ret = str;
		int i = StringUtils.indexOfDifference(str, strToCompare);
		
		if(i >= 0) {
			StringBuilder sb = new StringBuilder(str);
			sb.insert(i, flag);
			ret = sb.toString();
		}
		
		return ret;
	}
}

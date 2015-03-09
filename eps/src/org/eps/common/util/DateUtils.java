package org.eps.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static String formatDate(Date d) {
		return formatDate(d, Constants.DEFAULT_DATE_PATTERN);
	}
	
	public static String formatDate(Date d, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		
		return format.format(d);
	}

}

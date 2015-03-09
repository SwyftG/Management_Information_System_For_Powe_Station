package org.eps.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class ConditionUtils {
	
	private ConditionUtils() {
		
	}

	public static Date getBeginDate(String beginDate) {
		if (StringUtils.isNotBlank(beginDate)) {
			try {
				DateFormat df = new SimpleDateFormat(Constants.DEFAULT_DATE_PATTERN);
				
				beginDate += " 00:00:00";
				
				return df.parse(beginDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public static Date getEndDate(String endDate) {
		if (StringUtils.isNotBlank(endDate)) {
			try {
				DateFormat df = new SimpleDateFormat(Constants.DEFAULT_DATE_PATTERN);
				
				endDate += " 23:59:59";
				
				return df.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

}

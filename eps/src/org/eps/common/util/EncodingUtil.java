package org.eps.common.util;

import java.io.UnsupportedEncodingException;

public final class EncodingUtil {

	public static String encodingString(String str) {
		try {
			return new String(str.getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}

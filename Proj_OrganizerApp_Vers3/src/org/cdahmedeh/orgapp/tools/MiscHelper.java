package org.cdahmedeh.orgapp.tools;

/**
 * Contains small helper methods for making life a bit easier. 
 * 
 * @author cdahmedeh
 */
public class MiscHelper {
	/**
	 * Removes spacing before and after string. If the input is null, then it is
	 * converted to a blank string "". 
	 * 
	 * @param string String to process.
	 * @return String with trailing whitespace removed.
	 */
	public static String safeTrim(String string){
		return string != null ? string.trim() : "";
	}
}

/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.server.servlet.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 * md5 security hash
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class SecurityUtil {

	private static Logger logger = Logger.getLogger(SecurityUtil.class);
	
	
	/**
	 * Return the MD5 hahs of a String. It will work correctly for most 
	 * strings. A word which does not work correctly is "michael" (check 
	 * against online MD5 hash tools).	
	 *
	 * @param toHash plain text string to encryption
	 * @return an md5 hashed string
	 */
	public static String getMD5Hash(String toHash) {	
	    String md5Hash = "";
	
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        
	        md.update(toHash.getBytes(), 0, toHash.length());
	
	        md5Hash = new BigInteger(1, md.digest()).toString(16);
			
	    } catch (NoSuchAlgorithmException e) {
	    	logger.error("NoSuchAlgorithmException while getting MD5 hash of '" + toHash + "'");
			logger.error(ExceptionUtils.getStackTrace(e));
	    }
	
	    return md5Hash;
	}

}

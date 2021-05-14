package com.jeebase.system.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author DELL
 */
public class PasswordUtils {

	public static String getPassword(String password) {
		byte[] salt = null;
		String hashAlgorithmName = "MD5";
		int hashIterations = 5;
		return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
	}
}

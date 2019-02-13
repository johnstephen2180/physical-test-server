package com.loankim.examonline.util;

import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author LamHa
 *
 */
public class Security {

	public static String encryptMD5(String key) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(key.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		String hashtext = bigInt.toString(16);

		// Now we need to zero pad it if you actually want the full 32 chars.
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}

		return hashtext;
	}


	public static String encryptMD5(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(data);
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		String hashtext = bigInt.toString(16);

		// Now we need to zero pad it if you actually want the full 32 chars.
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}

		return hashtext;
	}


	public static String genPrivateKey(String token, long userId) {
		try {
			byte[] baseBin = Base64.encodeBase64((token + userId).getBytes());
			String encryptMD5 = encryptMD5(baseBin);
			return encryptMD5.substring(0, 10);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}


	public static String encrypt(String key, String clearText) throws Exception {
		Cipher c = Cipher.getInstance("RC4");
		Key mainKey = new SecretKeySpec(key.getBytes(), "RC4");
		c.init(Cipher.ENCRYPT_MODE, mainKey);
		byte[] encVal = c.doFinal(clearText.getBytes("UTF-8"));
		return Base64.encodeBase64String(encVal);
	}


	public static String decrypt(String key, String encryptedData) throws Exception {
		Cipher c = Cipher.getInstance("RC4");
		Key mainKey = new SecretKeySpec(key.getBytes(), "RC4");
		c.init(Cipher.DECRYPT_MODE, mainKey);
		byte[] decordedValue = Base64.decodeBase64(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue, "UTF-8");
		return decryptedValue;
	}


}
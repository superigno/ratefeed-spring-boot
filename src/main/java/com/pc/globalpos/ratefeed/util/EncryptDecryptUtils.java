package com.pc.globalpos.ratefeed.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gino.q
 * @date April 8, 2020
 * 
 * Encryption/decryption using AES
 *
 */
public class EncryptDecryptUtils {

	private static final Logger logger = LoggerFactory.getLogger(EncryptDecryptUtils.class);
	private static final String KEY = "!7AJSLD#123";
	private static SecretKeySpec secretKey;
    
	public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            byte[] key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
	
	public static String encrypt(String strToEncrypt) {
		try {	
			setKey(KEY);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			logger.error("Error while encrypting: ", e);
		}
		return null;
	}

	public static String decrypt(String strToDecrypt) {
		try {
			setKey(KEY);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			logger.error("Error while decrypting: ", e);
		}
		return null;
	}
	
}

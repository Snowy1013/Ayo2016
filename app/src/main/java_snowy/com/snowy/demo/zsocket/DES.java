package com.snowy.demo.zsocket;

import android.util.Log;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES {
	private Cipher cipher;
	private SecretKey key;
	private SecureRandom random;
	private static final byte[] DES_KEY = { 21, 1, -110, 82, -32, -85, -128,
			-65 };
	public static DES unique = null;
	public static DES getInstance() {
		if (unique == null) {
			unique = new DES();
		}
		return unique;
	}
	private DES() {
		 random = new SecureRandom();
		try {
			DESKeySpec desKeySpec = new DESKeySpec(DES_KEY);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

				 key = keyFactory.generateSecret(desKeySpec);
					this.cipher = Cipher.getInstance("DES");
					// TODO Auto-generated catch block
//				cipher.init(Cipher.ENCRYPT_MODE, key, random);
				
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch(InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public  byte[] encryptBasedDes(String data) {
		// String encryptedData = null;
		byte[] encryptedData = null;
		try {
//			SecureRandom sr = new SecureRandom();
//			DESKeySpec deskey = new DESKeySpec(DES_KEY);
//			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//			SecretKey key = keyFactory.generateSecret(deskey);
//			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key, random);
			// encryptedData = new sun.misc.BASE64Encoder().encode(cipher
			// .doFinal(data.getBytes()));
			encryptedData = cipher.doFinal(data.getBytes());
		} catch (Exception e) {
//			throw new RuntimeException("RuntimeException", e);
			Log.w("push",e.getMessage());
			e.printStackTrace();
		}
		return encryptedData;
	}
	public byte[] encryptBasedDes(byte[] data) {
		if(data == null) {
			return null;
		}
		byte[] encryptedData = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key, random);
			encryptedData = cipher.doFinal(data);
		} catch (Exception e) {
//			throw new RuntimeException("RuntimeException", e);
			Log.w("push",e.getMessage());
			e.printStackTrace();
		}
		return encryptedData;
	}

	public  byte[] decryptBasedDes(byte[] cryptData) {
		byte[] decryptedData = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key, random);
			decryptedData = cipher.doFinal(cryptData);
		} catch (Exception e) {
			System.out.println("exception decryptBasedDes !!!!!!!!!!!~~~~~~~~~~~~~~~~~~~~~~~");
//			throw new RuntimeException("RuntimeException", e);
			Log.w("push",e.getMessage());
			e.printStackTrace();
			
		}
		return decryptedData;
	}
}

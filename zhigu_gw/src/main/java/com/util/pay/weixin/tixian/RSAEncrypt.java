package com.util.pay.weixin.tixian;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAEncrypt {

	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) throws Exception {
		try {
			byte[] buffer = Base64.decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("没有这样的算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("密钥无效");
		} catch (NullPointerException e) {
			throw new Exception("空指针");
		}
	}

	/**
	 * 
	 * 
	 * @param publicKey
	 * 
	 * @param plainTextData
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
		if (publicKey == null) {
			throw new Exception("");
		}
		Cipher cipher = null;
		try {

			cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");

			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("出错了");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("出错了");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("出错了");
		} catch (BadPaddingException e) {
			throw new Exception("出错了");
		}
	}

}
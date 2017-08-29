package haron.aimshoot.module;

import haron.aimshoot.vo.AuthVO;
import haron.aimshoot.vo.UserVO;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import Decoder.BASE64Encoder;

public class SecuritySafe {
	private KeyPairGenerator generator;
	private KeyPair keyPair;
	private KeyFactory keyFactory;
	
	private PrivateKey privateKey;
	private RSAPublicKeySpec publicSpec;
	private Cipher cipher;
	private byte[] encryptedBytes;
	private byte[] decryptedBytes;
	private String decryptedValue;
	private MessageDigest localMessageDigest;
	
	public void generateToken(UserVO userVo, AuthVO authVo){
		userVo.setAccess_token("{\"project_name\":\"" + Value.PROJECT_NAME + "\",\"user_no\":" + userVo.getUser_no() + ",\"last_login_ip\":\"" + userVo.getLast_login_ip() + "\",\"token_time\":" + System.currentTimeMillis() + "}");
		 // 暗号化
		 authVo.setAccess_token(encryptAccessToken(userVo.getAccess_token(), authVo));
	}

	public void createRSA(HttpServletRequest request, AuthVO authVo) {
		
		//　RSA初期化
		
		request.removeAttribute("authVo");
		try {
			generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(1024);
			keyPair = generator.genKeyPair();
			keyFactory = KeyFactory.getInstance("RSA");
			authVo.setPublicKey(keyPair.getPublic());
			privateKey = keyPair.getPrivate();
			publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(authVo.getPublicKey(), RSAPublicKeySpec.class);
			authVo.setPrivateKey(keyPair.getPrivate());
			authVo.setPublicKeyExponent(publicSpec.getPublicExponent().toString(16));
			authVo.setPublicKeyModulus(publicSpec.getModulus().toString(16));
			request.setAttribute("authVo", authVo);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
	
	// RSA暗号化の解読
	public String decryptedRSA(String val, AuthVO authVo){
		
	     try {
	    	 cipher = Cipher.getInstance("RSA");
	    	 encryptedBytes = hexToByteArray(val);
	    	 cipher.init(Cipher.DECRYPT_MODE, authVo.getPrivateKey());
			 decryptedBytes = cipher.doFinal(encryptedBytes);
			 decryptedValue = new String(decryptedBytes, "utf-8");
	     } catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    return decryptedValue;
	}

	private byte[] hexToByteArray(String hex) {
		 if (hex == null || hex.length() % 2 != 0) {
	            return new byte[]{};
	        }

	        byte[] bytes = new byte[hex.length() / 2];
	        for (int i = 0; i < hex.length(); i += 2) {
	            byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
	            bytes[(int) Math.floor(i / 2)] = value;
	        }
	        return bytes;
	}
	
	public String encryptBCrypt(String val){
		return BCrypt.hashpw(val, BCrypt.gensalt(10));
	}
	
	public String decryptAccessToken(AuthVO authVo){
		decryptedValue = null;
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, authVo.getPrivateKey());
			byte[] arrData = cipher.doFinal(authVo.getAccess_token());
			decryptedValue = new String(arrData);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}

		return decryptedValue;
	}
	
	public byte[] encryptAccessToken(String val, AuthVO authVo){
		
		System.out.println("encryptAccessToken11:" + val);
		encryptedBytes = null;
		try {
			 cipher = Cipher.getInstance("RSA");
	         cipher.init(Cipher.ENCRYPT_MODE, authVo.getPublicKey());
	         encryptedBytes = cipher.doFinal(val.getBytes("UTF8"));
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptedBytes;
		//return new String(encryptedBytes);
	}
}

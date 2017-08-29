package haron.aimshoot.vo;

import java.security.PrivateKey;
import java.security.PublicKey;

public class AuthVO {
	private PrivateKey privateKey;
	private String publicKeyModulus;
	private String publicKeyExponent;
	private PublicKey publicKey;
	private byte[] access_token;
	private String project_name;
	private int user_no;
	private String last_login_ip;
	private long token_time;
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	public String getPublicKeyModulus() {
		return publicKeyModulus;
	}
	public void setPublicKeyModulus(String publicKeyModulus) {
		this.publicKeyModulus = publicKeyModulus;
	}
	public String getPublicKeyExponent() {
		return publicKeyExponent;
	}
	public void setPublicKeyExponent(String publicKeyExponent) {
		this.publicKeyExponent = publicKeyExponent;
	}
	public PublicKey getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	public byte[] getAccess_token() {
		return access_token;
	}
	public void setAccess_token(byte[] access_token) {
		this.access_token = access_token;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public String getLast_login_ip() {
		return last_login_ip;
	}
	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}
	public long getToken_time() {
		return token_time;
	}
	public void setToken_time(long token_time) {
		this.token_time = token_time;
	}
}

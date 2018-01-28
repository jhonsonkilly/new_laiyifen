package com.netease.nim.demo.sync.util;

import java.security.MessageDigest;
import java.util.Random;

public class SHA1Util {
	

	
	private static final char[] HEX_DIGITS={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	
	private static final int BYTE_LEN = 20;
	
	private static byte[] encode(byte[] ming){
		if(ming==null){
			return null;
		}
		try{
			
			MessageDigest md = MessageDigest.getInstance("SHA1");
			
			md.update(ming);
			
			return md.digest();
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	private static String getFormattedText(byte[] bytes){
		StringBuilder buf = new StringBuilder(BYTE_LEN*2);

		for(int j=0;j<BYTE_LEN;j++){
			buf.append(HEX_DIGITS[(bytes[j]>>4)&0x0f]);
			buf.append(HEX_DIGITS[bytes[j]&0x0f]);
		}
		return buf.toString();
	}
	
	private static byte[] hex2Byte(String str){
		byte[] bytes = new byte[str.length()/2];
		for(int i=0;i<BYTE_LEN;i++){
			bytes[i]=(byte)Integer.parseInt(str.substring(2*i, 2*i+2),16);
		}
		return bytes;
	}
	
	public static String encodePWD(String src){
		if(src==null||"".equals(src)||src.length()!=(2*BYTE_LEN)){
			return null;
		}
		byte[] pwdB = hex2Byte(src);
		byte[] generateB = generateFixBytes();
		int len =10;		
		for(int i=0;i<len;i++){
			pwdB = hmac(pwdB, generateB);
		}
		String pwdStr = getFormattedText(pwdB);
		String keyStr = getFormattedText(generateB);
		return (pwdStr+keyStr);
	}
	
	public static boolean checkPWD(String pwd,String srcPWD){
		if(pwd==null||"".equals(pwd)||pwd.length()!=(2*BYTE_LEN)){

			return false;
		}
		if(srcPWD==null||"".equals(srcPWD)||srcPWD.length()!=(4*BYTE_LEN)){
		
			return false;
		}
		String keyStr = srcPWD.substring(40, 80);
		String pwdStr = srcPWD.substring(0, 40);		
		byte[] keyB = hex2Byte(keyStr);		
		byte[] checkPWD = hex2Byte(pwd);
		int len =10;		
		for(int i=0;i<len;i++){
			checkPWD = hmac(checkPWD, keyB);
		}		
		String result = getFormattedText(checkPWD);		
		return result.equals(pwdStr);
	}
	

	public static String encodeSHA1(String src)throws Exception{
		if(src==null||"".equals(src)){
			return null;
		}
		return getFormattedText(encode(src.getBytes()));
	}
	
	private static byte[] generateFixBytes(){
		Random random = new Random();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		return bytes;
	}
	
	private static byte[] hmac(byte[] pwd,byte[] key){
		
		byte[] k_ipad = new byte[84];
		byte[] k_opad = new byte[84];
		
		System.arraycopy(key, 0, k_ipad, 0, BYTE_LEN);
		System.arraycopy(key, 0, k_opad, 0, BYTE_LEN);
		
		int i=64;
		do{
			i--;
			k_ipad[i]=(byte)(k_ipad[i]^0x36);
			k_opad[i]=(byte)(k_opad[i]^0x5C);			
		}while(i!=0);
		
		System.arraycopy(pwd, 0, k_ipad, 64, BYTE_LEN);
		
		byte[] result1 = SHA1Util.encode(k_ipad);
		
		System.arraycopy(result1, 0, k_opad, 64, BYTE_LEN);
		
		return SHA1Util.encode(k_opad);
		
	}

	public static void main(String args[]) {	
		for (int i = 0; i < 10000; i++) {
			System.out.println((System.currentTimeMillis()+"").substring(4)+new Random().nextInt(10));
		}
		
//		try {
//			String pass = SHA1Util.encodeSHA1("123456");
//			System.out.println(pass);
//			System.out.println(SHA1Util.encodePWD(pass));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//System.out.println(System.getProperty("openfireHome"));
//		System.out.println(SHA1Util.getFormattedText(SHA1Util.encode("000000".getBytes())));
//		System.out.println(SHA1Util.checkPWD("2cfe2b9e6a907824b0c656236cddbfcc9140d738", "dd53f831d04ba3758e30bd04c0d0228eafd90eb206a1027644d2c260fbd90444372e05c4cb993527"));
	}  

}

package util;


import java.security.MessageDigest;

public class Util {

	/**
	 * MessageDigest 라이브러리 이용 
	 * SHA-256 해시 알고리즘 적용
	 * SHA-256 해시 적용 바이트 배열 형태로 반
	 * @param input
	 * @return
	 */
	public static String getHash(String input) {
		StringBuffer result = new StringBuffer();
		
	    try {
	    	MessageDigest md = MessageDigest.getInstance("SHA-256");
	    	md.update(input.getBytes());
	    	
	    	byte bytes[] = md.digest();
	    	for (int i = 0; i < bytes.length; i++) {
				result.append(
						Integer.toString((bytes[i] & 0xff)+ 0x100, 16).substring(1)
				);
				
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return result.toString();
	}
}


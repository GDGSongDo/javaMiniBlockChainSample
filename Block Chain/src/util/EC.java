package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.bouncycastle.util.encoders.Base64;

public class EC {

	// 세부 알고리즘으로 sect163k1 을 사용합니다.
	private final String ALGORITHM = "sect163k1";
	
	public void generate(String privateKeyName, String publicKeyName) throws Exception{
		
		// 바운시 캐슬의 타원 곡선표준 알고리즘(ECDSA)를 사용합니다.
		KeyPairGenerator generator = KeyPairGenerator.getInstance("ECDSA", "BC");
		
		// 타원 곡선의 세부 알고리즘으로 sect163k1 을 사용합니다.
		ECGenParameterSpec ecsp;
		ecsp = new ECGenParameterSpec(ALGORITHM);
		generator.initialize(ecsp, new SecureRandom());
		
		// 해당 알고리즘으로 랜덤의 키 한 쌍을 생성합니다.
		KeyPair keyPair = generator.generateKeyPair();
		System.out.println("타원 곡선 암호키 한 쌍을 생성했습니");
		
		// 생성한 키 한 쌍에서 개인키와 공개키를 추출합니다.
		PrivateKey priv = keyPair.getPrivate();
		PublicKey pub = keyPair.getPublic();
		
		// 개인키와 공개키를 특정한 파일 이름으로 저장합니다.
		writePemFile(priv, "EC PRIVATE KEY", privateKeyName);
		writePemFile(pub, "EC PUBLIC KEY", publicKeyName);
				
	}
	
	// Pem 클래스를 이용해 생성된 암호키를 파일로 저장하는 함수입니다.
	private void writePemFile(Key key, String description, String filename) throws FileNotFoundException, IOException {
		Pem pemFile = new Pem(key, description);
		pemFile.write(filename);
		System.out.println(String.format("EC 암호키 %s을(를) %s 파일로 내보냈습니다.",description, filename));
	}
	
	// 문자열 형태의 인증서에서 개인키를 추출하는 함수
	public PrivateKey readPrivateKeyFromPemFile(String privateKeyName) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		String data = readString(privateKeyName);
		System.out.println("EC 개인키를 "+privateKeyName + "로부터 불러왔습니다.");
		System.out.println(data);
		
		// 불필요한 설명 구문을 제거합니다.
		data = data.replaceAll("-----BEGIN EC PRIVATE KEY-----", "");
		data = data.replaceAll("-----END EC PRIVATE KEY-----", "");
		
		// PEM 파일은 Base64로 인코딩 되어있으므로 디코딩해서 읽을 수 있도록 합니다.
		byte[] decoded = Base64.decode(data);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
		KeyFactory factory = KeyFactory.getInstance("ECDSA");
		PrivateKey privateKey = factory.generatePrivate(spec);
		
		return privateKey;
	}
	
	// 문자열 형태의 인증서에서 공개키를 추출하는 함수입니다.
	  public PublicKey readPublicKeyFromPemFile(String publicKeyName)
	      throws FileNotFoundException, IOException, NoSuchAlgorithmException, 
	      InvalidKeySpecException {
	    String data = readString(publicKeyName);
	    System.out.println("EC 개인키를 " + publicKeyName + "로부터 불러왔습니다.");
	    System.out.print(data);

	    // 불필요한 설명 구문을 제거합니다.
	    data = data.replaceAll("-----BEGIN EC PUBLIC KEY-----", "");
	    data = data.replaceAll("-----END EC PUBLIC KEY-----", "");

	    // PEM 파일은 Base64로 인코딩 되어있으므로 디코딩해서 읽을 수 있도록 합니다.
	    byte[] decoded = Base64.decode(data);
	    X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
	    KeyFactory factory = KeyFactory.getInstance("ECDSA");
	    PublicKey publicKey = factory.generatePublic(spec);

	    return publicKey;
	  }
	  
	// 특정한 파일에 작성되어 있는 문자열을 그대로 읽어오는 함수
	private String readString(String filename) throws FileNotFoundException, IOException{
		String pem = "";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = br.readLine())!=null) {
			pem += line + "\n";
		}
			br.close();
			return pem;
		
	}
}

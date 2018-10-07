package core;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;

import util.EC;
import util.Util;

public class BlockChainStarter {

	public static void main(String[] args) {
		
		/**
		 * nonce 구하기 
		 */
		/*
		int nonce = 0;
		while (true) {
			if(Util.getHash(nonce+"").substring(0,6).equals("000000")) {
				System.out.println("정답 : "+nonce);
				break;
			}
			nonce ++;
		}
		*/
		
		/*
		Block block = new Block(1,0, "바뀐 데이터");
		block.mine();
		block.getInformation();
		*/
		/*
		Block block1 = new Block(1, null, 0, "데이터");
	    block1.mine();
	    block1.getInformation();

	    Block block2 = new Block(2, block1.getBlockHash(), 0, "데이터");
	    block2.mine();
	    block2.getInformation();

	    Block block3 = new Block(3, block2.getBlockHash(), 0, "데이터");
	    block3.mine();
	    block3.getInformation();

	    Block block4 = new Block(4, block3.getBlockHash(), 0, "데이터");
	    block4.mine();
	    block4.getInformation();
		 */
		
		/*
		Transaction transaction = new Transaction("나동빈", "박한울", 1.5);

		System.out.println(transaction.getInformation());
		*/
		
		// 5강
		/*
		Block block1 = new Block(1, null, 0, new ArrayList());
		block1.mine();
		block1.showInformation();

		Block block2 = new Block(2, block1.getBlockHash(), 0, new ArrayList());
		block2.addTransaction(new Transaction("나동빈", "박한울", 1.5));
		block2.addTransaction(new Transaction("이태일", "박한울", 0.7));
		block2.mine();
		block2.showInformation();

		Block block3 = new Block(3, block2.getBlockHash(), 0, new ArrayList());
		block3.addTransaction(new Transaction("강종구", "이상욱", 8.2));
		block3.addTransaction(new Transaction("박한울", "나동빈", 0.4));
		block3.mine();
		block3.showInformation();

		Block block4 = new Block(4, block3.getBlockHash(), 0, new ArrayList());
		block4.addTransaction(new Transaction("이상욱", "강종구", 0.1));
		block4.mine();
		block4.showInformation();
		*/
		
		// 6강
		/*
		KeyPairGenerator kpg;
		try {
			// 무작위의 개인키와 공개키를 생성하기 위해 키 생성 객체를 정의합니다.
			kpg = KeyPairGenerator.getInstance("EC","SunEC");
			
			// 타원 곡선 디지털 서명 알고리즘 객체를 생성합니다.
			ECGenParameterSpec ecsp;
			// 세부 알고리즘 스펙을 정의합니다.
			ecsp = new ECGenParameterSpec("sect163k1");
			// 랜덤으로 임의의 키를 생성합니다.
			kpg.initialize(ecsp, new SecureRandom());
			
			// 개인키와 공개키 한 쌍을 생성합니다.
			KeyPair kp = kpg.genKeyPair();
			PrivateKey privKey = kp.getPrivate();
			PublicKey pubKey = kp.getPublic();
			
			// 서명 객체를 생성해 개인키를 설정합니다.
			Signature ecdsa;
			ecdsa = Signature.getInstance("SHA1withECDSA","SunEC");
			ecdsa.initSign(privKey);
			
			// 임의의 원래 문장을 정의합니다.
			String text = "동빈이가 한율이에게 100 코인 전송";
			System.out.println("원래 문장 : "+text);
			
			// 임의의 변경 문장을 정의합니다.
			String textInfected = "동빈이가 한율이에게 99999 코인 전송";
			System.out.println("변경 문장 : "+textInfected);
						
			// 원래 문장에 대해 암호화를 수행해 서명 값(암호문)을 얻어냅니다.
			ecdsa.update(text.getBytes("UTF-8"));
			byte[] signatureByte = ecdsa.sign();
			
			System.out.println("암호문 : 0x"+(new BigInteger(1, signatureByte).toString(16)).toUpperCase());
			
			// 서명 객체를 생성해 공개키를 이용해 복호화할 수 있도록 설정합니다.
			Signature signature;
			signature = Signature.getInstance("SHA1withECDSA", "SunEC");
			signature.initVerify(pubKey);
			
			// 원래 문장을 공개키로 복호화해 검증합니다.
			signature.update(text.getBytes("UTF-8"));
			System.out.println("원래 문장 검증 : "+ signature.verify(signatureByte));
			
			signature.update(textInfected.getBytes("UTF-8"));
			System.out.println("변경된 문장 검증 : "+ signature.verify(signatureByte));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		// 7강
		/*
		// 바운시 캐슬의 암호화 라이브러리를 사용하도록 설정합니다.
		Security.addProvider(new BouncyCastleProvider());
		
		// 타원 곡선 객체를 생성해 개인키와 공개키를 각각 private.pem, public.pem 으로 저장합니다.
		EC ec = new EC();
		try {
			ec.generate("private.pem", "public.pem");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		// 8강 예시 1
		/*
		//  바운시 캐슬의 암호화 라이브러리를 사용하도록 설정합니다.
		Security.addProvider(new BouncyCastleProvider());
		
		// 타원 곡선 객체를 생성해 개인키와 공개키를 각각 private.pem, public.pem으로 저장합니다.
		EC ec = new EC();
		try {
			ec.generate("private.pem", "public.pem");
			
			// 파일로 저장한 개인키와 공개키를 다시 프로그램으로 불러옵니다.
			PrivateKey privateKey = ec.readPrivateKeyFromPemFile("private.pem");
			PublicKey publicKey = ec.readPublicKeyFromPemFile("public.pem");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		// 8강 예시 2
		
		/*
		//  바운시 캐슬의 암호화 라이브러리를 사용하도록 설정합니다.
		Security.addProvider(new BouncyCastleProvider());
			
		// 타원 곡선 객체를 생성해 개인키와 공개키를 각각 private.pem, public.pem으로 저장합니다.
		EC ec = new EC();
		
		// 총 두 쌍의 키를 생성해 파일 형태로 저장합니다.
		try {
			ec.generate("private1.pem", "public1.pem");
			ec.generate("private2.pem", "public2.pem");
			
			// 파일 형태로 저장한 키 데이터를 프로그램으로 불러옵니다.
			PrivateKey privateKey1 = ec.readPrivateKeyFromPemFile("private1.pem");
			PublicKey publicKey1 = ec.readPublicKeyFromPemFile("public1.pem");
			PrivateKey privateKey2 = ec.readPrivateKeyFromPemFile("private2.pem");
			PublicKey publicKey2 = ec.readPublicKeyFromPemFile("public2.pem");
			
			Signature ecdsa;
			ecdsa = Signature.getInstance("SHA1withECDSA");
			// 개인키 1을 이용해 암호화(서명)합니다.
			ecdsa.initSign(privateKey1);
			
			String text = "평문입니다.";
			System.out.println("평문 정보 : "+text);
			byte[] baText = text.getBytes("UTF-8");
			
			// 평문 데이터를 암호화하여 서명한 데이터를 출력합니다.
			ecdsa.update(baText);
			byte[] baSignature = ecdsa.sign();
			System.out.println("서명됀 값 : 0x"+(new BigInteger(1, baSignature).toString(16)).toUpperCase());
			
			Signature signature;
			signature = Signature.getInstance("SHA1withECDSA");
			
			// 검증할 때는 공개키 2를 이용해 복호화를 수행합니다.
			signature.initVerify(publicKey2);
			signature.update(baText);
			boolean result = signature.verify(baSignature);
			
			// 개인키와 매칭되는 공개키가 아니므로 복호화에 실패합니다.
			System.out.println("신원 검증 : "+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		// 9강 - 개인지갑 기능
		// 개인키, 공개키, 지갑 주소 정보를 이용 다른 사람과 거래
		// 개인 지갑 기능 - 필요할 때마다 개인키 및 지갑 주소를 생성할 수 있으며
		//               자신의 지갑 주소에서 다른 지갑 주소로 가상화폐를 전송할 수 있는 기능
		/*
		Security.addProvider(new BouncyCastleProvider());
		EC ec = new EC();
		try {
			ec.generate("private1.pem", "public1.pem");
			ec.generate("private2.pem", "public2.pem");
			ec.generate("private3.pem", "public3.pem");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		// 10강
		// 9강에서 만든 세쌍의 키를 이용해 다양한 트랜잭션을 생성하고 검증하는 프로그램
		// 특정한 트랜잭션의 서명이 정상적이지 않다면 블록에 추가되지 않음 
		Security.addProvider(new BouncyCastleProvider());
		
		try {
			Wallet wallet1 = new Wallet();
			wallet1.setFromFile("private1.pem", "public1.pem");
			Wallet wallet2 = new Wallet();
			wallet2.setFromFile("private2.pem", "public2.pem");
			Wallet wallet3 = new Wallet();
			wallet3.setFromFile("private3.pem", "public3.pem");
			
			
			Block block1 = new Block(1, null, 0, new ArrayList());
			block1.mine();
			block1.showInformation();
			
			Block block2 = new Block(2, block1.getBlockHash(), 0, new ArrayList());
			
			// 지갑 1에서 지갑2로 코인을 전송했다는 의미를 가진 트랜잭션 생성
			Transaction transaction1 = new Transaction(wallet1, wallet2.getPublicKey(), 1.5, "2018-10-07 20:01:12.2");
			block2.addTransaction(transaction1);
			
			// 지갑 2에서 지갑 3으로 코인을 전송했다는 의미를 가진 트랜잭션을 생성합니다.
			Transaction transaction2 = new Transaction(wallet2, wallet3.getPublicKey(), 2.4, "2018-10-07 20:01:13.2");
			block2.mine();
			block2.showInformation();
			
			Block block3 = new Block(3, block2.getBlockHash(), 0, new ArrayList());
			
			// 지갑 1에서 지갑 3으로 코인을 전송했다는 의미를 가진 트랜잭션 생성
			Transaction transaction3 = new Transaction(wallet1, wallet3.getPublicKey(), 4.3, "2018-10-07 21:03:12.2");
			block3.addTransaction(transaction3);
			
			// 지갑 2에서 지갑3으로 코인을 전송했다는 의미를 가진 트랜잭션을 생성
			Transaction transaction4 = new Transaction(wallet2, wallet3.getPublicKey(),1.2, "2018-10-07 20:11:12.2");
			block3.addTransaction(transaction4);
			
			block3.mine();
			block3.showInformation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Next - 채굴 보상(Reward), 수수
		
		
	}
}
 
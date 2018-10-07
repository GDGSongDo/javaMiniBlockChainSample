package core;

import java.math.BigInteger;
import java.security.Signature;
import java.util.ArrayList;

import util.Util;

public class Block {

	/*
	private int blockID;
	private String previousBlockHash;
	private int nonce;
	private String data;
	private ArrayList<Transaction> transactionList;
	
	public int getBlockID() {
		return blockID;
	}
	public void setBlockID(int blockID) {
		this.blockID = blockID;
	}
	public int getNonce() {
		return nonce;
	}
	public void setNonce(int nonce) {
		this.nonce = nonce;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getPreviousBlockHash() {
		return previousBlockHash;
	}
	public void setPreviousBlockHash(String previousBlockHash) {
		this.previousBlockHash = previousBlockHash;
	}
	
	public Block(int blockID, String previousBlockHash, int nonce, String data) {
		this.blockID = blockID;
		this.nonce = nonce;
		this.data = data;
		this.previousBlockHash = previousBlockHash;
	}
	
	public Block(int blockID, String previousBlockHash, int nonce, ArrayList transactionList) {
		this.blockID = blockID;
		this.nonce = nonce;
		this.previousBlockHash = previousBlockHash;
		this.transactionList = transactionList;
	}
	
	public void addTransaction(Transaction transaction) {
		transactionList.add(transaction);
	}
	public void getInformation() {
		System.out.println("----------------------------");
		System.out.println("블록 번호 : "+getBlockID());
		System.out.println("이전 해시 : "+getPreviousBlockHash());
		System.out.println("채굴 변수 값 : "+getNonce());
		System.out.println("블록 데이터 : "+ getData());
		System.out.println("블록 해시 : " + getBlockHash());
		System.out.println("----------------------------");
	}
	public void showInformation() {
		System.out.println("----------------------------");
		System.out.println("블록 번호 : "+getBlockID());
		System.out.println("이전 해시 : "+getPreviousBlockHash());
		System.out.println("채굴 변수 값 : "+getNonce());
		System.out.println("트랜잭션 개수 : "+transactionList.size() + "개");
		for (int i = 0; i < transactionList.size(); i++) {
			System.out.println(transactionList.get(i).getInformation());
		}
		System.out.println("블록 해시 : " + getBlockHash());
		System.out.println("----------------------------");
	}
	public String getBlockHash() {
		// return Util.getHash(nonce+data+previousBlockHash);
		String transactionInformations = "";
		for (int i = 0; i < transactionList.size(); i++) {
			transactionInformations += transactionList.get(i).getInformation();
		}
		return Util.getHash(nonce+transactionInformations+previousBlockHash);
	}
	
	public void mine() {
		while(true) {
			if(getBlockHash().substring(0,4).equals("0000")) {
				System.out.println(blockID+"번째 블록의 채굴에 성공했습니다.");
				break;
			}
			nonce++;
		}
	}
	*/
	
	// 9강
	private static final String ALGORITHM = "SHA1withECDSA";
	
	private int blockID;
	private String previousBlockHash;
	private int nonce;
	private ArrayList<Transaction> transactionList;
	
	public int getBlockID() {
		return blockID;
	}
	public void setBlockID(int blockID) {
		this.blockID = blockID;
	}
	public String getPreviousBlockHash() {
		return previousBlockHash;
	}
	public void setPreviousBlockHash(String previousBlockHash) {
		this.previousBlockHash = previousBlockHash;
	}
	public int getNonce() {
		return nonce;
	}
	public void setNonce(int nonce) {
		this.nonce = nonce;
	}
	
	public Block(int blockID, String previousBlockHash, int nonce, ArrayList<Transaction> transactionList) {
		this.blockID = blockID;
		this.previousBlockHash = previousBlockHash;
		this.nonce = nonce;
		this.transactionList = transactionList;
	}
	
	// 특정한 트랜잭션이 정상적인지 검증합니다.
	private boolean verifyTransaction(Transaction transaction) throws Exception {
		Signature signature;
		signature = Signature.getInstance(ALGORITHM);
		byte[] baText = transaction.getData().getBytes("UTF-8");
		signature.initVerify(transaction.getSender());
		signature.update(baText);
		return signature.verify(new BigInteger(transaction.getSignature(), 16).toByteArray());
	}
	
	// 정상적인 트랜잭션에 한해서 블록에 추가합니다.
	public void addTransaction(Transaction transaction) throws Exception{
		if(verifyTransaction(transaction)) {
			System.out.println("정상적인 트랜잭션을 발견했습니다");
			transactionList.add(transaction);
		} else {
			System.out.println("트랜잭션이 바르게 인증되지 않았습니");
		}
	}
	
	public void showInformation() {
		System.out.println("----------------------------");
		System.out.println("블록 번호 : "+getBlockID());
		System.out.println("이전 해시 : "+getPreviousBlockHash());
		System.out.println("채굴 변수 값 : "+getNonce());
		System.out.println("트랜잭션 개수 : "+transactionList.size() + "개");
		for (int i = 0; i < transactionList.size(); i++) {
			System.out.println(transactionList.get(i).getInformation());
		}
		System.out.println("블록 해시 : " + getBlockHash());
		System.out.println("----------------------------");
	}
	
	public String getBlockHash() {
		String transactionInformations = "";
		for (int i = 0; i < transactionList.size(); i++) {
			transactionInformations += transactionList.get(i).getInformation();
		}
		return Util.getHash(nonce+transactionInformations+previousBlockHash);
	}
	
	public void mine() {
		while(true) {
			if(getBlockHash().substring(0, 4).equals("0000")) {
				System.out.println(blockID + "번째 블록의 채굴에 성공했습니다.");
				break;
			}
			nonce++;
		}
	}
}

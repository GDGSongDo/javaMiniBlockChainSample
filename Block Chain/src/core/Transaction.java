package core;

import java.security.PublicKey;
import java.sql.Timestamp;

import util.Util;

public class Transaction {
	
	/*
	String sender;
	String receiver;
	double amount;
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public Transaction(String sender, String receiver, double amount) {
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
				
	}
	
	public String getInformation() {
		return sender+"이(가) "+receiver+"에게 "+amount+"개의 코인을 보냈습니다.";
	}
	*/
	
	// 9강 signature, timestamp 추가
	String signature;
	PublicKey sender;
	PublicKey receiver;
	double amount;
	Timestamp timestamp;
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public PublicKey getSender() {
		return sender;
	}
	public void setSender(PublicKey sender) {
		this.sender = sender;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public PublicKey getReceiver() {
		return receiver;
	}
	
	// 서명 값을 포함한 트랜잭션 정보를 출력합니다.
	public String getInformation() {
		return "<"+signature+">\n"+
				new Util().getHash(sender.toString())+"->"+
				new Util().getHash(receiver.toString())+":"+
				amount+"개 ("+timestamp+")";
	}
	
	// 서명 값을 제외한 단순 트랜잭션 정보를 출력합니다.
	public String getData() {
		return new Util().getHash(sender.toString())+"->"+
				new Util().getHash(receiver.toString())+":"+
				amount+"개 ("+timestamp+")";
	}
	
	// 어떠한 지갑 주소로 얼마만큼의 가상화폐를 보냈는지에 대한 정보를 기준으로 초기화합니다.
	public Transaction(Wallet wallet, PublicKey receiver, double amount, String timestamp) throws Exception{
		this.sender = wallet.getPublicKey();
		this.receiver = receiver;
		this.amount = amount;
		this.timestamp = java.sql.Timestamp.valueOf(timestamp);
		this.signature = wallet.sign(getData());
	}
	
}

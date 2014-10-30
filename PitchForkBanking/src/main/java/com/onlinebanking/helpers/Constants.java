package com.onlinebanking.helpers;

public final class Constants {
	public static enum TransactionType {
	    TRANSFER, CREDIT, DEBIT 
	}
	
	public static enum RequestType {
	    ACCOUNT, TRANSACTION 
	}
	
	public static final double CRITICALTRANSACTION = 1000.0;
}

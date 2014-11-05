package com.onlinebanking.helpers;

public final class Constants {
	public static enum TransactionType {
	    TRANSFER, CREDIT, DEBIT 
	}
	
	public static enum RequestType {
	    ACCOUNT, TRANSACTION, CREATEACCOUNT 
	}
	
	public static final double CRITICALTRANSACTION = 1000.0;
	public static final int MIN_ACCOUNTNUMBER = 100000;
	public static final int MAX_ACCOUNTNUMBER = 999999;
}

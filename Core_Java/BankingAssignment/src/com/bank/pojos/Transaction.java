package com.bank.pojos;

public class Transaction {

	// BOOT_UP transaction takes place any time the RunBank's main function is run. QUIT is when the main function ends
	public enum transactionType {BOOT_UP, QUIT, LOGIN, LOGOUT, CREATE_ACCOUNT, WITHDRAW_CHECKING,
		TRANSFER_SAVINGS, DEPOSIT_CHECKING, DEPOSIT_SAVINGS, VIEW_ACCOUNT, EDIT_ACCOUNT,
		CLERK_LOGIN, CLERK_LOGOUT};

	// This is used to delimit fields in the toString and fromString methods
	public static final String delimit = "::";
	
	// The accountID for the account the transaction was carried out on. Equals 0 for no account
	private int accountID;
	// The id of the clerk overseeing the transaction if applicable. Equals 0 for no clerkk
	private int clerkID;
	// The username of the account the transaction was carried out on. Empty for no account
	private String username;
	// The date and time of the transaction
	private String date;
	// The type of transaction carried out
	private final transactionType type;
	// Summarizes the transaction that took place. Empty for simple transactions such as BOOT_UP, QUIT, LOGIN, LOGOUT, etc.
	private String summary;
	// Boolean stating whether a transaction was successful
	private boolean successful;
	// Used to summarize the reason for failed transactions. Empty for successful transactions
	private String reasonFailed;

	public Transaction(transactionType type) {
		super();
		this.type = type;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public int getClerkID() {
		return clerkID;
	}

	public void setClerkID(int clerkID) {
		this.clerkID = clerkID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public String getReasonFailed() {
		return reasonFailed;
	}

	public void setReasonFailed(String reasonFailed) {
		this.reasonFailed = reasonFailed;
	}

	public static String getDelimit() {
		return delimit;
	}

	public transactionType getType() {
		return type;
	}

	// FIXME double check I have all necessary values before writing these two methods
	public String toString() {
		return accountID + delimit + clerkID + delimit + username + delimit + date + delimit + 
				type + delimit + summary + delimit + successful + delimit + reasonFailed;
	}
	public static Transaction fromString(String str) throws NumberFormatException {
		String[] splitStr = str.split(delimit);
		Transaction tran = new Transaction(transactionType.valueOf(splitStr[4]));
		tran.setAccountID(Integer.parseInt(splitStr[0]));
		tran.setClerkID(Integer.parseInt(splitStr[1]));
		tran.setUsername(splitStr[2]);
		tran.setDate(splitStr[3]);
		tran.setSummary(splitStr[5]);
		tran.setSuccessful(Boolean.parseBoolean(splitStr[6]));
		tran.setReasonFailed(splitStr[7]);
		
		return tran;
	}
	
	// Validaes whether these strings can be used to make this object. Uses delimit
	public boolean validateStrings() {
		
		return !(username.contains(delimit) || date.contains(delimit) || 
				  summary.contains(delimit) || reasonFailed.contains(delimit));
	}

}

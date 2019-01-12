package com.az.rest.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

	@JsonIgnore
    private Long accountId;

	@JsonProperty(required = true)
	private String IBAN;
	
    @JsonProperty(required = true)
    private String accountHolder;

    @JsonProperty(required = true)
    private String accountType;
    
    @JsonProperty(required = true)
    private BigDecimal balance;    

    public Account() {
    }    
    
	public Account(String iBAN, String accountHolder, String accountType, BigDecimal balance) {
		super();
		IBAN = iBAN;
		this.accountHolder = accountHolder;
		this.accountType = accountType;
		this.balance = balance;
	}

	public Account(Long accountId, String iBAN, String accountHolder, String accountType, BigDecimal balance) {
		super();
		this.accountId = accountId;
		IBAN = iBAN;
		this.accountHolder = accountHolder;
		this.accountType = accountType;
		this.balance = balance;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long userAccountId) {
		this.accountId = userAccountId;
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IBAN == null) ? 0 : IBAN.hashCode());
		result = prime * result + ((accountHolder == null) ? 0 : accountHolder.hashCode());
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (IBAN == null) {
			if (other.IBAN != null)
				return false;
		} else if (!IBAN.equals(other.IBAN))
			return false;
		if (accountHolder == null) {
			if (other.accountHolder != null)
				return false;
		} else if (!accountHolder.equals(other.accountHolder))
			return false;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", IBAN=" + IBAN + ", accountHolder=" + accountHolder
				+ ", accountType=" + accountType + ", balance=" + balance + "]";
	}    
    	
}

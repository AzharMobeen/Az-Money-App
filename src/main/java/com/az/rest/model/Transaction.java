package com.az.rest.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {	
	
	@JsonIgnore
	private Long transactionId;
	
	@JsonProperty(required = true)
	private String fromIBAN;
	@JsonProperty(required = true)
	private String toIBAN;
	@JsonProperty(required = true)
	private BigDecimal amount;
	
	@JsonIgnore
	private BigDecimal transactionFee;
	
	public Transaction() {}	
	public Transaction(String fromIBAN, String toIBAN, BigDecimal amount, BigDecimal transactionFee) {
		super();
		this.fromIBAN = fromIBAN;
		this.toIBAN = toIBAN;
		this.amount = amount;
		this.transactionFee = transactionFee;
	}
	public Transaction(Long transactionId, String fromIBAN, String toIBAN, BigDecimal amount, BigDecimal transactionFee) {
		super();
		this.transactionId = transactionId;
		this.fromIBAN = fromIBAN;
		this.toIBAN = toIBAN;
		this.amount = amount;
		this.transactionFee = transactionFee;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getFromIBAN() {
		return fromIBAN;
	}

	public void setFromIBAN(String fromIBAN) {
		this.fromIBAN = fromIBAN;
	}

	public String getToIBAN() {
		return toIBAN;
	}

	public void setToIBAN(String toIBAN) {
		this.toIBAN = toIBAN;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}	

	public BigDecimal getTransactionFee() {
		return transactionFee;
	}
	
	public void setTransactionFee(BigDecimal transactionFee) {
		this.transactionFee = transactionFee;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((fromIBAN == null) ? 0 : fromIBAN.hashCode());
		result = prime * result + ((toIBAN == null) ? 0 : toIBAN.hashCode());
		result = prime * result + ((transactionFee == null) ? 0 : transactionFee.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
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
		Transaction other = (Transaction) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (fromIBAN == null) {
			if (other.fromIBAN != null)
				return false;
		} else if (!fromIBAN.equals(other.fromIBAN))
			return false;
		if (toIBAN == null) {
			if (other.toIBAN != null)
				return false;
		} else if (!toIBAN.equals(other.toIBAN))
			return false;
		if (transactionFee == null) {
			if (other.transactionFee != null)
				return false;
		} else if (!transactionFee.equals(other.transactionFee))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", fromIBAN=" + fromIBAN + ", toIBAN=" + toIBAN
				+ ", amount=" + amount + ", transactionFee=" + transactionFee + "]";
	}			
}

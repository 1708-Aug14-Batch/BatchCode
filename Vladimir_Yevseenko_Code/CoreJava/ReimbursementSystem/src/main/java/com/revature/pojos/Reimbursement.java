package com.revature.pojos;

import java.sql.Date;

import org.apache.log4j.Logger;

import com.revature.logging.Logging;

public class Reimbursement {
	private final int id;
	private final User submitter, resolver;
	private final Date submissionDate, resolutionDate;
	private final Status status;
	private final String description;
	private final double amount;
	
	private static Logger log = Logging.getLogger();
	
	
	public Reimbursement(User submitter, User resolver, Date submissionDate,
			Date resolutionDate, Status reimbursementStatus, String description, double amount) {
		this.id = -1;
		this.submitter = submitter;
		this.resolver = resolver;
		this.submissionDate = submissionDate;
		this.resolutionDate = resolutionDate;
		this.status = reimbursementStatus;
		this.description = description;
		this.amount = amount;
		log.debug("Reimbursement(...) created");
	}
	
	public Reimbursement(int id, User submitter, User resolver, Date submissionDate,
			Date resolutionDate, Status reimbursementStatus, String description, double amount) {
		this.id = id;
		this.submitter = submitter;
		this.resolver = resolver;
		this.submissionDate = submissionDate;
		this.resolutionDate = resolutionDate;
		this.status = reimbursementStatus;
		this.description = description;
		this.amount = amount;
		log.debug("Reimbursement(id, ...) created");
	}
	
	public int getId() {
		return id;
	}
	
	public User getSubmitter() {
		return submitter;
	}
	
	public User getResolver() {
		return resolver;
	}
	
	public Date getSubmissionDate() {
		return submissionDate;
	}
	
	public Date getResolutionDate() {
		return resolutionDate;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getAmount() {
		return amount;
	}
}

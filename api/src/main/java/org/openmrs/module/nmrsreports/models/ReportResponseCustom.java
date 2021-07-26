package org.openmrs.module.nmrsreports.models;

import java.math.BigInteger;
import java.util.Date;

public class ReportResponseCustom {
	
	BigInteger encounterId;
	
	Date encounterDate;
	
	int patientId;
	
	String identifier;
	
	String patientName;
	
	String encounterType;
	
	public BigInteger getEncounterId() {
		return encounterId;
	}
	
	public void setEncounterId(BigInteger encounterId) {
		this.encounterId = encounterId;
	}
	
	public Date getEncounterDate() {
		return encounterDate;
	}
	
	public void setEncounterDate(Date encounterDate) {
		this.encounterDate = encounterDate;
	}
	
	public int getPatientId() {
		return patientId;
	}
	
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public String getPatientName() {
		return patientName;
	}
	
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	public String getEncounterType() {
		return encounterType;
	}
	
	public void setEncounterType(String encounterType) {
		this.encounterType = encounterType;
	}
}

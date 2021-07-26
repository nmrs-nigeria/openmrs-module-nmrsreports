package org.openmrs.module.nmrsreports.models;

import java.math.BigInteger;

public class PatientList {
	
	int patientId;
	
	String eid;
	
	String pepid;
	
	String hospitalNo;
	
	String ancNo;
	
	String exposedInfantId;
	
	String hts;
	
	String pep;
	
	String recencyID;
	
	String biometricCaptured;
	
	String validCapture;
	
	public int getPatientId() {
		return patientId;
	}
	
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	
	public String getEid() {
		return eid;
	}
	
	public void setEid(String eid) {
		this.eid = eid;
	}
	
	public String getPepid() {
		return pepid;
	}
	
	public void setPepid(String pepid) {
		this.pepid = pepid;
	}
	
	public String getHospitalNo() {
		return hospitalNo;
	}
	
	public void setHospitalNo(String hospitalNo) {
		this.hospitalNo = hospitalNo;
	}
	
	public String getAncNo() {
		return ancNo;
	}
	
	public void setAncNo(String ancNo) {
		this.ancNo = ancNo;
	}
	
	public String getExposedInfantId() {
		return exposedInfantId;
	}
	
	public void setExposedInfantId(String exposedInfantId) {
		this.exposedInfantId = exposedInfantId;
	}
	
	public String getHts() {
		return hts;
	}
	
	public void setHts(String hts) {
		this.hts = hts;
	}
	
	public String getPep() {
		return pep;
	}
	
	public void setPep(String pep) {
		this.pep = pep;
	}
	
	public String getRecencyID() {
		return recencyID;
	}
	
	public void setRecencyID(String recencyID) {
		this.recencyID = recencyID;
	}
	
	public String getBiometricCaptured() {
		return biometricCaptured;
	}
	
	public void setBiometricCaptured(String biometricCaptured) {
		this.biometricCaptured = biometricCaptured;
	}
	
	public String getValidCapture() {
		return validCapture;
	}
	
	public void setValidCapture(String validCapture) {
		this.validCapture = validCapture;
	}
}

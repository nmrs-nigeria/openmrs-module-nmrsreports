package org.openmrs.module.nmrsreports.models;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public class Wrapper {
	
	private List<?> data;
	
	private int draw;
	
	private int recordsTotal;
	
	//private int pageLength;
	
	private int recordsFiltered;
	
	public Wrapper() {
	}
	
	public Wrapper(int draw, int recordsTotal, int recordsFiltered, List<?> data) {
		this.data = data;
		this.recordsTotal = recordsTotal;
		//this.pageLength = pageLength;
		this.recordsFiltered = recordsFiltered;
		this.draw = draw;
	}
}

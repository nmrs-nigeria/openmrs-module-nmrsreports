package org.openmrs.module.nmrsreports.fragment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.openmrs.EncounterType;
import org.openmrs.api.context.Context;
import org.openmrs.module.nmrsreports.api.NmrsreportsService;
import org.openmrs.module.nmrsreports.models.EncounterListResponse;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

public class EncountersFragmentController {
	
	NmrsreportsService nmrsreportingService;
	
	ObjectMapper mapper;
	
	public void controller(FragmentModel model) {
		String metadataModuleVersion = null;
		List<EncounterType> encounterTypes = Context.getEncounterService().getAllEncounterTypes();
		model.addAttribute("encounterTypes", encounterTypes);
	}
	
	@RequestMapping(value = "getEncounters", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getEncounters(@RequestParam("start") int start, @RequestParam("draw") int draw,
	        @RequestParam("length") int pageLength, @RequestParam(value = "from", required = false) Date from,
	        @RequestParam(value = "to", required = false) Date to,
	        @RequestParam(value = "identifier", required = false) String identifier,
	        @RequestParam("encounterTypeId") int encounterTypeId,
	        @RequestParam(value = "ndrVisitId", required = false) String ndrVisitId) {
		List<EncounterListResponse> returningData = null;
		JsonObject jsonObject = null;
		
		int recordsTotal = 10;
		int pageLengtheDefault = pageLength;
		int recordsFiltered = 10;
		nmrsreportingService = Context.getService(NmrsreportsService.class);
		List<EncounterListResponse> patientLists = nmrsreportingService.getEncounterTypeReports(from, to, encounterTypeId,
		    identifier, ndrVisitId);
		if (patientLists.size() >= 10) {
			returningData = patientLists.subList(start, pageLength + start);
		} else {
			recordsTotal = patientLists.size();
			pageLengtheDefault = pageLength;
			recordsFiltered = patientLists.size();
			returningData = patientLists;
		}
		Map<String, Object> json = new HashMap();
		json.put("draw", String.valueOf(draw++));
		json.put("recordsTotal", patientLists.size());
		json.put("recordsFiltered", patientLists.size());
		json.put("data", returningData);
		return json;
	}
	
}

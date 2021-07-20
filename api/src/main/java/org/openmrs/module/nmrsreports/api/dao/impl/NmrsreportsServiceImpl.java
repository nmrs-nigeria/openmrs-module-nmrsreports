/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nmrsreports.api.dao.impl;

import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.Module;
import org.openmrs.module.nmrsreports.Item;
import org.openmrs.module.nmrsreports.api.NmrsreportsService;
import org.openmrs.module.nmrsreports.api.dao.NmrsreportsDao;
import org.openmrs.module.nmrsreports.models.EncounterListResponse;
import org.openmrs.module.nmrsreports.models.PatientList;
import org.openmrs.module.nmrsreports.models.ReportResponse;

import java.util.Date;
import java.util.List;

public class NmrsreportsServiceImpl extends BaseOpenmrsService implements NmrsreportsService {
	
	NmrsreportsDao dao;
	
	UserService userService;
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(NmrsreportsDao dao) {
		this.dao = dao;
	}
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public Item getItemByUuid(String uuid) throws APIException {
		return dao.getItemByUuid(uuid);
	}
	
	@Override
	public Item saveItem(Item item) throws APIException {
		if (item.getOwner() == null) {
			item.setOwner(userService.getUser(1));
		}
		
		return dao.saveItem(item);
	}
	
	@Override
	public List<Module> getModules() throws APIException {
		return this.dao.getModules();
	}
	
	@Override
	public List<PatientList> getPatientList() throws APIException {
		return this.dao.getPatientList();
	}
	
	@Override
	public List<ReportResponse> getReports(Date from, Date to, String issueType) throws APIException {
		return this.dao.getReports(from, to, issueType);
	}
	
	@Override
	public List<EncounterListResponse> getEncounterTypeReports(Date from, Date to, int encounterTypeId, String identifier,
	        String ndrVisitId) throws APIException {
		return this.dao.getEncounterTypeReports(from, to, encounterTypeId, identifier, ndrVisitId);
	}
	
}

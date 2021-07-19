package org.openmrs.module.nmrsreports.scripts;

import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import java.util.Date;

public class ReportScripts {
	
	private static DbSessionFactory sessionFactory;
	
	public static String loadScript(Date from, Date to, String issueType) {
		String queryString = null;
		switch (issueType) {
			case "getPatientWithARVRefillIssues":
				queryString = getPatientWithARVRefillIssues(from, to);
				break;
			case "getPatientDrugPickupButNORegimen":
				queryString = getPatientDrugPickupButNORegimen(from, to);
				break;
			case "getPatientNoLGA":
				queryString = getPatientNoLGA(from, to);
				break;
			case "getPatientNoState":
				queryString = getPatientNoLGA(from, to);
				break;
		}
		return queryString;
	}
	
	public static String getPatientWithARVRefillIssues(Date from, Date to) {
		
		StringBuilder queryString = new StringBuilder(
		        "SELECT encounterId,encounterDate, patientId, identifier, patientName,encounterType  FROM(\n"
		                + "SELECT \n"
		                + "MAX(encounter.`encounter_id`)  AS encounterId, \n"
		                + "DATE(last_pickups.last_pickup_date)  AS encounterDate,\n"
		                + "IF(getconceptvalV2(MAX(encounter.`encounter_id`),159368,encounter.patient_id) = 0,1,getconceptvalV2(MAX(encounter.`encounter_id`),159368,encounter.patient_id)) AS `daysOfARVRefil`,\n"
		                + "encounter.`patient_id` AS patientId,\n"
		                + "patient_identifier.`identifier`,\n"
		                + "CONCAT(pn.`family_name`, \" \", pn.`given_name`) patientName,\n"
		                + "encounter_type.`name` AS encounterType\n"
		                + "FROM `encounter` AS encounter INNER JOIN (\n"
		                + "SELECT patient_id, MAX(`encounter_datetime`) last_pickup_date FROM  `encounter` WHERE `encounter_type` = 13  AND `voided` =0 GROUP BY `patient_id` ) AS last_pickups\n"
		                + "ON(last_pickups.patient_id = encounter.`patient_id` AND last_pickups.last_pickup_date = encounter.`encounter_datetime` AND encounter.`encounter_type` = 13 )\n"
		                + "JOIN patient_identifier  ON(patient_identifier.`patient_id` = encounter.`patient_id`)\n"
		                + "JOIN `encounter_type` ON (encounter.`encounter_type` = encounter_type.`encounter_type_id`)\n"
		                + "JOIN `person_name` pn ON(pn.`person_id`=encounter.patient_id AND pn.voided=0 AND pn.`preferred`=1)\n"
		                + "LEFT JOIN obs obsgroup ON  (obsgroup.concept_id=162240 AND obsgroup.encounter_id=encounter.encounter_id)\n"
		                + "\n"
		                + "WHERE encounter.`voided` =0 \n"
		                + "GROUP BY encounter.`patient_id`) AS art_table  WHERE art_table.DaysOfARVRefil IS  NULL OR art_table.DaysOfARVRefil =0  OR art_table.DaysOfARVRefil > 180");
		if (from != null)
			queryString.append(" AND (art_table.encounterDate >= :fromDate OR art_table.encounterDate >= :fromDate)");
		if (to != null)
			queryString.append(" AND (art_table.encounterDate <= :toDate OR art_table.encounterDate >= :toDate)");
		return queryString.toString();
	}
	
	public static String getPatientDrugPickupButNORegimen(Date from, Date to) {
		
		StringBuilder queryString = new StringBuilder(
		        "SELECT \n"
		                + "regimen_list.encounterId, regimen_list.encounterDate, regimen_list.patient_id AS patientId,  \n"
		                + "patient_identifier.`identifier`,\n"
		                + "CONCAT(pn.`family_name`, \" \", pn.`given_name`) patientName,\n"
		                + "encounter_type.`name` AS encounterType\n"
		                + "FROM (\n"
		                + "SELECT \n"
		                + "encounter.`encounter_id` AS encounterId,\n"
		                + "encounter.`encounter_type` AS encounter_type,\n"
		                + "DATE(encounter.`encounter_datetime`)  AS encounterDate,\n"
		                + "obs.`concept_id`,\n"
		                + "obs.`value_coded`,\n"
		                + "obs.`voided`,\n"
		                + "encounter.`patient_id`\n"
		                + "FROM `encounter` AS encounter  \n"
		                + "LEFT JOIN obs ON(obs.`encounter_id` = encounter.encounter_id AND obs.concept_id IN (164506, 164513, 165702, 164507, 164514, 165703) )\n"
		                + "WHERE encounter.`encounter_type` = 13 AND encounter.`voided` =0 AND (obs.`voided` =0 OR obs.`voided` IS NULL) AND \n"
		                + "encounter.`encounter_datetime` between '2021-05-01' and now()\n"
		                + "GROUP BY  encounter.`encounter_id`\n"
		                + ") AS regimen_list\n"
		                + "JOIN patient_identifier  ON(patient_identifier.`patient_id` = regimen_list.`patient_id`)\n"
		                + "JOIN `encounter_type` ON (regimen_list.`encounter_type` = encounter_type.`encounter_type_id`)\n"
		                + "JOIN `person_name` pn ON(pn.`person_id`=regimen_list.patient_id AND pn.voided=0 AND pn.`preferred`=1)\n"
		                + "WHERE  regimen_list.value_coded IS NULL\n" + "GROUP BY regimen_list.encounterId");
		if (from != null)
			queryString.append(" AND (regimen_list.encounterDate >= :fromDate OR regimen_list.encounterDate >= :fromDate)");
		if (to != null)
			queryString.append(" AND (regimen_list.encounterDate <= :toDate OR regimen_list.encounterDate >= :toDate)");
		return queryString.toString();
	}
	
	public static String getPatientNoLGA(Date from, Date to) {
		
		StringBuilder queryString = new StringBuilder(
		        "SELECT  CAST(encounterId AS UNSIGNED) AS encounterId, encounterDate, patientId, identifier,patientName,encounterType  FROM(\n"
		                + "SELECT \n"
		                + "REPLACE( DATE(patient.`date_created`), '-', '' )  AS encounterId,\n"
		                + "'Patient Address' AS encounterType,\n"
		                + "DATE(patient.`date_created`) AS encounterDate,\n"
		                + "patient.patient_id AS patientId,\n"
		                + "patient_identifier.`identifier` , \n"
		                + "CONCAT(pn.`family_name`, \" \", pn.`given_name`) patientName,\n"
		                + "pa.city_village,\n"
		                + "pa.state_province\n"
		                + "FROM patient\n"
		                + "LEFT JOIN `person_address` pa ON (pa.`person_id` = patient.patient_id AND pa.`voided` =0)\n"
		                + "JOIN patient_identifier  ON(patient_identifier.`patient_id` = patient.`patient_id`)\n"
		                + "JOIN `person_name` pn ON(pn.`person_id`=patient.patient_id AND pn.voided=0 AND pn.`preferred`=1)\n");
		if (from != null)
			queryString.append(" AND (patient.date_created >= :fromDate OR patient.date_created >= :fromDate)");
		if (to != null)
			queryString.append(" AND (patient.date_created <= :toDate OR patient.date_created >= :toDate)");
		
		queryString
		        .append("GROUP BY patient.patient_id ) AS patientAdress \n"
		                + "WHERE patientAdress.city_village IS NULL OR patientAdress.city_village  = 'null' OR patientAdress.city_village  = ''");
		
		return queryString.toString();
	}
	
	public static String getPatientNoState(Date from, Date to) {
		
		StringBuilder queryString = new StringBuilder(
		        "SELECT CAST(encounterId AS UNSIGNED) as encounterId , encounterDate, patientId, identifier,patientName,encounterType  FROM(\n"
		                + "SELECT \n"
		                + "REPLACE( DATE(patient.`date_created`), '-', '' )  AS encounterId,\n"
		                + "'Patient Address' AS encounterType,\n"
		                + "DATE(patient.`date_created`) AS encounterDate,\n"
		                + "patient.patient_id AS patientId,\n"
		                + "patient_identifier.`identifier` , \n"
		                + "CONCAT(pn.`family_name`, \" \", pn.`given_name`) patientName,\n"
		                + "pa.city_village,\n"
		                + "pa.state_province\n"
		                + "FROM patient\n"
		                + "LEFT JOIN `person_address` pa ON (pa.`person_id` = patient.patient_id AND pa.`voided` =0)\n"
		                + "JOIN patient_identifier  ON(patient_identifier.`patient_id` = patient.`patient_id`)\n"
		                + "JOIN `person_name` pn ON(pn.`person_id`=patient.patient_id AND pn.voided=0 AND pn.`preferred`=1)\n");
		if (from != null)
			queryString.append(" AND (patient.date_created >= :fromDate OR patient.date_created >= :fromDate)");
		if (to != null)
			queryString.append(" AND (patient.date_created <= :toDate OR patient.date_created >= :toDate)");
		
		queryString
		        .append("GROUP BY patient.patient_id ) AS patientAdress \n"
		                + "WHERE patientAdress.city_village IS NULL OR patientAdress.city_village  = 'null' OR patientAdress.city_village  = ''");
		
		return queryString.toString();
	}
	
	public static String getPatientList() {
		
		StringBuilder queryString = new StringBuilder(
		        "SELECT \n"
		                + "p.`patient_id` AS patientId,    \n"
		                + "MAX(IF(pidentifier.identifier_type=3,  pidentifier.identifier,'')) eid,\n"
		                + "MAX(IF(pidentifier.identifier_type=4,  pidentifier.identifier,'')) pepid,\n"
		                + "MAX(IF(pidentifier.identifier_type=5,  pidentifier.identifier,'')) hospitalNo,\n"
		                + "MAX(IF(pidentifier.identifier_type=6,  pidentifier.identifier,'')) ancNo,\n"
		                + "MAX(IF(pidentifier.identifier_type=7,  pidentifier.identifier,'')) exposedInfantId,\n"
		                + "MAX(IF(pidentifier.identifier_type=8,  pidentifier.identifier,'')) hts,\n"
		                + "MAX(IF(pidentifier.identifier_type=9,  pidentifier.identifier,'')) pep,\n"
		                + "MAX(IF(pidentifier.identifier_type=10,  pidentifier.identifier,'')) recencyID,\n"
		                + "IF(biometrictable.patient_Id IS NOT NULL,'Yes','No') AS biometricCaptured,\n"
		                + "IF(biometrictable.patient_Id IS NOT NULL,IF(invalidprint.patient_Id IS NOT NULL,'No','Yes'),\"\") AS validCapture\n"
		                + "\n"
		                + "FROM `patient` p\n"
		                + "\n"
		                + "LEFT JOIN  ( SELECT \n"
		                + "patient_identifier.`patient_id` as patientId,\n"
		                + "patient_identifier.`identifier`,\n"
		                + "`identifier_type`\n"
		                + "FROM `patient_identifier`   \n"
		                + "\n"
		                + ") AS pidentifier ON (pidentifier.patientId = p.`patient_id`)\n"
		                + "\n"
		                + "LEFT JOIN `person_name` pn ON(pn.`person_id`=p.patient_id AND p.voided=0 AND pn.`preferred`=1)\n"
		                + "INNER JOIN person ON(person.person_id=p.patient_id)\n"
		                + "LEFT JOIN (\n"
		                + "    SELECT \n"
		                + "    DISTINCT biometricinfo.patient_Id\n"
		                + "    FROM \n"
		                + "    biometricinfo\n"
		                + "    ) AS biometrictable \n"
		                + "    ON(p.patient_id=biometrictable.patient_Id AND p.voided=0)\n"
		                + "    LEFT JOIN (\n"
		                + "    SELECT \n"
		                + "    DISTINCT biometricinfo.patient_Id\n"
		                + "    FROM \n"
		                + "    biometricinfo WHERE template NOT LIKE 'Rk1S%' OR CONVERT(new_template USING utf8) NOT LIKE 'Rk1S%'\n"
		                + "    ) AS invalidprint \n" + "    ON(p.patient_id=invalidprint.patient_Id AND p.voided=0)\n"
		                + "    LEFT JOIN global_property ON(global_property.property='facility_datim_code')\n" + "    \n"
		                + "GROUP BY p.`patient_id` \n" + "\n");
		return queryString.toString();
	}
	
	public static String getEncounterList(Date from, Date to, int encounterType, String identifier, String ndrVisitId) {
		
		StringBuilder queryString = new StringBuilder("SELECT \n" + "encounter.`encounter_id` AS encounterId ,  \n"
		        + "REPLACE( DATE(encounter.`encounter_datetime`), '-', '' )  AS ndrEncounterId,\n"
		        + "DATE(encounter.`encounter_datetime`) AS encounterDate,\n" + "encounter.`patient_id` AS patientId,\n"
		        + "\n" + "pidentifier.`identifier`,\n" + "CONCAT(pn.`family_name`, \" \", pn.`given_name`) patientName,\n"
		        + "encounter_type.`name` AS encounterType,\n" + "encounter_type.`encounter_type_id` AS encounterTypeId\n"
		        + "FROM `encounter`\n"
		        + "JOIN `encounter_type` ON ( encounter.`encounter_type` = encounter_type.`encounter_type_id`)\n"
		        + "LEFT JOIN  ( SELECT \n" + "    patient_identifier.`patient_id`,\n"
		        + "    patient_identifier.`identifier`,\n" + "    `identifier_type`\n"
		        + "    FROM `patient_identifier`   \n" + "    \n"
		        + "    ) AS pidentifier ON (pidentifier.patient_id = encounter.`patient_id`)\n"
		        + "LEFT JOIN `person_name` pn ON(pn.`person_id`=encounter.patient_id AND pn.voided=0 AND pn.`preferred`=1)"
		        + " where encounter_type.`encounter_type_id` = :encounter_type_id ");
		
		if (from != null)
			queryString
			        .append(" AND (encounter.encounter_datetime >= :fromDate OR encounter.encounter_datetime >= :fromDate)");
		if (to != null)
			queryString.append(" AND (encounter.encounter_datetime <= :toDate OR encounter.encounter_datetime >= :toDate)");
		
		if (identifier != null)
			queryString.append(" AND pidentifier.identifier like :identifier ");
		
		if (ndrVisitId != null)
			queryString.append(" AND REPLACE( DATE(encounter.`encounter_datetime`), '-', '' ) like :ndrVisitId ");
		queryString.append(" GROUP BY encounter.`encounter_id`");
		
		return queryString.toString();
	}
}

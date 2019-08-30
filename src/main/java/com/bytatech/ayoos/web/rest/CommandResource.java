package com.bytatech.ayoos.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytatech.ayoos.client.patient_dms.api.*;
import com.bytatech.ayoos.client.patient_dms.model.*;
import com.bytatech.ayoos.client.patient_dms.model.SiteBodyCreate.VisibilityEnum;
import com.bytatech.ayoos.service.*;
import com.bytatech.ayoos.service.dto.AddressLineDTO;
import com.bytatech.ayoos.service.dto.PatientDTO;
import com.bytatech.ayoos.web.rest.errors.BadRequestAlertException;
import com.bytatech.ayoos.web.rest.util.HeaderUtil;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;

@RestController
@RequestMapping("/api/commands")
public class CommandResource {

	private final Logger log = LoggerFactory.getLogger(CommandResource.class);

	private static final String ENTITY_NAME = "Patient";

	@Autowired
	PeopleApi peopleApi;
	@Autowired
	private Decoder decoder;
	@Autowired
	private Encoder encoder;

	private final CommandService commandService;
	private final AddressLineService addressLineService;

	public CommandResource(Encoder encoder,Decoder decoder, CommandService commandService,
			AddressLineService addressLineService) {
		this.encoder = encoder;
		this.decoder = decoder;
		this.commandService = commandService;
		this.addressLineService = addressLineService;
	}

	/**
	 * POST /patients : Create a new patient.
	 *
	 * @param patientDTO
	 *            the patientDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         patientDTO, or with status 400 (Bad Request) if the patient has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */

	
	  @PostMapping("/patients") 
	  public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) throws URISyntaxException {
	  log.debug("REST request to save Patient : {}", patientDTO); if
	  (patientDTO.getId() != null) { throw new
	  BadRequestAlertException("A new patient cannot already have an ID",
	  ENTITY_NAME, "idexists"); }
	  createPersonOnDMS(patientDTO);
	  
	  SitesApi site=getSiteApiClient(patientDTO.getPatientCode(),patientDTO.getPatientCode());
	  
	  String siteId= patientDTO.getPatientCode()+"site";
	  String dmsId = createSite(site,siteId);
	  patientDTO.setDmsId(dmsId); 
	  PatientDTO result = commandService.save(patientDTO);
	  
	  return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME,
	  result.getId().toString())) .body(result); }

	/**
	 * PUT /patients : Updates an existing patient.
	 *
	 * @param patientDTO
	 *            the patientDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         patientDTO, or with status 400 (Bad Request) if the patientDTO is not
	 *         valid, or with status 500 (Internal Server Error) if the patientDTO
	 *         couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	/*
	 * @PutMapping("/patients") public ResponseEntity<PatientDTO>
	 * updatePatient(@RequestBody PatientDTO patientDTO) throws URISyntaxException {
	 * log.debug("REST request to update Patient : {}", patientDTO); if
	 * (patientDTO.getId() == null) { throw new
	 * BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull"); } PatientDTO
	 * result = commandService.save(patientDTO); return ResponseEntity.ok()
	 * .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME,
	 * patientDTO.getId().toString())).body(result); }
	 */

	/**
	 * POST /address-lines : Create a new addressLine.
	 *
	 * @param addressLineDTO
	 *            the addressLineDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         addressLineDTO, or with status 400 (Bad Request) if the addressLine
	 *         has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	/*
	 * @PostMapping("/address-lines") public ResponseEntity<AddressLineDTO>
	 * createAddressLine(@RequestBody AddressLineDTO addressLineDTO) throws
	 * URISyntaxException { log.debug("REST request to save AddressLine : {}",
	 * addressLineDTO); if (addressLineDTO.getId() != null) { throw new
	 * BadRequestAlertException("A new addressLine cannot already have an ID",
	 * ENTITY_NAME, "idexists"); } AddressLineDTO resultDTO =
	 * addressLineService.save(addressLineDTO); if (resultDTO.getId() == null) {
	 * throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull"); }
	 * AddressLineDTO result = addressLineService.save(resultDTO); return
	 * ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME,
	 * result.getId().toString())) .body(result); }
	 */
	/**
	 * PUT /address-lines : Updates an existing addressLine.
	 *
	 * @param addressLineDTO
	 *            the addressLineDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         addressLineDTO, or with status 400 (Bad Request) if the
	 *         addressLineDTO is not valid, or with status 500 (Internal Server
	 *         Error) if the addressLineDTO couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	/*
	 * @PutMapping("/address-lines") public ResponseEntity<AddressLineDTO>
	 * updateAddressLine(@RequestBody AddressLineDTO addressLineDTO) throws
	 * URISyntaxException { log.debug("REST request to update AddressLine : {}",
	 * addressLineDTO); if (addressLineDTO.getId() == null) { throw new
	 * BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull"); }
	 * AddressLineDTO result = addressLineService.save(addressLineDTO); return
	 * ResponseEntity.ok() .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME,
	 * addressLineDTO.getId().toString())) .body(result); }
	 */

	public void createPersonOnDMS(PatientDTO patientDTO) {
		log.debug("=================into the process createPeople()===========");

		PersonBodyCreate personBodyCreate = new PersonBodyCreate();
		personBodyCreate.setId(patientDTO.getPatientCode());
		personBodyCreate.setFirstName(patientDTO.getPatientCode());
		personBodyCreate.setEmail(patientDTO.getPatientCode()+"@gmail.com");
		personBodyCreate.setPassword(patientDTO.getPatientCode());
		peopleApi.createPerson(personBodyCreate, null);

	}

	public PersonEntry getPersonOnDMS(String patientCode) {
		return peopleApi.getPerson(patientCode, null).getBody();
	}

	public SitesApi getSiteApiClient(String userName, String password) {
		SitesApi sitesApi = Feign.builder().encoder(encoder).decoder(decoder)
				.requestInterceptor(new BasicAuthRequestInterceptor("userName", "password"))
				.target(SitesApi.class, "http://34.74.192.113:8082/alfresco/api/-default-/public/alfresco/versions/1");
	
		return sitesApi;
	}

	/**
	 * Create a new patientDMS-Site.
	 *
	 * @param siteId
	 *            the patientDMS-Site to create
	 *
	 */

	public String createSite(SitesApi site,String siteId) {
		SiteBodyCreate siteBodyCreate = new SiteBodyCreate();
		siteBodyCreate.setTitle(siteId);
		siteBodyCreate.setId(siteId);
		siteBodyCreate.setVisibility(VisibilityEnum.MODERATED);
		SiteEntry entry = site.createSite(siteBodyCreate).getBody();
		return entry.getEntry().getId();
	}

}

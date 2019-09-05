package com.bytatech.ayoos.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytatech.ayoos.client.patient_dms.api.*;
import com.bytatech.ayoos.client.patient_dms.model.*;
import com.bytatech.ayoos.client.patient_dms.model.SiteBodyCreate.VisibilityEnum;
import com.bytatech.ayoos.domain.User;
import com.bytatech.ayoos.security.SecurityUtils;
import com.bytatech.ayoos.service.*;
import com.bytatech.ayoos.service.dto.AddressLineDTO;
import com.bytatech.ayoos.service.dto.PatientDTO;
import com.bytatech.ayoos.web.rest.errors.BadRequestAlertException;
import com.bytatech.ayoos.web.rest.util.HeaderUtil;

import feign.Client;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;

@Import(FeignClientsConfiguration.class)
@RestController

public class CommandResource {

	private final Logger log = LoggerFactory.getLogger(CommandResource.class);

	private static final String ENTITY_NAME = "Patient";



	private FooClient fooClient;
	
	@Autowired
	private PeopleApi peopleApis;
	private Decoder decoder;

	private Encoder encoder;

	private CommandService commandService;
	private AddressLineService addressLineService;
	private UserService user;


	public CommandResource(Encoder encoder, Decoder decoder, CommandService commandService,
			AddressLineService addressLineService, UserService user) {
		this.encoder = encoder;
		this.decoder = decoder;
		this.commandService = commandService;
		this.addressLineService = addressLineService;
		this.user = user;
	}

	@GetMapping("/test/{s}")
	public void Test(@PathVariable String s) {
		 FooClient foo= getSiteApiClient("abdul.rafeek@com","tks");
		createSite(foo,s);
	}
	
	
	public FooClient getSiteApiClient(String userName, String password) {
		this.fooClient = Feign.builder().encoder(encoder).decoder(decoder)
				.requestInterceptor(new BasicAuthRequestInterceptor(userName, password))
				.target(FooClient.class, "https://tohpih.trial.alfresco.com/alfresco/api/-default-/public/alfresco/versions/1");
	
		return fooClient;
	}


	public String createSite(FooClient foo,String siteId) {
		SiteBodyCreate siteBodyCreate = new SiteBodyCreate();
		siteBodyCreate.setTitle(siteId);
		siteBodyCreate.setId(siteId);
		siteBodyCreate.setVisibility(VisibilityEnum.MODERATED);
		SiteEntry entry = foo.createSite(siteBodyCreate).getBody();
		return entry.getEntry().getId();
	}
}

/*
 * Copyright 2013-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bytatech.ayoos.client.patient_dms.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.bytatech.ayoos.client.patient_dms.model.PersonBodyCreate;
import com.bytatech.ayoos.client.patient_dms.model.PersonEntry;
import com.bytatech.ayoos.client.patient_dms.model.SiteBodyCreate;
import com.bytatech.ayoos.client.patient_dms.model.SiteEntry;

import feign.RequestLine;
import io.swagger.annotations.ApiParam;



public interface FooClient {
/*	@RequestLine("GET /foos")
    List<Foo> getFoos();*/
	
	
	
	
	/*	@RequestLine("GET /sites")
		    ResponseEntity<SitePaging> listSites();*/


		@RequestLine("POST /people")
		ResponseEntity<PersonEntry> createPerson(PersonBodyCreate personBodyCreate);
	
		@RequestLine("POST /sites")
		 ResponseEntity<SiteEntry> createSite( SiteBodyCreate siteBodyCreate);
	
	
}

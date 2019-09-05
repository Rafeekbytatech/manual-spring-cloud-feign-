
  package com.bytatech.ayoos.client.patient_dms.api;
  
import org.springframework.cloud.openfeign.FeignClient;

import com.bytatech.ayoos.client.patient_dms.ClientConfiguration;

@FeignClient(name = "${patientDMS.name:patientDMS}", url = "${patientDMS.url:https://tohpih.trial.alfresco.com/alfresco/api/-default-/public/alfresco/versions/1}", configuration = ClientConfiguration.class)
public interface PeopleApiClient extends PeopleApi {
}

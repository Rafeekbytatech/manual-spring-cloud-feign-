package com.bytatech.ayoos.web.rest;
import com.bytatech.ayoos.domain.AddressLine;
import com.bytatech.ayoos.domain.Patient;
import com.bytatech.ayoos.service.AddressLineService;
import com.bytatech.ayoos.web.rest.errors.BadRequestAlertException;
import com.bytatech.ayoos.web.rest.util.HeaderUtil;
import com.bytatech.ayoos.web.rest.util.PaginationUtil;
import com.bytatech.ayoos.service.dto.AddressLineDTO;
import com.bytatech.ayoos.service.dto.PatientDTO;
import com.bytatech.ayoos.service.mapper.*;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AddressLine.
 */
@RestController
@RequestMapping("/api")
public class AddressLineResource {

    private final Logger log = LoggerFactory.getLogger(AddressLineResource.class);

    private static final String ENTITY_NAME = "patientServiceAddressLine";

    private final AddressLineService addressLineService;

    @Autowired
    private  AddressLineMapper  addressLineMapper;
    public AddressLineResource(AddressLineService addressLineService) {
        this.addressLineService = addressLineService;
    }

    /**
     * POST  /address-lines : Create a new addressLine.
     *
     * @param addressLineDTO the addressLineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addressLineDTO, or with status 400 (Bad Request) if the addressLine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/address-lines")
    public ResponseEntity<AddressLineDTO> createAddressLine(@RequestBody AddressLineDTO addressLineDTO) throws URISyntaxException {
        log.debug("REST request to save AddressLine : {}", addressLineDTO);
        if (addressLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new addressLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressLineDTO result = addressLineService.save(addressLineDTO);
        return ResponseEntity.created(new URI("/api/address-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /address-lines : Updates an existing addressLine.
     *
     * @param addressLineDTO the addressLineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addressLineDTO,
     * or with status 400 (Bad Request) if the addressLineDTO is not valid,
     * or with status 500 (Internal Server Error) if the addressLineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/address-lines")
    public ResponseEntity<AddressLineDTO> updateAddressLine(@RequestBody AddressLineDTO addressLineDTO) throws URISyntaxException {
        log.debug("REST request to update AddressLine : {}", addressLineDTO);
        if (addressLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AddressLineDTO result = addressLineService.save(addressLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addressLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /address-lines : get all the addressLines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of addressLines in body
     */
    @GetMapping("/address-lines")
    public ResponseEntity<List<AddressLineDTO>> getAllAddressLines(Pageable pageable) {
        log.debug("REST request to get a page of AddressLines");
        Page<AddressLineDTO> page = addressLineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/address-lines");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /address-lines/:id : get the "id" addressLine.
     *
     * @param id the id of the addressLineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addressLineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/address-lines/{id}")
    public ResponseEntity<AddressLineDTO> getAddressLine(@PathVariable Long id) {
        log.debug("REST request to get AddressLine : {}", id);
        Optional<AddressLineDTO> addressLineDTO = addressLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressLineDTO);
    }

    /**
     * DELETE  /address-lines/:id : delete the "id" addressLine.
     *
     * @param id the id of the addressLineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/address-lines/{id}")
    public ResponseEntity<Void> deleteAddressLine(@PathVariable Long id) {
        log.debug("REST request to delete AddressLine : {}", id);
        addressLineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/address-lines?query=:query : search for the addressLine corresponding
     * to the query.
     *
     * @param query the query of the addressLine search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/address-lines")
    public ResponseEntity<List<AddressLineDTO>> searchAddressLines(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AddressLines for query {}", query);
        Page<AddressLineDTO> page = addressLineService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/address-lines");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @PostMapping("/address-lines/toDto")
    public ResponseEntity<List<AddressLineDTO>> listToDto(@RequestBody List<AddressLine> addressLine) {
    	 log.debug("REST request to convert to DTO");
    	List<AddressLineDTO> dtos = new ArrayList<>();
    	addressLine.forEach(a -> {dtos.add(addressLineMapper.toDto(a));});
    	return ResponseEntity.ok().body(dtos);
    }

   @PostMapping("/address-lines/modelToDto")
    public ResponseEntity<AddressLineDTO> modelToDto(@RequestBody AddressLine addressLine) {
    	 log.debug("REST request to convert to DTO");
    	return ResponseEntity.ok().body(addressLineMapper.toDto(addressLine));
    }

    
    
    
    
    
    
    
    
    
    
    
	@GetMapping("/address-linesByPatientId/{patientId}")
	public List<AddressLineDTO> getAllAddressLinesByPatientId(@PathVariable Long patientId){
		return addressLineService.findByPatientId(patientId);
	}


}

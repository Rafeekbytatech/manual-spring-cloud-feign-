package com.bytatech.ayoos.service.impl;

import com.bytatech.ayoos.service.CommandService;

import com.bytatech.ayoos.domain.Patient;
import com.bytatech.ayoos.repository.PatientRepository;
import com.bytatech.ayoos.repository.search.PatientSearchRepository;
import com.bytatech.ayoos.service.dto.PatientDTO;
import com.bytatech.ayoos.service.mapper.PatientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Patient.
 */
@Service
@Transactional
public class CommandServiceImpl implements CommandService {

    private final Logger log = LoggerFactory.getLogger(CommandServiceImpl.class);

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final PatientSearchRepository patientSearchRepository;

    public CommandServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper, PatientSearchRepository patientSearchRepository) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.patientSearchRepository = patientSearchRepository;
    }

    /**
     * Save a patient.
     *
     * @param patientDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PatientDTO save(PatientDTO patientDTO) {
        log.debug("Request to save Patient : {}", patientDTO);
        Patient patient = patientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        
        System.out.println("<<<<<<<<<<<<<<<jpa Repository>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ patient );
        
        PatientDTO result = patientMapper.toDto(patient);
      Patient P=  patientSearchRepository.save(patient);
      System.out.println("<<<<<<<<<<<<<<<elasticsearch Repository>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ patient );
       // patientSearchRepository.save (patientSearchRepository.save(patient));
        
        return result;
    }

    /**
     * Get all the patients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PatientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Patients");
        return patientRepository.findAll(pageable)
            .map(patientMapper::toDto);
    }


    /**
     * Get one patient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PatientDTO> findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        return patientRepository.findById(id)
            .map(patientMapper::toDto);
    }

    /**
     * Delete the patient by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.deleteById(id);
        patientSearchRepository.deleteById(id);
    }

    /**
     * Search for the patient corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PatientDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Patients for query {}", query);
        return patientSearchRepository.search(queryStringQuery(query), pageable)
            .map(patientMapper::toDto);
    }
}

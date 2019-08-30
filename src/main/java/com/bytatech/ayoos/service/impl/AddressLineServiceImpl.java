package com.bytatech.ayoos.service.impl;

import com.bytatech.ayoos.service.AddressLineService;
import com.bytatech.ayoos.domain.AddressLine;
import com.bytatech.ayoos.repository.AddressLineRepository;
import com.bytatech.ayoos.repository.search.AddressLineSearchRepository;
import com.bytatech.ayoos.service.dto.AddressLineDTO;
import com.bytatech.ayoos.service.mapper.AddressLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AddressLine.
 */
@Service
@Transactional
public class AddressLineServiceImpl implements AddressLineService {

    private final Logger log = LoggerFactory.getLogger(AddressLineServiceImpl.class);

    private final AddressLineRepository addressLineRepository;

    private final AddressLineMapper addressLineMapper;

    private final AddressLineSearchRepository addressLineSearchRepository;

    public AddressLineServiceImpl(AddressLineRepository addressLineRepository, AddressLineMapper addressLineMapper, AddressLineSearchRepository addressLineSearchRepository) {
        this.addressLineRepository = addressLineRepository;
        this.addressLineMapper = addressLineMapper;
        this.addressLineSearchRepository = addressLineSearchRepository;
    }

    /**
     * Save a addressLine.
     *
     * @param addressLineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AddressLineDTO save(AddressLineDTO addressLineDTO) {
        log.debug("Request to save AddressLine : {}", addressLineDTO);
        AddressLine addressLine = addressLineMapper.toEntity(addressLineDTO);
        addressLine = addressLineRepository.save(addressLine);
        AddressLineDTO result = addressLineMapper.toDto(addressLine);
        addressLineSearchRepository.save(addressLineSearchRepository.save(addressLine));
        return result;
    }

    /**
     * Get all the addressLines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AddressLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AddressLines");
        return addressLineRepository.findAll(pageable)
            .map(addressLineMapper::toDto);
    }


    /**
     * Get one addressLine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AddressLineDTO> findOne(Long id) {
        log.debug("Request to get AddressLine : {}", id);
        return addressLineRepository.findById(id)
            .map(addressLineMapper::toDto);
    }

    /**
     * Delete the addressLine by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AddressLine : {}", id);
        addressLineRepository.deleteById(id);
        addressLineSearchRepository.deleteById(id);
    }

    /**
     * Search for the addressLine corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AddressLineDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AddressLines for query {}", query);
        return addressLineSearchRepository.search(queryStringQuery(query), pageable)
            .map(addressLineMapper::toDto);
    }

	@Override
	public List<AddressLineDTO> findByPatientId(Long patientId) {
		
		return addressLineMapper.toDto(addressLineRepository.findByPatientId(patientId));
	}
}

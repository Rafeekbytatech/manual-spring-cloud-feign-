package com.bytatech.ayoos.web.rest;

import com.bytatech.ayoos.PatientServiceApp;

import com.bytatech.ayoos.domain.AddressLine;
import com.bytatech.ayoos.repository.AddressLineRepository;
import com.bytatech.ayoos.repository.search.AddressLineSearchRepository;
import com.bytatech.ayoos.service.AddressLineService;
import com.bytatech.ayoos.service.dto.AddressLineDTO;
import com.bytatech.ayoos.service.mapper.AddressLineMapper;
import com.bytatech.ayoos.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.bytatech.ayoos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AddressLineResource REST controller.
 *
 * @see AddressLineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatientServiceApp.class)
public class AddressLineResourceIntTest {

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    @Autowired
    private AddressLineRepository addressLineRepository;

    @Autowired
    private AddressLineMapper addressLineMapper;

    @Autowired
    private AddressLineService addressLineService;

    /**
     * This repository is mocked in the com.bytatech.ayoos.repository.search test package.
     *
     * @see com.bytatech.ayoos.repository.search.AddressLineSearchRepositoryMockConfiguration
     */
    @Autowired
    private AddressLineSearchRepository mockAddressLineSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAddressLineMockMvc;

    private AddressLine addressLine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddressLineResource addressLineResource = new AddressLineResource(addressLineService);
        this.restAddressLineMockMvc = MockMvcBuilders.standaloneSetup(addressLineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressLine createEntity(EntityManager em) {
        AddressLine addressLine = new AddressLine()
            .city(DEFAULT_CITY)
            .district(DEFAULT_DISTRICT)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .zipCode(DEFAULT_ZIP_CODE);
        return addressLine;
    }

    @Before
    public void initTest() {
        addressLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddressLine() throws Exception {
        int databaseSizeBeforeCreate = addressLineRepository.findAll().size();

        // Create the AddressLine
        AddressLineDTO addressLineDTO = addressLineMapper.toDto(addressLine);
        restAddressLineMockMvc.perform(post("/api/address-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressLineDTO)))
            .andExpect(status().isCreated());

        // Validate the AddressLine in the database
        List<AddressLine> addressLineList = addressLineRepository.findAll();
        assertThat(addressLineList).hasSize(databaseSizeBeforeCreate + 1);
        AddressLine testAddressLine = addressLineList.get(addressLineList.size() - 1);
        assertThat(testAddressLine.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddressLine.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testAddressLine.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAddressLine.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAddressLine.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);

        // Validate the AddressLine in Elasticsearch
        verify(mockAddressLineSearchRepository, times(1)).save(testAddressLine);
    }

    @Test
    @Transactional
    public void createAddressLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressLineRepository.findAll().size();

        // Create the AddressLine with an existing ID
        addressLine.setId(1L);
        AddressLineDTO addressLineDTO = addressLineMapper.toDto(addressLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressLineMockMvc.perform(post("/api/address-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddressLine in the database
        List<AddressLine> addressLineList = addressLineRepository.findAll();
        assertThat(addressLineList).hasSize(databaseSizeBeforeCreate);

        // Validate the AddressLine in Elasticsearch
        verify(mockAddressLineSearchRepository, times(0)).save(addressLine);
    }

    @Test
    @Transactional
    public void getAllAddressLines() throws Exception {
        // Initialize the database
        addressLineRepository.saveAndFlush(addressLine);

        // Get all the addressLineList
        restAddressLineMockMvc.perform(get("/api/address-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getAddressLine() throws Exception {
        // Initialize the database
        addressLineRepository.saveAndFlush(addressLine);

        // Get the addressLine
        restAddressLineMockMvc.perform(get("/api/address-lines/{id}", addressLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(addressLine.getId().intValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddressLine() throws Exception {
        // Get the addressLine
        restAddressLineMockMvc.perform(get("/api/address-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddressLine() throws Exception {
        // Initialize the database
        addressLineRepository.saveAndFlush(addressLine);

        int databaseSizeBeforeUpdate = addressLineRepository.findAll().size();

        // Update the addressLine
        AddressLine updatedAddressLine = addressLineRepository.findById(addressLine.getId()).get();
        // Disconnect from session so that the updates on updatedAddressLine are not directly saved in db
        em.detach(updatedAddressLine);
        updatedAddressLine
            .city(UPDATED_CITY)
            .district(UPDATED_DISTRICT)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .zipCode(UPDATED_ZIP_CODE);
        AddressLineDTO addressLineDTO = addressLineMapper.toDto(updatedAddressLine);

        restAddressLineMockMvc.perform(put("/api/address-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressLineDTO)))
            .andExpect(status().isOk());

        // Validate the AddressLine in the database
        List<AddressLine> addressLineList = addressLineRepository.findAll();
        assertThat(addressLineList).hasSize(databaseSizeBeforeUpdate);
        AddressLine testAddressLine = addressLineList.get(addressLineList.size() - 1);
        assertThat(testAddressLine.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddressLine.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testAddressLine.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddressLine.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAddressLine.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);

        // Validate the AddressLine in Elasticsearch
        verify(mockAddressLineSearchRepository, times(1)).save(testAddressLine);
    }

    @Test
    @Transactional
    public void updateNonExistingAddressLine() throws Exception {
        int databaseSizeBeforeUpdate = addressLineRepository.findAll().size();

        // Create the AddressLine
        AddressLineDTO addressLineDTO = addressLineMapper.toDto(addressLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressLineMockMvc.perform(put("/api/address-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddressLine in the database
        List<AddressLine> addressLineList = addressLineRepository.findAll();
        assertThat(addressLineList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AddressLine in Elasticsearch
        verify(mockAddressLineSearchRepository, times(0)).save(addressLine);
    }

    @Test
    @Transactional
    public void deleteAddressLine() throws Exception {
        // Initialize the database
        addressLineRepository.saveAndFlush(addressLine);

        int databaseSizeBeforeDelete = addressLineRepository.findAll().size();

        // Delete the addressLine
        restAddressLineMockMvc.perform(delete("/api/address-lines/{id}", addressLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AddressLine> addressLineList = addressLineRepository.findAll();
        assertThat(addressLineList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AddressLine in Elasticsearch
        verify(mockAddressLineSearchRepository, times(1)).deleteById(addressLine.getId());
    }

    @Test
    @Transactional
    public void searchAddressLine() throws Exception {
        // Initialize the database
        addressLineRepository.saveAndFlush(addressLine);
        when(mockAddressLineSearchRepository.search(queryStringQuery("id:" + addressLine.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(addressLine), PageRequest.of(0, 1), 1));
        // Search the addressLine
        restAddressLineMockMvc.perform(get("/api/_search/address-lines?query=id:" + addressLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressLine.class);
        AddressLine addressLine1 = new AddressLine();
        addressLine1.setId(1L);
        AddressLine addressLine2 = new AddressLine();
        addressLine2.setId(addressLine1.getId());
        assertThat(addressLine1).isEqualTo(addressLine2);
        addressLine2.setId(2L);
        assertThat(addressLine1).isNotEqualTo(addressLine2);
        addressLine1.setId(null);
        assertThat(addressLine1).isNotEqualTo(addressLine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressLineDTO.class);
        AddressLineDTO addressLineDTO1 = new AddressLineDTO();
        addressLineDTO1.setId(1L);
        AddressLineDTO addressLineDTO2 = new AddressLineDTO();
        assertThat(addressLineDTO1).isNotEqualTo(addressLineDTO2);
        addressLineDTO2.setId(addressLineDTO1.getId());
        assertThat(addressLineDTO1).isEqualTo(addressLineDTO2);
        addressLineDTO2.setId(2L);
        assertThat(addressLineDTO1).isNotEqualTo(addressLineDTO2);
        addressLineDTO1.setId(null);
        assertThat(addressLineDTO1).isNotEqualTo(addressLineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(addressLineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(addressLineMapper.fromId(null)).isNull();
    }
}

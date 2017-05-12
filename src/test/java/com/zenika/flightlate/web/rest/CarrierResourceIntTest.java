package com.zenika.flightlate.web.rest;

import com.zenika.flightlate.FlightlateApp;

import com.zenika.flightlate.domain.Carrier;
import com.zenika.flightlate.repository.CarrierRepository;
import com.zenika.flightlate.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CarrierResource REST controller.
 *
 * @see CarrierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlightlateApp.class)
public class CarrierResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CarrierRepository carrierRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarrierMockMvc;

    private Carrier carrier;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarrierResource carrierResource = new CarrierResource(carrierRepository);
        this.restCarrierMockMvc = MockMvcBuilders.standaloneSetup(carrierResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carrier createEntity(EntityManager em) {
        Carrier carrier = new Carrier()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return carrier;
    }

    @Before
    public void initTest() {
        carrier = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarrier() throws Exception {
        int databaseSizeBeforeCreate = carrierRepository.findAll().size();

        // Create the Carrier
        restCarrierMockMvc.perform(post("/api/carriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carrier)))
            .andExpect(status().isCreated());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeCreate + 1);
        Carrier testCarrier = carrierList.get(carrierList.size() - 1);
        assertThat(testCarrier.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCarrier.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCarrierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carrierRepository.findAll().size();

        // Create the Carrier with an existing ID
        carrier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarrierMockMvc.perform(post("/api/carriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carrier)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCarriers() throws Exception {
        // Initialize the database
        carrierRepository.saveAndFlush(carrier);

        // Get all the carrierList
        restCarrierMockMvc.perform(get("/api/carriers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carrier.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCarrier() throws Exception {
        // Initialize the database
        carrierRepository.saveAndFlush(carrier);

        // Get the carrier
        restCarrierMockMvc.perform(get("/api/carriers/{id}", carrier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carrier.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarrier() throws Exception {
        // Get the carrier
        restCarrierMockMvc.perform(get("/api/carriers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarrier() throws Exception {
        // Initialize the database
        carrierRepository.saveAndFlush(carrier);
        int databaseSizeBeforeUpdate = carrierRepository.findAll().size();

        // Update the carrier
        Carrier updatedCarrier = carrierRepository.findOne(carrier.getId());
        updatedCarrier
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restCarrierMockMvc.perform(put("/api/carriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCarrier)))
            .andExpect(status().isOk());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeUpdate);
        Carrier testCarrier = carrierList.get(carrierList.size() - 1);
        assertThat(testCarrier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCarrier.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCarrier() throws Exception {
        int databaseSizeBeforeUpdate = carrierRepository.findAll().size();

        // Create the Carrier

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarrierMockMvc.perform(put("/api/carriers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carrier)))
            .andExpect(status().isCreated());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCarrier() throws Exception {
        // Initialize the database
        carrierRepository.saveAndFlush(carrier);
        int databaseSizeBeforeDelete = carrierRepository.findAll().size();

        // Get the carrier
        restCarrierMockMvc.perform(delete("/api/carriers/{id}", carrier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carrier.class);
    }
}

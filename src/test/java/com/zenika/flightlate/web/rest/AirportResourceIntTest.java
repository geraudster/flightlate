package com.zenika.flightlate.web.rest;

import com.zenika.flightlate.FlightlateApp;

import com.zenika.flightlate.domain.Airport;
import com.zenika.flightlate.repository.AirportRepository;
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
 * Test class for the AirportResource REST controller.
 *
 * @see AirportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlightlateApp.class)
public class AirportResourceIntTest {

    private static final String DEFAULT_IATA = "AAAAAAAAAA";
    private static final String UPDATED_IATA = "BBBBBBBBBB";

    private static final Float DEFAULT_LON = 1F;
    private static final Float UPDATED_LON = 2F;

    private static final Float DEFAULT_LAT = 1F;
    private static final Float UPDATED_LAT = 2F;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAirportMockMvc;

    private Airport airport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AirportResource airportResource = new AirportResource(airportRepository);
        this.restAirportMockMvc = MockMvcBuilders.standaloneSetup(airportResource)
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
    public static Airport createEntity(EntityManager em) {
        Airport airport = new Airport()
            .iata(DEFAULT_IATA)
            .lon(DEFAULT_LON)
            .lat(DEFAULT_LAT)
            .name(DEFAULT_NAME);
        return airport;
    }

    @Before
    public void initTest() {
        airport = createEntity(em);
    }

    @Test
    @Transactional
    public void createAirport() throws Exception {
        int databaseSizeBeforeCreate = airportRepository.findAll().size();

        // Create the Airport
        restAirportMockMvc.perform(post("/api/airports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airport)))
            .andExpect(status().isCreated());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeCreate + 1);
        Airport testAirport = airportList.get(airportList.size() - 1);
        assertThat(testAirport.getIata()).isEqualTo(DEFAULT_IATA);
        assertThat(testAirport.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testAirport.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testAirport.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAirportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = airportRepository.findAll().size();

        // Create the Airport with an existing ID
        airport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAirportMockMvc.perform(post("/api/airports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airport)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAirports() throws Exception {
        // Initialize the database
        airportRepository.saveAndFlush(airport);

        // Get all the airportList
        restAirportMockMvc.perform(get("/api/airports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airport.getId().intValue())))
            .andExpect(jsonPath("$.[*].iata").value(hasItem(DEFAULT_IATA.toString())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAirport() throws Exception {
        // Initialize the database
        airportRepository.saveAndFlush(airport);

        // Get the airport
        restAirportMockMvc.perform(get("/api/airports/{id}", airport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(airport.getId().intValue()))
            .andExpect(jsonPath("$.iata").value(DEFAULT_IATA.toString()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAirport() throws Exception {
        // Get the airport
        restAirportMockMvc.perform(get("/api/airports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAirport() throws Exception {
        // Initialize the database
        airportRepository.saveAndFlush(airport);
        int databaseSizeBeforeUpdate = airportRepository.findAll().size();

        // Update the airport
        Airport updatedAirport = airportRepository.findOne(airport.getId());
        updatedAirport
            .iata(UPDATED_IATA)
            .lon(UPDATED_LON)
            .lat(UPDATED_LAT)
            .name(UPDATED_NAME);

        restAirportMockMvc.perform(put("/api/airports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAirport)))
            .andExpect(status().isOk());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeUpdate);
        Airport testAirport = airportList.get(airportList.size() - 1);
        assertThat(testAirport.getIata()).isEqualTo(UPDATED_IATA);
        assertThat(testAirport.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testAirport.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testAirport.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAirport() throws Exception {
        int databaseSizeBeforeUpdate = airportRepository.findAll().size();

        // Create the Airport

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAirportMockMvc.perform(put("/api/airports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airport)))
            .andExpect(status().isCreated());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAirport() throws Exception {
        // Initialize the database
        airportRepository.saveAndFlush(airport);
        int databaseSizeBeforeDelete = airportRepository.findAll().size();

        // Get the airport
        restAirportMockMvc.perform(delete("/api/airports/{id}", airport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Airport.class);
    }
}

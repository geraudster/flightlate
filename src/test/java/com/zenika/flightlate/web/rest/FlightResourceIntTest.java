package com.zenika.flightlate.web.rest;

import com.zenika.flightlate.FlightlateApp;

import com.zenika.flightlate.domain.Flight;
import com.zenika.flightlate.repository.FlightRepository;
import com.zenika.flightlate.service.FlightService;
import com.zenika.flightlate.service.dto.FlightDTO;
import com.zenika.flightlate.service.mapper.FlightMapper;
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
 * Test class for the FlightResource REST controller.
 *
 * @see FlightResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlightlateApp.class)
public class FlightResourceIntTest {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Integer DEFAULT_DAY_OF_MONTH = 1;
    private static final Integer UPDATED_DAY_OF_MONTH = 2;

    private static final Integer DEFAULT_DAY_OF_WEEK = 1;
    private static final Integer UPDATED_DAY_OF_WEEK = 2;

    private static final Integer DEFAULT_DEP_TIME = 1;
    private static final Integer UPDATED_DEP_TIME = 2;

    private static final String DEFAULT_FLIGHT_NUM = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_NUM = "BBBBBBBBBB";

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private FlightService flightService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFlightMockMvc;

    private Flight flight;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FlightResource flightResource = new FlightResource(flightService);
        this.restFlightMockMvc = MockMvcBuilders.standaloneSetup(flightResource)
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
    public static Flight createEntity(EntityManager em) {
        Flight flight = new Flight()
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .dayOfMonth(DEFAULT_DAY_OF_MONTH)
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .depTime(DEFAULT_DEP_TIME)
            .flightNum(DEFAULT_FLIGHT_NUM);
        return flight;
    }

    @Before
    public void initTest() {
        flight = createEntity(em);
    }

    @Test
    @Transactional
    public void createFlight() throws Exception {
        int databaseSizeBeforeCreate = flightRepository.findAll().size();

        // Create the Flight
        FlightDTO flightDTO = flightMapper.flightToFlightDTO(flight);
        restFlightMockMvc.perform(post("/api/flights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flightDTO)))
            .andExpect(status().isCreated());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeCreate + 1);
        Flight testFlight = flightList.get(flightList.size() - 1);
        assertThat(testFlight.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testFlight.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testFlight.getDayOfMonth()).isEqualTo(DEFAULT_DAY_OF_MONTH);
        assertThat(testFlight.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testFlight.getDepTime()).isEqualTo(DEFAULT_DEP_TIME);
        assertThat(testFlight.getFlightNum()).isEqualTo(DEFAULT_FLIGHT_NUM);
    }

    @Test
    @Transactional
    public void createFlightWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = flightRepository.findAll().size();

        // Create the Flight with an existing ID
        flight.setId(1L);
        FlightDTO flightDTO = flightMapper.flightToFlightDTO(flight);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlightMockMvc.perform(post("/api/flights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flightDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFlights() throws Exception {
        // Initialize the database
        flightRepository.saveAndFlush(flight);

        // Get all the flightList
        restFlightMockMvc.perform(get("/api/flights?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flight.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].dayOfMonth").value(hasItem(DEFAULT_DAY_OF_MONTH)))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK)))
            .andExpect(jsonPath("$.[*].depTime").value(hasItem(DEFAULT_DEP_TIME)))
            .andExpect(jsonPath("$.[*].flightNum").value(hasItem(DEFAULT_FLIGHT_NUM.toString())));
    }

    @Test
    @Transactional
    public void getFlight() throws Exception {
        // Initialize the database
        flightRepository.saveAndFlush(flight);

        // Get the flight
        restFlightMockMvc.perform(get("/api/flights/{id}", flight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(flight.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.dayOfMonth").value(DEFAULT_DAY_OF_MONTH))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK))
            .andExpect(jsonPath("$.depTime").value(DEFAULT_DEP_TIME))
            .andExpect(jsonPath("$.flightNum").value(DEFAULT_FLIGHT_NUM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFlight() throws Exception {
        // Get the flight
        restFlightMockMvc.perform(get("/api/flights/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFlight() throws Exception {
        // Initialize the database
        flightRepository.saveAndFlush(flight);
        int databaseSizeBeforeUpdate = flightRepository.findAll().size();

        // Update the flight
        Flight updatedFlight = flightRepository.findOne(flight.getId());
        updatedFlight
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .dayOfMonth(UPDATED_DAY_OF_MONTH)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .depTime(UPDATED_DEP_TIME)
            .flightNum(UPDATED_FLIGHT_NUM);
        FlightDTO flightDTO = flightMapper.flightToFlightDTO(updatedFlight);

        restFlightMockMvc.perform(put("/api/flights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flightDTO)))
            .andExpect(status().isOk());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeUpdate);
        Flight testFlight = flightList.get(flightList.size() - 1);
        assertThat(testFlight.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testFlight.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testFlight.getDayOfMonth()).isEqualTo(UPDATED_DAY_OF_MONTH);
        assertThat(testFlight.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testFlight.getDepTime()).isEqualTo(UPDATED_DEP_TIME);
        assertThat(testFlight.getFlightNum()).isEqualTo(UPDATED_FLIGHT_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingFlight() throws Exception {
        int databaseSizeBeforeUpdate = flightRepository.findAll().size();

        // Create the Flight
        FlightDTO flightDTO = flightMapper.flightToFlightDTO(flight);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFlightMockMvc.perform(put("/api/flights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flightDTO)))
            .andExpect(status().isCreated());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFlight() throws Exception {
        // Initialize the database
        flightRepository.saveAndFlush(flight);
        int databaseSizeBeforeDelete = flightRepository.findAll().size();

        // Get the flight
        restFlightMockMvc.perform(delete("/api/flights/{id}", flight.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Flight.class);
    }
}

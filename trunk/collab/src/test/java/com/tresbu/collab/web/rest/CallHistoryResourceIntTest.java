package com.tresbu.collab.web.rest;

import com.tresbu.collab.CollabApp;
import com.tresbu.collab.domain.CallHistory;
import com.tresbu.collab.domain.User;
import com.tresbu.collab.domain.User;
import com.tresbu.collab.repository.CallHistoryRepository;
import com.tresbu.collab.service.CallHistoryService;
import com.tresbu.collab.service.dto.CallHistoryDTO;
import com.tresbu.collab.service.mapper.CallHistoryMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tresbu.collab.domain.enumeration.CallType;
import com.tresbu.collab.domain.enumeration.CallStatus;
/**
 * Test class for the CallHistoryResource REST controller.
 *
 * @see CallHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CollabApp.class)
public class CallHistoryResourceIntTest {

    private static final CallType DEFAULT_CALL_TYPE = CallType.ADHOCAUDIO;
    private static final CallType UPDATED_CALL_TYPE = CallType.ADHOCVIDEO;

    private static final CallStatus DEFAULT_CALL_STATUS = CallStatus.ACCEPTED;
    private static final CallStatus UPDATED_CALL_STATUS = CallStatus.REJECTED;

    private static final Long DEFAULT_CALL_DURATION = 1L;
    private static final Long UPDATED_CALL_DURATION = 2L;

    @Inject
    private CallHistoryRepository callHistoryRepository;

    @Inject
    private CallHistoryMapper callHistoryMapper;

    @Inject
    private CallHistoryService callHistoryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCallHistoryMockMvc;

    private CallHistory callHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CallHistoryResource callHistoryResource = new CallHistoryResource();
        ReflectionTestUtils.setField(callHistoryResource, "callHistoryService", callHistoryService);
        this.restCallHistoryMockMvc = MockMvcBuilders.standaloneSetup(callHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CallHistory createEntity(EntityManager em) {
        CallHistory callHistory = new CallHistory();
        callHistory = new CallHistory()
                .callType(DEFAULT_CALL_TYPE)
                .callStatus(DEFAULT_CALL_STATUS)
                .callDuration(DEFAULT_CALL_DURATION);
        // Add required entity
       /* User caller = UserResourceIntTest.createEntity(em);
        em.persist(caller);
        em.flush();
        callHistory.setCaller(caller);*/
        // Add required entity
       /* User callee = UserResourceIntTest.createEntity(em);
        em.persist(callee);
        em.flush();
        callHistory.setCallee(callee);*/
        return callHistory;
    }

    @Before
    public void initTest() {
        callHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCallHistory() throws Exception {
        int databaseSizeBeforeCreate = callHistoryRepository.findAll().size();

        // Create the CallHistory
        CallHistoryDTO callHistoryDTO = callHistoryMapper.callHistoryToCallHistoryDTO(callHistory);

        restCallHistoryMockMvc.perform(post("/api/call-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(callHistoryDTO)))
                .andExpect(status().isCreated());

        // Validate the CallHistory in the database
        List<CallHistory> callHistories = callHistoryRepository.findAll();
        assertThat(callHistories).hasSize(databaseSizeBeforeCreate + 1);
        CallHistory testCallHistory = callHistories.get(callHistories.size() - 1);
        assertThat(testCallHistory.getCallType()).isEqualTo(DEFAULT_CALL_TYPE);
        assertThat(testCallHistory.getCallStatus()).isEqualTo(DEFAULT_CALL_STATUS);
        assertThat(testCallHistory.getCallDuration()).isEqualTo(DEFAULT_CALL_DURATION);
    }

    @Test
    @Transactional
    public void checkCallTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = callHistoryRepository.findAll().size();
        // set the field null
        callHistory.setCallType(null);

        // Create the CallHistory, which fails.
        CallHistoryDTO callHistoryDTO = callHistoryMapper.callHistoryToCallHistoryDTO(callHistory);

        restCallHistoryMockMvc.perform(post("/api/call-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(callHistoryDTO)))
                .andExpect(status().isBadRequest());

        List<CallHistory> callHistories = callHistoryRepository.findAll();
        assertThat(callHistories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCallStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = callHistoryRepository.findAll().size();
        // set the field null
        callHistory.setCallStatus(null);

        // Create the CallHistory, which fails.
        CallHistoryDTO callHistoryDTO = callHistoryMapper.callHistoryToCallHistoryDTO(callHistory);

        restCallHistoryMockMvc.perform(post("/api/call-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(callHistoryDTO)))
                .andExpect(status().isBadRequest());

        List<CallHistory> callHistories = callHistoryRepository.findAll();
        assertThat(callHistories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCallHistories() throws Exception {
        // Initialize the database
        callHistoryRepository.saveAndFlush(callHistory);

        // Get all the callHistories
        restCallHistoryMockMvc.perform(get("/api/call-histories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(callHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].callType").value(hasItem(DEFAULT_CALL_TYPE.toString())))
                .andExpect(jsonPath("$.[*].callStatus").value(hasItem(DEFAULT_CALL_STATUS.toString())))
                .andExpect(jsonPath("$.[*].callDuration").value(hasItem(DEFAULT_CALL_DURATION.intValue())));
    }

    @Test
    @Transactional
    public void getCallHistory() throws Exception {
        // Initialize the database
        callHistoryRepository.saveAndFlush(callHistory);

        // Get the callHistory
        restCallHistoryMockMvc.perform(get("/api/call-histories/{id}", callHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(callHistory.getId().intValue()))
            .andExpect(jsonPath("$.callType").value(DEFAULT_CALL_TYPE.toString()))
            .andExpect(jsonPath("$.callStatus").value(DEFAULT_CALL_STATUS.toString()))
            .andExpect(jsonPath("$.callDuration").value(DEFAULT_CALL_DURATION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCallHistory() throws Exception {
        // Get the callHistory
        restCallHistoryMockMvc.perform(get("/api/call-histories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCallHistory() throws Exception {
        // Initialize the database
        callHistoryRepository.saveAndFlush(callHistory);
        int databaseSizeBeforeUpdate = callHistoryRepository.findAll().size();

        // Update the callHistory
        CallHistory updatedCallHistory = callHistoryRepository.findOne(callHistory.getId());
        updatedCallHistory
                .callType(UPDATED_CALL_TYPE)
                .callStatus(UPDATED_CALL_STATUS)
                .callDuration(UPDATED_CALL_DURATION);
        CallHistoryDTO callHistoryDTO = callHistoryMapper.callHistoryToCallHistoryDTO(updatedCallHistory);

        restCallHistoryMockMvc.perform(put("/api/call-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(callHistoryDTO)))
                .andExpect(status().isOk());

        // Validate the CallHistory in the database
        List<CallHistory> callHistories = callHistoryRepository.findAll();
        assertThat(callHistories).hasSize(databaseSizeBeforeUpdate);
        CallHistory testCallHistory = callHistories.get(callHistories.size() - 1);
        assertThat(testCallHistory.getCallType()).isEqualTo(UPDATED_CALL_TYPE);
        assertThat(testCallHistory.getCallStatus()).isEqualTo(UPDATED_CALL_STATUS);
        assertThat(testCallHistory.getCallDuration()).isEqualTo(UPDATED_CALL_DURATION);
    }

    @Test
    @Transactional
    public void deleteCallHistory() throws Exception {
        // Initialize the database
        callHistoryRepository.saveAndFlush(callHistory);
        int databaseSizeBeforeDelete = callHistoryRepository.findAll().size();

        // Get the callHistory
        restCallHistoryMockMvc.perform(delete("/api/call-histories/{id}", callHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CallHistory> callHistories = callHistoryRepository.findAll();
        assertThat(callHistories).hasSize(databaseSizeBeforeDelete - 1);
    }
}

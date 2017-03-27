package com.tresbu.collab.web.rest;

import com.tresbu.collab.CollabApp;
import com.tresbu.collab.domain.EmailNotification;
import com.tresbu.collab.domain.User;

import com.tresbu.collab.repository.EmailNotificationRepository;
import com.tresbu.collab.service.EmailNotificationService;
import com.tresbu.collab.service.dto.EmailNotificationDTO;
import com.tresbu.collab.service.mapper.EmailNotificationMapper;

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

/**
 * Test class for the EmailNotificationResource REST controller.
 *
 * @see EmailNotificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CollabApp.class)
public class EmailNotificationResourceIntTest {
    private static final String DEFAULT_SUBJECT = "AAAAA";
    private static final String UPDATED_SUBJECT = "BBBBB";
    private static final String DEFAULT_MESSAGE = "AAAAA";
    private static final String UPDATED_MESSAGE = "BBBBB";

    private static final Long DEFAULT_SENT_DATE = 1L;
    private static final Long UPDATED_SENT_DATE = 2L;

    @Inject
    private EmailNotificationRepository emailNotificationRepository;

    @Inject
    private EmailNotificationMapper emailNotificationMapper;

    @Inject
    private EmailNotificationService emailNotificationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEmailNotificationMockMvc;

    private EmailNotification emailNotification;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmailNotificationResource emailNotificationResource = new EmailNotificationResource();
        ReflectionTestUtils.setField(emailNotificationResource, "emailNotificationService", emailNotificationService);
        this.restEmailNotificationMockMvc = MockMvcBuilders.standaloneSetup(emailNotificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailNotification createEntity(EntityManager em) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification = new EmailNotification()
                .subject(DEFAULT_SUBJECT)
                .message(DEFAULT_MESSAGE)
                .sentDate(DEFAULT_SENT_DATE);
        // Add required entity
      /*  User fromUser = UserResourceIntTest.createEntity(em);
        em.persist(fromUser);
        em.flush();*/
        emailNotification.setFromUser(null);
        // Add required entity
       /* User toUser = UserResourceIntTest.createEntity(em);
        em.persist(toUser);
        em.flush();*/
        emailNotification.setToUser(null);
        return emailNotification;
    }

    @Before
    public void initTest() {
        emailNotification = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailNotification() throws Exception {
        int databaseSizeBeforeCreate = emailNotificationRepository.findAll().size();

        // Create the EmailNotification
        EmailNotificationDTO emailNotificationDTO = emailNotificationMapper.emailNotificationToEmailNotificationDTO(emailNotification);

        restEmailNotificationMockMvc.perform(post("/api/email-notifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(emailNotificationDTO)))
                .andExpect(status().isCreated());

        // Validate the EmailNotification in the database
        List<EmailNotification> emailNotifications = emailNotificationRepository.findAll();
        assertThat(emailNotifications).hasSize(databaseSizeBeforeCreate + 1);
        EmailNotification testEmailNotification = emailNotifications.get(emailNotifications.size() - 1);
        assertThat(testEmailNotification.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEmailNotification.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testEmailNotification.getSentDate()).isEqualTo(DEFAULT_SENT_DATE);
    }

    @Test
    @Transactional
    public void getAllEmailNotifications() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get all the emailNotifications
        restEmailNotificationMockMvc.perform(get("/api/email-notifications?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(emailNotification.getId().intValue())))
                .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
                .andExpect(jsonPath("$.[*].sentDate").value(hasItem(DEFAULT_SENT_DATE.intValue())));
    }

    @Test
    @Transactional
    public void getEmailNotification() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);

        // Get the emailNotification
        restEmailNotificationMockMvc.perform(get("/api/email-notifications/{id}", emailNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailNotification.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.sentDate").value(DEFAULT_SENT_DATE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmailNotification() throws Exception {
        // Get the emailNotification
        restEmailNotificationMockMvc.perform(get("/api/email-notifications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailNotification() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);
        int databaseSizeBeforeUpdate = emailNotificationRepository.findAll().size();

        // Update the emailNotification
        EmailNotification updatedEmailNotification = emailNotificationRepository.findOne(emailNotification.getId());
        updatedEmailNotification
                .subject(UPDATED_SUBJECT)
                .message(UPDATED_MESSAGE)
                .sentDate(UPDATED_SENT_DATE);
        EmailNotificationDTO emailNotificationDTO = emailNotificationMapper.emailNotificationToEmailNotificationDTO(updatedEmailNotification);

        restEmailNotificationMockMvc.perform(put("/api/email-notifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(emailNotificationDTO)))
                .andExpect(status().isOk());

        // Validate the EmailNotification in the database
        List<EmailNotification> emailNotifications = emailNotificationRepository.findAll();
        assertThat(emailNotifications).hasSize(databaseSizeBeforeUpdate);
        EmailNotification testEmailNotification = emailNotifications.get(emailNotifications.size() - 1);
        assertThat(testEmailNotification.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEmailNotification.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testEmailNotification.getSentDate()).isEqualTo(UPDATED_SENT_DATE);
    }

    @Test
    @Transactional
    public void deleteEmailNotification() throws Exception {
        // Initialize the database
        emailNotificationRepository.saveAndFlush(emailNotification);
        int databaseSizeBeforeDelete = emailNotificationRepository.findAll().size();

        // Get the emailNotification
        restEmailNotificationMockMvc.perform(delete("/api/email-notifications/{id}", emailNotification.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EmailNotification> emailNotifications = emailNotificationRepository.findAll();
        assertThat(emailNotifications).hasSize(databaseSizeBeforeDelete - 1);
    }
}

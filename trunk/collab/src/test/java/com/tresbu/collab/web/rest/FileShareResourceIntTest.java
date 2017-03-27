package com.tresbu.collab.web.rest;

import com.tresbu.collab.CollabApp;
import com.tresbu.collab.domain.FileShare;
import com.tresbu.collab.domain.User;
import com.tresbu.collab.domain.User;
import com.tresbu.collab.repository.FileShareRepository;
import com.tresbu.collab.service.FileShareService;
import com.tresbu.collab.service.dto.FileShareDTO;
import com.tresbu.collab.service.mapper.FileShareMapper;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tresbu.collab.domain.enumeration.ContentType;
/**
 * Test class for the FileShareResource REST controller.
 *
 * @see FileShareResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CollabApp.class)
public class FileShareResourceIntTest {
    private static final String DEFAULT_FILE_NAME = "AAA";
    private static final String UPDATED_FILE_NAME = "BBB";

    private static final ContentType DEFAULT_CONTENT_TYPE = ContentType.TEXT;
    private static final ContentType UPDATED_CONTENT_TYPE = ContentType.IMAGE;

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    @Inject
    private FileShareRepository fileShareRepository;

    @Inject
    private FileShareMapper fileShareMapper;

    @Inject
    private FileShareService fileShareService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFileShareMockMvc;

    private FileShare fileShare;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FileShareResource fileShareResource = new FileShareResource();
        ReflectionTestUtils.setField(fileShareResource, "fileShareService", fileShareService);
        this.restFileShareMockMvc = MockMvcBuilders.standaloneSetup(fileShareResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileShare createEntity(EntityManager em) {
        FileShare fileShare = new FileShare();
        fileShare = new FileShare()
                .fileName(DEFAULT_FILE_NAME)
                .contentType(DEFAULT_CONTENT_TYPE)
                .content(DEFAULT_CONTENT)
                .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE);
        // Add required entity
       /* User fromUser = UserResourceIntTest.createEntity(em);
        em.persist(fromUser);
        em.flush();*/
        fileShare.setFromUser(null);
        // Add required entity
       /* User toUser = UserResourceIntTest.createEntity(em);
        em.persist(toUser);
        em.flush();*/
        fileShare.setToUser(null);
        return fileShare;
    }

    @Before
    public void initTest() {
        fileShare = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileShare() throws Exception {
        int databaseSizeBeforeCreate = fileShareRepository.findAll().size();

        // Create the FileShare
        FileShareDTO fileShareDTO = fileShareMapper.fileShareToFileShareDTO(fileShare);

        restFileShareMockMvc.perform(post("/api/file-shares")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fileShareDTO)))
                .andExpect(status().isCreated());

        // Validate the FileShare in the database
        List<FileShare> fileShares = fileShareRepository.findAll();
        assertThat(fileShares).hasSize(databaseSizeBeforeCreate + 1);
        FileShare testFileShare = fileShares.get(fileShares.size() - 1);
        assertThat(testFileShare.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testFileShare.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testFileShare.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testFileShare.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileShareRepository.findAll().size();
        // set the field null
        fileShare.setFileName(null);

        // Create the FileShare, which fails.
        FileShareDTO fileShareDTO = fileShareMapper.fileShareToFileShareDTO(fileShare);

        restFileShareMockMvc.perform(post("/api/file-shares")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fileShareDTO)))
                .andExpect(status().isBadRequest());

        List<FileShare> fileShares = fileShareRepository.findAll();
        assertThat(fileShares).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileShareRepository.findAll().size();
        // set the field null
        fileShare.setContentType(null);

        // Create the FileShare, which fails.
        FileShareDTO fileShareDTO = fileShareMapper.fileShareToFileShareDTO(fileShare);

        restFileShareMockMvc.perform(post("/api/file-shares")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fileShareDTO)))
                .andExpect(status().isBadRequest());

        List<FileShare> fileShares = fileShareRepository.findAll();
        assertThat(fileShares).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFileShares() throws Exception {
        // Initialize the database
        fileShareRepository.saveAndFlush(fileShare);

        // Get all the fileShares
        restFileShareMockMvc.perform(get("/api/file-shares?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fileShare.getId().intValue())))
                .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
                .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))));
    }

    @Test
    @Transactional
    public void getFileShare() throws Exception {
        // Initialize the database
        fileShareRepository.saveAndFlush(fileShare);

        // Get the fileShare
        restFileShareMockMvc.perform(get("/api/file-shares/{id}", fileShare.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileShare.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getNonExistingFileShare() throws Exception {
        // Get the fileShare
        restFileShareMockMvc.perform(get("/api/file-shares/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileShare() throws Exception {
        // Initialize the database
        fileShareRepository.saveAndFlush(fileShare);
        int databaseSizeBeforeUpdate = fileShareRepository.findAll().size();

        // Update the fileShare
        FileShare updatedFileShare = fileShareRepository.findOne(fileShare.getId());
        updatedFileShare
                .fileName(UPDATED_FILE_NAME)
                .contentType(UPDATED_CONTENT_TYPE)
                .content(UPDATED_CONTENT)
                .contentContentType(UPDATED_CONTENT_CONTENT_TYPE);
        FileShareDTO fileShareDTO = fileShareMapper.fileShareToFileShareDTO(updatedFileShare);

        restFileShareMockMvc.perform(put("/api/file-shares")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fileShareDTO)))
                .andExpect(status().isOk());

        // Validate the FileShare in the database
        List<FileShare> fileShares = fileShareRepository.findAll();
        assertThat(fileShares).hasSize(databaseSizeBeforeUpdate);
        FileShare testFileShare = fileShares.get(fileShares.size() - 1);
        assertThat(testFileShare.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileShare.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testFileShare.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testFileShare.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteFileShare() throws Exception {
        // Initialize the database
        fileShareRepository.saveAndFlush(fileShare);
        int databaseSizeBeforeDelete = fileShareRepository.findAll().size();

        // Get the fileShare
        restFileShareMockMvc.perform(delete("/api/file-shares/{id}", fileShare.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FileShare> fileShares = fileShareRepository.findAll();
        assertThat(fileShares).hasSize(databaseSizeBeforeDelete - 1);
    }
}

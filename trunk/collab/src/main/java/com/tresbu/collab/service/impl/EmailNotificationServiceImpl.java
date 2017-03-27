package com.tresbu.collab.service.impl;

import com.tresbu.collab.service.EmailNotificationService;
import com.tresbu.collab.domain.EmailNotification;
import com.tresbu.collab.repository.EmailNotificationRepository;
import com.tresbu.collab.repository.UserRepository;
import com.tresbu.collab.security.SecurityUtils;
import com.tresbu.collab.service.dto.EmailNotificationDTO;
import com.tresbu.collab.service.mapper.EmailNotificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EmailNotification.
 */
@Service
@Transactional
public class EmailNotificationServiceImpl implements EmailNotificationService{

    private final Logger log = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);
    
    @Inject
    private EmailNotificationRepository emailNotificationRepository;

    @Inject
    private EmailNotificationMapper emailNotificationMapper;
    
    
    @Inject
    private UserRepository userRepository;

    /**
     * Save a emailNotification.
     *
     * @param emailNotificationDTO the entity to save
     * @return the persisted entity
     */
    public EmailNotificationDTO save(EmailNotificationDTO emailNotificationDTO) {
        log.debug("Request to save EmailNotification : {}", emailNotificationDTO);
        EmailNotification emailNotification = emailNotificationMapper.emailNotificationDTOToEmailNotification(emailNotificationDTO);
        
        emailNotification.setCreatedBy(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        emailNotification.setCreatedDate(Instant.now().toEpochMilli());
        emailNotification.setTenant(SecurityUtils.getCurrentUserTenant());
        emailNotification = emailNotificationRepository.save(emailNotification);
        EmailNotificationDTO result = emailNotificationMapper.emailNotificationToEmailNotificationDTO(emailNotification);
        return result;
    }

    /**
     *  Get all the emailNotifications.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<EmailNotificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailNotifications");
        Page<EmailNotification> result = emailNotificationRepository.findAll(pageable);
        return result.map(emailNotification -> emailNotificationMapper.emailNotificationToEmailNotificationDTO(emailNotification));
    }

    /**
     *  Get one emailNotification by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EmailNotificationDTO findOne(Long id) {
        log.debug("Request to get EmailNotification : {}", id);
        EmailNotification emailNotification = emailNotificationRepository.findOne(id);
        EmailNotificationDTO emailNotificationDTO = emailNotificationMapper.emailNotificationToEmailNotificationDTO(emailNotification);
        return emailNotificationDTO;
    }

    /**
     *  Delete the  emailNotification by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EmailNotification : {}", id);
        emailNotificationRepository.delete(id);
    }
}

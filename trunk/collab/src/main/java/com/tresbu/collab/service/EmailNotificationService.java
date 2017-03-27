package com.tresbu.collab.service;

import com.tresbu.collab.service.dto.EmailNotificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing EmailNotification.
 */
public interface EmailNotificationService {

    /**
     * Save a emailNotification.
     *
     * @param emailNotificationDTO the entity to save
     * @return the persisted entity
     */
    EmailNotificationDTO save(EmailNotificationDTO emailNotificationDTO);

    /**
     *  Get all the emailNotifications.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EmailNotificationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" emailNotification.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EmailNotificationDTO findOne(Long id);

    /**
     *  Delete the "id" emailNotification.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

package com.tresbu.collab.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tresbu.collab.service.EmailNotificationService;
import com.tresbu.collab.web.rest.util.HeaderUtil;
import com.tresbu.collab.web.rest.util.PaginationUtil;
import com.tresbu.collab.service.dto.EmailNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing EmailNotification.
 */
@RestController
@RequestMapping("/api")
public class EmailNotificationResource {

    private final Logger log = LoggerFactory.getLogger(EmailNotificationResource.class);
        
    @Inject
    private EmailNotificationService emailNotificationService;

    /**
     * POST  /email-notifications : Create a new emailNotification.
     *
     * @param emailNotificationDTO the emailNotificationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emailNotificationDTO, or with status 400 (Bad Request) if the emailNotification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/email-notifications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmailNotificationDTO> createEmailNotification(@Valid @RequestBody EmailNotificationDTO emailNotificationDTO) throws URISyntaxException {
        log.debug("REST request to save EmailNotification : {}", emailNotificationDTO);
        if (emailNotificationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("emailNotification", "idexists", "A new emailNotification cannot already have an ID")).body(null);
        }
        EmailNotificationDTO result = emailNotificationService.save(emailNotificationDTO);
        return ResponseEntity.created(new URI("/api/email-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("emailNotification", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /email-notifications : Updates an existing emailNotification.
     *
     * @param emailNotificationDTO the emailNotificationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emailNotificationDTO,
     * or with status 400 (Bad Request) if the emailNotificationDTO is not valid,
     * or with status 500 (Internal Server Error) if the emailNotificationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/email-notifications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmailNotificationDTO> updateEmailNotification(@Valid @RequestBody EmailNotificationDTO emailNotificationDTO) throws URISyntaxException {
        log.debug("REST request to update EmailNotification : {}", emailNotificationDTO);
        if (emailNotificationDTO.getId() == null) {
            return createEmailNotification(emailNotificationDTO);
        }
        EmailNotificationDTO result = emailNotificationService.save(emailNotificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("emailNotification", emailNotificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /email-notifications : get all the emailNotifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of emailNotifications in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/email-notifications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EmailNotificationDTO>> getAllEmailNotifications(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of EmailNotifications");
        Page<EmailNotificationDTO> page = emailNotificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/email-notifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /email-notifications/:id : get the "id" emailNotification.
     *
     * @param id the id of the emailNotificationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emailNotificationDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/email-notifications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmailNotificationDTO> getEmailNotification(@PathVariable Long id) {
        log.debug("REST request to get EmailNotification : {}", id);
        EmailNotificationDTO emailNotificationDTO = emailNotificationService.findOne(id);
        return Optional.ofNullable(emailNotificationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /email-notifications/:id : delete the "id" emailNotification.
     *
     * @param id the id of the emailNotificationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/email-notifications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmailNotification(@PathVariable Long id) {
        log.debug("REST request to delete EmailNotification : {}", id);
        emailNotificationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("emailNotification", id.toString())).build();
    }

}

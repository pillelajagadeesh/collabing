package com.tresbu.collab.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tresbu.collab.service.CallHistoryService;
import com.tresbu.collab.web.rest.util.HeaderUtil;
import com.tresbu.collab.web.rest.util.PaginationUtil;
import com.tresbu.collab.service.dto.CallHistoryDTO;
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
 * REST controller for managing CallHistory.
 */
@RestController
@RequestMapping("/api")
public class CallHistoryResource {

    private final Logger log = LoggerFactory.getLogger(CallHistoryResource.class);
        
    @Inject
    private CallHistoryService callHistoryService;

    /**
     * POST  /call-histories : Create a new callHistory.
     *
     * @param callHistoryDTO the callHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new callHistoryDTO, or with status 400 (Bad Request) if the callHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/call-histories",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CallHistoryDTO> createCallHistory(@Valid @RequestBody CallHistoryDTO callHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save CallHistory : {}", callHistoryDTO);
        if (callHistoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("callHistory", "idexists", "A new callHistory cannot already have an ID")).body(null);
        }
        CallHistoryDTO result = callHistoryService.save(callHistoryDTO);
        return ResponseEntity.created(new URI("/api/call-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("callHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /call-histories : Updates an existing callHistory.
     *
     * @param callHistoryDTO the callHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated callHistoryDTO,
     * or with status 400 (Bad Request) if the callHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the callHistoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/call-histories",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CallHistoryDTO> updateCallHistory(@Valid @RequestBody CallHistoryDTO callHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update CallHistory : {}", callHistoryDTO);
        if (callHistoryDTO.getId() == null) {
            return createCallHistory(callHistoryDTO);
        }
        CallHistoryDTO result = callHistoryService.save(callHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("callHistory", callHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /call-histories : get all the callHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of callHistories in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/call-histories",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CallHistoryDTO>> getAllCallHistories(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CallHistories");
        Page<CallHistoryDTO> page = callHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/call-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /call-histories/:id : get the "id" callHistory.
     *
     * @param id the id of the callHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the callHistoryDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/call-histories/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CallHistoryDTO> getCallHistory(@PathVariable Long id) {
        log.debug("REST request to get CallHistory : {}", id);
        CallHistoryDTO callHistoryDTO = callHistoryService.findOne(id);
        return Optional.ofNullable(callHistoryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /call-histories/:id : delete the "id" callHistory.
     *
     * @param id the id of the callHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/call-histories/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCallHistory(@PathVariable Long id) {
        log.debug("REST request to delete CallHistory : {}", id);
        callHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("callHistory", id.toString())).build();
    }

}

package com.tresbu.collab.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tresbu.collab.service.FileShareService;
import com.tresbu.collab.web.rest.util.HeaderUtil;
import com.tresbu.collab.web.rest.util.PaginationUtil;
import com.tresbu.collab.service.dto.FileShareDTO;
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
 * REST controller for managing FileShare.
 */
@RestController
@RequestMapping("/api")
public class FileShareResource {

    private final Logger log = LoggerFactory.getLogger(FileShareResource.class);
        
    @Inject
    private FileShareService fileShareService;

    /**
     * POST  /file-shares : Create a new fileShare.
     *
     * @param fileShareDTO the fileShareDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fileShareDTO, or with status 400 (Bad Request) if the fileShare has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/file-shares",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FileShareDTO> createFileShare(@Valid @RequestBody FileShareDTO fileShareDTO) throws URISyntaxException {
        log.debug("REST request to save FileShare : {}", fileShareDTO);
        if (fileShareDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("fileShare", "idexists", "A new fileShare cannot already have an ID")).body(null);
        }
        FileShareDTO result = fileShareService.save(fileShareDTO);
        return ResponseEntity.created(new URI("/api/file-shares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fileShare", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-shares : Updates an existing fileShare.
     *
     * @param fileShareDTO the fileShareDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fileShareDTO,
     * or with status 400 (Bad Request) if the fileShareDTO is not valid,
     * or with status 500 (Internal Server Error) if the fileShareDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/file-shares",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FileShareDTO> updateFileShare(@Valid @RequestBody FileShareDTO fileShareDTO) throws URISyntaxException {
        log.debug("REST request to update FileShare : {}", fileShareDTO);
        if (fileShareDTO.getId() == null) {
            return createFileShare(fileShareDTO);
        }
        FileShareDTO result = fileShareService.save(fileShareDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fileShare", fileShareDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-shares : get all the fileShares.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fileShares in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/file-shares",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FileShareDTO>> getAllFileShares(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FileShares");
        Page<FileShareDTO> page = fileShareService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/file-shares");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /file-shares/:id : get the "id" fileShare.
     *
     * @param id the id of the fileShareDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fileShareDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/file-shares/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FileShareDTO> getFileShare(@PathVariable Long id) {
        log.debug("REST request to get FileShare : {}", id);
        FileShareDTO fileShareDTO = fileShareService.findOne(id);
        return Optional.ofNullable(fileShareDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /file-shares/:id : delete the "id" fileShare.
     *
     * @param id the id of the fileShareDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/file-shares/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFileShare(@PathVariable Long id) {
        log.debug("REST request to delete FileShare : {}", id);
        fileShareService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fileShare", id.toString())).build();
    }

}

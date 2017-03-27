package com.tresbu.collab.service;

import com.tresbu.collab.service.dto.FileShareDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing FileShare.
 */
public interface FileShareService {

    /**
     * Save a fileShare.
     *
     * @param fileShareDTO the entity to save
     * @return the persisted entity
     */
    FileShareDTO save(FileShareDTO fileShareDTO);

    /**
     *  Get all the fileShares.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FileShareDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" fileShare.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FileShareDTO findOne(Long id);

    /**
     *  Delete the "id" fileShare.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

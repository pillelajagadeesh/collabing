package com.tresbu.collab.service;

import com.tresbu.collab.service.dto.CallHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing CallHistory.
 */
public interface CallHistoryService {

    /**
     * Save a callHistory.
     *
     * @param callHistoryDTO the entity to save
     * @return the persisted entity
     */
    CallHistoryDTO save(CallHistoryDTO callHistoryDTO);

    /**
     *  Get all the callHistories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CallHistoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" callHistory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CallHistoryDTO findOne(Long id);

    /**
     *  Delete the "id" callHistory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

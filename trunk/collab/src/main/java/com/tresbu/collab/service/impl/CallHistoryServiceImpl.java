package com.tresbu.collab.service.impl;

import com.tresbu.collab.service.CallHistoryService;
import com.tresbu.collab.domain.CallHistory;
import com.tresbu.collab.repository.CallHistoryRepository;
import com.tresbu.collab.repository.UserRepository;
import com.tresbu.collab.security.SecurityUtils;
import com.tresbu.collab.service.dto.CallHistoryDTO;
import com.tresbu.collab.service.mapper.CallHistoryMapper;
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
 * Service Implementation for managing CallHistory.
 */
@Service
@Transactional
public class CallHistoryServiceImpl implements CallHistoryService{

    private final Logger log = LoggerFactory.getLogger(CallHistoryServiceImpl.class);
    
    @Inject
    private CallHistoryRepository callHistoryRepository;

    @Inject
    private CallHistoryMapper callHistoryMapper;
    
    
    @Inject
    private UserRepository userRepository;

    /**
     * Save a callHistory.
     *
     * @param callHistoryDTO the entity to save
     * @return the persisted entity
     */
    public CallHistoryDTO save(CallHistoryDTO callHistoryDTO) {
        log.debug("Request to save CallHistory : {}", callHistoryDTO);
        CallHistory callHistory = callHistoryMapper.callHistoryDTOToCallHistory(callHistoryDTO);
        
        callHistory.setCreatedBy(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        callHistory.setCreatedDate(Instant.now().toEpochMilli());
        callHistory.setTenant(SecurityUtils.getCurrentUserTenant());
        
        callHistory = callHistoryRepository.save(callHistory);
        CallHistoryDTO result = callHistoryMapper.callHistoryToCallHistoryDTO(callHistory);
        return result;
    }

    /**
     *  Get all the callHistories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<CallHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CallHistories");
        Page<CallHistory> result = callHistoryRepository.findAll(pageable);
        return result.map(callHistory -> callHistoryMapper.callHistoryToCallHistoryDTO(callHistory));
    }

    /**
     *  Get one callHistory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CallHistoryDTO findOne(Long id) {
        log.debug("Request to get CallHistory : {}", id);
        CallHistory callHistory = callHistoryRepository.findOne(id);
        CallHistoryDTO callHistoryDTO = callHistoryMapper.callHistoryToCallHistoryDTO(callHistory);
        return callHistoryDTO;
    }

    /**
     *  Delete the  callHistory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CallHistory : {}", id);
        callHistoryRepository.delete(id);
    }
}

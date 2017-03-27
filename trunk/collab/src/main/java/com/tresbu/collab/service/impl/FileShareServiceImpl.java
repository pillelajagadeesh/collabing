package com.tresbu.collab.service.impl;

import com.tresbu.collab.service.FileShareService;
import com.tresbu.collab.domain.FileShare;
import com.tresbu.collab.repository.FileShareRepository;
import com.tresbu.collab.repository.UserRepository;
import com.tresbu.collab.security.SecurityUtils;
import com.tresbu.collab.service.dto.FileShareDTO;
import com.tresbu.collab.service.mapper.FileShareMapper;
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
 * Service Implementation for managing FileShare.
 */
@Service
@Transactional
public class FileShareServiceImpl implements FileShareService{

    private final Logger log = LoggerFactory.getLogger(FileShareServiceImpl.class);
    
    @Inject
    private FileShareRepository fileShareRepository;

    @Inject
    private FileShareMapper fileShareMapper;
    @Inject
    private UserRepository userRepository;

    /**
     * Save a fileShare.
     *
     * @param fileShareDTO the entity to save
     * @return the persisted entity
     */
    public FileShareDTO save(FileShareDTO fileShareDTO) {
        log.debug("Request to save FileShare : {}", fileShareDTO);
        FileShare fileShare = fileShareMapper.fileShareDTOToFileShare(fileShareDTO);
        fileShare.setCreatedBy(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        fileShare.setCreatedDate(Instant.now().toEpochMilli());
        fileShare.setTenant(SecurityUtils.getCurrentUserTenant());
        fileShare = fileShareRepository.save(fileShare);
        FileShareDTO result = fileShareMapper.fileShareToFileShareDTO(fileShare);
        return result;
    }

    /**
     *  Get all the fileShares.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<FileShareDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileShares");
        Page<FileShare> result = fileShareRepository.findAll(pageable);
        return result.map(fileShare -> fileShareMapper.fileShareToFileShareDTO(fileShare));
    }

    /**
     *  Get one fileShare by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public FileShareDTO findOne(Long id) {
        log.debug("Request to get FileShare : {}", id);
        FileShare fileShare = fileShareRepository.findOne(id);
        FileShareDTO fileShareDTO = fileShareMapper.fileShareToFileShareDTO(fileShare);
        return fileShareDTO;
    }

    /**
     *  Delete the  fileShare by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FileShare : {}", id);
        fileShareRepository.delete(id);
    }
}

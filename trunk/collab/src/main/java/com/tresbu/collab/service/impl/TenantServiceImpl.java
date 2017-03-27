package com.tresbu.collab.service.impl;

import com.tresbu.collab.service.TenantService;
import com.tresbu.collab.domain.Tenant;
import com.tresbu.collab.repository.TenantRepository;
import com.tresbu.collab.repository.UserRepository;
import com.tresbu.collab.security.SecurityUtils;
import com.tresbu.collab.service.dto.TenantDTO;
import com.tresbu.collab.service.mapper.TenantMapper;
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
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Tenant.
 */
@Service
@Transactional
public class TenantServiceImpl implements TenantService{

    private final Logger log = LoggerFactory.getLogger(TenantServiceImpl.class);
    
    @Inject
    private TenantRepository tenantRepository;
    
    @Inject
    private UserRepository userRepository;

    @Inject
    private TenantMapper tenantMapper;

    /**
     * Save a tenant.
     *
     * @param tenantDTO the entity to save
     * @return the persisted entity
     */
    public TenantDTO save(TenantDTO tenantDTO) {
        log.debug("Request to save Tenant : {}", tenantDTO);
        if(tenantDTO.getId() != null){
        	// This is important to set whether it is trakeye tennat or not. Else false will be stored in db and that creates problem while in  password reset and other api's
        	  Tenant existingTenant= tenantRepository.findOne(tenantDTO.getId());
        	  tenantDTO.setTrakeyeTenant(existingTenant.getTrakeyeTenant());
        }
      
        Tenant tenant = tenantMapper.tenantDTOToTenant(tenantDTO);
        tenant.createdBy(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        tenant.setCreatedDate(Instant.now().toEpochMilli());
        if(tenantDTO.getTrakeyeTenant() == null){
        	tenant.setTrakeyeTenant(false);
        }else{
        	tenant.setTrakeyeTenant(tenantDTO.getTrakeyeTenant());
        }
        
        tenant = tenantRepository.save(tenant);
        TenantDTO result = tenantMapper.tenantToTenantDTO(tenant);
        return result;
    }

    /**
     *  Get all the tenants.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<TenantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tenants");
        Page<Tenant> result = tenantRepository.findAll(pageable);
        return result.map(tenant -> tenantMapper.tenantToTenantDTO(tenant));
    }

    /**
     *  Get one tenant by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TenantDTO findOne(Long id) {
        log.debug("Request to get Tenant : {}", id);
        Tenant tenant = tenantRepository.findOne(id);
        TenantDTO tenantDTO = tenantMapper.tenantToTenantDTO(tenant);
        return tenantDTO;
    }
    
    @Transactional(readOnly = true) 
    public Optional<Tenant> findOneByName(String name) {
        log.debug("Request to get Tenant : {}", name);
        Optional<Tenant> tenant = tenantRepository.findOneByName(name);
        //TenantDTO tenantDTO = tenantMapper.tenantToTenantDTO(tenant.get());
        return tenant;
    }

    /**
     *  Delete the  tenant by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tenant : {}", id);
        tenantRepository.delete(id);
    }
}

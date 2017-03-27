package com.tresbu.collab.service;

import com.tresbu.collab.domain.Tenant;
import com.tresbu.collab.domain.User;
import com.tresbu.collab.service.dto.TenantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Tenant.
 */
public interface TenantService {

    /**
     * Save a tenant.
     *
     * @param tenantDTO the entity to save
     * @return the persisted entity
     */
    TenantDTO save(TenantDTO tenantDTO);

    /**
     *  Get all the tenants.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TenantDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tenant.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TenantDTO findOne(Long id);
    
    Optional<Tenant> findOneByName(String name);

    /**
     *  Delete the "id" tenant.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

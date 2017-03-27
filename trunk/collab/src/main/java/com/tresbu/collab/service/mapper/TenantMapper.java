package com.tresbu.collab.service.mapper;

import com.tresbu.collab.domain.*;
import com.tresbu.collab.service.dto.TenantDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Tenant and its DTO TenantDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface TenantMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.login", target = "createdByLogin")
    TenantDTO tenantToTenantDTO(Tenant tenant);

    List<TenantDTO> tenantsToTenantDTOs(List<Tenant> tenants);

    @Mapping(source = "createdById", target = "createdBy")
    Tenant tenantDTOToTenant(TenantDTO tenantDTO);

    List<Tenant> tenantDTOsToTenants(List<TenantDTO> tenantDTOs);
}

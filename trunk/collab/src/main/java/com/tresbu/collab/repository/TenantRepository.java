package com.tresbu.collab.repository;

import com.tresbu.collab.domain.Tenant;
import com.tresbu.collab.domain.User;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Tenant entity.
 */
@SuppressWarnings("unused")
public interface TenantRepository extends JpaRepository<Tenant,Long> {

    @Query("select tenant from Tenant tenant where tenant.createdBy.login = ?#{principal.username}")
    List<Tenant> findByCreatedByIsCurrentUser();
    
    Optional<Tenant> findOneByName(String name);
    Tenant findOneById(Long id);

}

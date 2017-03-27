package com.tresbu.collab.repository;

import com.tresbu.collab.domain.EmailNotification;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EmailNotification entity.
 */
@SuppressWarnings("unused")
public interface EmailNotificationRepository extends JpaRepository<EmailNotification,Long> {

    @Query("select emailNotification from EmailNotification emailNotification where emailNotification.fromUser.login = ?#{principal.username}")
    List<EmailNotification> findByFromUserIsCurrentUser();

}

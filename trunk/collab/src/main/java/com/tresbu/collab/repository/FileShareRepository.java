package com.tresbu.collab.repository;

import com.tresbu.collab.domain.FileShare;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FileShare entity.
 */
@SuppressWarnings("unused")
public interface FileShareRepository extends JpaRepository<FileShare,Long> {

    @Query("select fileShare from FileShare fileShare where fileShare.fromUser.login = ?#{principal.username}")
    List<FileShare> findByFromUserIsCurrentUser();

    @Query("select fileShare from FileShare fileShare where fileShare.toUser.login = ?#{principal.username}")
    List<FileShare> findByToUserIsCurrentUser();

}

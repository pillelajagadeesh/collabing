package com.tresbu.collab.repository;

import com.tresbu.collab.domain.CallHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CallHistory entity.
 */
@SuppressWarnings("unused")
public interface CallHistoryRepository extends JpaRepository<CallHistory,Long> {

    @Query("select callHistory from CallHistory callHistory where callHistory.caller.login = ?#{principal.username}")
    List<CallHistory> findByCallerIsCurrentUser();

    @Query("select callHistory from CallHistory callHistory where callHistory.callee.login = ?#{principal.username}")
    List<CallHistory> findByCalleeIsCurrentUser();

}

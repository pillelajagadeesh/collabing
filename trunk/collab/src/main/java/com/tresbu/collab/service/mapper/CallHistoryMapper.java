package com.tresbu.collab.service.mapper;

import com.tresbu.collab.domain.*;
import com.tresbu.collab.service.dto.CallHistoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CallHistory and its DTO CallHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserMapper.class, })
public interface CallHistoryMapper {

    @Mapping(source = "caller.id", target = "callerId")
    @Mapping(source = "caller.login", target = "callerLogin")
    @Mapping(source = "callee.id", target = "calleeId")
    @Mapping(source = "callee.login", target = "calleeLogin")
    CallHistoryDTO callHistoryToCallHistoryDTO(CallHistory callHistory);

    List<CallHistoryDTO> callHistoriesToCallHistoryDTOs(List<CallHistory> callHistories);

    @Mapping(source = "callerId", target = "caller")
    @Mapping(source = "calleeId", target = "callee")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    CallHistory callHistoryDTOToCallHistory(CallHistoryDTO callHistoryDTO);

    List<CallHistory> callHistoryDTOsToCallHistories(List<CallHistoryDTO> callHistoryDTOs);
}

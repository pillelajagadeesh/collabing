package com.tresbu.collab.service.mapper;

import com.tresbu.collab.domain.*;
import com.tresbu.collab.service.dto.FileShareDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity FileShare and its DTO FileShareDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserMapper.class, })
public interface FileShareMapper {

    @Mapping(source = "fromUser.id", target = "fromUserId")
    @Mapping(source = "fromUser.login", target = "fromUserLogin")
    @Mapping(source = "toUser.id", target = "toUserId")
    @Mapping(source = "toUser.login", target = "toUserLogin")
    FileShareDTO fileShareToFileShareDTO(FileShare fileShare);

    List<FileShareDTO> fileSharesToFileShareDTOs(List<FileShare> fileShares);

    @Mapping(source = "fromUserId", target = "fromUser")
    @Mapping(source = "toUserId", target = "toUser")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    FileShare fileShareDTOToFileShare(FileShareDTO fileShareDTO);

    List<FileShare> fileShareDTOsToFileShares(List<FileShareDTO> fileShareDTOs);
}

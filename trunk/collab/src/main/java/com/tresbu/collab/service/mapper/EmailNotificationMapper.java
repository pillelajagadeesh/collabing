package com.tresbu.collab.service.mapper;

import com.tresbu.collab.domain.*;
import com.tresbu.collab.service.dto.EmailNotificationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity EmailNotification and its DTO EmailNotificationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface EmailNotificationMapper {

    @Mapping(source = "fromUser.id", target = "fromUserId")
    @Mapping(source = "fromUser.login", target = "fromUserLogin")
    @Mapping(source = "toUser.id", target = "toUserId")
    @Mapping(source = "toUser.login", target = "toUserLogin")
    EmailNotificationDTO emailNotificationToEmailNotificationDTO(EmailNotification emailNotification);

    List<EmailNotificationDTO> emailNotificationsToEmailNotificationDTOs(List<EmailNotification> emailNotifications);

    @Mapping(source = "fromUserId", target = "fromUser")
    @Mapping(source = "toUserId", target = "toUser")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    EmailNotification emailNotificationDTOToEmailNotification(EmailNotificationDTO emailNotificationDTO);

    List<EmailNotification> emailNotificationDTOsToEmailNotifications(List<EmailNotificationDTO> emailNotificationDTOs);

    
}

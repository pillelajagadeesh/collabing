package com.tresbu.collab.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the EmailNotification entity.
 */
public class EmailNotificationDTO implements Serializable {

    private Long id;

    private String subject;

    private String message;

    private Long sentDate;


    private Long fromUserId;
    

    private String fromUserLogin;

    private Long toUserId;
    

    private String toUserLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public Long getSentDate() {
        return sentDate;
    }

    public void setSentDate(Long sentDate) {
        this.sentDate = sentDate;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long userId) {
        this.fromUserId = userId;
    }


    public String getFromUserLogin() {
        return fromUserLogin;
    }

    public void setFromUserLogin(String userLogin) {
        this.fromUserLogin = userLogin;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }


    public String getToUserLogin() {
        return toUserLogin;
    }

    public void setToUserLogin(String toUserLogin) {
        this.toUserLogin = toUserLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailNotificationDTO emailNotificationDTO = (EmailNotificationDTO) o;

        if ( ! Objects.equals(id, emailNotificationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmailNotificationDTO{" +
            "id=" + id +
            ", subject='" + subject + "'" +
            ", message='" + message + "'" +
            ", sentDate='" + sentDate + "'" +
            '}';
    }
}

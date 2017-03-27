package com.tresbu.collab.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EmailNotification.
 */
@Entity
@Table(name = "collab_email_notification")
public class EmailNotification extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "message")
    private String message;

    @Column(name = "sent_date")
    private Long sentDate;

    @ManyToOne
    @NotNull
    private User fromUser;

    @ManyToOne
    @NotNull
    private User toUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public EmailNotification subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public EmailNotification message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSentDate() {
        return sentDate;
    }

    public EmailNotification sentDate(Long sentDate) {
        this.sentDate = sentDate;
        return this;
    }

    public void setSentDate(Long sentDate) {
        this.sentDate = sentDate;
    }

    public User getFromUser() {
        return fromUser;
    }

    public EmailNotification fromUser(User user) {
        this.fromUser = user;
        return this;
    }

    public void setFromUser(User user) {
        this.fromUser = user;
    }

    public User getToUser() {
        return toUser;
    }

    public EmailNotification toUser(User toUser) {
        this.toUser = toUser;
        return this;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmailNotification emailNotification = (EmailNotification) o;
        if(emailNotification.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, emailNotification.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmailNotification{" +
            "id=" + id +
            ", subject='" + subject + "'" +
            ", message='" + message + "'" +
            ", sentDate='" + sentDate + "'" +
            '}';
    }
}

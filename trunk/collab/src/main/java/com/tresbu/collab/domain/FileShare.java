package com.tresbu.collab.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.Objects;

import com.tresbu.collab.domain.enumeration.ContentType;

/**
 * A FileShare.
 */
@Entity
@Table(name = "collab_file_share")
public class FileShare extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "file_name", length = 50, nullable = false)
    private String fileName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Type(type="org.hibernate.type.BinaryType") 
    @Column(name = "content")
    private byte[] content;

    @Column(name = "content_content_type")
    private String contentContentType;

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

    public String getFileName() {
        return fileName;
    }

    public FileShare fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public FileShare contentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public FileShare content(byte[] content) {
        this.content = content;
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public FileShare contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public User getFromUser() {
        return fromUser;
    }

    public FileShare fromUser(User user) {
        this.fromUser = user;
        return this;
    }

    public void setFromUser(User user) {
        this.fromUser = user;
    }

    public User getToUser() {
        return toUser;
    }

    public FileShare toUser(User user) {
        this.toUser = user;
        return this;
    }

    public void setToUser(User user) {
        this.toUser = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FileShare fileShare = (FileShare) o;
        if(fileShare.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fileShare.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FileShare{" +
            "id=" + id +
            ", fileName='" + fileName + "'" +
            ", contentType='" + contentType + "'" +
            ", content='" + content + "'" +
            ", contentContentType='" + contentContentType + "'" +
            '}';
    }
}

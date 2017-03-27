package com.tresbu.collab.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

import com.tresbu.collab.domain.enumeration.ContentType;

/**
 * A DTO for the FileShare entity.
 */
public class FileShareDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String fileName;

    @NotNull
    private ContentType contentType;

    @Lob
    private byte[] content;

    private String contentContentType;

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
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
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

    public void setToUserId(Long userId) {
        this.toUserId = userId;
    }


    public String getToUserLogin() {
        return toUserLogin;
    }

    public void setToUserLogin(String userLogin) {
        this.toUserLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileShareDTO fileShareDTO = (FileShareDTO) o;

        if ( ! Objects.equals(id, fileShareDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FileShareDTO{" +
            "id=" + id +
            ", fileName='" + fileName + "'" +
            ", contentType='" + contentType + "'" +
            ", content='" + content + "'" +
            '}';
    }
}

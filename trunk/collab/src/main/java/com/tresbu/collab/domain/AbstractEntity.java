package com.tresbu.collab.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
@MappedSuperclass
public class AbstractEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1520754675551898384L;

	@CreatedDate
    @Column(name = "created_date", nullable = false)
    @JsonIgnore
    private long createdDate =Instant.now().toEpochMilli();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    private long lastModifiedDate = Instant.now().toEpochMilli();

  

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

   

    public long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    @ManyToOne
    private User createdBy;



	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	@ManyToOne
	private User updatedBy;



	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}
	@ManyToOne
	private Tenant tenant;



	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
    
   
}

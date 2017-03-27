package com.tresbu.collab.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tenant.
 */
@Entity
@Table(name = "collab_tenant")
public class Tenant implements Serializable {

    

    /**
	 * 
	 */
	private static final long serialVersionUID = 6829635402038024950L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @NotNull
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "organization", length = 50, nullable = false)
    private String organization;

    @Column(name = "created_date")
    private Long createdDate;

    @Column(name = "updated_date")
    private Long updatedDate;

    @ManyToOne
    private User createdBy;
    
    @Column(name = "trakeye_tenant")
    private Boolean trakeyeTenant = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Tenant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public Tenant address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Tenant city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public Tenant phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public Tenant email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganization() {
        return organization;
    }

    public Tenant organization(String organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public Tenant createdDate(Long createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedDate() {
        return updatedDate;
    }

    public Tenant updatedDate(Long updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public Tenant createdBy(User user) {
        this.createdBy = user;
        return this;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }
    
    

    

	public Boolean getTrakeyeTenant() {
		return trakeyeTenant;
	}

	public void setTrakeyeTenant(Boolean trakeyeTenant) {
		this.trakeyeTenant = trakeyeTenant;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tenant tenant = (Tenant) o;
        if(tenant.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tenant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tenant{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", phone='" + phone + "'" +
            ", email='" + email + "'" +
            ", organization='" + organization + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            '}';
    }
}

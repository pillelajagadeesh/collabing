package com.tresbu.collab.service.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import com.tresbu.collab.domain.Authority;
import com.tresbu.collab.domain.Tenant;
import com.tresbu.collab.domain.User;


public class UserUIDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 7442359188227402786L;
	
	public UserUIDTO() {
	
	}
	
	public UserUIDTO(User user) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.langKey = user.getLangKey();
		this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
		this.activated=user.getActivated();
		
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email =user.getEmail();
		this.resetKey=user.getResetKey();
		
		this.createdBy=user.getCreatedBy();
		this.tenant = user.getTenant();

	
	}
	
	
	
	private String login;
	
	private Long id;

	private String firstName;

	private String lastName;

	private String email;

	private boolean activated = false;

	private String langKey;

	private Set<String> authorities;
	
	
	/*IMPORTANT: Please donot remover Tenant attribute in UserUIDTO because it is needed  to hide password link in navbar based on
	 * whether user is trakeye user or not
	 *  
	 */
	private Tenant tenant;

	/*IMPORTANT: Please donot remover resetKey attribute in UserUIDTO beacause it is needed in trakeye
	 *  to reset password based on reset key.
	 */
	private String resetKey;
	
	private String createdBy;
	

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getResetKey() {
		return resetKey;
	}

	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public String getLangKey() {
		return langKey;
	}

	public void setLangKey(String langKey) {
		this.langKey = langKey;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}
	
	

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	@Override
	public String toString() {
		return "UserUIDTO [login=" + login + ", id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", activated=" + activated + ", langKey=" + langKey + ", authorities="
				+ authorities + ", resetKey=" + resetKey + ", createdBy=" + createdBy + "]";
	}

	


	
	
	
}




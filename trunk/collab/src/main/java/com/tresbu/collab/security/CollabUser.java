package com.tresbu.collab.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.tresbu.collab.domain.Tenant;

public class CollabUser extends org.springframework.security.core.userdetails.User {
	
	public CollabUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
	}

	public CollabUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO Auto-generated constructor stub
	}
	
	public CollabUser(String username, String password, Collection<? extends GrantedAuthority> authorities,Tenant tenant) {
		super(username, password, authorities);
		this.tenant = tenant;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6975666437558077513L;
	private Tenant tenant;

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	
}

package org.eps.common.po;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eps.common.util.Constants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "userinfo")
public class UserInfo implements Serializable, UserDetails {

	private static final long serialVersionUID = -5031396113233069554L;

	private Long id;
	private String username;
	private String password;
	private Long userLevel;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "userlevel")
	public Long getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Long userLevel) {
		this.userLevel = userLevel;
	}

	@Transient
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList(Constants.DEFAULT_SPRING_SECURITY_ROLE);
	}

	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.username.equals(((UserInfo) obj).getUsername());
	}
	
	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

}

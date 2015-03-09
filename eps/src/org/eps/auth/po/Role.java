package org.eps.auth.po;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eps.common.po.Menu;

@Entity
@Table(name = "role")
public class Role implements Serializable {

	private static final long serialVersionUID = -5518856872998470064L;

	private Long id;
	private String roleName;
	private String description;
	
	private List<Menu> menus;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "role_name")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "role_desc")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Transient
	public List<Menu> getMenus() {
		return menus;
	}
	
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

}

package org.eps.auth.action;

import java.io.PrintWriter;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.convention.annotation.Action;
import org.eps.auth.po.Role;
import org.eps.auth.service.RoleService;
import org.eps.common.action.BaseAction;
import org.eps.common.util.Constants;
import org.eps.common.util.Tools;
import org.eps.common.vo.JsonVO;

public class RoleAction extends BaseAction {

	private static final long serialVersionUID = -6961564391073461666L;

	@Resource(name = "roleService")
	private RoleService roleService;

	private Long roleId;
	private String menuIds;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	@Action("listAllRole")
	public void listAllRole() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			PrintWriter out = response.getWriter();

			String json = this.roleService.findAllRole();

			out.write(json);

			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("loadRole")
	public void loadRole() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();
			
			Role role = this.roleService.findRoleById(roleId);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, role);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "加载角色信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);

		IOUtils.closeQuietly(out);
	}
	
	@Action("settingRoleAuth")
	public void settingRoleAuth() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();
			
			Long[] ids = Tools.split2Array(menuIds, Constants.DEFAULT_DELIMITER);
			
			this.roleService.settingRole(roleId, ids);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "设置角色权限时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);

		IOUtils.closeQuietly(out);
	}

}

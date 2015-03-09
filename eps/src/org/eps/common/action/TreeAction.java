package org.eps.common.action;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.convention.annotation.Action;
import org.eps.common.po.Menu;
import org.eps.common.po.UserInfo;
import org.eps.common.service.MenuService;
import org.eps.common.service.TreeService;
import org.eps.common.util.Constants;
import org.eps.common.vo.Tree;
import org.springframework.security.core.context.SecurityContextHolder;

public class TreeAction extends BaseAction {

	private static final long serialVersionUID = 1612302911249053537L;
	
	@Resource(name = "menuService")
	private MenuService menuService;
	
	@Resource(name = "treeService")
	private TreeService treeService;
	
	@Action("buildSystemMenuTree")
	public void buildSystemMenuTree() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			PrintWriter out = response.getWriter();
			
			UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			List<Menu> menus = this.menuService.findCurrentUserMenus(user);
			
			Tree tree = this.treeService.getSystemMenuTree(menus);
			
			String json = JSONArray.fromObject(tree).toString();
			
			out.write(json);
			
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("buildOrgTree")
	public void buildOrgTree() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			PrintWriter out = response.getWriter();
			
			Tree tree = this.treeService.getOrgTree();
			
			String json = JSONArray.fromObject(tree).toString();
			
			out.write(json);
			
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Action("buildEmployeeTree")
	public void buildEmployeeTree() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			PrintWriter out = response.getWriter();
			
			Tree tree = this.treeService.getEmployeeTree();
			
			String json = JSONArray.fromObject(tree).toString();
			
			out.write(json);
			
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

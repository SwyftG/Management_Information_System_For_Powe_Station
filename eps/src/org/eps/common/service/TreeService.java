package org.eps.common.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.eps.common.dao.MenuDao;
import org.eps.common.po.Menu;
import org.eps.common.util.Constants;
import org.eps.common.vo.Tree;
import org.eps.hrm.dao.OrgDao;
import org.eps.hrm.po.Org;
import org.eps.hrm.service.EmployeeService;
import org.eps.hrm.vo.EmpInfoTreeVO;
import org.springframework.stereotype.Service;

@Service("treeService")
public class TreeService {
	
	@Resource(name = "menuDao")
	private MenuDao menuDao;
	
	@Resource(name = "orgDao")
	private OrgDao orgDao;
	
	@Resource(name = "employeeService")
	private EmployeeService employeeService;
	
	public Tree getSystemMenuTree(List<Menu> menus) throws Exception {
		Tree tree = new Tree();
		
		tree.setId(Constants.DEFAULT_SUPER_ID);
		tree.setText("系统功能");
		tree.setState(Constants.TREE_STATE_OPEN);
		tree.getAttributes().put("url", StringUtils.EMPTY);
		
		Queue<Tree> rootChildren = buildSystemMenuTree(menus, Constants.DEFAULT_SUPER_ID);
		tree.setChildren(rootChildren);
		
		return tree;
	}
	
	public Tree getOrgTree() throws Exception {
		Tree tree = new Tree();
		
		tree.setId(Constants.DEFAULT_SUPER_ID);
		tree.setText("机构树");
		tree.setState(Constants.TREE_STATE_OPEN);
		tree.getAttributes().put("url", StringUtils.EMPTY);
		
		Queue<Tree> rootChildren = buildOrgTree(this.orgDao.findAll(), Constants.DEFAULT_SUPER_ID);
		tree.setChildren(rootChildren);
		
		return tree;
	}
	
	public Tree getEmployeeTree() throws Exception {
		Tree tree = new Tree();
		
		tree.setId(0L);
		tree.setText("员工列表");
		tree.setState(Constants.TREE_STATE_OPEN);
		
		List<EmpInfoTreeVO> vos = this.employeeService.getEmpInfoTreeData();
		
		Queue<Tree> rootChildren = buildEmployeeTree(vos, Constants.DEFAULT_SUPER_ID);
		tree.setChildren(rootChildren);
		
		return tree;
	}

	private Queue<Tree> buildSystemMenuTree(List<Menu> menus, long superId) throws Exception {
		try {
			Queue<Tree> trees = new LinkedList<Tree>();
			
			Tree tree = null;
			
			for (Menu menu : menus) {
				if (menu.getParentId() == superId) {
					tree = new Tree();
					
					tree.setId(menu.getId());
					tree.setText(menu.getMenuName());
					
					if (StringUtils.isNotBlank(menu.getMenuUrl())) {
						tree.getAttributes().put("url", menu.getMenuUrl());
					} else {
						tree.getAttributes().put("url", StringUtils.EMPTY);
					}
					
					if (!Long.valueOf(menu.getParentId()).equals(Constants.DEFAULT_SUPER_ID)) {
						tree.getAttributes().put("pid", menu.getParentId());
					}
					
					Queue<Tree> children = buildSystemMenuTree(menus, menu.getId());
					
					if (CollectionUtils.isNotEmpty(children)) {
						tree.setChildren(children);
						tree.setState(Constants.TREE_STATE_CLOSED);
					}
					
					trees.add(tree);
				}
			}
			
			return trees;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private Queue<Tree> buildOrgTree(List<Org> orgs, long superId) throws Exception {
		try {
			Queue<Tree> trees = new LinkedList<Tree>();
			
			Tree tree = null;
			
			for (Org org : orgs) {
				if (org.getParentId() == superId) {
					tree = new Tree();
					
					tree.setId(org.getId());
					tree.setText(org.getOrgName());
					
					Queue<Tree> children = buildOrgTree(orgs, org.getId());
					
					if (CollectionUtils.isNotEmpty(children)) {
						tree.setChildren(children);
						tree.setState(Constants.TREE_STATE_CLOSED);
					}
					
					trees.add(tree);
				}
			}
			
			return trees;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private Queue<Tree> buildEmployeeTree(List<EmpInfoTreeVO> vos, long superId) throws Exception {
		Queue<Tree> trees = new LinkedList<Tree>();
		
		Tree tree = null;
		
		for (EmpInfoTreeVO vo : vos) {
			if (vo.getPid() == superId) {
				tree = new Tree();
				
				tree.setId(vo.getId());
				tree.setText(vo.getName());
				
				Queue<Tree> children = buildEmployeeTree(vos, vo.getId());
				
				if (CollectionUtils.isNotEmpty(children)) {
					tree.setChildren(children);
					tree.setState(Constants.TREE_STATE_CLOSED);
				}
				
				trees.add(tree);
			}
		}
		
		return trees;
	}
	
}

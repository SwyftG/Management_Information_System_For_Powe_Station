package org.eps.hrm.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.eps.common.util.DateUtils;
import org.eps.common.vo.DataGrid;
import org.eps.hrm.dao.EmpSkillDao;
import org.eps.hrm.po.EmpSkill;
import org.eps.hrm.vo.EmpSkillVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("empSkillService")
public class EmpSkillService {
	
	@Resource(name = "empSkillDao")
	private EmpSkillDao empSkillDao;
	
	public String findEmpSkillByEmpId(Long empId) throws Exception {
		List<EmpSkill> list = this.empSkillDao.findEmpSkillByEmpId(empId);
		
		DataGrid dg = new DataGrid(list.size(), list);
		
		return JSONObject.fromObject(dg).toString();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void addEmpSkill(EmpSkill skill) throws Exception {
		this.empSkillDao.add(skill);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modifyEmpSkill(EmpSkill skill) throws Exception {
		this.empSkillDao.update(skill);
	}
	
	public EmpSkill findEmpSkillById(Long id) throws Exception {
		EmpSkill skill = this.empSkillDao.findByPrimaryKey(id);
		
		skill.setAcquireTimeStr(DateUtils.formatDate(skill.getAcquireTime(), "yyyy-MM-dd"));
		
		return skill;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeEmpSkill(Long[] ids) throws Exception {
		this.empSkillDao.batchDelete(ids);
	}
	
	public String findEmpSkillByCondition(String personCode, String name, Long orgId) throws Exception {
		List<EmpSkillVO> vos = this.empSkillDao.findEmpSkillByCondition(personCode, name, orgId);
		
		DataGrid dg = new DataGrid(vos.size(), vos);
		
		return JSONObject.fromObject(dg).toString();
	}

}

package org.eps.hrm.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.eps.common.vo.DataGrid;
import org.eps.hrm.dao.EmpRewardInfoDao;
import org.eps.hrm.po.EmpRewardInfo;
import org.eps.hrm.vo.EmpRewardVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("empRewardInfoService")
public class EmpRewardInfoService {
	
	@Resource(name = "empRewardInfoDao")
	private EmpRewardInfoDao empRewardInfoDao;
	
	public String findAllEmpRewardInfo() throws Exception {
		List<EmpRewardVO> vos = this.empRewardInfoDao.findAllEmpRewardInfo();
		
		DataGrid dg = new DataGrid(vos.size(), vos);
		
		return JSONObject.fromObject(dg).toString();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void addEmpRewardInfo(EmpRewardInfo info) throws Exception {
		this.empRewardInfoDao.add(info);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modifyEmpRewardInfo(EmpRewardInfo info) throws Exception {
		this.empRewardInfoDao.update(info);
	}
	
	public EmpRewardVO findEmpRewardInfoById(Long id) throws Exception {
		return this.empRewardInfoDao.findEmpRewardInfoById(id);
	}
	
	public EmpRewardInfo findInfoById(Long id) throws Exception {
		return this.empRewardInfoDao.findByPrimaryKey(id);
	}
	
	@Transactional
	public void removeEmpRewardInfo(Long[] ids) throws Exception {
		this.empRewardInfoDao.batchDelete(ids);
	}

}

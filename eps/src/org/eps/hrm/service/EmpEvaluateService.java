package org.eps.hrm.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eps.common.util.Constants;
import org.eps.common.vo.DataGrid;
import org.eps.hrm.dao.EmpEvaluateDao;
import org.eps.hrm.dao.EmployeeDao;
import org.eps.hrm.po.EmpEvaluate;
import org.eps.hrm.po.Employee;
import org.eps.hrm.vo.EmpEvaluateVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("empEvaluateService")
public class EmpEvaluateService {
	
	@Resource(name = "empEvaluateDao")
	private EmpEvaluateDao empEvaluateDao;
	
	@Resource(name = "employeeDao")
	private EmployeeDao employeeDao;
	
	public String findAllEmpEvaluate() throws Exception {
		List<EmpEvaluate> list = this.empEvaluateDao.findAll();
		
		for (EmpEvaluate evaluate : list) {
			evaluate.setEmpName(this.employeeDao.findByPrimaryKey(evaluate.getEmpId()).getName());
		}
		
		DataGrid dg = new DataGrid(list.size(), list);
		
		return JSONObject.fromObject(dg).toString();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void addEmpEvaluate(EmpEvaluate evaluate) throws Exception {
		Long count = this.empEvaluateDao.countEmpEvaluateByCondition(evaluate.getEmpId(), evaluate.getEvaluateYear());
		
		if (0 != count.intValue()) {
			Employee emp = this.employeeDao.findByPrimaryKey(evaluate.getEmpId());
			
			throw new Exception(emp.getName() + "已经添加了" + evaluate.getEvaluateYear() + "年的评价信息，请不要重复添加！");
		}
		
		this.empEvaluateDao.add(evaluate);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modifyEmpEvaluate(EmpEvaluate evaluate) throws Exception {
		this.empEvaluateDao.update(evaluate);
	}
	
	public EmpEvaluate findEmpEvaluateById(Long id) throws Exception {
		EmpEvaluate evaluate = this.empEvaluateDao.findByPrimaryKey(id);
		
		evaluate.setEmpName(this.employeeDao.findByPrimaryKey(evaluate.getEmpId()).getName());
		
		return evaluate;
	}

	@Transactional(rollbackFor = Exception.class)
	public void removeEmpEvaluate(Long[] ids) throws Exception {
		this.empEvaluateDao.batchDelete(ids);
	}
	
	public String generateEmpEvaluateChartXml(int year) throws Exception {
		String xml = StringUtils.EMPTY;
		
		List<EmpEvaluateVO> vos = this.empEvaluateDao.findEmpEvaluateByYear(year);
		
		if (CollectionUtils.isNotEmpty(vos)) {
			Document document = DocumentHelper.createDocument();
			document.setXMLEncoding(Constants.DEFAULT_CHARSET);
			
			Element graph = document.addElement("graph")
					                .addAttribute("showNames", "1")
					                .addAttribute("decimalPrecision", "0")
					                .addAttribute("caption", year + "年员工评定结果");
			
			for (EmpEvaluateVO vo : vos) {
				Element set = graph.addElement("set");
				
				set.addAttribute("name", vo.getEvaluateLevel())
				   .addAttribute("value", String.valueOf(vo.getNum()));
			}
			
			xml = document.asXML();
		}
		
		return xml;
	}
	
}

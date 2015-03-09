package org.eps.safetyinspection.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eps.common.util.ColorUtils;
import org.eps.common.util.Constants;
import org.eps.common.util.DateUtils;
import org.eps.common.vo.DataGrid;
import org.eps.safetyinspection.dao.AccidentInfoDao;
import org.eps.safetyinspection.po.AccidentInfo;
import org.eps.safetyinspection.vo.AccidentInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccidentInfoService {
	
	@Resource(name = "accidentInfoDao")
	private AccidentInfoDao accidentInfoDao;
	
	public String findAllAccidentInfo() throws Exception {
		List<AccidentInfo> list = this.accidentInfoDao.findAllAccidentInfo();
		
		DataGrid dg = new DataGrid(list.size(), list);
		
		return JSONObject.fromObject(dg).toString();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void addAccidentInfo(AccidentInfo accidentInfo) throws Exception {
		this.accidentInfoDao.add(accidentInfo);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modifyAccidentInfo(AccidentInfo accidentInfo) throws Exception {
		this.accidentInfoDao.update(accidentInfo);
	}
	
	public AccidentInfo findAccidentInfoById(Long id) throws Exception {
		AccidentInfo info = this.accidentInfoDao.findByPrimaryKey(id);
		
		info.setAccidentTimeStr(DateUtils.formatDate(info.getAccidentTime(), "yyyy-MM-dd"));
		
		return info;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeAccidentInfo(Long[] ids) throws Exception {
		this.accidentInfoDao.batchDelete(ids);
	}
	
	public String generateChartXml() {
		Long minYear = this.accidentInfoDao.getMinYear();
		Long maxYear = this.accidentInfoDao.getMaxYear();
		
		if (!minYear.equals(0L) && !maxYear.equals(0L)) {
			Document document = DocumentHelper.createDocument();
			
			Element graph = document.addElement("graph");
			
			graph.addAttribute("caption", minYear + "年至" + maxYear + "年事故统计")
			     .addAttribute("xAxisName", "年度")
			     .addAttribute("yAxisName", "事故数量")
			     .addAttribute("decimalPrecision", "0")
			     .addAttribute("formatNumberScale", "0");
			
			Element set = null;
			
			List<AccidentInfoVO> vos = this.accidentInfoDao.findAccidentInfo2Chart();
			
			for (AccidentInfoVO vo : vos) {
				set = graph.addElement("set");
				
				set.addAttribute("name", vo.getAccidentYear())
				   .addAttribute("value", String.valueOf(vo.getAccidentNum()))
				   .addAttribute("color", ColorUtils.getRandomColor());
			}
			
			document.setXMLEncoding(Constants.DEFAULT_CHARSET);
			
			return document.asXML();
		}
		
		return StringUtils.EMPTY;
	}

}

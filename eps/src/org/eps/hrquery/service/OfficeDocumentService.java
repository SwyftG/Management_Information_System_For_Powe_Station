package org.eps.hrquery.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.eps.common.util.DateUtils;
import org.eps.common.vo.DataGrid;
import org.eps.hrm.dao.OrgDao;
import org.eps.hrm.po.Org;
import org.eps.hrquery.dao.OfficeDocumentDao;
import org.eps.hrquery.po.OfficeDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("officeDocumentService")
public class OfficeDocumentService {
	
	@Resource(name = "officeDocumentDao")
	private OfficeDocumentDao officeDocumentDao;
	
	@Resource(name = "orgDao")
	private OrgDao orgDao;
	
	public String findDocumentByCondition(Map<String, Object> condition) throws Exception {
		List<OfficeDocument> documents = wrapper(this.officeDocumentDao.findDocumentByCondition(condition));
		
		DataGrid dg = new DataGrid(documents.size(), documents);
		
		return JSONObject.fromObject(dg).toString();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void addOfficeDocument(OfficeDocument document) throws Exception  {
		this.officeDocumentDao.add(document);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeOfficeDocument(Long[] ids) throws Exception {
		this.officeDocumentDao.batchDelete(ids);
	}
	
	private List<OfficeDocument> wrapper(List<OfficeDocument> documents) {
		for (OfficeDocument document : documents) {
			Org org = this.orgDao.findByPrimaryKey(document.getOrgId());
			
			document.setOrgName(org.getOrgName());
			document.setPublishDateStr(DateUtils.formatDate(document.getPublishDate()));
		}
		
		return documents;
	}

}

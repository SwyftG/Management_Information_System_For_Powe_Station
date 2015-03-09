package org.eps.common.service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.eps.common.util.Constants;

public class FileUploadService {
	
	public String uploadFireEquipmentImg(InputStream input, String fn) throws Exception {
		String fileName = FilenameUtils.getName(fn);
		
		WebContext ctx = WebContextFactory.get();
		
		ServletContext sctx = ctx.getServletContext();
		
		String filePath = sctx.getRealPath(Constants.FIREEQUIPMENT_UPLOAD_FILE_PATH);
		
		fileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(fileName);
		
		FileUtils.copyInputStreamToFile(input, new File(filePath, fileName));
		
		return Constants.FIREEQUIPMENT_UPLOAD_FILE_PATH + fileName;
	}
	
	public String uploadSafetyToolImg(InputStream input, String fn) throws Exception {
		String fileName = FilenameUtils.getName(fn);
		
		WebContext ctx = WebContextFactory.get();
		
		ServletContext sctx = ctx.getServletContext();
		
		String filePath = sctx.getRealPath(Constants.SAFETYTOOL_UPLOAD_FILE_PATH);
		
		fileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(fileName);
		
		FileUtils.copyInputStreamToFile(input, new File(filePath, fileName));
		
		return Constants.SAFETYTOOL_UPLOAD_FILE_PATH + fileName;
	}
	
	public String uploadDocument(InputStream input, String fn) throws Exception {
		String fileName = FilenameUtils.getName(fn);
		
		WebContext ctx = WebContextFactory.get();
		
		ServletContext sctx = ctx.getServletContext();
		
		String filePath = sctx.getRealPath(Constants.DOCUMENT_UPLOAD_FILE_PATH);
		
		fileName = UUID.randomUUID() + "_" + fileName;
		
		FileUtils.copyInputStreamToFile(input, new File(filePath, fileName));
		
		return Constants.DOCUMENT_UPLOAD_FILE_PATH + fileName;
	}

}

package com.nbicc.ita.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import static com.nbicc.ita.constant.ParameterKeys.PHOTO_PATH;
import static com.nbicc.ita.constant.ParameterKeys.GUIDE_PHOTO_PATH;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nbicc.ita.constant.ResponseCode;
import com.nbicc.ita.model.Brand;
import com.nbicc.ita.service.ProductService;
import com.nbicc.ita.service.UtilService;
import com.nbicc.ita.util.LoginRequired;
import com.nbicc.ita.util.ResponseFactory;

@Component("utilHandle")
@Path("util")
@MultipartConfig
public class UtilHandle extends HttpServlet {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UtilService utilService;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@POST
	@Path("/logout")
	public Response logout(@Context HttpServletRequest request){
		HttpSession session1 = request.getSession();	  
		session1.invalidate();
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
	}
	
	@POST
	@LoginRequired
	@Path("/mutiFileUpload/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response mutiFileUpload(@Context HttpServletRequest request,@PathParam("id") String productId) throws IOException, ServletException{
		String path=productService.updateH5Path(productId,null);
		//path=path+"/imgs";
		utilService.deleteAndCreateFile(path);     //删除并创建新文件
		final Collection<Part> parts = request.getParts();
		boolean state=utilService.commitMutiFile(parts, path);
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, "success");
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, "file error");
		}
	}
	
	@POST
	@LoginRequired
	@Path("/setGuidePhoto/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response setGuidePhoto(@Context HttpServletRequest request,@PathParam("id") String productId) throws Exception{
		Brand brand=productService.getAuditById(productId);
		String path=GUIDE_PHOTO_PATH+brand.getDeviceKey()+"/guide/img/";
		//String path="D:\\Backup\\567\\236\\";
		utilService.deleteAndCreateFile(path);     //删除并创建新文件
		
		final Collection<Part> parts = request.getParts();
		List<String> list=utilService.commitMutiFileString(parts, path);
		File jsFile = new File(path+"url.js");
		jsFile.createNewFile();
		String content="window._urlPath="+list.toString();
		boolean state=utilService.writeTxtFile(content, jsFile);
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, path+"url.js");
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, "file error");
		}
		
	}
	
	@POST
	@LoginRequired
	@Path("/fileUpload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response fileUpload(@Context HttpServletRequest request) throws IOException, ServletException{
		final Part filePart = (Part) request.getPart("photo");
		String path = request.getParameter("path");
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String id = str.replace("-", "");
		if (null == path || "".equals(path)) {
			path = PHOTO_PATH + id + ".jpg";
		}
		utilService.commitFile(filePart, path);
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, path);
	}
	
	@POST
	@Path("/setH5Path")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response pagePath(@Context HttpServletRequest request,@FormDataParam("id") String productId,@FormDataParam("main") String main,
			@FormDataParam("file") InputStream uploadedInputStream,@FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException, ServletException {

		String path = productService.updateH5Path(productId,main);
		if("error".equals(path)){
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, "file path error!");
		}
		File file = new File(path);
		File[] all = file.listFiles();
		if (all!=null) {
			for (File f : all) {
				String fpath = f.getAbsolutePath();
				if (fpath.endsWith("zip") || fpath.endsWith("rar")) {
					f.delete();
				}
			}
		}
		String fileName = fileDetail.getFileName();
	    if(fileName.endsWith("zip")){
	    	fileName="detail.zip";
	    }
	    else{
	    	fileName="detail.rar";
	    }
	    path=path+"/"+fileName;
	    
	    utilService.uploadFile(uploadedInputStream, path);
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, path);
	}
	
	

}

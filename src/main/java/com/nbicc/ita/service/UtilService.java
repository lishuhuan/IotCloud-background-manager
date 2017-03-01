package com.nbicc.ita.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.Part;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("utilService")
public class UtilService {

	public void uploadFile(InputStream uploadedInputStream, String path) throws IOException {
		// 1、创建一个DiskFileItemFactory工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 2、创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 解决上传文件名的中文乱码
		upload.setHeaderEncoding("UTF-8");
		FileOutputStream out = new FileOutputStream(path);
		try {
			byte buffer[] = new byte[1024];
			// 判断输入流中的数据是否已经读完的标识
			int len = 0;
			// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
			while ((len = uploadedInputStream.read(buffer)) > 0) {
				// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" +
				// filename)当中
				out.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			uploadedInputStream.close();
			out.close();
		}
	}

	public void commitFile(Part filePart, String path) throws IOException {
		OutputStream out = null;
		InputStream filecontent = null;

		try {
			out = new FileOutputStream(new File(path));
			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

		} catch (FileNotFoundException fne) {

		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}

		}
	}

	public Boolean commitMutiFile(Collection<Part> parts, String path) throws IOException {
		String photopath = path + "/imgs";
		int i = 0;
		List<String> filelist = new ArrayList<>();
		for (Part part : parts) {
			i += 1;
			String npath = photopath + "/" + String.valueOf(i) + ".jpg";
			commitFile(part, npath);
			filelist.add(String.valueOf(i) + ".jpg");
		}

		String text = "_urlPath = " + String.valueOf(filelist);
		File file = new File(path + "/path.js");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWritter = new FileWriter(file);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write(text);
		bufferWritter.close();
		return true;
	}


	public List<String> commitMutiFileString(Collection<Part> parts, String path) throws IOException {
		List<String> list = new ArrayList<>();
		for (Part part : parts) {
			String name = getFileName(part);
			String npath = path + "/" + name + ".jpg";
			commitFile(part, npath);
			list.add(name);
		}
		return list;
	}

	public boolean writeTxtFile(String content, File fileName) throws Exception {
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("GBK"));
			o.close();
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	private String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	public void deleteAndCreateFile(String path) {
		path = path + "/imgs";
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isFile()) {
						files[i].delete();
					}
				}
			}
		}
		file.mkdirs();
	}

}

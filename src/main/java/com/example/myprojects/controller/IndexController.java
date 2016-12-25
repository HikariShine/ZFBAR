package com.example.myprojects.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.myprojects.vr.ImageCrop;
import com.example.myprojects.vr.ImageProcess;

/**
 * Hello world!
 *
 */
@Controller
public class IndexController {

	@RequestMapping("/")
    String home() {
    	return "index";
    }
	
    @RequestMapping("/v2")
    String homeV2() {
    	return "indexV2";
    }
    
    @RequestMapping("/v1")
    String homeV1() {
    	return "indexV1";
    }
    
    @RequestMapping("/cropAndProcess")
	ResponseEntity<byte[]>  doCropAndProcess(HttpServletRequest request, HttpServletResponse response, MultipartFile pictureFile) throws Exception {
    	BufferedImage src = ImageIO.read(pictureFile.getInputStream());
    	src = ImageCrop.crop(src);
    	BufferedImage newSrc = ImageProcess.process(src);
    	String fileName = new Date().getTime() + ".png";
    	File file = new File(fileName);
    	ImageIO.write(newSrc, "PNG", file);// 输出到文件流
    	HttpHeaders headers = new HttpHeaders();  
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
        headers.setContentDispositionFormData("attachment", fileName);  
    	return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),  
                headers, HttpStatus.CREATED);
    }
    
	@RequestMapping("/processCropped")
	ResponseEntity<byte[]> doProcessCropped(HttpServletRequest request, HttpServletResponse response, MultipartFile pictureFile) throws Exception {
		BufferedImage src = ImageIO.read(pictureFile.getInputStream());
    	BufferedImage newSrc = ImageProcess.process(src);
    	String fileName = new Date().getTime() + ".png";
    	File file = new File(fileName);
    	ImageIO.write(newSrc, "PNG", file);// 输出到文件流
    	HttpHeaders headers = new HttpHeaders();  
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
        headers.setContentDispositionFormData("attachment", fileName);  
    	return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),  
                headers, HttpStatus.CREATED);
    }
	
    @RequestMapping("/process")
	@ResponseBody
	String doCreate(HttpServletRequest request, HttpServletResponse response, MultipartFile pictureFile) throws Exception {
    	BufferedImage src = ImageIO.read(pictureFile.getInputStream());
    	BufferedImage newSrc = ImageProcess.process(src);
    	String fileName = new Date().getTime() + ".png";
    	File file = new File(fileName);
    	ImageIO.write(newSrc, "PNG", file);// 输出到文件流
    	return fileName;
    }
	
	@RequestMapping("/download")
	ResponseEntity<byte[]> doDownload(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception {
		HttpHeaders headers = new HttpHeaders();  
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
        headers.setContentDispositionFormData("attachment", fileName);  
        File file = new File(fileName);
    	return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),  
                headers, HttpStatus.CREATED);
	}
    
}
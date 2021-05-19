package com.comviva.DocumentManagementSystem.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.comviva.DocumentManagementSystem.bean.Document;
import com.comviva.DocumentManagementSystem.bean.User;
import com.comviva.DocumentManagementSystem.controller.DocumentController;
import com.comviva.DocumentManagementSystem.dao.DocumentRepository;

@Service
@Transactional
public class DocumentUploadDownloadService implements DocumentServices {
	@Autowired
	private final DocumentRepository docRepository;
	private static String UPLOAD_FOLDER = "C://Documents//";

	public String uploadDir;

	public DocumentUploadDownloadService(DocumentRepository docRepository) {
		this.docRepository = docRepository;
	}

	public Document getFile(Integer userId,Integer docId) { // Download from DB
		return docRepository.findByUserIdAndDocId(userId,docId);
		//return docRepository.findById(id).get();
	}
	
	public Optional<Document> findFile(Integer docId) { 
		return docRepository.findById(docId);
	}

	public Stream<Document> getAllFiles() {
		return docRepository.findAll().stream();

	}

	public Stream<Document> listDocs(Integer user_id) {
		return docRepository.findAllByUserId(user_id).stream();
	}


	public void uploadDoc(User user, MultipartFile file) throws Exception {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Document myFile = new Document(fileName, file.getContentType(), file.getBytes());
		myFile.setData(file.getBytes());
		myFile.setUser(user);
		myFile.setDocType(file.getContentType());
		myFile.setDocName(fileName);
		myFile.setUserId(user.getUser_id());
		myFile.setDocCategory(DocumentController.category);
		
		docRepository.save(myFile);
		myFile.setUser(user);
		String usrname = user.getName();
        System.out.println("name:"+usrname);

		String path = UPLOAD_FOLDER + usrname +"//";

		System.out.println(path);
		File uploadRootDir = new File(path);
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		
		String pathnew = path+ DocumentController.category;
		
		File uploadRootDirnew = new File(pathnew);
		if (!uploadRootDirnew.exists()) {
			uploadRootDirnew.mkdirs();
		}
		System.out.println(pathnew);
		
		// Client File Name
		String name = file.getOriginalFilename();
		System.out.println("Client File Name = " + name);
		List<File> uploadedFiles = new ArrayList<File>();
		List<String> failedFiles = new ArrayList<String>();
		if (name != null && name.length() > 0) {
			try {
				// Create the file at server
				File serverFile = new File(uploadRootDirnew.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(file.getBytes());
				stream.close();
				uploadedFiles.add(serverFile);
				System.out.println("Write file: " + serverFile);
			} catch (Exception e) {
				System.out.println("Error Write file: " + name);
				failedFiles.add(name);
			}
		}
		
	}

}

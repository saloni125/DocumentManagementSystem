package com.comviva.DocumentManagementSystem.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.comviva.DocumentManagementSystem.bean.Document;
import com.comviva.DocumentManagementSystem.bean.User;
import com.comviva.DocumentManagementSystem.response.ResponseFile;
import com.comviva.DocumentManagementSystem.service.DocumentUploadDownloadService;
import com.comviva.DocumentManagementSystem.service.UserService;

@RestController
public class DocumentController {
	@Autowired
	private DocumentUploadDownloadService docStorageService;
	@Autowired
	private UserService us;
	public static String category;
	@GetMapping("/files") // All files in database
	public ResponseEntity<List<ResponseFile>> getListFiles() {
		List<ResponseFile> files = docStorageService.getAllFiles().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(Integer.toString(dbFile.getDocId())).toUriString();

			return new ResponseFile(dbFile.getDocName(), fileDownloadUri, dbFile.getDocType(), dbFile.getData().length);
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(files);
	}
 
	@GetMapping("/download/{docId}") // Download any file by document Id
	public ResponseEntity<byte[]> downloadFile(@PathVariable("docId") Integer docId) {
		
		Document fileDB=docStorageService.getFile(UserController.loginId, docId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("dash");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getDocName() + "\"")
				.body(fileDB.getData());
	}
	
	@GetMapping({"uploadFile/list" , "/list"}) // List files of a user
	ModelAndView docList(Model model) {
		List<ResponseFile> files = (docStorageService.listDocs(UserController.loginId)).map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(Integer.toString(dbFile.getDocId())).toUriString();

			return new ResponseFile(dbFile.getDocName(), fileDownloadUri, dbFile.getDocType(), dbFile.getData().length);
		}).collect(Collectors.toList());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("list");
	
		Stream<Document> documents=docStorageService.listDocs(UserController.loginId); 
	    model.addAttribute("documents", documents.iterator());
		//return ResponseEntity.status(HttpStatus.OK).body(files);
		return modelAndView;
		

	}

	@PostMapping("/uploadFile") //Upload file for a user
	@ResponseBody
	public ModelAndView upFiles(@RequestParam("docCategory") String docCategory,@ModelAttribute("user") User user,@RequestParam("file") MultipartFile file, Model model)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		System.out.println(UserController.loginId);
		Optional<User> userExist = us.findById(UserController.loginId);
		category=docCategory;
		System.out.println(docCategory);
		docStorageService.uploadDoc(userExist.get(), file);
		model.addAttribute("Document", file);
		modelAndView.addObject("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
		 
		modelAndView.setViewName("dash");
		return modelAndView;
	}
    
   
	

}

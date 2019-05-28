package com.policy.bazaar.imageupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.policy.bazaar.employee.model.Employees;
import com.policy.bazaar.globalresponse.GlobalResponse;
import com.policy.bazaar.repository.EmployeeRepository;

@RestController
public class UserImageUpload {
	
	@Autowired
	EmployeeRepository employeeRepository;

	private static String UPLOADED_FOLDER = "/home/archit/Archit/Back End Projects/PolicyApp/src/main/webapp/images/images";

	@PostMapping(value = "/upload/{userid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public GlobalResponse uploadImage(@RequestParam("image") MultipartFile file , @PathVariable Integer userid) throws IOException {

		GlobalResponse globalResponse = new GlobalResponse();

		File convertFile = new File(UPLOADED_FOLDER + file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		
		Employees employee = employeeRepository.findById(userid).get();
		
		employee.setImageurl("images" +file.getOriginalFilename());
		
		employeeRepository.save(employee);
		globalResponse.setData(null);
		globalResponse.setMessage("Image Uploaded Successfully");
		globalResponse.setStatus(true);

		return globalResponse;

	}
}

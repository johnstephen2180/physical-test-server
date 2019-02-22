package com.loankim.examonline.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loankim.examonline.exception.CreateExamException;
import com.loankim.examonline.manager.ExaminationManager;
import com.loankim.examonline.om.Examination;
import com.loankim.examonline.security.model.AuthenticatedUser;

@RestController
@RequestMapping("/admin")
public class CreateExamController {
	@Autowired
	private ExaminationManager examManager;


	@PostMapping(path = "/create/exam")
	public @ResponseBody Examination createExam(@RequestParam("title") String title,
			@RequestParam("questionFile") MultipartFile file, @RequestParam("suggestFile") MultipartFile suggestFile,
			@RequestParam("resultFile") MultipartFile answerFile, Authentication authentication) throws IOException {

		AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
		Examination exam = examManager.createExamination(user.getUserId(), title, file.getInputStream(),
				suggestFile.getInputStream(), answerFile.getInputStream());

		return exam;
	}


	@PostMapping(path = "/commit/exam")
	public @ResponseBody Examination commitExam(Authentication authentication) throws CreateExamException, IOException {
		AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
		return examManager.commitExamination(user.getUserId());
	}

}

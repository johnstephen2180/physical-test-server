package com.loankim.examonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.loankim.examonline.manager.ExaminationManager;
import com.loankim.examonline.om.Examination;
import com.loankim.examonline.om.LessonHistory;
import com.loankim.examonline.om.Question;
import com.loankim.examonline.security.model.AuthenticatedUser;

@RestController
@RequestMapping("/exam")
public class ExaminationController {
	@Autowired
	private ExaminationManager examManager;


	@PostMapping(path = "/get")
	public @ResponseBody Examination getExam(@RequestParam("id") long examId) {
		return examManager.getExam(examId);
	}


	@PostMapping(path = "/all")
	public @ResponseBody Page<Examination> getAllExam(@RequestParam("page") int page) {
		return examManager.getExam(page);
	}


	@PostMapping(path = "/check")
	public @ResponseBody boolean checkResult(@RequestParam("id") long questionId,
			@RequestParam("result") float result) {
		Question question = examManager.getQuestion(questionId);
		return question.isRight(result);
	}


	@PostMapping(path = "/track")
	public @ResponseBody boolean checkResult(@RequestParam("examId") long examId,
			@RequestParam("questionOrder") int questionOrder, @RequestParam("suggestOrder") int suggestOrder,
			Authentication authentication) {
		System.out.println("track: question:" + questionOrder + "/suggestOrder:" + suggestOrder);
		AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
		return examManager.trackQuestion(user.getUserId(), examId, questionOrder, suggestOrder);
	}


	@PostMapping(path = "/history")
	public @ResponseBody Page<LessonHistory> getLessonHistoryByUser(Authentication authentication) {
		AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
		return examManager.getExamByAccountId(user.getUserId(), 0);
	}


	@PostMapping(path = "/history/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody Page<LessonHistory> getLessonHistoryByAdmin() {
		return examManager.getLessonHistory(0);
	}

}

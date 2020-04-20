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

/**
 * @author LamHM
 *
 */
@RestController
@RequestMapping("/exam")
public class ExaminationController {
	@Autowired
	private ExaminationManager examManager;


	@PostMapping(path = "/get")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public @ResponseBody Examination getExam(@RequestParam("id") long examId) {
		Examination exam = examManager.getExam(examId);
		exam.getQuestionList().sort((q1, q2) -> q1.getOrder() - q2.getOrder());
		return exam;
	}


	@PostMapping(path = "/all")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public @ResponseBody Page<Examination> getAllExam(@RequestParam("page") int page) {
		return examManager.getExam(page);
	}


	@PostMapping(path = "/check")
	public @ResponseBody boolean checkResult(@RequestParam("id") long questionId, @RequestParam("result") float result,
			Authentication authentication) {
		Question question = examManager.getQuestion(questionId);
		boolean right = question.isRight(result);
		if (right) {
			trackQuestion(question.getExamId(), question.getOrder(), question.getSuggestList().size(), authentication,
					right);
		}
		return right;
	}


	@PostMapping(path = "/track")
	public @ResponseBody boolean trackQuestion(@RequestParam("examId") long examId,
			@RequestParam("questionOrder") int questionOrder, @RequestParam("suggestOrder") int suggestOrder,
			Authentication authentication, boolean isRight) {
		AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
		return examManager.trackQuestion(user.getUserId(), examId, questionOrder, suggestOrder, isRight);
	}


	@PostMapping(path = "/history")
	public @ResponseBody Page<LessonHistory> getLessonHistoryByUser(@RequestParam("page") int page,
			Authentication authentication) {
		AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
		return examManager.getExamByAccountId(user.getUserId(), page);
	}


	@PostMapping(path = "/history/detail")
	public @ResponseBody LessonHistory getLessonHistoryDetailByUser(@RequestParam("historyId") String historyId) {
		return examManager.getLessonHistory(historyId);
	}


	@PostMapping(path = "/history/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody Page<LessonHistory> getLessonHistoryByAdmin(@RequestParam("page") int page) {
		return examManager.getLessonHistory(page);
	}

}

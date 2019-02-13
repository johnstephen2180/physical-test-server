package com.loankim.examonline.om;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lesson-history")
public class LessonHistory {
	@Id
	private String id;
	@Indexed
	private long accountId;
	private long examId;
	private boolean isFinished;
	private long startTime;
	private List<QuestionState> questions;

	@Transient
	private Examination examination;
	@Transient
	private Account account;


	public LessonHistory() {
	}


	public LessonHistory(long accountId, long examId) {
		this.accountId = accountId;
		this.examId = examId;
		this.id = genId(accountId, examId);
		questions = new ArrayList<>();
		startTime = System.currentTimeMillis();
	}


	public static String genId(long accountId, long examId) {
		return accountId + "_" + examId;
	}


	public void updateQuestionTrack(int questionOrder, int suggestOrder) {
		if (!examination.checkExistQuestionOrSuggest(questionOrder, suggestOrder)) {
			return;
		}

		Optional<QuestionState> questionOpt = questions.stream()
				.filter(question -> question.getQuestionOrder() == questionOrder).findFirst();
		QuestionState questionState = null;
		if (questionOpt.isPresent()) {
			questionState = questionOpt.get();
			questionState.setSuggestAt(suggestOrder);
		} else {
			questionState = new QuestionState(questionOrder, suggestOrder);
			questions.add(questionState);
		}
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public long getAccountId() {
		return accountId;
	}


	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}


	public long getExamId() {
		return examId;
	}


	public void setExamId(long examId) {
		this.examId = examId;
	}


	public boolean isFinished() {
		return isFinished;
	}


	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}


	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	public List<QuestionState> getQuestions() {
		return questions;
	}


	public void setQuestions(List<QuestionState> questions) {
		this.questions = questions;
	}


	public Examination getExamination() {
		return examination;
	}


	public void setExamination(Examination examination) {
		this.examination = examination;
	}


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}

}

package com.loankim.examonline.om;

import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exams")
public class Examination {
	@Id
	private long id;
	private String title;
	@Indexed
	private String urlTitle;
	private long createTime;
	private long accountId;
	private Integer timeTestMinute;
	private int questionNo;

	private boolean isCountdown;

	@Transient
	private List<Question> questionList;


	public Examination() {
	}


	public Examination(long id, String title, long accountId) {
		this.id = id;
		this.title = title;
		this.urlTitle = title;
		this.accountId = accountId;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getId() {
		return id;
	}


	public void genContent(String folderName) {
		questionList.stream().forEach(question -> question.genContent(folderName));
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public long getCreateTime() {
		return createTime;
	}


	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}


	public long getAccountId() {
		return accountId;
	}


	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}


	public boolean isCountdown() {
		return isCountdown;
	}


	public void setCountdown(boolean isCountdown) {
		this.isCountdown = isCountdown;
	}


	public Integer getTimeTestMinute() {
		return timeTestMinute;
	}


	public void setTimeTestMinute(Integer timeTestMinute) {
		this.timeTestMinute = timeTestMinute;
	}


	public String getUrlTitle() {
		return urlTitle;
	}


	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
	}


	public List<Question> getQuestionList() {
		return questionList;
	}


	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
		this.setQuestionNo(questionList.size());
	}


	public Question getQuestion(int questionOrder) {
		return questionList.stream().filter(question -> question.getOrder() == questionOrder).findFirst().get();
	}


	public boolean checkExistQuestionOrSuggest(int questionOrder, int suggestOrder) {
		Optional<Question> questionOpt = questionList.stream().filter(question -> question.getOrder() == questionOrder)
				.findFirst();
		if (!questionOpt.isPresent())
			return false;

		return questionOpt.get().getSuggestList().size() >= suggestOrder;
	}


	public int getQuestionNo() {
		return questionNo;
	}


	public void setQuestionNo(int questionNo) {
		this.questionNo = questionNo;
	}

}

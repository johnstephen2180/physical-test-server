package com.loankim.examonline.om;

import java.util.List;

import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loankim.examonline.util.ExportPPTxToImage;

@Document(collection = "question")
public class Question {
	@Id
	private long id;
	@Indexed
	private long examId;
	private int order;
	private String content;
	private List<Suggest> suggestList;
	private Answer answer;

	@Transient
	private XSLFSlide slide;


	public Question() {
	}


	public void genContent(String folderName) {
		String fileName = genFileName();
		content = ExportPPTxToImage.convertSlideToImage(slide, folderName, fileName);
		suggestList.stream().forEach(suggest -> suggest.genContent(folderName, fileName));
		answer.genContent(folderName, fileName);
	}


	private String genFileName() {
		return "q_" + id + "_" + order;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isRight(float result) {
		return answer.getResult() == result;
	}

	@JsonIgnore
	public XSLFSlide getSlide() {
		return slide;
	}


	public void setSlide(XSLFSlide slide) {
		this.slide = slide;
	}


	public long getExamId() {
		return examId;
	}


	public void setExamId(long examId) {
		this.examId = examId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public List<Suggest> getSuggestList() {
		return suggestList;
	}


	public void setSuggestList(List<Suggest> suggestList) {
		this.suggestList = suggestList;
	}


	public Answer getAnswer() {
		return answer;
	}


	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

}

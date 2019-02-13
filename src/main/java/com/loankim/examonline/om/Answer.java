package com.loankim.examonline.om;

import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loankim.examonline.util.ExportPPTxToImage;

public class Answer {
	private String content;
	private float result;
	@Transient
	private int order;
	@Transient
	private XSLFSlide slide;


	public Answer() {
	}


	public Answer(int order, XSLFSlide slide) {
		this.order = order;
		this.slide = slide;
	}


	public void genContent(String folderName, String questionFileName) {
		content = ExportPPTxToImage.convertSlideToImage(slide, folderName, "a_" + questionFileName);
	}


	public float getResult() {
		return result;
	}


	public void setResult(float result) {
		this.result = result;
	}


	@JsonIgnore
	public XSLFSlide getSlide() {
		return slide;
	}


	public void setSlide(XSLFSlide slide) {
		this.slide = slide;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}

}

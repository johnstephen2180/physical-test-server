package com.loankim.examonline.om;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.loankim.examonline.util.ExportPPTxToImage;

@JsonInclude(Include.NON_NULL)
public class Answer {
	private String content;
	private Float result;
	@Transient
	private int order;
	@Transient
	private XSLFSlide slide;


	public Answer() {
	}


	public Answer(XSLFSlide slide) {
		this.slide = slide;
		String title = slide.getTitle().trim();
		String[] items = StringUtils.split(title, "#");
		this.order = Integer.parseInt(items[0]);
		if (items.length >= 2) {
			result = Float.parseFloat(items[1]);
		}
	}


	public void genContent(String folderName, String questionFileName) {
		content = ExportPPTxToImage.convertSlideToImage(slide, folderName, "a_" + questionFileName);
	}


	public Float getResult() {
		return result;
	}


	public void setResult(Float result) {
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

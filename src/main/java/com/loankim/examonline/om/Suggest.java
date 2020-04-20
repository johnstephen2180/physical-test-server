package com.loankim.examonline.om;

import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loankim.examonline.util.ExportPPTxToImage;

/**
 * @author LamHM
 *
 */
public class Suggest {
	private int order;
	private int questionOrder;
	private String content;
	private int showInMinutes;

	@Transient
	private XSLFSlide slide;


	public void genContent(String folderName, String questionFileName) {
		content = ExportPPTxToImage.convertSlideToImage(slide, folderName, "s_" + questionFileName + "_" + order);
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getShowInMinutes() {
		return showInMinutes;
	}


	public void setShowInMinutes(int showInMinutes) {
		this.showInMinutes = showInMinutes;
	}


	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}


	@JsonIgnore
	public XSLFSlide getSlide() {
		return slide;
	}


	public void setSlide(XSLFSlide slide) {
		this.slide = slide;
	}


	public int getQuestionOrder() {
		return questionOrder;
	}


	public void setQuestionOrder(int questionOrder) {
		this.questionOrder = questionOrder;
	}

}

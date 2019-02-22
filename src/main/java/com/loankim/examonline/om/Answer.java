package com.loankim.examonline.om;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.loankim.examonline.util.ExportPPTxToImage;

@JsonInclude(Include.NON_NULL)
public class Answer {
	private List<String> contentList;
	private Float result;
	@Transient
	private int order;

	@Transient
	private List<XSLFSlide> slideList;


	public Answer() {
	}


	public Answer(XSLFSlide slide, int order, Float result) {
		slideList = new ArrayList<>();
		contentList = new ArrayList<>();
		addSlide(slide);

		this.order = order;
		this.result = result;
	}


	public static int getOrderFromSlide(XSLFSlide slide) {
		String title = slide.getTitle().trim();
		String[] items = StringUtils.split(title, "#");
		return Integer.parseInt(items[0]);
	}


	public void genContent(String folderName, String questionFileName) {
		for (int i = 0; i < slideList.size(); i++) {
			contentList.add(ExportPPTxToImage.convertSlideToImage(slideList.get(i), folderName,
					"a_" + questionFileName + "i_" + i));
		}
	}


	public void addSlide(XSLFSlide slide) {
		slideList.add(slide);
	}


	public List<String> getContentList() {
		return contentList;
	}


	public void setContentList(List<String> contentList) {
		this.contentList = contentList;
	}


	public Float getResult() {
		return result;
	}


	public void setResult(Float result) {
		this.result = result;
	}


	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}

}

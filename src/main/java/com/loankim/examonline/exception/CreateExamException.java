package com.loankim.examonline.exception;

/**
 * @author LamHM
 *
 */
public class CreateExamException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String slideStyle;
	private int slideOrder;
	private int code;


	public static CreateExamException answerSlideExpception(int slideOrder) {
		CreateExamException createExamException = new CreateExamException();
		createExamException.setSlideStyle("as");
		createExamException.setSlideOrder(slideOrder);
		return createExamException;
	}


	public String getSlideStyle() {
		return slideStyle;
	}


	public void setSlideStyle(String slideStyle) {
		this.slideStyle = slideStyle;
	}


	public int getSlideOrder() {
		return slideOrder;
	}


	public void setSlideOrder(int slideOrder) {
		this.slideOrder = slideOrder;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}

}

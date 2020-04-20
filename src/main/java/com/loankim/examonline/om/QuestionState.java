package com.loankim.examonline.om;

/**
 * @author LamHM
 *
 */
public class QuestionState {
	private int questionOrder;
	private boolean isFinish;
	private int suggestAt;


	public QuestionState() {
	}


	public QuestionState(int questionOrder, int suggestAt) {
		this.setQuestionOrder(questionOrder);
		this.suggestAt = suggestAt;
	}


	public boolean isFinish() {
		return isFinish;
	}


	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}


	public int getSuggestAt() {
		return suggestAt;
	}


	public void setSuggestAt(int suggestAt) {
		this.suggestAt = suggestAt;
	}


	public int getQuestionOrder() {
		return questionOrder;
	}


	public void setQuestionOrder(int questionOrder) {
		this.questionOrder = questionOrder;
	}

}

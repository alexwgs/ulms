package com.cmbccd.ulms.edu.domain;

import java.util.List;

public class DisputeQuestion {

	private String quesCode;
	private short disputeResult;
	private String dispNum;
	private short quesType;
	private String quesStem;
	private String options;
	private String answer;

	private List<QuesDispute> quesDisputes;

	public String getQuesCode() {
		return quesCode;
	}

	public void setQuesCode(String quesCode) {
		this.quesCode = quesCode;
	}

	public short getDisputeResult() {
		return disputeResult;
	}

	public void setDisputeResult(short disputeResult) {
		this.disputeResult = disputeResult;
	}

	public String getDispNum() {
		return dispNum;
	}

	public void setDispNum(String dispNum) {
		this.dispNum = dispNum;
	}

	public short getQuesType() {
		return quesType;
	}

	public void setQuesType(short quesType) {
		this.quesType = quesType;
	}

	public String getQuesStem() {
		return quesStem;
	}

	public void setQuesStem(String quesStem) {
		this.quesStem = quesStem;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<QuesDispute> getQuesDisputes() {
		return quesDisputes;
	}

	public void setQuesDisputes(List<QuesDispute> quesDisputes) {
		this.quesDisputes = quesDisputes;
	}

	
}

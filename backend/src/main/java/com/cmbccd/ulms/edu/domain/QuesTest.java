package com.cmbccd.ulms.edu.domain;

import java.math.BigDecimal;

public class QuesTest {
    private String journo;

    private String examCode;

    private String ploNum;

    private String quesCode;

    private Integer testStat;

    private String userAnswer;

    private Integer ifCorrect;

    private BigDecimal score;

    private String handleDate;

    private Integer quesOrder;
    
    private QuesBank question;
    
    private QuesDispute quesDispute;

    public String getJourno() {
        return journo;
    }

    public void setJourno(String journo) {
        this.journo = journo == null ? null : journo.trim();
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode == null ? null : examCode.trim();
    }

    public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public String getQuesCode() {
        return quesCode;
    }

    public void setQuesCode(String quesCode) {
        this.quesCode = quesCode == null ? null : quesCode.trim();
    }

    public Integer getTestStat() {
        return testStat;
    }

    public void setTestStat(Integer testStat) {
        this.testStat = testStat;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer == null ? null : userAnswer.trim();
    }

    public Integer getIfCorrect() {
        return ifCorrect;
    }

    public void setIfCorrect(Integer ifCorrect) {
        this.ifCorrect = ifCorrect;
    }

    public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate == null ? null : handleDate.trim();
    }

    public Integer getQuesOrder() {
        return quesOrder;
    }

    public void setQuesOrder(Integer quesOrder) {
        this.quesOrder = quesOrder;
    }

	public QuesBank getQuestion() {
		return question;
	}

	public void setQuestion(QuesBank question) {
		this.question = question;
	}
	
	

	public QuesDispute getQuesDispute() {
		return quesDispute;
	}

	public void setQuesDispute(QuesDispute quesDispute) {
		this.quesDispute = quesDispute;
	}

	@Override
	public String toString() {
		return "QuesTest [journo=" + journo + ", examCode=" + examCode + ", ploNum=" + ploNum + ", quesCode=" + quesCode
				+ ", testStat=" + testStat + ", userAnswer=" + userAnswer + ", ifCorrect=" + ifCorrect + ", score="
				+ score + ", handleDate=" + handleDate + ", quesOrder=" + quesOrder + ", question=" + question + "]";
	}
    
    
}
package com.cmbccd.ulms.edu.domain;

import com.alibaba.excel.annotation.ExcelProperty;

public class QuesBank {
    @ExcelProperty("题目编号")
    private String quesCode;

    @ExcelProperty("题库")
    private String libCode;

    @ExcelProperty("题干")
    private String quesStem;

    @ExcelProperty("题目类型")
    private Integer quesType;

    @ExcelProperty("附件编号")
    private String fileCode;

    @ExcelProperty("附件类型")
    private String fileType;
    @ExcelProperty("附件时长")
    private Integer fileDuration;
    @ExcelProperty("选项")
    private String options;
    @ExcelProperty("答案")
    private String answer;
    @ExcelProperty("解析")
    private String quesExplain;
    @ExcelProperty("题目状态")
    private Integer quesStat;
    @ExcelProperty("操作经办")
    private String handlePlo;
    @ExcelProperty("操作时间")
    private String handleDate;
    @ExcelProperty("分类")
    private String category;
    @ExcelProperty("知识库文章")
    private String knowledge;

    public String getQuesCode() {
        return quesCode;
    }

    public void setQuesCode(String quesCode) {
        this.quesCode = quesCode == null ? null : quesCode.trim();
    }

    public String getLibCode() {
        return libCode;
    }

    public void setLibCode(String libCode) {
        this.libCode = libCode == null ? null : libCode.trim();
    }

    public String getQuesStem() {
        return quesStem;
    }

    public void setQuesStem(String quesStem) {
        this.quesStem = quesStem == null ? null : quesStem.trim();
    }

    public Integer getQuesType() {
        return quesType;
    }

    public void setQuesType(Integer quesType) {
        this.quesType = quesType;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode == null ? null : fileCode.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
        /**
         * 设置题库编号
         * @param libCode 题库编号
         */
    }

    public Integer getFileDuration() {
        return fileDuration;
        /**
         * 获取题干内容
         * @return 题干内容
         */
    }

    public void setFileDuration(Integer fileDuration) {
        this.fileDuration = fileDuration;
        /**
         * 设置题干内容
         * @param quesStem 题干内容
         */
    }

    public String getOptions() {
        return options;
        /**
         * 获取题目类型
         * @return 题目类型
         */
    }

    public void setOptions(String options) {
        this.options = options == null ? null : options.trim();
        /**
         * 设置题目类型
         * @param quesType 题目类型
         */
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public String getQuesExplain() {
        return quesExplain;
    }

    public void setQuesExplain(String quesExplain) {
        this.quesExplain = quesExplain == null ? null : quesExplain.trim();
    }

    public Integer getQuesStat() {
        return quesStat;
    }

    public void setQuesStat(Integer quesStat) {
        this.quesStat = quesStat;
    }

    public String getHandlePlo() {
        return handlePlo;
    }

    public void setHandlePlo(String handlePlo) {
        this.handlePlo = handlePlo == null ? null : handlePlo.trim();
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate == null ? null : handleDate.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge == null ? null : knowledge.trim();
    }
}
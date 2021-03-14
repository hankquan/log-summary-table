package com.github.howaric.log.summary.core;

public class ReportSection extends ReportLine {

    private String sectionName;

    public ReportSection(String sectionName) {
        super();
        this.sectionName = sectionName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ReportSectionName [sectionName=").append(sectionName).append("]");
        return builder.toString();
    }

    @Override
    protected String getNo() {
        return "";
    }

    @Override
    protected String getAction() {
        return "[" + sectionName + "]";
    }

    @Override
    protected String getResult() {
        return "";
    }

    @Override
    protected String getRemarks() {
        return "";
    }

    @Override
    protected String getTime() {
        return "";
    }

    @Override
    protected Long getCreateTime() {
        return null;
    }

}

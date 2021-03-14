package com.github.howaric.log.summary.core;

import com.github.howaric.log.summary.util.LogUtil;

public class ReportStep extends ReportLine {

    private String index;
    private String description;
    private String result = "V";
    private StringBuilder remarks;
    private long consumingTime;
    private boolean isUpdatedTime = false;
    private long createTime;

    public ReportStep(String index, String description) {
        super();
        this.index = index;
        this.description = description;
        this.consumingTime = System.currentTimeMillis();
        this.createTime = this.consumingTime;
    }

    public void updateConsumingTime() {
        this.consumingTime = System.currentTimeMillis() - this.consumingTime;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isUpdatedTime() {
        return isUpdatedTime;
    }

    public void setUpdatedTime(boolean isUpdatedTime) {
        this.isUpdatedTime = isUpdatedTime;
    }

    public void setFailMessage(String failMessage) {
        this.remarks = new StringBuilder(failMessage);
    }

    public void appendRemark(String remark) {
        if (this.remarks == null) {
            this.remarks = new StringBuilder();
        }
        this.remarks.append(remark);
    }

    public boolean isFailed() {
        return result.equals("V") ? false : true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ReportStep [description=").append(description).append("]");
        return builder.toString();
    }

    @Override
    protected String getNo() {
        return index;
    }

    @Override
    protected String getAction() {
        return description;
    }

    @Override
    protected String getResult() {
        return result;
    }

    @Override
    protected String getTime() {
        if (consumingTime > 1500000000000L) {
            updateConsumingTime();
        }
        return LogUtil.secondToTime(((Long) (Long.valueOf(consumingTime) / 1000)).intValue());
    }

    @Override
    protected String getRemarks() {
        return remarks == null ? "" : remarks.toString();
    }

    @Override
    public Long getCreateTime() {
        return createTime;
    }

}

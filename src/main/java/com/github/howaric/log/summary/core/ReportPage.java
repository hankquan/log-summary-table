package com.github.howaric.log.summary.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ReportPage implements ReportInfo {

    private String pageName;
    private List<ReportInfo> lines = new ArrayList<>();
    private Map<String, String> headers = new LinkedHashMap<>();

    private long startTime;
    private boolean summarized = false;

    public long getStartTime() {
        return startTime;
    }

    private final AtomicInteger stepIndex = new AtomicInteger(0);

    public ReportPage(String pageName) {
        super();
        this.pageName = pageName;
        this.startTime = System.currentTimeMillis();
    }

    public ReportSection newSection(String sectionName) {
        ReportSection reportSection = new ReportSection(sectionName);
        insert(reportSection);
        return reportSection;
    }

    public ReportStep newStep(String stepDescription) {
        ReportStep reportStep = new ReportStep(pageName + ".s" + stepIndex.incrementAndGet(), stepDescription);
        insert(reportStep);
        return reportStep;
    }

    public void insert(ReportInfo reportInfo) {
        updateLastestReportStepConsumingTime();
        lines.add(reportInfo);
    }

    public String getPageName() {
        return pageName;
    }

    public void updateLastestReportStepConsumingTime() {
        ReportStep latestReportStep = getLatestReportStep();
        if (latestReportStep != null && !latestReportStep.isUpdatedTime()) {
            latestReportStep.updateConsumingTime();
            latestReportStep.setUpdatedTime(true);
        }
    }

    public ReportStep getLatestReportStep() {
        ReportStep result = null;
        for (ReportInfo reportInfo : lines) {
            if (reportInfo instanceof ReportStep) {
                result = (ReportStep) reportInfo;
            }
        }
        return result;
    }

    public boolean isSummarized() {
        return summarized;
    }

    public void setSummarized(boolean isSummaried) {
        this.summarized = isSummaried;
    }

    public List<ReportInfo> getReportInfos() {
        return lines;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

}

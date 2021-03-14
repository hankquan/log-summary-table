package com.github.howaric.log.summary.core;

public abstract class ReportRunnable implements Runnable {

    protected ReportPage reportPage;

    public ReportRunnable() {
        this.reportPage = LogReport.createPage();
    }

    @Override
    public void run() {
        LogReport.setCurrentPage(reportPage);
        runReport();
        reportPage.getLatestReportStep().updateConsumingTime();
    }

    protected abstract void runReport();
}

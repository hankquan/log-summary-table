package com.github.howaric.log.summary.core;

import com.github.howaric.log.summary.util.LogUtil;
import com.github.howaric.log.summary.util.StringUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LogReport {

    private static final String REPORT_HEADER_SUMMARY_NAME = "Summary";
    private static final String REPORT_HEADER_START_TIME = "Start time";
    private static final String REPORT_HEADER_END_TIME = "End time";
    private static final String REPORT_HEADER_DURATION = "Duration";
    private static InheritableThreadLocal<ReportPage> firstReportBank = new InheritableThreadLocal<>();
    private static InheritableThreadLocal<AtomicInteger> pageIndexBank = new InheritableThreadLocal<>();
    private static ThreadLocal<ReportPage> currentReportPageBank = new ThreadLocal<>();

    public static String newReport(String reportName) {
        newReport();
        if (!StringUtils.isEmpty(reportName)) {
            addHeader(REPORT_HEADER_SUMMARY_NAME, reportName);
        }
        return LogUtil.alignCenter("[Summary: " + reportName + "]");
    }

    private static void newReport() {
        pageIndexBank.remove();
        firstReportBank.set(new ReportPage(getPageName()));
        currentReportPageBank.set(getFirstReportPage());
    }

    public static void setCurrentPage(ReportPage reportPage) {
        currentReportPageBank.set(reportPage);
    }

    static ReportStep getLatestReportStep() {
        ReportPage currentReportPage = getCurrentReportPage();
        if (currentReportPage == null) {
            return null;
        }
        return currentReportPage.getLatestReportStep();
    }

    public static ReportPage createPage() {
        ReportPage currentReportPage = getCurrentReportPage();
        if (currentReportPage == null) {
            return null;
        }
        ReportPage reportPage = new ReportPage(getPageName());
        currentReportPage.insert(reportPage);
        return reportPage;
    }

    /**
     * Print the summary table
     *
     * @return
     */
    public static String summary() {
        ReportPage reportPage = getFirstReportPage();
        if (reportPage == null) {
            return StringUtils.EMPTY;
        }
        reportPage.updateLastestReportStepConsumingTime();
        if (reportPage.isSummarized()) {
            return StringUtils.EMPTY;
        }
        reportPage.setSummarized(true);
        Map<String, String> headers = reportPage.getHeaders();
        headers.put(REPORT_HEADER_START_TIME, LogUtil.formatDate(reportPage.getStartTime()));
        headers.put(REPORT_HEADER_END_TIME, LogUtil.formatDate(System.currentTimeMillis()));
        int duration = Long.valueOf(System.currentTimeMillis() - reportPage.getStartTime()).intValue();
        headers.put(REPORT_HEADER_DURATION, LogUtil.secondToTime(duration / 1000));
        return ReportRender.render(reportPage, headers);
    }

    /**
     * Add a pair of description in the title part of summary table
     *
     * @param key
     * @param value
     */
    public static void addHeader(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }
        ReportPage firstReportPage = getFirstReportPage();
        if (firstReportPage != null) {
            firstReportPage.getHeaders().put(key, value);
        }
    }

    /**
     * Create a new section
     *
     * @param sectionName
     */
    public static String section(String sectionName) {
        ReportPage currentReportPage = getCurrentReportPage();
        if (currentReportPage == null) {
            newReport("MySummary");
        }
        ReportSection newSection = getCurrentReportPage().newSection(sectionName);
        return newSection.getNo() + "- [" + sectionName + "]";
    }

    /**
     * Create a new step
     *
     * @param stepDescription
     */
    public static String step(String stepDescription) {
        ReportPage currentReportPage = getCurrentReportPage();
        if (currentReportPage == null) {
            newReport("MySummary");
        }
        ReportStep newStep = getCurrentReportPage().newStep(stepDescription);
        return LogUtil.alignCenter(newStep.getNo() + " " + stepDescription);
    }

    /**
     * Set "X" to the result of current step
     *
     * @param failMessage A error message which will be showed in remark column in summary table
     */
    public static String fail(String failMessage) {
        ReportStep latestReportStep = LogReport.getLatestReportStep();
        if (latestReportStep != null) {
            latestReportStep.setFailMessage("[ERROR] " + failMessage);
            latestReportStep.setResult("X");
        }
        return failMessage;
    }

    /**
     * This remark message will be added to current step and print as a normal log,
     * if there is no step yet, it will only print this message.
     *
     * @param remark A message which will be showed in remark column in summary table
     */
    public static String remark(String remark) {
        ReportStep latestReportStep = getCurrentReportPage().getLatestReportStep();
        if (latestReportStep != null && !latestReportStep.isFailed()) {
            latestReportStep.appendRemark(remark);
        }
        return remark;
    }

    /**
     * when report is empty, return true
     *
     * @return
     */
    public static boolean isReportEmpty() {
        return getCurrentReportPage() == null || getCurrentReportPage().getLatestReportStep() == null;
    }

    private static ReportPage getFirstReportPage() {
        return firstReportBank.get();
    }

    private static ReportPage getCurrentReportPage() {
        return currentReportPageBank.get();
    }

    private static String getPageName() {
        AtomicInteger pageIndex = pageIndexBank.get();
        if (pageIndex == null) {
            pageIndex = new AtomicInteger(0);
            pageIndexBank.set(pageIndex);
        }
        return " T" + pageIndexBank.get().incrementAndGet();
    }

}

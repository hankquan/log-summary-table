package com.github.howaric.log.summary.decorator;

import com.github.howaric.log.summary.core.LogReport;
import com.github.howaric.log.summary.core.ReportPage;
import com.github.howaric.log.summary.core.ReportStep;
import org.springframework.core.task.TaskDecorator;

public class LogSummaryTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        ReportPage reportPage = LogReport.createPage();
        return () -> {
            try {
                if (reportPage != null) {
                    LogReport.setCurrentPage(reportPage);
                }
                runnable.run();
            } finally {
                if (reportPage != null) {
                    ReportStep latestReportStep = reportPage.getLatestReportStep();
                    if (latestReportStep != null) {
                        latestReportStep.updateConsumingTime();
                    }
                }
            }
        };
    }

}

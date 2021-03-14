package com.github.howaric.log.summary.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;

public abstract class MdcReportRunnable extends ReportRunnable {

    private static final Logger logger = LoggerFactory.getLogger(MdcReportRunnable.class);
    private final Map mdcContext = MDC.getCopyOfContextMap();

    @Override
    public final void runReport() {
        if (mdcContext != null) {
            MDC.setContextMap(mdcContext);
        }
        try {
            runWithMdc();
        } catch (Exception e) {
            logger.error("Found error in MdcRunnable framework", e);
        } finally {
            logger.info("Thread job has been finished, returns this thread to thread pool!");
            MDC.clear();
        }
    }

    protected abstract void runWithMdc();

}

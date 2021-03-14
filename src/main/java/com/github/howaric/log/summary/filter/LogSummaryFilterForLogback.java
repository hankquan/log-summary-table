package com.github.howaric.log.summary.filter;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.github.howaric.log.summary.core.LogReport;
import com.github.howaric.log.summary.util.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.Field;

public class LogSummaryFilterForLogback extends Filter {

    private static final String MESSAGE = "message";
    private static final String HEADER_DETAIL_SEPARATOR = "=";
    private static final String MULTI_HEADER_SEPARATOR = ",";

    private interface KeyWord {
        String TITLE = "#title";
        String HEADER = "#header";
        String SECTION = "#section";
        String STEP = "#step";
        String FAIL = "#fail";
        String REMARK = "#remark";
        String SUMMARY = "#summary";
    }

    @Override
    public FilterReply decide(Object event) {
        if (event instanceof LoggingEvent) {
            LoggingEvent loggingEvent = (LoggingEvent) event;
            String message = loggingEvent.getMessage();
            Object[] argumentArray = loggingEvent.getArgumentArray();
            if (argumentArray != null) {
                message = MessageFormatter.arrayFormat(message, argumentArray).getMessage();
            }
            if (message.startsWith(KeyWord.TITLE)) {
                reWriteMessage(loggingEvent, LogReport.newReport(message.substring(KeyWord.TITLE.length()).trim()));
            }
            if (message.startsWith(KeyWord.HEADER)) {
                String headers = message.substring(KeyWord.HEADER.length()).trim();
                extractHeaderInfo(headers);
                return FilterReply.DENY;
            }
            if (message.startsWith(KeyWord.SECTION)) {
                reWriteMessage(loggingEvent, LogReport.section(message.substring(KeyWord.SECTION.length()).trim()));
            }
            if (message.startsWith(KeyWord.STEP)) {
                reWriteMessage(loggingEvent, LogReport.step(message.substring(KeyWord.STEP.length()).trim()));
            }
            if (message.startsWith(KeyWord.FAIL)) {
                if (!LogReport.isReportEmpty()) {
                    reWriteMessage(loggingEvent, LogReport.fail(message.substring(KeyWord.FAIL.length()).trim()));
                }
            }
            if (message.startsWith(KeyWord.REMARK)) {
                if (!LogReport.isReportEmpty()) {
                    reWriteMessage(loggingEvent, LogReport.remark(message.substring(KeyWord.REMARK.length()).trim()));
                }
            }
            if (message.equals(KeyWord.SUMMARY)) {
                if (LogReport.isReportEmpty()) {
                    return FilterReply.DENY;
                }
                reWriteMessage(loggingEvent, LogReport.summary());
            }
        }
        return FilterReply.ACCEPT;
    }

    private void extractHeaderInfo(String headers) {
        if (!StringUtils.isEmpty(headers) && headers.contains(HEADER_DETAIL_SEPARATOR)) {
            String[] headerArray = headers.split(MULTI_HEADER_SEPARATOR);
            for (String header : headerArray) {
                String[] headerDetail = header.split(HEADER_DETAIL_SEPARATOR);
                LogReport.addHeader(headerDetail[0], headerDetail[1]);
            }
        }
    }

    private void reWriteMessage(LoggingEvent loggingEvent, String newMessage) {
        try {
            Field messageField = LoggingEvent.class.getDeclaredField(MESSAGE);
            if (messageField != null) {
                messageField.setAccessible(true);
                messageField.set(loggingEvent, newMessage);
            }
        } catch (Exception e) {
            System.err.println("Error in LogSummaryFilterForLogback:" + e.getMessage());
        }
    }

}

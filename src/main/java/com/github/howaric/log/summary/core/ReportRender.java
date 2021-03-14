package com.github.howaric.log.summary.core;

import com.github.howaric.log.summary.util.LogUtil;
import com.inamik.utils.SimpleTableFormatter;
import com.inamik.utils.TableFormatter;

import java.util.List;
import java.util.Map;

public class ReportRender {

    public static final int REMARK_MAX_WIDTH = 60;

    public static String render(ReportPage reportPage, Map<String, String> headMap) {
        // generate head
        String heads = LogUtil.fillMapInBox(headMap);
        // generate table
        TableFormatter summaryTable = new SimpleTableFormatter(true).nextRow()
                .nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(" No. ")
                .nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(" ACTION ")
                .nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(" RESULT ")
                .nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(" REMARKS ")
                .nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(" TIMESTAMP ")
                .nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(" DURATION ");

        create(summaryTable, reportPage.getReportInfos());
        String tableString = getTableAsString(summaryTable);
        return "-\n" + heads + "\n" + tableString;
    }

    private static void create(TableFormatter summaryTable, List<ReportInfo> reportInfos) {
        for (ReportInfo reportInfo : reportInfos) {
            if (reportInfo instanceof ReportPage) {
                ReportPage page = (ReportPage) reportInfo;
                List<ReportInfo> subReportInfos = page.getReportInfos();
                create(summaryTable, subReportInfos);
            } else {
                ReportLine reportLine = (ReportLine) reportInfo;
                summaryTable.nextRow();
                summaryTable.nextCell(TableFormatter.ALIGN_LEFT, TableFormatter.VALIGN_TOP);
                summaryTable.addLine(reportLine.getNo() + " ");
                summaryTable.nextCell(TableFormatter.ALIGN_LEFT, TableFormatter.VALIGN_TOP);
                summaryTable.addLine(" " + reportLine.getAction() + " ");
                summaryTable.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_TOP);
                summaryTable.addLine(reportLine.getResult());

                summaryTable.nextCell(TableFormatter.ALIGN_LEFT, TableFormatter.VALIGN_CENTER);
                String remarks = reportLine.getRemarks();
                if (remarks.length() > REMARK_MAX_WIDTH) {
                    while (remarks.length() > REMARK_MAX_WIDTH) {
                        String line = remarks.substring(0, REMARK_MAX_WIDTH - 1) + "- ";
                        remarks = remarks.substring(REMARK_MAX_WIDTH - 1);
                        summaryTable.addLine(" " + line + " ");
                    }
                    summaryTable.addLine(" " + remarks + " ");
                } else {
                    summaryTable.addLine(" " + remarks + " ");
                }

                summaryTable.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_TOP);
                summaryTable.addLine(" " + LogUtil.formatDate(reportLine.getCreateTime()) + " ");

                summaryTable.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_TOP);
                summaryTable.addLine(" " + reportLine.getTime() + " ");
            }
        }
    }

    private static String getTableAsString(TableFormatter table) {
        StringBuilder strBuilder = new StringBuilder();
        for (String summaryLine : table.getFormattedTable()) {
            strBuilder.append(summaryLine);
            strBuilder.append("\n");
        }
        return strBuilder.toString();
    }

}

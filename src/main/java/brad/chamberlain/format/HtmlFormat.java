package brad.chamberlain.format;

import brad.chamberlain.datamodel.Employee;
import brad.chamberlain.report.Report;
import brad.chamberlain.report.ReportDetail;
import brad.chamberlain.report.ReportSummary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Month;
import java.util.Map;

public class HtmlFormat implements Formatter {
    private String reportPath;
    @Override
    public void writeReportFile(Report report, String outputPath) {
        this.reportPath = outputPath;
        try
        {
            Files.write(Paths.get(outputPath), formatReportOutput(report).getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private String formatReportOutput(Report report)
    {
        StringBuilder reportData = new StringBuilder("<HTML><HEAD><title>REPORT FOR ").append(report.reportYear).append("</title></HEAD><BODY>");
        reportData.append("<H1>Cell phone usage report for ").append(report.reportYear).append("</H1>");
        reportData.append("<hr style=\"border-top: 2px solid black\"/>");
        reportData.append(buildReportSummary(report.getReportSummary()));
        reportData.append("<hr style=\"border-top: 2px solid black\"/>");
        reportData.append(buildReportDetail(report.getReportDetails()));
        reportData.append("</BODY></HTML>");
        return reportData.toString();
    }

    private StringBuilder buildReportSummary(ReportSummary reportSummary)
    {
        StringBuilder summary = new StringBuilder("<h2>Summary</h2><table>")
                .append("<tr><th style=\"border-right: 1px solid black;\">Report Run Date</th><th style=\"border-right: 1px solid black;\">Number of Phones</th><th style=\"border-right: 1px solid black;\">Total Minutes</th><th style=\"border-right: 1px solid black;\">Total Data</th><th style=\"border-right: 1px solid black;\">Average Minutes</th><th style=\"border-right: 1px solid black;\">Average Data</th></tr>")
                .append("<tr><td style=\"border-right: 1px solid black;\">").append(reportSummary.getReportDate().toString()).append("</td><td style=\"border-right: 1px solid black;\">")
                .append(reportSummary.getNumberOfPhones()).append("</td><td style=\"border-right: 1px solid black;\">")
                .append(reportSummary.getTotalMinutes()).append("</td><td style=\"border-right: 1px solid black;\">")
                .append(reportSummary.getTotalDataUsed()).append("</td><td style=\"border-right: 1px solid black;\">")
                .append(reportSummary.getAverageMinutes()).append("</td><td style=\"border-right: 1px solid black;\">")
                .append(reportSummary.getAverageData()).append("</td></tr></table>");
        return summary;
    }

    private StringBuilder buildReportDetail(Map<Employee, ReportDetail> employeeMap)
    {
        StringBuilder detail = new StringBuilder("<h2>Details</h2><table>");
        for(Employee employee : employeeMap.keySet()) {
            detail.append("<tr><td colspan='100%'><hr style=\"border-top: 1px solid grey;\"/></td></tr>");
            detail.append("<tr><th>Employee Id</th><th>Employee Name</th><th>Model</th><th>Purchase Date</th></tr>")
                    .append("<tr><td style=\"text-align:center\">").append(employee.getEmployeeId()).append("</td><td style=\"text-align:center\">")
                    .append(employee.getEmployeeName()).append("</td><td style=\"text-align:center\">").append(employee.getModel())
                    .append("</td><td style=\"text-align:center\">").append(employee.getPurchaseDate()).append("</td></tr>");
            detail.append("<tr><td colspan='100%'><table>");
            detail.append("<tr><td></td><td colspan='100%'><hr style=\"border-top: 1px solid grey;\"/></td></tr>");
            detail.append("<tr><td></td>");
            for(Month month : Month.values())
            {
                detail.append("<td style=\"border-right: 1px solid black;\">").append(month).append("</td>");
            }
            detail.append("</tr><tr><td style=\"border-right: 1px solid black;\">Minutes</td>");
            ReportDetail reportDetail = employeeMap.get(employee);
            Map<Month, Integer> minutes = reportDetail.getMinutes();
            for(Month month : Month.values())
            {
                detail.append("<td style=\"border-right: 1px solid black;text-align:center\">").append(minutes.get(month)).append("</td>");
            }
            Map<Month, Double> dataUses = reportDetail.getData();
            detail.append("</tr><tr><td style=\"border-right: 1px solid black;\">Data</td>");
            for(Month month : Month.values())
            {
                detail.append("<td style=\"border-right: 1px solid black;text-align:center\">").append(dataUses.get(month)).append("</td>");
            }
            detail.append("</tr></table></td>");
        }
        detail.append("</tr></table>");
        return detail;
    }

    @Override
    public String getReportPath() {
        return this.reportPath;
    }
}

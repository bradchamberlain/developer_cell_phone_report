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

public class FileFormat implements Formatter{

    /**
     * Output of this report should look something like this:
     *
     * Cell phone usage report for <YEAR>
     * ------------------------------------------------------------------------------------------------------
     *
     * Report Run Date   Number of Phones  Total Minutes     Total Data        Average Minutes   Average Data
     *
     * ------------------------------------------------------------------------------------------------------
     *
     * Employee Id  Employee Name     Model              Purchase Date
     *
     * ID  Name  Model  Purchase Date
     *
     * Minutes  Jan  Feb  Mar  Apr  May  Jun  Jul  Aug  Sep  Oct  Nov  Dec
     * Data     Jan  Feb  Mar  Apr  May  Jun  Jul  Aug  Sep  Oct  Nov  Dec
     */

    private static final String LINE =           "\n------------------------------------------------------------------------------------------------------\n";
    private static final String SUMMARY_HEADER = "\nReport Run Date   Number of Phones  Total Minutes     Total Data        Average Minutes   Average Data\n";
    private static final String DETAIL_HEADER =  "\nEmployee Id  Employee Name     Model              Purchase Date\n";
    private static final String CALENDAR_HEADER = "         JAN    FEB    MAR    APR    MAY    JUN    JUL    AUG    SEP    OCT    NOV    DEC";
    private static final String MINUTES_HEADER =  "Minutes: ";
    private static final String DATA_HEADER    =  "Data:    ";

    private String reportPath;

    @Override
    public void writeReportFile(Report report, String outputPath)
    {
        reportPath = outputPath;
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
        StringBuilder reportData = new StringBuilder();
        reportData.append(getReportHeader(report));
        reportData.append(buildReportSummary(report.getReportSummary()));
        reportData.append(buildReportDetail(report.getReportDetails()));
        reportData.append("\n");
        return reportData.toString();
    }

    private StringBuilder getReportHeader(Report report)
    {
        StringBuilder reportHeader = new StringBuilder("Cell phone usage report for ")
                .append(report.reportYear)
                .append(LINE);
        return reportHeader;
    }

    private StringBuilder buildReportSummary(ReportSummary reportSummary)
    {
        StringBuilder summary = new StringBuilder(SUMMARY_HEADER);
        summary.append(rpad(reportSummary.getReportDate().toString(),18));
        summary.append(rpad(reportSummary.getNumberOfPhones(),18));
        summary.append(rpad(reportSummary.getTotalMinutes(), 18));
        summary.append(rpad(reportSummary.getTotalDataUsed(), 18));
        summary.append(rpad(reportSummary.getAverageMinutes(), 18));
        summary.append(rpad(reportSummary.getAverageData(), 18));
        return summary;
    }

    private StringBuilder buildReportDetail(Map<Employee, ReportDetail> employeeMap)
    {
        StringBuilder detail = new StringBuilder();
        for(Employee employee : employeeMap.keySet()) {
            detail.append("\n").append(LINE);
            detail.append(DETAIL_HEADER);
            detail.append(rpad(employee.getEmployeeId(), 13));
            detail.append(rpad(employee.getEmployeeName(), 18));
            detail.append(rpad(employee.getModel(), 19));
            detail.append(employee.getPurchaseDate().toString()).append("\n\n").append(CALENDAR_HEADER).append("\n").append(MINUTES_HEADER);
            ReportDetail reportDetail = employeeMap.get(employee);
            Map<Month, Integer> minutes = reportDetail.getMinutes();
            for(Month month : Month.values())
            {
                detail.append(rpad(minutes.get(month),7));
            }
            Map<Month, Double> dataUses = reportDetail.getData();
            detail.append("\n").append(DATA_HEADER);
            for(Month month : Month.values())
            {
                detail.append(rpad( dataUses.get(month), 7));
            }
        }
        return detail;
    }

    private String rpad(double data, int padLength)
    {
        return rpad("" + data, padLength);
    }

    private String rpad(String data, int padLength)
    {
        while(data.length() < padLength)
        {
            data = data + " ";
        }
        return data;
    }

    @Override
    public String getReportPath() {
        return reportPath;
    }
}

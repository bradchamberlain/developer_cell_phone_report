package brad.chamberlain.report;

import brad.chamberlain.datamodel.CellPhoneUsage;
import brad.chamberlain.datamodel.Employee;
import brad.chamberlain.dataload.CellPhoneData;
import brad.chamberlain.dataload.FileCellPhoneData;
import brad.chamberlain.format.FileFormat;
import brad.chamberlain.format.Formatter;
import brad.chamberlain.print.Printer;
import org.apache.commons.lang3.StringUtils;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;


public class Report
{
    public final int reportYear;

    private CellPhoneData cellPhoneData;
    private ReportSummary reportSummary;
    private HashMap<Employee, ReportDetail> reportDetails = new HashMap<>();

    public Report(int reportYear)
    {
        this.reportYear = reportYear;
    }

    public void generateReport() throws Exception
    {
        cellPhoneData = new FileCellPhoneData();
        cellPhoneData.loadData(reportYear);
        buildReportSummary();
        buildReportDetail();
    }

    private void buildReportSummary()
    {
        reportSummary = new ReportSummary();
        cellPhoneData.getCellPhoneUsages().forEach(cellPhoneUse -> addUsageDataToSummary(cellPhoneUse));
    }

    private void addUsageDataToSummary(CellPhoneUsage cellPhoneUse)
    {
        reportSummary.addCellPhoneId(cellPhoneUse.getEmployeeId());
        reportSummary.addMinutes(cellPhoneUse.getTotalMinutes());
        reportSummary.addData(cellPhoneUse.getTotalData());
    }

    private void buildReportDetail()
    {
        cellPhoneData.getCellPhoneUsages().forEach(cellPhoneUse -> addUsageRecordToDetail(cellPhoneUse));
    }

    private void addUsageRecordToDetail(CellPhoneUsage cellPhoneUsage)
    {
        Employee employee = getEmployee(cellPhoneUsage);
        ReportDetail reportDetail = getEmployeeReportDetail(employee);
        Month usageMonth = cellPhoneUsage.getDate().getMonth();

        reportDetail.addMinutes(usageMonth, cellPhoneUsage.getTotalMinutes());
        reportDetail.addData(usageMonth, cellPhoneUsage.getTotalData());
        reportDetails.put(employee, reportDetail);
    }

    private Employee getEmployee(CellPhoneUsage cellPhoneUsage)
    {
        return cellPhoneData.getEmployees().get(cellPhoneUsage.getEmployeeId());
    }

    private ReportDetail getEmployeeReportDetail(Employee employee)
    {
        ReportDetail reportDetail = reportDetails.get(employee);
        if(reportDetail == null)
        {
            reportDetail = new ReportDetail();
        }
        return reportDetail;
    }

    public ReportSummary getReportSummary()
    {
        return reportSummary;
    }

    public Map<Employee, ReportDetail> getReportDetails()
    {
        return reportDetails;
    }

    public static void main(String[] args) throws Exception
    {
        if(args.length < 1)
        {
            System.out.println("Usage: Report <number: year> <path: path_to_local_file> <optional: don't print if true>");
        }
        else
        {
            System.out.println("Building report for " + args[0]);
            Report report = new Report(Integer.parseInt(args[0]));
            report.generateReport();
            System.out.println("Write report to file " + args[1]);
            Formatter format = new FileFormat();
            format.writeReportFile(report, args[1]);
            if(args.length < 3 || !"true".equals(args[2]))
            {
                System.out.println("Sending file to printer");
                Printer.print(format.getReportPath());
            }
        }
    }
}

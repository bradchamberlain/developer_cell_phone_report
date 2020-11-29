package brad.chamberlain.report;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ReportSummary
{
    private static final String DOUBLE_FORMAT = "%.2f";

    private final LocalDate reportDate = LocalDate.now();
    private final Set<Integer> cellPhoneIds = new HashSet<>();

    private int totalMinutes = 0;
    private int totalDataUsed = 0;

    public void addCellPhoneId(int cellPhoneId)
    {
        this.cellPhoneIds.add(cellPhoneId);
    }

    public void addMinutes(int minutes)
    {
        if(minutes > 0) {
            this.totalMinutes += minutes;
        }
    }
    public String getTotalMinutes()
    {
        return "" + this.totalMinutes;
    }
    public String getAverageMinutes()
    {
        double totalMinutes = this.totalMinutes;
        return String.format(DOUBLE_FORMAT,(totalMinutes / cellPhoneIds.size()));
    }

    public void addData(Double dataUsed)
    {
        if(dataUsed > 0)
        {
            this.totalDataUsed += (int)(dataUsed * 100);
        }
    }

    public String getTotalDataUsed()
    {
        return String.format(DOUBLE_FORMAT,normalizeIntBackToDouble(totalDataUsed));
    }

    public String getAverageData()
    {
        double d = normalizeIntBackToDouble(totalDataUsed/cellPhoneIds.size());
        return String.format(DOUBLE_FORMAT,d);
    }

    private double normalizeIntBackToDouble(int totalDataUsed)
    {
        return (double) totalDataUsed / 100;
    }

    public String getNumberOfPhones()
    {
        return "" + this.cellPhoneIds.size();
    }

    public LocalDate getReportDate()
    {
        return this.reportDate;
    }
}

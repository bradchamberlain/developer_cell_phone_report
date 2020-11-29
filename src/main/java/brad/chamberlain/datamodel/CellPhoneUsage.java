package brad.chamberlain.datamodel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CellPhoneUsage
{
    private static final DateTimeFormatter MONTH_SLASH_DATE_SLASH_YEAR = DateTimeFormatter.ofPattern("M/d/y");

    private int employeeId;
    private LocalDate date;
    private int totalMinutes;
    private double totalData;

    public CellPhoneUsage(String input)
    {
        String[] values = input.split(",");
        setEmployeeId(values[0]);
        setDate(values[1]);
        setTotalMinutes(values[2]);
        setTotalData(values[3]);
    }

    public int getEmployeeId()
    {
        return employeeId;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public int getTotalMinutes()
    {
        return totalMinutes;
    }

    public double getTotalData()
    {
        return totalData;
    }

    public void setEmployeeId(String employeeId)

    {
        this.employeeId = Integer.parseInt(employeeId);
    }

    public void setDate(String format_MMDDYYYY_with_Slashes)
    {
        this.date = LocalDate.parse(format_MMDDYYYY_with_Slashes, MONTH_SLASH_DATE_SLASH_YEAR);;
    }

    public void setTotalMinutes(String totalMinutes)
    {
        this.totalMinutes = Integer.parseInt(totalMinutes);
    }

    public void setTotalData(String totalData)
    {
        this.totalData = Double.parseDouble(totalData);
    }
}

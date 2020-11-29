package brad.chamberlain.datamodel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Employee
{

    private int employeeId;
    private String employeeName;
    private LocalDate purchaseDate;
    private String model;

    public Employee()
    {
    }

    public Employee(String input)
    {
        String[] values = input.split(",");
        setEmployeeId(values[0]);
        setEmployeeName(values[1]);
        setPurchaseDate(values[2]);
        setModel(values[3]);
    }

    public int getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(String employeeId)
    {
        this.employeeId = Integer.parseInt(employeeId);
    }

    public String getEmployeeName()
    {
        return employeeName;
    }

    public void setEmployeeName(String employeeName)
    {
        this.employeeName = employeeName;
    }

    public LocalDate getPurchaseDate()
    {
        return purchaseDate;
    }

    public void setPurchaseDate(String formatYYYYMMDD )
    {
        this.purchaseDate = LocalDate.parse(formatYYYYMMDD, DateTimeFormatter.BASIC_ISO_DATE);
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(employeeId);
    }
}

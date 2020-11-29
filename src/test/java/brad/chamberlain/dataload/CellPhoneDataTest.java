package brad.chamberlain.dataload;

import brad.chamberlain.datamodel.CellPhoneUsage;
import brad.chamberlain.datamodel.Employee;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CellPhoneDataTest
{

    private final CellPhoneData cellPhoneData = new FileCellPhoneData();

    public CellPhoneDataTest() throws IOException {
        cellPhoneData.loadData(2016);
    }

    @Test
    public void loadDataFromFileTest()
    {
        assertEquals(9, cellPhoneData.getEmployees().size());
        Employee employee = cellPhoneData.getEmployees().get(1903);
        assertEquals(1903, employee.getEmployeeId());
        assertEquals("Stevie Griffin", employee.getEmployeeName());
        assertEquals("2015-01-01", employee.getPurchaseDate().toString());
        assertEquals("Samsung Galaxy 6", employee.getModel());
        assertEquals(6, cellPhoneData.getCellPhoneUsages().size());
        CellPhoneUsage cellPhoneUsage = cellPhoneData.getCellPhoneUsages().get(0);
        assertEquals(1906, cellPhoneUsage.getEmployeeId());
        assertEquals("2016-11-22", cellPhoneUsage.getDate().toString());
        assertEquals(6, cellPhoneUsage.getTotalMinutes());
        assertEquals(1.1, cellPhoneUsage.getTotalData());
    }
}

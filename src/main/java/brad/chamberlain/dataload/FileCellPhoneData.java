package brad.chamberlain.dataload;

import brad.chamberlain.datamodel.CellPhoneUsage;
import brad.chamberlain.datamodel.Employee;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileCellPhoneData implements CellPhoneData
{

    private static final String CELL_PHONE_DATA_FILE_PATH = "data/CellPhone.csv";
    private static final String CELL_PHONE_USAGE_BY_MONTH_FILE_PATH = "data/CellPhoneUsageByMonth.csv";

    Map<Integer, Employee> employees;
    List<CellPhoneUsage> cellPhoneUsages;

    public FileCellPhoneData()
    {
        super();
        verifyDataFilesExist();
    }

    private void verifyDataFilesExist()
    {
        try
        {
            checkFileExists(CELL_PHONE_DATA_FILE_PATH);
            checkFileExists(CELL_PHONE_USAGE_BY_MONTH_FILE_PATH);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private void checkFileExists(String fileToCheck) throws FileNotFoundException
    {
        File f = new File(fileToCheck);
        if (!f.exists())
        {
            throw new FileNotFoundException("Cannot continue, file expected but not found at " + fileToCheck);
        }
    }

    @Override
    public void loadData(int year) throws IOException
    {
        employees = new HashMap<>();
        cellPhoneUsages = new ArrayList<>();
        loadEmployeeDataFromFile();
        loadUsageDataFromFile(year);
    }


    private void loadEmployeeDataFromFile() throws IOException
    {
        List<String> cellPhoneDataLines = readLinesFromFile(CELL_PHONE_DATA_FILE_PATH);
        cellPhoneDataLines.forEach(line -> addEmployeeFromInput(line));
    }

    private void addEmployeeFromInput(String lineInput)
    {
        if(!isHeaderLine(lineInput))
        {
            Employee employee = new Employee(lineInput);
            employees.put(employee.getEmployeeId(), employee);
        }
    }

    private void loadUsageDataFromFile(int year) throws IOException
    {
        List<String> usageDataLines = readLinesFromFile(CELL_PHONE_USAGE_BY_MONTH_FILE_PATH);
        usageDataLines.forEach(line -> addCellUsageIntoCollection(line, year));
    }

    private List<String> readLinesFromFile(String path) throws IOException
    {
        return Files.readAllLines(Paths.get(path));
    }

    private void addCellUsageIntoCollection(String lineInput, int year)
    {
        if(!isHeaderLine(lineInput))
        {
            CellPhoneUsage cellPhoneUsage = new CellPhoneUsage(lineInput);
            if(cellPhoneUsage.getDate().getYear() == year) {
                cellPhoneUsages.add(new CellPhoneUsage(lineInput));
            }
        }
    }

    private boolean isHeaderLine(String lineInput)
    {
        String[] vals = lineInput.split(",");
        return vals.length < 2 || !NumberUtils.isParsable(vals[0]);
    }

    @Override
    public Map<Integer, Employee> getEmployees()
    {
        return employees;
    }

    @Override
    public List<CellPhoneUsage> getCellPhoneUsages()
    {
        return cellPhoneUsages;
    }
}

package brad.chamberlain.dataload;

import brad.chamberlain.datamodel.CellPhoneUsage;
import brad.chamberlain.datamodel.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CellPhoneData
{

    public Map<Integer, Employee> getEmployees();

    public List<CellPhoneUsage> getCellPhoneUsages();

    public void loadData(int year) throws IOException;
}

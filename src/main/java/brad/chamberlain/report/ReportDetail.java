package brad.chamberlain.report;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class ReportDetail
{

    private final Map<Month, Integer> minutes = new HashMap<>();
    private final Map<Month, Integer> data  = new HashMap<>();

    public ReportDetail()
    {
        for(Month month : Month.values())
        {
            minutes.put(month, 0);
            data.put(month, 0);
        }
    }

    public void addMinutes(Month month, int minutesToAdd)
    {
        int currentMinutes = minutes.get(month);
        minutes.put(month,currentMinutes + minutesToAdd);
    }

    public void addData(Month month, double dataUseToAdd)
    {
        int currentData = data.get(month);
        data.put(month,  currentData + (int)(dataUseToAdd * 100));
    }

    public Map<Month, Integer> getMinutes()
    {
        return minutes;
    }

    public Map<Month, Double> getData()
    {
        Map<Month, Double> convertedValues = new HashMap<>();
        for (Month month : data.keySet()) {
            int monthDataUse = (data.get(month));
            convertedValues.put(month, normalizeIntBackToDouble(monthDataUse));
        }
        return convertedValues;
    }

    private double normalizeIntBackToDouble(int monthDataUse)
    {
        return (double) monthDataUse / 100;
    }
}

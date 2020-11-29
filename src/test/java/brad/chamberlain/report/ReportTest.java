package brad.chamberlain.report;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ReportTest {

    private static final int YEAR = 2016;

    @Test
    public void generateReportSummaryTests() throws Exception {
        Report report = setUpReport();
        assertNotNull(report.getReportSummary());
        ReportSummary reportSummary = report.getReportSummary();
        assertEquals(LocalDate.now(), reportSummary.getReportDate());
        assertEquals("2", reportSummary.getNumberOfPhones());
        assertEquals("107", reportSummary.getTotalMinutes());
        assertEquals("20.27", reportSummary.getTotalDataUsed());
        assertEquals("53.50", reportSummary.getAverageMinutes());
        assertEquals("10.13", reportSummary.getAverageData());
    }

    public static Report setUpReport() throws Exception {
        Report report = new Report(YEAR);
        report.generateReport();
        return report;
    }

}

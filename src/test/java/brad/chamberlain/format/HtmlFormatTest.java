package brad.chamberlain.format;

import brad.chamberlain.report.ReportTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HtmlFormatTest {
    @Test
    public void testReportFileWorks() throws Exception
    {
        File outputFile = new File("test.html");
        if(outputFile.exists())
        {
            outputFile.delete();
        }
        Formatter format = new HtmlFormat();
        format.writeReportFile(ReportTest.setUpReport(), outputFile.getPath());
        assertTrue(outputFile.exists());
        assertEquals(outputFile.getPath(),format.getReportPath());
    }
}

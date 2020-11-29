package brad.chamberlain.format;

import static org.junit.jupiter.api.Assertions.*;

import brad.chamberlain.report.ReportTest;
import org.junit.jupiter.api.Test;

import java.io.File;

public class FileFormatTest {
    @Test
    public void testReportFileWorks() throws Exception
    {
        File outputFile = new File("test.output");
        if(outputFile.exists())
        {
            outputFile.delete();
        }
        FileFormat format = new FileFormat();
        format.writeReportFile(ReportTest.setUpReport(), outputFile.getPath());
        assertTrue(outputFile.exists());
        assertEquals(outputFile.getPath(),format.getReportPath());
    }
}

package brad.chamberlain.format;

import brad.chamberlain.report.Report;

public interface Formatter {
    public void writeReportFile(Report report, String outputPath);
    public String getReportPath();
}

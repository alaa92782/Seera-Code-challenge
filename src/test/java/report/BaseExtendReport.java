package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import config.ApplicationProperties;

import java.time.LocalDate;
import java.time.ZoneOffset;

public class BaseExtendReport {

    private ApplicationProperties properties;

    private ExtentHtmlReporter htmlReporter;
    private ExtentReports extent;

    public BaseExtendReport(ApplicationProperties properties, ExtentReports extent) {
        this.properties = properties;
        this.extent = extent;
        initializeReport();
    }

    public void initializeReport() {
        String reportPath = properties.getProperty("almosafer.report.path") + getReportName();
        htmlReporter = new ExtentHtmlReporter(reportPath);

        extent.attachReporter(htmlReporter);

        htmlReporter.config().setDocumentTitle("Almosafer Report");
        htmlReporter.config().setReportName("Almosafer Report Results");
        htmlReporter.config().setTheme(Theme.STANDARD);
    }

    private String getReportName() {
        LocalDate current = LocalDate.now();
        long millis = current.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();

        return "almosafer-results-report " + millis + ".html";
    }

    public ExtentReports getExtent() {
        return extent;
    }
}
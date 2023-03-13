import config.ApplicationProperties;
import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import report.BaseExtendReport;
import report.ExtendReportInstance;
import tests.AsyncApiTest;
import tests.FaresCalenderApiTest;
import tests.HotelDetailsApiTest;

@RunWith(Suite.class)
@SuiteClasses({AsyncApiTest.class, HotelDetailsApiTest.class, FaresCalenderApiTest.class})
public class AlmosaferTests {

    private static final ApplicationProperties PROPERTIES = new ApplicationProperties();
    private static BaseExtendReport report = new BaseExtendReport(PROPERTIES, ExtendReportInstance.getInstance());

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = PROPERTIES.getProperty("almosafer.base.url");
    }

    @AfterClass
    public static void tearDown() {
        report.getExtent().flush();
    }
}